package com.mmga.upclock.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
//    private ImageView btnSetTime;
//    private ImageView btnConfirm;
    private TextView textTomorrow;
    private TextView textTimeHour;
    private TextView textTimeMinute;
    private TextView textSwitchState;
    //    private TextView textSetTime;
//    private TextView textConfirmTime;
    private RelativeLayout setTime;
    private RelativeLayout confirmSetting;

    private ImageView hourUp;
    private ImageView hourDown;
    private ImageView minuteUp;
    private ImageView minuteDown;

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
        setTime.setClickable(true);
        setTime.setOnClickListener(this);




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
//        btnSetTime = (ImageView) findViewById(R.id.btn_set_time);
//        btnConfirm = (ImageView) findViewById(R.id.btn_confirm);
//        textSetTime = (TextView) findViewById(R.id.text_set_time);
//        textConfirmTime = (TextView) findViewById(R.id.text_confirm);
//        textConfirmTime.setVisibility(View.INVISIBLE);
        setTime = (RelativeLayout) findViewById(R.id.layout_set_time);
        confirmSetting = (RelativeLayout) findViewById(R.id.layout_confirm_time);
        textTomorrow = (TextView) findViewById(R.id.text_tomorrow);
        textTimeHour = (TextView) findViewById(R.id.text_time_Hour);
        textTimeMinute = (TextView) findViewById(R.id.text_time_Minute);
        textSwitchState = (TextView) findViewById(R.id.text_switch_state);
        mButton = (Button) findViewById(R.id.menuButton);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mRightDrawer = (LinearLayout) findViewById(R.id.right_drawer);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);

        hourUp = (ImageView) findViewById(R.id.hour_up);
        hourDown = (ImageView) findViewById(R.id.hour_down);
        minuteUp = (ImageView) findViewById(R.id.minute_up);
        minuteDown = (ImageView) findViewById(R.id.minute_down);



    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_set_time:
                confirmSetting.setVisibility(View.VISIBLE);
                hourUp.setVisibility(View.VISIBLE);
                hourDown.setVisibility(View.VISIBLE);
                minuteUp.setVisibility(View.VISIBLE);
                minuteDown.setVisibility(View.VISIBLE);

                confirmSetting.setOnClickListener(this);
                setTime.setClickable(false);
                openSetTimeUIAnim();
                break;
            case R.id.layout_confirm_time:
                saveData();
//                updateUI();
                setTime.setVisibility(View.VISIBLE);
                confirmSetting.setClickable(false);
                closeSetTimeUIAnim();
                break;
            case R.id.hour_up:
                Toast.makeText(MainActivity.this,"hourup",Toast.LENGTH_SHORT).show();
                break;
            case R.id.hour_down:
                Toast.makeText(MainActivity.this,"hourdown",Toast.LENGTH_SHORT).show();
                break;
            case R.id.minute_up:
                Toast.makeText(MainActivity.this,"minuteup",Toast.LENGTH_SHORT).show();
                break;
            case R.id.minute_down:
                Toast.makeText(MainActivity.this,"minutedown",Toast.LENGTH_SHORT).show();
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

        float textSetTimeCurTransY = setTime.getTranslationY();
        float middleY = getScreenHeight(this) * 0.5f*0.9f;
        float leftX = getScreenWidth(this) * 0.2f * 0.9f;
        float rightX = getScreenWidth(this) * 0.8f * 0.9f;
        float textHeight = textTimeHour.getHeight();
        hourUp.setX(leftX);
        hourDown.setX(leftX);
        minuteUp.setX(rightX);
        minuteDown.setX(rightX);

//                界面缩小
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(main, "ScaleY", 1f, 0.9f);
        anim1.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(main, "ScaleX", 1f, 0.9f);
        anim2.setInterpolator(new AccelerateInterpolator());



//                “设置”按钮消失
       /* ObjectAnimator anim3 = ObjectAnimator.ofFloat(
                setTime, "translationY", textSetTimeCurTransY, textSetTimeCurTransY + 80);
        anim3.setInterpolator(new DecelerateInterpolator());*/
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(setTime, "alpha", 1f, 0f);
        anim4.setInterpolator(new DecelerateInterpolator());


//                “确认”按钮出现
        ObjectAnimator anim5 = ObjectAnimator.ofFloat(
                confirmSetting, "translationY", textSetTimeCurTransY + 80, textSetTimeCurTransY);
        anim5.setInterpolator(new OvershootInterpolator());
        ObjectAnimator anim6 = ObjectAnimator.ofFloat(confirmSetting, "alpha", 0f, 1f);
        anim6.setInterpolator(new DecelerateInterpolator());


//        弹出Hour的上下箭头

        ObjectAnimator anim7 = ObjectAnimator.ofFloat(hourUp, "translationY", middleY, middleY - textHeight*0.5f);
        ObjectAnimator anim8 = ObjectAnimator.ofFloat(hourDown,"translationY", middleY, middleY + textHeight*0.5f);
        anim7.setInterpolator(new OvershootInterpolator());
        anim8.setInterpolator(new OvershootInterpolator());
        ObjectAnimator anim9 = ObjectAnimator.ofFloat(hourUp, "alpha",0f,1f);
        ObjectAnimator anim10 = ObjectAnimator.ofFloat(hourDown, "alpha",0f,1f);

//        弹出Minute的上下箭头
        ObjectAnimator anim11 = ObjectAnimator.ofFloat(minuteUp, "translationY", middleY, middleY - textHeight*0.5f);
        ObjectAnimator anim12 = ObjectAnimator.ofFloat(minuteDown,"translationY", middleY, middleY + textHeight*0.5f);
        anim11.setInterpolator(new OvershootInterpolator());
        anim12.setInterpolator(new OvershootInterpolator());
        ObjectAnimator anim13 = ObjectAnimator.ofFloat(minuteUp, "alpha",0f,1f);
        ObjectAnimator anim14 = ObjectAnimator.ofFloat(minuteDown, "alpha",0f,1f);


//              动画监听器 开始
        anim5.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                setTime.setClickable(false);
                confirmSetting.setVisibility(View.VISIBLE);
                hourUp.setVisibility(View.VISIBLE);
                hourDown.setVisibility(View.VISIBLE);
                minuteUp.setVisibility(View.VISIBLE);
                minuteDown.setVisibility(View.VISIBLE);
            }
        });

//              动画监听器 结束
        anim5.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                confirmSetting.setClickable(true);
                hourUp.setClickable(true);
                hourDown.setClickable(true);
                minuteUp.setClickable(true);
                minuteDown.setClickable(true);
            }
        });


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.playTogether(anim1,anim2,/*anim3,*/anim4,anim5,anim6,anim7,anim8,anim9,
                anim10,anim11,anim12,anim13,anim14/*,anim15,anim16,anim17,anim18*/ );
        animatorSet.start();



    }



    private void closeSetTimeUIAnim() {

        float textSetTimeCurTransY = confirmSetting.getTranslationY();
        float middleY = getScreenHeight(this) * 0.5f*0.9f;
        float leftX = getScreenWidth(this) * 0.2f*0.9f;
        float rightX = getScreenWidth(this) * 0.8f*0.9f;
        float textHeight = textTimeHour.getHeight();
        hourUp.setX(leftX);
        hourDown.setX(leftX);
        minuteUp.setX(rightX);
        minuteDown.setX(rightX);

//                界面放大
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(main, "ScaleY", 0.9f, 1f);
        anim1.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(main, "ScaleX", 0.9f, 1f);
        anim2.setInterpolator(new AccelerateInterpolator());

//                “设置”按钮出现
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(
                setTime, "translationY", textSetTimeCurTransY + 80, textSetTimeCurTransY);
        anim3.setInterpolator(new OvershootInterpolator());
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(setTime, "alpha", 0f, 1f);
        anim4.setInterpolator(new DecelerateInterpolator());

//                “确认”按钮消失
        /*ObjectAnimator anim5 = ObjectAnimator.ofFloat(
                confirmSetting, "translationY", textSetTimeCurTransY, textSetTimeCurTransY + 80);
        anim5.setInterpolator(new DecelerateInterpolator());*/
        ObjectAnimator anim6 = ObjectAnimator.ofFloat(confirmSetting, "alpha", 1f, 0f);
        anim6.setInterpolator(new DecelerateInterpolator());


//        收回Hour的上下箭头
        ObjectAnimator anim7 = ObjectAnimator.ofFloat(hourUp, "translationY", middleY - textHeight*0.5f, middleY);
        ObjectAnimator anim8 = ObjectAnimator.ofFloat(hourDown,"translationY", middleY + textHeight*0.5f, middleY);
        anim7.setInterpolator(new OvershootInterpolator());
        anim8.setInterpolator(new OvershootInterpolator());
        ObjectAnimator anim9 = ObjectAnimator.ofFloat(hourUp, "alpha",1f,0f);
        ObjectAnimator anim10 = ObjectAnimator.ofFloat(hourDown, "alpha",1f,0f);
        anim9.setInterpolator(new DecelerateInterpolator());
        anim10.setInterpolator(new DecelerateInterpolator());

//        收回Minute的上下箭头
        ObjectAnimator anim11 = ObjectAnimator.ofFloat(minuteUp, "translationY", middleY - textHeight*0.5f, middleY);
        ObjectAnimator anim12 = ObjectAnimator.ofFloat(minuteDown,"translationY", middleY + textHeight*0.5f, middleY);
        anim11.setInterpolator(new OvershootInterpolator());
        anim12.setInterpolator(new OvershootInterpolator());
        ObjectAnimator anim13 = ObjectAnimator.ofFloat(minuteUp, "alpha",1f,0f);
        ObjectAnimator anim14 = ObjectAnimator.ofFloat(minuteDown, "alpha",1f,0f);
        anim11.setInterpolator(new DecelerateInterpolator());
        anim12.setInterpolator(new DecelerateInterpolator());


//              动画监听器 开始
        anim6.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                confirmSetting.setClickable(false);
                hourUp.setClickable(false);
                hourDown.setClickable(false);
                minuteUp.setClickable(false);
                minuteDown.setClickable(false);


            }
        });
//              动画监听器 结束
        anim6.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationStart(animation);
                confirmSetting.setVisibility(View.GONE);
                setTime.setClickable(true);
                hourUp.setVisibility(View.GONE);
                hourDown.setVisibility(View.GONE);
                minuteUp.setVisibility(View.GONE);
                minuteDown.setVisibility(View.GONE);

            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.playTogether(anim1,anim2,anim3,anim4,/*anim5,*/anim6,anim7,anim8,
                anim9,anim10,anim11,anim12,anim13,anim14);
        animatorSet.start();

    }

    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

    private static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

}
