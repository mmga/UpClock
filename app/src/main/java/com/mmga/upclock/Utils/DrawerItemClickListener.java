package com.mmga.upclock.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by mmga on 2015/5/15.
 */
public class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {

    public DrawerItemClickListener(Context context) {
        this.context = context;
    }

    private Context context = null;

    public Context getContext() {
        return context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    private void selectItem(int position) {
        Toast.makeText(getContext(), "item" + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置闹铃铃声");
        ((Activity)getContext()).startActivityForResult(intent, 1);
    }
}
