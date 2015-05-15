package com.mmga.upclock.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mmga.upclock.R;
import com.mmga.upclock.Utils.DrawerItemClickListener;

/**
 * Created by mmga on 2015/5/14.
 */
public class MainActivity extends Activity {
    private ImageButton btnSettings;
    private ImageButton btnSetTime;
    private TextView textTomorrow;
    private TextView textTime;
    private TextView textSwitchState;

    private Button mButton;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mRightDrawer;
    private ListView mDrawerList;
    private String[] listData = new String[]{"主题设置", "铃声设置","分享给朋友"};



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


        mButton = (Button) findViewById(R.id.menuButton);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mRightDrawer = (LinearLayout) findViewById(R.id.right_drawer);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener(MainActivity.this));

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mRightDrawer);
            }
        });


    }

















}
