package com.mmga.upclock.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mmga.upclock.Activity.SetTheme;

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
        switch (position) {
            case 0:
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置闹铃铃声");
                ((Activity)getContext()).startActivityForResult(intent, 1);
                break;
            case 1:
                Intent intent1 = new Intent(getContext(), SetTheme.class);
                getContext().startActivity(intent1);
                break;
            case 2:
                Uri uri = Uri.parse("mailto:1034752949@qq.com");
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO,uri);
                String[] email = {"1034752949@qq.com"}; // 需要注意，email必须以数组形式传入
                emailIntent.putExtra(Intent.EXTRA_CC, email); // 抄送人
                getContext().startActivity(Intent.createChooser(emailIntent, "请选择邮件类应用"));
                break;

            case 3:
                Toast.makeText(getContext(), "╮(╯_╰)╭", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
