package com.marocks.todo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marocks.todo.MainActivity;
import com.marocks.todo.R;
import com.marocks.todo.TodoDetail;
import com.marocks.todo.Utile;
import com.marocks.todo.model.Todo;
import com.marocks.todo.ui.LoginView;
import com.marocks.todo.ui.SignUp;

import java.util.List;

/**
    Created by anil on 23/8/16.
 */
public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    Context context;
    List<Todo> dataList;

    public ToDoAdapter(Context context, List<Todo> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.todo_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Todo todo = dataList.get(position);
        holder.title.setText(todo.getTitle());
        holder.descp.setText(todo.getDescrption());
        holder.holder.setBackgroundColor(ContextCompat.getColor(context, Utile.getPriorityColor(todo.getPriority())));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,LoginView.class);
                Utile.tempTodo=todo;
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((MainActivity)context, (View)holder.holder, "holder");
                context.startActivity(intent, options.toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addItem(Todo tempTodo) {
        dataList.add(tempTodo);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        TextView title,descp;
        CardView holder;
        public ViewHolder(View itemView) {
            super(itemView);
            title =(TextView) itemView.findViewById(R.id.title);
            descp =(TextView) itemView.findViewById(R.id.desp);
            holder =(CardView) itemView.findViewById(R.id.holder);
        }
    }
}
