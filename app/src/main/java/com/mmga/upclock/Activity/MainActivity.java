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
import android.support.v4.widget.DrawerLayout;
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
    private static final int LOAD_DATA = 0;
    private static final int CLOSE_SET_TIME_INTERFACE = 1;
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

    private int hourData = -1;
    private int minuteData =-1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        init();
        loadData();
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
        confirmSetting.setOnClickListener(this);
        hourUp.setOnClickListener(this);
        hourDown.setOnClickListener(this);
        minuteUp.setOnClickListener(this);
        minuteDown.setOnClickListener(this);

    }

    private void init() {
        main = (RelativeLayout) findViewById(R.id.main);
        btnSettings = (ImageButton) findViewById(R.id.btn_settings);
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

//                confirmSetting.setOnClickListener(this);
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
                addOneHour();
                break;
            case R.id.hour_down:
                Toast.makeText(MainActivity.this,"hourdown",Toast.LENGTH_SHORT).show();
                minusOneHour();
                break;
            case R.id.minute_up:
                Toast.makeText(MainActivity.this,"minuteup",Toast.LENGTH_SHORT).show();
                addFiveMinutes();
                break;
            case R.id.minute_down:
                Toast.makeText(MainActivity.this,"minutedown",Toast.LENGTH_SHORT).show();
                minusFiveMinutes();
                break;
            default:
                break;
        }
    }



    private void addOneHour() {
        int curHour = Integer.parseInt(textTimeHour.getText().toString());
        curHour ++;
        if (curHour >= 0 && curHour < 10) {
            textTimeHour.setText("0"+curHour);
        }else if (curHour >= 10 && curHour < 24) {
            textTimeHour.setText("" + curHour);
        }else if (curHour == 24) {
            textTimeHour.setText("00");
        } else {
            //时间小于零，或大于24
        }
    }

    private void minusOneHour() {
        int curHour = Integer.parseInt(textTimeHour.getText().toString());
        curHour --;
        if (curHour >= 0 && curHour < 10) {
            textTimeHour.setText("0"+curHour);
        }else if (curHour >= 10 && curHour < 24) {
            textTimeHour.setText("" + curHour);
        }else if (curHour == -1) {
            textTimeHour.setText("23");
        } else {
            //时间小于零，或大于24
        }
    }

    private void addFiveMinutes() {
        int curMinute = Integer.parseInt(textTimeMinute.getText().toString());
        curMinute = curMinute + 5;
        if (curMinute >= 0 && curMinute < 10) {
            textTimeMinute.setText("0"+curMinute);
        }else if (curMinute >= 10 && curMinute < 60) {
            textTimeMinute.setText("" + curMinute);
        }else if (curMinute == 60) {
            textTimeMinute.setText("00");
        } else {
            //时间小于零，或大于24
        }
    }

    private void minusFiveMinutes() {
        int curMinute = Integer.parseInt(textTimeMinute.getText().toString());
        curMinute = curMinute - 5;
        if (curMinute >= 0 && curMinute < 10) {
            textTimeMinute.setText("0"+curMinute);
        }else if (curMinute >= 10 && curMinute < 60) {
            textTimeMinute.setText("" + curMinute);
        }else if (curMinute == -5) {
            textTimeMinute.setText("55");
        } else {
            //时间小于零，或大于24
        }
    }




    private void saveData() {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        String hour = textTimeHour.getText().toString();
        String minute = textTimeMinute.getText().toString();
        editor.putString("hour", hour);
        editor.putString("minute", minute);
        editor.commit();
    }

    private void loadData() {
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        final String hour = pref.getString("hour", "07");
        final String minute = pref.getString("minute", "00");
        textTimeHour.setText(hour);
        textTimeMinute.setText(minute);

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
