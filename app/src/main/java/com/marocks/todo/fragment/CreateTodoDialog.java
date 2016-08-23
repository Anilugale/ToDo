package com.marocks.todo.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.marocks.todo.MainActivity;
import com.marocks.todo.R;
import com.marocks.todo.Utile;
import com.marocks.todo.model.Todo;

public  class CreateTodoDialog {


   static MainActivity mainActivity;

    public static void showDialog(final MainActivity context, final View view){
        final View dialogView = View.inflate(context, R.layout.dialog_view, null);
        mainActivity = context;
        AlertDialog.Builder builder =  new AlertDialog.Builder(context);
        builder.setView(dialogView)
                .setCancelable(false);


        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                revealShow(dialogView, true, null,view);
            }
        });
        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealShow(dialogView, false, dialog,view);
            }
        });
        dialogView.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealShow(dialogView, false, dialog,view);
                Utile.isToDoCreated = true;
                Utile.tempTodo = new Todo();
                Utile.tempTodo.setTitle("new todo ");
                Utile.tempTodo.setDescrption(context.getString(R.string.longdsc));
                Utile.tempTodo.setPriority((int) (Math.abs(Math.random()*100)%10));
                if(mainActivity!=null){
                    mainActivity.done();
                }
            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

    }

    private static  void revealShow(View rootView, boolean reveal, final AlertDialog dialog,View fromView){
        final View view = rootView.findViewById(R.id.reveal_view);

        float maxRadius = mainActivity.getWindow().getWindowManager().getDefaultDisplay().getHeight();

        int data[] = new int[2];
        fromView.getLocationOnScreen(data);

        int w1 = data[0];
        int h1 = data[1];

        if(reveal){
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view,
                    w1 , h1, 0, maxRadius);
            revealAnimator.setDuration(500);
            view.setVisibility(View.VISIBLE);
            revealAnimator.start();

        } else {

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, w1 , h1 , maxRadius, 0);
            anim.setDuration(500);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);
                }
            });

            anim.start();
        }

    }
}