package com.marocks.todo;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.marocks.todo.adapter.ToDoAdapter;
import com.marocks.todo.fragment.CreateTodoDialog;
import com.marocks.todo.model.Todo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    RecyclerView todoList;
    ToDoAdapter  adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        todoList = (RecyclerView) findViewById(R.id.todoList);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.colorAccent});
        swipeRefreshLayout.setOnRefreshListener(this);
        todoList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ToDoAdapter(this,new ArrayList<Todo>());
        todoList.setAdapter(adapter);

        todoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx,int dy){
                super.onScrolled(recyclerView, dx, dy);

                if (dy >0) {
                    // Scroll Down
                    if (fab.isShown()) {
                        fab.hide();
                    }
                }
                else if (dy <0) {
                    // Scroll Up
                    if (!fab.isShown()) {
                        fab.show();
                    }
                }
            }
        });
    }

    public void AddTodo(View view) {
        CreateTodoDialog.showDialog(this,view);
    }

    public   void done() {
        if(Utile.isToDoCreated){

            if(Utile.tempTodo!=null){
                adapter.addItem(Utile.tempTodo);
                Utile.clearTempTODO();
            }

        }
    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        },500);
    }
}
