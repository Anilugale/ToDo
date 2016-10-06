package com.marocks.todo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
  Created by anil on 6/10/16.
 */

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(context.getString(R.string.action))){

            Log.e("AlarmReceiver", "onReceive: ");
            NotificationUtil.showNotification(context);
        }
    }
}
