package com.example.buzzclock;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class AlarmService extends Service {

    private static final String CHANNEL_ID = "BuzzClock_Channel";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int num = intent.getExtras().getInt("times");
        int delay = intent.getExtras().getInt("delay");

        Calendar c = Calendar.getInstance();
        for (int n =0; n < num; ++n) {

            c = getNextInterval(c, 10);

            Log.d("MSG:", c.getTime().toString());

            Intent alarmIntent = new Intent(this, AlarmReceiver.class);
            PendingIntent pintent = PendingIntent.getBroadcast(this, n, alarmIntent,PendingIntent.FLAG_IMMUTABLE);

            AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

            AlarmManager.AlarmClockInfo alarmInfo = new AlarmManager.AlarmClockInfo(c.getTimeInMillis(), pintent);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                NotificationChannel channel = new NotificationChannel("Lyndsay", "LyndsayChannel", NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("Nothing");

                NotificationManager nman = getSystemService(NotificationManager.class);

                nman.createNotificationChannel(channel);

                Notification note = new Notification.Builder(this, "Lyndsay")
                        .setContentTitle("BuzzClock")
                        .setContentText("BuzzClock")
                        .setContentIntent(pintent)
                        .setTicker("Ticker")
                        .build();
                startForeground(n+1, note);
            }

            manager.setAlarmClock(alarmInfo, pintent);
        }
        return START_STICKY;
    }
    protected Calendar getNextInterval(Calendar currTime, int interval) {
        int lastDigit = currTime.get(Calendar.MINUTE) % 10;
        int diff = interval - lastDigit % interval;

        int add = lastDigit == 0? interval : diff;

        Calendar output = (Calendar)currTime.clone();
        output.set(Calendar.MINUTE, currTime.get(Calendar.MINUTE) + add);
        output.set(Calendar.SECOND, 0);

        return output;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
