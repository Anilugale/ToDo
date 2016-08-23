package com.marocks.todo;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TodoDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);
        init();
    }

    private void init() {


       ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if(Utile.tempTodo!=null){

            TextView title,desp;
            CardView holder;
            title=(TextView) findViewById(R.id.title);
            desp=(TextView) findViewById(R.id.desp);
            holder=(CardView) findViewById(R.id.holder);
            title.setText(Utile.tempTodo.getTitle());
            desp.setText(Utile.tempTodo.getDescrption());
            holder.setBackgroundColor(ContextCompat.getColor(this,Utile.getPriorityColor(Utile.tempTodo.getPriority())));

        }else{
            finish();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utile.tempTodo=null;
    }
}
