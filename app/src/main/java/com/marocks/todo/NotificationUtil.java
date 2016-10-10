package com.marocks.todo;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.marocks.todo.model.ToDoItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
  Created by anil on 6/10/16.
 */

public class NotificationUtil {

    public static void createNotification(String name,String id, Date date, Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context.getString(R.string.action));
        intent.putExtra("name",name);
        intent.putExtra("id",id);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context,(Integer.valueOf(id)) , intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,date==null?Calendar.getInstance().getTimeInMillis():date.getTime(), alarmIntent);
    }


    public static void showNotification(Context context, String name,String id){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle(name);
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MainActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        mBuilder.setAutoCancel(true);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(id.hashCode(), mBuilder.build());
    }

    public static void createNotificationList(ArrayList<ToDoItem> todoList, MainActivity mainActivity) {
        AlarmManager alarmManager = (AlarmManager) mainActivity.getSystemService(Context.ALARM_SERVICE);
        for (ToDoItem item: todoList) {
            if (item.getSchedule() != null) {
                Intent intent = new Intent(mainActivity.getString(R.string.action));
                intent.putExtra("name",item.getDesc());
                intent.putExtra("id",item.getId());
                alarmManager.cancel( PendingIntent.getBroadcast(mainActivity,(Integer.valueOf(item.getId())) , intent, 0));
                if(Utile.getDateParse(item.getSchedule().getDate(), item.getSchedule().getTime()).getTime()>System.currentTimeMillis()) {
                    createNotification(item.getDesc(), item.getId(), Utile.getDateParse(item.getSchedule().getDate(), item.getSchedule().getTime()), mainActivity);
                }
            }
        }


    }
}
