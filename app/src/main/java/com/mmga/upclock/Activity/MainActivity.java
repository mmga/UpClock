package com.mmga.upclock.Activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.format.Time;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmga.upclock.R;
import com.mmga.upclock.Receiver.AlarmReceiver;
import com.mmga.upclock.Service.UpClockService;
import com.mmga.upclock.Utils.DrawerItemClickListener;
import com.mmga.upclock.Utils.SysApplication;
import com.mmga.upclock.Utils.ThemeUtil;

/**
 * Created by mmga on 2015/5/14.
 */
public class MainActivity extends Activity implements View.OnClickListener{
    private static final int LOAD_DATA = 0;
    private static final int CLOSE_SET_TIME_INTERFACE = 1;
    private RelativeLayout main;
    private ImageView btnSettings;
    private TextView textTomorrow;
    private TextView textTimeHour;
    private TextView textTimeColon;
    private TextView textTimeMinute;
    private TextView textSwitchState;
    private RelativeLayout setTime;
    private RelativeLayout confirmSetting;

    private ImageView hourUp;
    private ImageView hourDown;
    private ImageView minuteUp;
    private ImageView minuteDown;

    private DrawerLayout mDrawerLayout;
    private LinearLayout mRightDrawer;
    private ListView mDrawerList;
    private String[] listData = new String[]{"铃声设置","主题设置","意见反馈"};


    private static final int INTERVAL = 1000 * 60 * 60 * 24;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //        判断所加载的主题
        ThemeUtil.loadCustomTheme(this);
        SysApplication.getInstance().addActivity(this);

//        ImageView header = (ImageView)findViewById(R.id.nav_header);
//        header.setBackgroundResource(R.color.cyan);


        init();
        loadData();
        changeTomorrow();

//        设置背景色
//        Resources res = getResources();
//        Drawable drawable = res.getDrawable(R.drawable.bkcolor);
//        this.getWindow().setBackgroundDrawable(drawable);


//        测试用，直接打开PlayAlarm界面
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PlayAlarm.class);
                startActivity(i);
            }
        });

//        右边抽屉菜单栏的ListView
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener(MainActivity.this));
//        打开抽屉菜单栏的按钮
        btnSettings.setOnClickListener(new View.OnClickListener() {
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


    //    改变界面上“明天”“今天”的逻辑
    private void changeTomorrow() {
        Time t = new Time();
        t.setToNow();
        int h1 = t.hour;
        int m1 = t.minute;
        int h2 = Integer.parseInt((String) textTimeHour.getText());
        int m2 = Integer.parseInt((String) textTimeMinute.getText());

        if (h2 < h1 || (h2 == h1 && m2 <= m1)) {
            textTomorrow.setText("明天");
        } else {
            textTomorrow.setText("今天");
        }
    }

//    关联控件
    private void init() {
        main = (RelativeLayout) findViewById(R.id.main);
        btnSettings = (ImageView) findViewById(R.id.btn_settings);
        btnSettings.setClickable(true);
        setTime = (RelativeLayout) findViewById(R.id.layout_set_time);
        confirmSetting = (RelativeLayout) findViewById(R.id.layout_confirm_time);
        textTomorrow = (TextView) findViewById(R.id.text_tomorrow);
        textTimeColon = (TextView) findViewById(R.id.text_time_colon);
        textTimeHour = (TextView) findViewById(R.id.text_time_Hour);
        textTimeMinute = (TextView) findViewById(R.id.text_time_Minute);
        textSwitchState = (TextView) findViewById(R.id.text_switch_state);
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
//            点击“设置”按钮
            case R.id.layout_set_time:
                confirmSetting.setVisibility(View.VISIBLE);
                hourUp.setVisibility(View.VISIBLE);
                hourDown.setVisibility(View.VISIBLE);
                minuteUp.setVisibility(View.VISIBLE);
                minuteDown.setVisibility(View.VISIBLE);
                setTime.setClickable(false);
                openSetTimeUIAnim();
                break;
//            点击“确认”按钮
            case R.id.layout_confirm_time:
                saveData();
                changeTomorrow();
                setTime.setVisibility(View.VISIBLE);
                confirmSetting.setClickable(false);
                closeSetTimeUIAnim();
                break;
//            加小时
            case R.id.hour_up:
                addOneHour();
                changeTomorrow();
                break;
//            减小时
            case R.id.hour_down:
                minusOneHour();
                changeTomorrow();
                break;
//            加分钟
            case R.id.minute_up:
                addFiveMinutes();
                changeTomorrow();
                break;
//            减分钟
            case R.id.minute_down:
                minusFiveMinutes();
                changeTomorrow();
                break;
            default:
                break;
        }
    }


//       加一小时
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

//    减一小时
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

//    加5分钟
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

//    见五分钟
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



//  保存闹铃数据到SharedPreferences
    private void saveData() {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        String hour = textTimeHour.getText().toString();
        String minute = textTimeMinute.getText().toString();
        editor.putString("hour", hour);
        editor.putString("minute", minute);
        editor.commit();
//        清空闹铃服务
        cleanAlarm();
        Log.d(">>>>>>>>>>>>>", "cleanAlarm");
//        启动闹铃服务
        startAlarm(hour, minute);
        Log.d(">>>>>>>>>>>>>", "startAlarm");

    }

//    清空闹铃数据
    private void cleanAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.cancel(pi);
    }

//    启动闹铃服务
    private void startAlarm(String hour,String minute) {
        Intent intent = new Intent(this, UpClockService.class);
        intent.putExtra("hour", hour);
        intent.putExtra("minute", minute);
        startService(intent);
    }

//    主页面加载时间数据
    private void loadData() {
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        final String hour = pref.getString("hour", "07");
        final String minute = pref.getString("minute", "00");
        textTimeHour.setText(hour);
        textTimeMinute.setText(minute);


    }


//    打开设置界面的动画
    private void openSetTimeUIAnim() {

        float textSetTimeCurTransY = setTime.getTranslationY();
        float middleY = getScreenHeight(this) * 0.5f*0.9f;
        float leftX = getScreenWidth(this) * 0.2f*0.9f;
        float rightX = getScreenWidth(this) * 0.8f*0.9f;
        float textHeight = textTimeHour.getHeight();
        hourUp.setX(leftX);
        hourDown.setX(leftX);
        minuteUp.setX(rightX);
        minuteDown.setX(rightX);




//                “设置”按钮消失
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

//        背景色渐变
        ObjectAnimator anim15 = (ObjectAnimator) AnimatorInflater
                .loadAnimator(MainActivity.this, R.animator.background_color);
        anim15.setEvaluator(new ArgbEvaluator());
        anim15.setTarget(mDrawerLayout);

//        界面各组件缩小
        Animator anim16 = AnimatorInflater.loadAnimator(MainActivity.this,R.animator.scale_reduce);
        anim16.setTarget(textTimeHour);
        Animator anim17 = AnimatorInflater.loadAnimator(MainActivity.this,R.animator.scale_reduce);
        anim17.setTarget(textTimeMinute);
        Animator anim18 = AnimatorInflater.loadAnimator(MainActivity.this,R.animator.scale_reduce);
        anim18.setTarget(textTimeColon);
        Animator anim19 = AnimatorInflater.loadAnimator(MainActivity.this,R.animator.scale_reduce);
        anim19.setTarget(textTomorrow);
        Animator anim20 = AnimatorInflater.loadAnimator(MainActivity.this,R.animator.scale_reduce);
        anim20.setTarget(textSwitchState);

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
        animatorSet.playTogether(anim4,anim5,anim6,anim7,anim8,anim9,
                anim10,anim11,anim12,anim13,anim14,anim15,anim16,anim17,anim18,anim19,anim20);
        animatorSet.start();



    }



//    关闭设置界面的动画
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


//                “设置”按钮出现
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(
                setTime, "translationY", textSetTimeCurTransY + 80, textSetTimeCurTransY);
        anim3.setInterpolator(new OvershootInterpolator());
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(setTime, "alpha", 0f, 1f);
        anim4.setInterpolator(new DecelerateInterpolator());

//                “确认”按钮消失
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

//        背景色渐变
        ObjectAnimator anim15 = (ObjectAnimator) AnimatorInflater
                .loadAnimator(MainActivity.this, R.animator.background_color_reverse);
        anim15.setEvaluator(new ArgbEvaluator());
        anim15.setTarget(mDrawerLayout);


//        界面各组件放大
        Animator anim16 = AnimatorInflater.loadAnimator(MainActivity.this,R.animator.scale_increase);
        anim16.setTarget(textTimeHour);
        Animator anim17 = AnimatorInflater.loadAnimator(MainActivity.this,R.animator.scale_increase);
        anim17.setTarget(textTimeMinute);
        Animator anim18 = AnimatorInflater.loadAnimator(MainActivity.this,R.animator.scale_increase);
        anim18.setTarget(textTimeColon);
        Animator anim19 = AnimatorInflater.loadAnimator(MainActivity.this,R.animator.scale_increase);
        anim19.setTarget(textTomorrow);
        Animator anim20 = AnimatorInflater.loadAnimator(MainActivity.this,R.animator.scale_increase);
        anim20.setTarget(textSwitchState);

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
        animatorSet.playTogether(anim3,anim4,anim6,anim7,anim8,
                anim9,anim10,anim11,anim12,anim13,anim14,anim15,anim16,anim17,anim18,anim19,anim20);
        animatorSet.start();

    }

    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

//    获取屏幕宽度
    private static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    //    设置铃声的回调函数
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri pickedUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            RingtoneManager.setActualDefaultRingtoneUri(MainActivity.this, RingtoneManager.TYPE_ALARM, pickedUri);
        }


    }


    public Uri getSystemDefaultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM);
    }
}
