package com.mmga.upclock.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.mmga.upclock.R;

/**
 * Created by mmga on 2015/6/5.
 */
public class Welcome extends Activity {

    private boolean isFirstIn = false;
    private static final int TIME = 1000;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
        }
    };

    private void init() {
        SharedPreferences preferences = getSharedPreferences("firstIn", MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", true);
        if (!isFirstIn) {
            mHandler.sendEmptyMessageDelayed(GO_HOME, TIME);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstIn", false);
            editor.commit();
        }
    }

    private void goHome() {
        Intent i = new Intent(Welcome.this, MainActivity.class);
        startActivity(i);
        finish();
    }
    private void goGuide() {
        Intent i = new Intent(Welcome.this, Guide.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }
}
