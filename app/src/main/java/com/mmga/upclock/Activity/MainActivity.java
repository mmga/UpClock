package com.mmga.upclock.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mmga.upclock.R;

/**
 * Created by mmga on 2015/5/14.
 */
public class MainActivity extends Activity {
    private ImageButton btnSettings;
    private ImageButton btnSetTime;
    private TextView textTomorrow;
    private TextView textTime;
    private TextView textSwitchState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        btnSettings = (ImageButton) findViewById(R.id.btn_settings);
        btnSetTime = (ImageButton) findViewById(R.id.btn_set_time);
        textTomorrow = (TextView) findViewById(R.id.text_tomorrow);
        textTime = (TextView) findViewById(R.id.text_time);
        textSwitchState = (TextView) findViewById(R.id.text_switch_state);


    }
}
