package com.example.buzzclock;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

public class VibrationManager {
    public static void MakeVibrate(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        Log.d("VIB:", "Vibrating!");

        if (Build.VERSION.SDK_INT >= 26) {
            int ampHigh = 255;
            int ampLow = 200;

            long[] timings = {0, 1000, 100, 1000, 100, 1000, 100, 1000};
            int[] amps = {0, ampHigh, 0, ampLow, 0, ampHigh, 0, ampLow};
            v.vibrate(VibrationEffect.createWaveform(timings, amps, -1));
        }
        else {
            v.vibrate(500);
        }
    }
}
