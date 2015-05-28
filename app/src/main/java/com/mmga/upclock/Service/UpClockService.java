package com.mmga.upclock.Service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.Time;

import com.mmga.upclock.Activity.MainActivity;
import com.mmga.upclock.R;
import com.mmga.upclock.Receiver.AlarmReceiver;

import java.util.Calendar;

/**
 * Created by mmga on 2015/5/22.
 */
public class UpClockService extends Service {

    private static final int INTERVAL = 1000 * 60 * 60 * 24;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        flags = START_STICKY;



        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        String hourData = intent.getStringExtra("hour");
        String minuteData = intent.getStringExtra("minute");
        int hour = Integer.parseInt(hourData);
        int minute = Integer.parseInt(minuteData);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Time t = new Time();
        t.setToNow();
        long triggerTime;
        String day;
        if (hour < t.hour || (hour == t.hour && minute <= t.minute)) {
            triggerTime = calendar.getTimeInMillis() + INTERVAL;
            day = "明天";
        } else {
            triggerTime = calendar.getTimeInMillis();
            day = "今天";
        }


        Notification notification = new Notification(R.drawable.ic_alarm, "闹铃已设定", System.currentTimeMillis());
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(this, "闹铃设定时间", day + " " + hourData + ":" + minuteData, pendingIntent);
        startForeground(1, notification);

        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, INTERVAL, pi);

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        stopForeground(true);
//
//        Intent sevice = new Intent(this, UpClockService.class);
//        this.startService(sevice);

        super.onDestroy();
    }
}
