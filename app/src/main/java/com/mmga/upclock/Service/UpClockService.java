package com.mmga.upclock.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.mmga.upclock.Receiver.AlarmReceiver;

import java.util.Calendar;

/**
 * Created by mmga on 2015/5/22.
 */
public class UpClockService extends Service {

    private long bias;
    private static final int INTERVAL = 1000 * 60 * 60 * 24;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        String hourData = intent.getStringExtra("hour");
        String minuteData = intent.getStringExtra("minute");
        int hour = Integer.parseInt(hourData);
        int minute = Integer.parseInt(minuteData);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), INTERVAL, pi);

        return super.onStartCommand(intent, flags, startId);

    }
}
