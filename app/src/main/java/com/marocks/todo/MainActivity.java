package com.marocks.todo;

import android.support.v4.widget.SwipeRefreshLayout;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        todoList = (RecyclerView) findViewById(R.id.todoList);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.colorAccent});
        swipeRefreshLayout.setOnRefreshListener(this);
        todoList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ToDoAdapter(this,new ArrayList<Todo>());
        todoList.setAdapter(adapter);
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
