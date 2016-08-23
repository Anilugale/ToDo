package com.marocks.todo;

import android.support.v4.content.ContextCompat;

import com.marocks.todo.model.Todo;

/**
  Created by anil on 23/8/16.
 */
public class Utile {
    public static boolean isToDoCreated;
    public static Todo tempTodo;

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

}
