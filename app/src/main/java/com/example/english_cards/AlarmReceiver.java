package com.example.english_cards;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        try {
            Intent intent1 = new Intent(context, MainActivity.class);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(intent.getStringExtra("title"))
                    .setContentText(intent.getStringExtra("text"))
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setLights(0x0000FF, 3000, 2000)
                    .setContentIntent(pendingIntent);
            notificationManager.notify(56, builder.build());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}