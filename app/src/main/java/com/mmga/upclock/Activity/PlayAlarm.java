package com.mmga.upclock.Activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Service;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
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
import com.mmga.upclock.Utils.SysApplication;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_alarm);
        SysApplication.getInstance().addActivity(this);

        backCircle = (ImageView) findViewById(R.id.image_back_circle);
        btnGetUp = (ImageView) findViewById(R.id.btn_getup);
        imgTarget = (ImageView) findViewById(R.id.image_target);
        textGet = (TextView) findViewById(R.id.text_get);
        arrows = (RelativeLayout) findViewById(R.id.arrows);
        textCustom = (TextView) findViewById(R.id.text_custom);


//        初始化控件的位置
        final float initY = (float) (0.8*heightPixels());
        btnGetUp.setTranslationY(initY);
        imgTarget.setTranslationY((float) (0.2 * heightPixels()));
        backCircle.setTranslationY((float) (0.2 * heightPixels()));


//        设置锁屏可用
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

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
                        move(event.getRawY(),initY);
                        break;
                    case MotionEvent.ACTION_UP:
                        float yTar = imgTarget.getY();
//                        如果没拖到位置，就返回原位
                        if (event.getRawY() >= yTar) {
                            moveBack(initY);
//                         如果拖到位置了，切换界面
                        } else {
                            changeUI();
                            mp.pause();
                            vibrator.cancel();
//                            定时4秒后关闭
                            TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                    onDestroy();
                                }
                            };
                            Timer timer = new Timer();
                            timer.schedule(task, 4000);
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

//     播放铃声
    private void mpStart() {
        mp = MediaPlayer.create(PlayAlarm.this, getSystemDefaultRingtoneUri());
        mp.setLooping(true);
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
        Log.d(">>>>>>>>>>>>>>>", "changeUI");
        backCircle.setVisibility(View.VISIBLE);
        imgTarget.setVisibility(View.GONE);
        textCustom.setVisibility(View.VISIBLE);
        textGet.setVisibility(View.GONE);
        arrows.setVisibility(View.GONE);
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(backCircle, "scaleX", 1f, 20f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(backCircle, "scaleY", 1f, 20f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(btnGetUp, "scaleX", 1f, 0f);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(btnGetUp, "scaleY", 1f, 0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.playTogether(anim1, anim2,anim3,anim4);
        animatorSet.start();

    }

//    按钮归位动画
    private void moveBack(float initY) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(btnGetUp, "translationY", initY);
        anim1.setInterpolator(new DecelerateInterpolator());
        anim1.setDuration(300);
        anim1.start();
    }

//    拖动按钮的逻辑
    private void move(float rawY,float initY) {
        float y1 = btnGetUp.getY();
        float yTar = imgTarget.getY();
        float fingerY = (float) (rawY - 0.5 * btnGetUp.getHeight());

        if (fingerY >= yTar && fingerY <= initY) {
            if (((y1 - fingerY >= 10) && y1 >= fingerY) || ((fingerY - y1 >= 10) && y1 < fingerY)) {
                btnGetUp.setY(fingerY);
            }
        }else if (fingerY < yTar) {
            btnGetUp.setY(yTar);
        }else if (fingerY > initY) {
            btnGetUp.setY(initY);
        }
    }

//    获取铃声地址
    private Uri getSystemDefaultRingtoneUri() {
            return RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM);
    }

    @Override
    public void onBackPressed() {
        SysApplication.getInstance().exit();
    }

    @Override
    protected void onDestroy() {
        mp.stop();
        mp.release();
        super.onDestroy();
    }


}
