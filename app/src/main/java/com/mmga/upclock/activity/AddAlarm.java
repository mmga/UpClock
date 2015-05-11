package com.mmga.upclock.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.mmga.upclock.R;

/**
 * Created by mmga on 2015/5/11.
 */
public class AddAlarm extends Activity {

    private TimePicker timePicker;
    private Button btnAddAlarm;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        final Time t = new Time();
        t.setToNow();
        timePicker.setCurrentHour(t.hour);
        timePicker.setCurrentMinute(t.minute);

        btnAddAlarm = (Button) findViewById(R.id.btn_save);
        btnAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_bottom);
            }
        });
    }

    /*private void addAlarmList() {
        SharedPreferences.Editor editor = getContext().getSharedPreferences(AlarmList.class.getName(), Context.MODE_PRIVATE).edit();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < adapter.getCount(); i++) {
            sb.append(adapter.getItem(i))
        }


        editor.commit();
    }*/



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_bottom);
    }
}
