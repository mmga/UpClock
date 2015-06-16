package com.mmga.upclock.Activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmga.upclock.R;
import com.mmga.upclock.Service.UpClockService;
import com.mmga.upclock.Utils.SysApplication;
import com.mmga.upclock.Utils.ThemeUtil;

import java.io.IOException;

/**
 * Created by mmga on 2015/5/22.
 */
public class PlayAlarm extends Activity {

    private ImageView btnGetUp;
    private ImageView imgTarget;
    private ImageView backCircle;
    private TextView textGet;
    private TextView textCustom;
    private RelativeLayout arrows;
    private MediaPlayer mp;
    private TextView textHour,textMinute,textColon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.loadCustomTheme(this);
        setContentView(R.layout.play_alarm);
        SysApplication.getInstance().addActivity(this);

        backCircle = (ImageView) findViewById(R.id.image_back_circle);
        btnGetUp = (ImageView) findViewById(R.id.btn_getup);
        imgTarget = (ImageView) findViewById(R.id.image_target);
        textGet = (TextView) findViewById(R.id.text_get);
        arrows = (RelativeLayout) findViewById(R.id.arrows);
        textCustom = (TextView) findViewById(R.id.text_custom);
        textHour = (TextView) findViewById(R.id.pa_time_hour);
        textMinute = (TextView) findViewById(R.id.pa_time_minute);
        Time t = new Time();
        t.setToNow();
        textHour.setText(t.hour + "");
        textMinute.setText(t.minute+"");

        SharedPreferences preferences = getSharedPreferences("word", MODE_PRIVATE);
        textCustom.setText(preferences.getString("word", "睡你麻痹起来嗨"));


//        重新启动服务
        startAlarm("" + t.hour, "" + t.minute);

//        设置锁屏可用
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

//        获取音量管理器
        final AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        final int curVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.d(">>>>>>>>>>", "" + maxVolume);
        Log.d(">>>>>>>>>>", "" + curVolume);
//        调音量至max
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,maxVolume,0);

//        播放铃声
        mpStart();

//        震动
        final Vibrator vibrator = (Vibrator) PlayAlarm.this.getSystemService(Service.VIBRATOR_SERVICE);
        long[] pattern = new long[]{1000, 1000, 1000};
        vibrator.vibrate(pattern, 1);

//        触摸事件
        btnGetUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        move(event.getRawY());
                        break;
                    case MotionEvent.ACTION_UP:
                        float yTar = (float) (imgTarget.getY() + 0.5 * btnGetUp.getHeight());
//                        如果没拖到位置，就返回原位
                        if (event.getRawY() >= yTar) {
                            moveBack();
//                         如果拖到位置了，切换界面
                        } else {
                            changeUI();
                            mp.stop();
                            vibrator.cancel();
//                            定时3秒后关闭
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                    音量复原
                                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,curVolume,0);
                                    onDestroy();
                                }
                            }, 4000);
                        }
                        break;
                }
                return true;
            }
        });
    }


    //    获取屏幕高度
    private int heightPixels() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

//    获取铃声地址
    private Uri getSystemDefaultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM);
    }

//     播放铃声
    private void mpStart() {
        if (getSystemDefaultRingtoneUri() != null) {
            mp = MediaPlayer.create(PlayAlarm.this, getSystemDefaultRingtoneUri());
        } else {
            mp = MediaPlayer.create(PlayAlarm.this, R.raw.clock);
        }
        mp.setLooping(true);
        mp.setVolume(1,1);
        try {
            mp.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
    }

//    切换界面动画
    private void changeUI() {
        backCircle.setVisibility(View.VISIBLE);
        imgTarget.setVisibility(View.GONE);
        textCustom.setVisibility(View.VISIBLE);
        textGet.setVisibility(View.GONE);
        arrows.setVisibility(View.GONE);
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(backCircle, "scaleX", 1f, 80f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(backCircle, "scaleY", 1f, 80f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(btnGetUp, "scaleX", 1f, 0f);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(btnGetUp, "scaleY", 1f, 0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.playTogether(anim1, anim2,anim3,anim4);
        animatorSet.start();

    }

//    按钮归位动画
    private void moveBack() {
        float margin = getResources().getDimensionPixelSize(R.dimen.margin_bottom);
        Log.d(">>>>>>>>>>>", "marginPX= " + margin);
        Log.d(">>>>>>>>>>>", "btnHeight= " +btnGetUp.getHeight());
        float initY = heightPixels() - margin - btnGetUp.getHeight()-40;
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(btnGetUp, "Y", initY);
        anim1.setInterpolator(new DecelerateInterpolator());
        anim1.setDuration(300);
        anim1.start();
    }

//    拖动按钮的逻辑
    private void move(float rawY) {
        float margin = getResources().getDimensionPixelSize(R.dimen.margin_bottom);
        float initY = heightPixels() - margin - btnGetUp.getHeight()-20;
        float y1 = btnGetUp.getY();
        float yTar = imgTarget.getY();
        float fingerY = (float) (rawY - 0.5 * btnGetUp.getHeight());

        if (fingerY >= yTar && fingerY <= initY) {
            if (((y1 - fingerY >= 5) && y1 >= fingerY) || ((fingerY - y1 >= 5) && y1 < fingerY)) {
                btnGetUp.setY(fingerY);
            }
        } else if (fingerY < yTar) {
            btnGetUp.setY(yTar);
        } else if (fingerY > initY) {
            btnGetUp.setY(initY);
        }
    }

    private void startAlarm(String hour,String minute) {
        Intent intent = new Intent(this, UpClockService.class);
        intent.putExtra("hour", hour);
        intent.putExtra("minute", minute);
        startService(intent);
    }

    @Override
    public void onBackPressed() {
        SysApplication.getInstance().exit();
    }

    @Override
    protected void onDestroy() {
        mp.release();
        SysApplication.getInstance().exit();
    }


}
