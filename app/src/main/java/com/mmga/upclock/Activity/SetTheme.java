package com.mmga.upclock.Activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mmga.upclock.R;
import com.mmga.upclock.Utils.ThemeUtil;

/**
 * Created by mmga on 2015/6/15.
 */
public class SetTheme extends Activity implements View.OnClickListener{

    final private int PURPLE = 1;
    final private int PINK = 2;

    RelativeLayout btnConfirm;
    ImageView purple, pink;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_theme);
        ThemeUtil.loadCustomTheme(this);

        btnConfirm = (RelativeLayout) findViewById(R.id.confirm_setting);
        purple = (ImageView) findViewById(R.id.purple);
        pink = (ImageView) findViewById(R.id.pink);


        purple.setOnClickListener(this);
        pink.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.purple:
                saveColor(PURPLE);
                break;
            case R.id.pink:
                saveColor(PINK);
                break;

        }

    }

    private void saveColor(int color) {
        SharedPreferences.Editor editor = getSharedPreferences("theme", MODE_PRIVATE).edit();
        editor.putInt("color", color);
        editor.commit();
        Toast.makeText(this, "succeed" + color, Toast.LENGTH_SHORT).show();
    }

}
