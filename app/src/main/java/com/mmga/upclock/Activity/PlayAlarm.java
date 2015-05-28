package com.mmga.upclock.Activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmga.upclock.R;

/**
 * Created by mmga on 2015/5/22.
 */
public class PlayAlarm extends Activity {

    private ImageView btnGetUp;
    private ImageView imgTarget;
    private ImageView chovronUp1;
    private ImageView chovronUp2;
    private ImageView chovronUp3;
    private ImageView chovronUp4;
    private TextView textGet;
    private TextView textUp;
    private RelativeLayout arrows;


    private MediaPlayer mp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_alarm);
        btnGetUp = (ImageView) findViewById(R.id.btn_getup);
        imgTarget = (ImageView) findViewById(R.id.image_target);
        chovronUp1 = (ImageView) findViewById(R.id.chevron_up1);
        chovronUp2 = (ImageView) findViewById(R.id.chevron_up2);
        chovronUp3 = (ImageView) findViewById(R.id.chevron_up3);
        chovronUp4 = (ImageView) findViewById(R.id.chevron_up4);
        textGet = (TextView) findViewById(R.id.text_get);
        textUp = (TextView) findViewById(R.id.text_up);
        arrows = (RelativeLayout) findViewById(R.id.arrows);


//        WindowManager windowManager = getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        float screenHeight = display.getHeight();
//        float ydpi = getResources().getDisplayMetrics().ydpi;
//        float imgHeight = btnGetUp.getHeight();
//        final float INIT_Y = screenHeight-80*(ydpi/160)-imgHeight;
        final float INIT_Y = 800;
        btnGetUp.setTranslationY(INIT_Y);

//        chovronUpAnim();

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

//        mp = MediaPlayer.create(this, R.raw.clock);
//        mp.setLooping(true);
//        mp.start();

        btnGetUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        btnGetUp.setY(event.getRawY() - event.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        textDisappear(event.getRawY(),INIT_Y);
                        arrowDisappear(event.getRawY(),INIT_Y);
                        move(event.getRawY(),INIT_Y);

                        break;
                    case MotionEvent.ACTION_UP:
                        float yTar = imgTarget.getY();
                        if (event.getRawY() >= yTar) {
                            moveBack(INIT_Y);
                        } else {
                            changeUI();
                        }
                        break;

                }
                return true;
            }
        });


    }

//    public static class InitY {
//
//        public static float getInitY(ImageView view) {
//            int[] location = new int[2];
//            view.getLocationOnScreen(location);
//            return location[1];
//        }
//    }

    private void changeUI() {
        Log.d(">>>>>>>>>>>>>>>", "changeUI");
        //TO DO
    }

//    private void chovronUpAnim() {
//        ObjectAnimator anim1 = ObjectAnimator.ofFloat(chovronUp1, "translationY", chovronUp1.getY(), chovronUp1.getY() - 15);
//        ObjectAnimator anim2 = ObjectAnimator.ofFloat(chovronUp2, "translationY", chovronUp2.getY(), chovronUp2.getY() - 15);
//        ObjectAnimator anim3 = ObjectAnimator.ofFloat(chovronUp3, "translationY", chovronUp3.getY(), chovronUp3.getY() - 15);
//        ObjectAnimator anim4 = ObjectAnimator.ofFloat(chovronUp4, "translationY", chovronUp4.getY(), chovronUp4.getY() - 15);
//        ObjectAnimator anim5 = ObjectAnimator.ofFloat(chovronUp1, "translationY", chovronUp1.getY(), chovronUp1.getY() + 15);
//        ObjectAnimator anim6 = ObjectAnimator.ofFloat(chovronUp2, "translationY", chovronUp2.getY(), chovronUp2.getY() + 15);
//        ObjectAnimator anim7 = ObjectAnimator.ofFloat(chovronUp3, "translationY", chovronUp3.getY(), chovronUp3.getY() + 15);
//        ObjectAnimator anim8 = ObjectAnimator.ofFloat(chovronUp4, "translationY", chovronUp4.getY(), chovronUp4.getY() + 15);
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.setDuration(300);
//
//        animatorSet.play(anim1);
//        animatorSet.play(anim2).with(anim5).after(anim1);
//        animatorSet.play(anim3).with(anim6).after(anim2);
//        animatorSet.play(anim4).with(anim7).after(anim3);
//        animatorSet.play(anim8).after(anim4);
//        anim8.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                chovronUpAnim();
//            }
//        });
//    }

    private void moveBack(float initY) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(btnGetUp, "translationY", initY);
        anim1.setDuration(300);
        anim1.setInterpolator(new DecelerateInterpolator());
        anim1.start();
    }

    private void move(float rawY,float initY) {
        float y1 = btnGetUp.getY();
        float yTar = imgTarget.getY();
        float fingerY = (float) (rawY - 0.5 * btnGetUp.getHeight());
//        Log.d(">>>>>>>>>", "y1="+y1);
//        Log.d(">>>>>>>>>", "yTar="+yTar);
//        Log.d(">>>>>>>>>", "yRaw="+rawY);
//        Log.d(">>>>>>>>>", "fingerY="+fingerY);
//        Log.d(">>>>>>>>>", "initY="+initY);

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



    private void arrowDisappear(float rawY, float initY) {
        float alpha = 1 - ((initY - rawY) * 2 / 100);
        arrows.setAlpha(alpha);

        float y1 = btnGetUp.getY();
        if (((y1 - rawY >= 10) && y1 >= rawY) || ((rawY - y1 >= 10) && y1 < rawY)) {
            arrows.setY((float) (rawY-0.5*btnGetUp.getHeight())/5);
        }
    }

    private void textDisappear(float rawY,float initY) {
        float y1 = btnGetUp.getY();
        float yTar = imgTarget.getY();
        float fingerY = (float) (rawY - 0.5 * btnGetUp.getHeight());

        float alpha = 1 - ((initY - y1)  / 100);
        textGet.setAlpha(alpha);
        textUp.setAlpha(alpha);

        if (fingerY >= yTar && fingerY <= initY) {
            if (((y1 - fingerY >= 10) && y1 >= fingerY) || ((fingerY - y1 >= 10) && y1 < fingerY)) {
                textGet.setTranslationY((float) (0.5 * y1-textGet.getTranslationY()));
                textUp.setTranslationY((float) (0.5 * y1-textUp.getY()));
            }
        }else if (fingerY < yTar) {
        }else if (fingerY > initY) {
        }
    }




    @Override
    protected void onDestroy() {

//        mp.stop();
//        mp.release();
        super.onDestroy();
    }


}
