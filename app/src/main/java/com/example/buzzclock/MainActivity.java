package com.example.buzzclock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
   Button b_fourPt;
   Button b_sevenPt;
   Button b_vibTest;
   Context context;
    public void StartAlarmService(int times) {

        Intent intent = new Intent (context, AlarmService.class);
        intent.putExtra("times", times);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startForegroundService(intent);
        }
        else {
            startService(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        b_fourPt = (Button) findViewById(R.id.foutPt);
        b_fourPt.setOnClickListener(v -> StartAlarmService(4));

        b_sevenPt = (Button) findViewById(R.id.sevenPt);
        b_sevenPt.setOnClickListener(v -> StartAlarmService(7));

        b_vibTest = (Button) findViewById(R.id.vibTest);
        b_vibTest.setOnClickListener(v -> VibrationManager.MakeVibrate(context));
    }
}