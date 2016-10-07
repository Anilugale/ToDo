package com.marocks.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.marocks.todo.adapter.ToDoAdapter;
import com.marocks.todo.api.ApiUtil;
import com.marocks.todo.api.HandleResponce;
import com.marocks.todo.fragment.CreateTodoDialog;
import com.marocks.todo.model.ToDoItem;
import com.marocks.todo.ui.LoginView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener , HandleResponce {

    RecyclerView todoList;
    ToDoAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton fab;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sh = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        if (sh.getString(Utile.autTokenJson, null) != null) {
            init();
        } else {
            startActivity(new Intent(this, LoginView.class));
            finish();
        }

    }

    private void init() {
        todoList = (RecyclerView) findViewById(R.id.todoList);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.colorAccent});
        swipeRefreshLayout.setOnRefreshListener(this);
        todoList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ToDoAdapter(this, new ArrayList<ToDoItem>());
        todoList.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(todoList);
        todoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    // Scroll Down
                    if (fab.isShown()) {
                        fab.hide();
                    }
                } else if (dy < 0) {
                    // Scroll Up
                    if (!fab.isShown()) {
                        fab.show();
                    }
                }
            }
        });

        ApiUtil.jsonArrayRequest(this,ApiUtil.todos,null, Request.Method.GET);
        swipeRefreshLayout.setRefreshing(true);
    }

    public void AddTodo(View view) {
        Utile.tempTodo = null;
        CreateTodoDialog.showDialog(this, view);
    }

    public void done() {
        if (Utile.isToDoCreated) {

            if (Utile.tempTodo != null && adapter!=null) {
                adapter.addItem(Utile.tempTodo);
                Utile.clearTempTODO();

            }
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
        }
    }


    @Override
    public void onRefresh() {
        ApiUtil.jsonArrayRequest(this,ApiUtil.todos,null, Request.Method.GET);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.app_name)
                    .setMessage("Do you want to logout this account?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            logout();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setCancelable(false)
                    .create().show();
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    void logout() {
        SharedPreferences sh = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        sh.edit().putString(Utile.autTokenJson, null).apply();
        finish();
    }

    @Override
    public void onResponse(JSONObject response) {
        swipeRefreshLayout.setRefreshing(false);
        try {
            ArrayList<ToDoItem> todoList = ApiUtil.gson.fromJson(String.valueOf(response), new TypeToken<List<ToDoItem>>() {
            }.getType());

            if(adapter!=null){
                adapter.setData(todoList);
                adapter.notifyDataSetChanged();
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void onResponse(JSONArray response) {
        swipeRefreshLayout.setRefreshing(false);
        try {

            Type todoListType = new TypeToken<ArrayList<ToDoItem>>() {
            }.getType();

            ArrayList<ToDoItem> todoList = ApiUtil.gson.fromJson(String.valueOf(response), todoListType);

            if(adapter!=null){
                Collections.reverse(todoList);
                adapter.setData(todoList);
                adapter.notifyDataSetChanged();
            }

            NotificationUtil.createNotificationList(todoList,this);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(VolleyError error) {
        swipeRefreshLayout.setRefreshing(false);

        if(error!=null && error.networkResponse!=null && error.networkResponse.statusCode == 500){
            startActivity(new Intent(this,LoginView.class));
            finish();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    boolean isRealyDelete;

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        public boolean onMove(RecyclerView recyclerView,
                              RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                    final int fromPos = viewHolder.getAdapterPosition();
//                    final int toPos = viewHolder.getAdapterPosition();
//                    // move item in `fromPos` to `toPos` in adapter.
            return true;// true if moved, false otherwise
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView

            final int position = viewHolder.getLayoutPosition();
            final ToDoItem itemToDelete = adapter.getItemAt(position);
            adapter.dataList.remove(position);
            adapter.notifyItemRemoved(position);


            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "TODO is deleting", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            isRealyDelete = false;
                            adapter.dataList.add(position,itemToDelete);
                            adapter.notifyItemInserted(position);
                            Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "TODO is Revert!", Snackbar.LENGTH_SHORT);
                            snackbar1.show();
                        }
                    }).setCallback(new Snackbar.Callback() {

                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {

                            if(isRealyDelete){
                                ApiUtil.jsonRequest(MainActivity.this,ApiUtil.todos+"/"+itemToDelete.getId(),new JSONObject(), Request.Method.DELETE);
                                Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "TODO is deleted !", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                            }
                        }

                        @Override
                        public void onShown(Snackbar snackbar) {
                            isRealyDelete = true;
                        }
                    });

            snackbar.show();
        }
    };


}
