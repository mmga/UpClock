package com.mmga.upclock.Activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
    final private int RED = 3;
    final private int ORANGE = 4;
    final private int LIGHTGREEN = 7;
    final private int TEAL = 8;
    final private int CYAN = 9;
    final private int LIGHTBLUE = 10;

    RelativeLayout btnConfirm;
    ImageView purple, pink, red, orange, lightGreen, teal, cyan, lightBlue;
    private EditText editText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.loadCustomTheme(this);
        setContentView(R.layout.set_theme);

        editText = (EditText) findViewById(R.id.custom_edit_text);

        btnConfirm = (RelativeLayout) findViewById(R.id.confirm_setting);
        purple = (ImageView) findViewById(R.id.purple);
        pink = (ImageView) findViewById(R.id.pink);
        red = (ImageView) findViewById(R.id.red);
        orange = (ImageView) findViewById(R.id.orange);
        lightGreen = (ImageView) findViewById(R.id.light_green);
        teal = (ImageView) findViewById(R.id.teal);
        cyan = (ImageView) findViewById(R.id.cyan);
        lightBlue = (ImageView) findViewById(R.id.light_blue);


        purple.setOnClickListener(this);
        pink.setOnClickListener(this);
        red.setOnClickListener(this);
        orange.setOnClickListener(this);
        lightGreen.setOnClickListener(this);
        teal.setOnClickListener(this);
        cyan.setOnClickListener(this);
        lightBlue.setOnClickListener(this);

        btnConfirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_setting:
                saveCustomEditText();
                finish();
                break;
            case R.id.purple:
                selectTheme(PURPLE);
                break;
            case R.id.pink:
                selectTheme(PINK);
                break;
            case R.id.red:
                selectTheme(RED);
                break;
            case R.id.orange:
                selectTheme(ORANGE);
                break;
            case R.id.light_green:
                selectTheme(LIGHTGREEN);
                break;
            case R.id.teal:
                selectTheme(TEAL);
                break;
            case R.id.cyan:
                selectTheme(CYAN);
                break;
            case R.id.light_blue:
                selectTheme(LIGHTBLUE);
                break;

        }

    }

    private void saveCustomEditText() {
        SharedPreferences.Editor editor = getSharedPreferences("word", MODE_PRIVATE).edit();
        editor.putString("word", editText.getText().toString());
        editor.apply();
    }

    private void selectTheme(int color) {
        SharedPreferences.Editor editor = getSharedPreferences("theme", MODE_PRIVATE).edit();
        editor.putInt("color", color);
        editor.apply();
        ThemeUtil.changetheme(this,color);
        Toast.makeText(this, "succeed" + color, Toast.LENGTH_SHORT).show();
    }

}
