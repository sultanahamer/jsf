package com.openapp.jsf.broadcastrecievers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.openapp.jsf.activities.Home;
import com.openapp.jsf.activities.R;
import com.openapp.jsf.services.Updates;

public class BootCompletedReciever extends BroadcastReceiver {
    public BootCompletedReciever() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d("yo!!", "reboot done");
        Intent serviceIntent = new Intent(context, Updates.class);
        context.startService(serviceIntent);

        Intent tmp = new Intent(context, Home.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, tmp, 0);
        Notification n  = new NotificationCompat.Builder(context)
                .setContentTitle("Service Restarted")
                .setContentText("Subject")
                .setSmallIcon(R.drawable.icon)
                .setAutoCancel(true)
                .setContentIntent(pIntent).build();


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }
}
