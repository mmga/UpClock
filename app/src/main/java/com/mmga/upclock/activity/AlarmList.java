package com.mmga.upclock.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.mmga.upclock.R;

/**
 * Created by mmga on 2015/5/10.
 */
public class AlarmList extends Activity {



    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alarm_list);

        lv = (ListView) findViewById(R.id.list_view);
        lv.setAdapter(new ListViewAdapter(this));

    }




}