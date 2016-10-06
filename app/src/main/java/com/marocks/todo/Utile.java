package com.marocks.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.marocks.todo.model.ToDoItem;

/**
  Created by anil on 23/8/16.
 */
public class Utile {



    public static boolean isToDoCreated;
    public static ToDoItem tempTodo;


    public static final String nameJson = "name";
    public static final String passwordJson = "password";
    public static final String password_confirmationJson="password_confirmation";
    public static final String domainJson="domain_name";
    public static final String emailJson="email";
    public static final String autTokenJson="auth_token";


    public static void clearTempTODO() {
         isToDoCreated= false;
         tempTodo =null;
    }

    public static int getPriorityColor(int priority){


        switch (priority) {
            case 1 :
                return  R.color.cardBG1;

            case 2 :
                return  R.color.cardBG2;
            case 3 :
                return  R.color.cardBG3;

            case 4 :
                return  R.color.cardBG4;

            case 5 :
                return  R.color.cardBG5;
            case 6 :
                return  R.color.cardBG1;

            case 7 :
                return  R.color.cardBG2;
            case 8 :
                return  R.color.cardBG3;

            case 9 :
                return  R.color.cardBG4;

            case 0 :
                return  R.color.cardBG5;

        }
        return 0;

    }


    public  static boolean isValidEmail(CharSequence target) {
        return  !TextUtils.isEmpty(target.toString().trim()) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public  static boolean isEmptyText(CharSequence target) {
        return !TextUtils.isEmpty(target.toString().trim());
    }


    public  static void showErrorDialog(Context context,int text){

        final AlertDialog.Builder dialog= new AlertDialog.Builder(context)
                .setMessage(text)
                .setTitle("")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i){
                                dialogInterface.dismiss();
                            }
                        }

                );
        dialog.show();
    }

}
