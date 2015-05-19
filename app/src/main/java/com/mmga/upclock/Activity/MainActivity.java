package com.mmga.upclock.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mmga.upclock.R;
import com.mmga.upclock.Utils.DrawerItemClickListener;

/**
 * Created by mmga on 2015/5/14.
 */
public class MainActivity extends Activity implements View.OnClickListener{
    private static final int OPEN_SET_TIME_INTERFACE = 1;
    private static final int CLOSE_SET_TIME_INTERFACE = 0;
    private RelativeLayout main;
    private ImageButton btnSettings;
    private ImageButton btnSetTime;
    private TextView textTomorrow;
    private TextView textTimeHour;
    private TextView textTimeMinute;
    private TextView textSwitchState;
    private TextView textSetTime;
    private TextView textConfirmTime;

    private TextView textHourMinusOne;
    private TextView textHourAddOne;
    private TextView textMinuteMinusFive;
    private TextView textMinuteAddFive;
    final private float TIME_TRANS_DIS = 50;


    private Button mButton;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mRightDrawer;
    private ListView mDrawerList;
    private String[] listData = new String[]{"主题设置", "铃声设置","分享给朋友"};

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        loadData();
        Log.d(">>>>>>>>>", "2");
        init();
        Log.d(">>>>>>>>>", "3");
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.bkcolor);
        this.getWindow().setBackgroundDrawable(drawable);

//        右边抽屉菜单栏的ListView
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener(MainActivity.this));
//        打开抽屉菜单栏的按钮
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mRightDrawer);
            }
        });

//        打开闹钟设置界面的按钮
        textSetTime.setClickable(true);
        textSetTime.setOnClickListener(this);




//        @Override
//        public void onClick(View v) {
//            Toast.makeText(MainActivity.this, "设定时间", Toast.LENGTH_SHORT).show();
//
//            new Thread(){
//                public void run() {
//                    try {
//                        Message message = new Message();
//                        message.what = OPEN_SET_TIME_INTERFACE;
//                        handler.sendMessage(message);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }.start();
//        }

    }



    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OPEN_SET_TIME_INTERFACE:
                    Toast.makeText(MainActivity.this, "open", Toast.LENGTH_SHORT).show();
                    Resources res = getResources();
                    Drawable drawable = res.getDrawable(R.drawable.bkcolorgray);
                    MainActivity.this.getWindow().setBackgroundDrawable(drawable);
                    break;
                case CLOSE_SET_TIME_INTERFACE:
                    Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;

            }
        }

    };

    private void init() {
        main = (RelativeLayout) findViewById(R.id.main);
        btnSettings = (ImageButton) findViewById(R.id.btn_settings);
        btnSetTime = (ImageButton) findViewById(R.id.btn_set_time);
        textSetTime = (TextView) findViewById(R.id.text_set_time);
        textConfirmTime = (TextView) findViewById(R.id.text_confirm);
        textConfirmTime.setVisibility(View.INVISIBLE);
        textTomorrow = (TextView) findViewById(R.id.text_tomorrow);
        textTimeHour = (TextView) findViewById(R.id.text_time_Hour);
        textTimeMinute = (TextView) findViewById(R.id.text_time_Minute);
        textSwitchState = (TextView) findViewById(R.id.text_switch_state);
        mButton = (Button) findViewById(R.id.menuButton);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mRightDrawer = (LinearLayout) findViewById(R.id.right_drawer);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);


//        textHourMinusOne = (TextView) findViewById(R.id.text_time_hour_minus_one);
//        textHourAddOne = (TextView) findViewById(R.id.text_time_hour_add_one);
//        textMinuteMinusFive = (TextView) findViewById(R.id.text_time_minute_minus_five);
//        textMinuteAddFive = (TextView) findViewById(R.id.text_time_minute_add_five);



    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_set_time:
                openSetTimeUIAnim();

//                textConfirmTime.setVisibility(View.VISIBLE);
//                textSetTime.setVisibility(View.GONE);
                textConfirmTime.setClickable(true);
                textConfirmTime.setOnClickListener(this);
                break;
            case R.id.text_confirm:
//                saveData();
//                updateUI();
                closeSetTimeUIAnim();
                textConfirmTime.setVisibility(View.GONE);
                textSetTime.setVisibility(View.VISIBLE);
                textConfirmTime.setClickable(false);
                break;



            default:
                break;
        }
    }

//    private void updateUI() {
//        new Thread(){
//                    public void run() {
//                        try {
//                            Message message = new Message();
//                            message.what = CLOSE_SET_TIME_INTERFACE;
//                            message. =
//                            handler.sendMessage(message);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }.start();
//    }

//    private Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case OPEN_SET_TIME_INTERFACE:
//                    Toast.makeText(MainActivity.this, "open", Toast.LENGTH_SHORT).show();
//                    Resources res = getResources();
//                    Drawable drawable = res.getDrawable(R.drawable.bkcolorgray);
//                    MainActivity.this.getWindow().setBackgroundDrawable(drawable);
//                    break;
//                case CLOSE_SET_TIME_INTERFACE:
//                    Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT).show();
//                    break;
//                default:
//                    break;
//
//            }
//        }
//
//    };


    private void saveData() {

        String hour = textTimeHour.getText().toString();
        String minute = textTimeMinute.getText().toString();
        editor = pref.edit();
        editor.putString("hour", hour);
        editor.putString("minute", minute);
        editor.commit();
    }

    private void loadData() {
        pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if (pref!=null) {
            String hour = pref.getString("hour", "07");
            String minute = pref.getString("minute", "00");
            Log.d(">>>>>>>>>", "1");
//            textTimeHour.setText(hour);
//            textTimeMinute.setText(minute);
        } else {
            Toast.makeText(this,"loadData",Toast.LENGTH_SHORT).show();
        }
    }


    private void openSetTimeUIAnim() {

        final float textSetTimeCurTransY = textSetTime.getTranslationY();

        //                界面缩小
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(main, "ScaleY", 1f, 0.9f);
        anim1.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(main, "ScaleX", 1f, 0.9f);
        anim2.setInterpolator(new AccelerateInterpolator());



//                “设置”按钮消失
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(
                textSetTime, "translationY", textSetTimeCurTransY, textSetTimeCurTransY + 50);
        anim3.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(textSetTime, "alpha", 1f, 0f);
        anim4.setInterpolator(new DecelerateInterpolator());
        anim3.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                textSetTime.setVisibility(View.GONE);
            }
        });

//                “确认”按钮出现
        ObjectAnimator anim5 = ObjectAnimator.ofFloat(
                textConfirmTime, "translationY", textSetTimeCurTransY - 50, textSetTimeCurTransY);
        anim5.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator anim6 = ObjectAnimator.ofFloat(textConfirmTime, "alpha", 0.3f, 1f);
        anim6.setInterpolator(new DecelerateInterpolator());
        anim5.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                textConfirmTime.setVisibility(View.VISIBLE);
            }
        });


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
//        animatorSet.playTogether(anim1,anim2);
//        animatorSet.playSequentially(anim1,anim3);
        animatorSet.playTogether(anim1,anim2,anim3,anim4,anim5,anim6);
        animatorSet.start();
        

    }

    private void closeSetTimeUIAnim() {

        float textSetTimeCurTransY = textConfirmTime.getTranslationY();

//                界面放大
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(main, "ScaleY", 0.9f, 1f);
        anim1.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(main, "ScaleX", 0.9f, 1f);
        anim2.setInterpolator(new AccelerateInterpolator());

//                “设置”按钮出现
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(
                textSetTime, "translationY", textSetTimeCurTransY - 50, textSetTimeCurTransY);
        anim3.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(textSetTime, "alpha", 0f, 1f);
        anim4.setInterpolator(new DecelerateInterpolator());

//                “确认”按钮消失
        ObjectAnimator anim5 = ObjectAnimator.ofFloat(
                textConfirmTime, "translationY", textSetTimeCurTransY, textSetTimeCurTransY+50);
        anim5.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator anim6 = ObjectAnimator.ofFloat(textConfirmTime, "alpha", 1f, 0f);
        anim6.setInterpolator(new DecelerateInterpolator());


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
//        animatorSet.playTogether(anim1,anim2);
//        animatorSet.playSequentially(anim1,anim3);
        animatorSet.playTogether(anim1,anim2,anim3,anim4,anim5,anim6);
        animatorSet.start();

    }



}
