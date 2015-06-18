package com.mmga.upclock.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.mmga.upclock.R;

public class ThemeUtil {

    private static int sTheme;
    static final public int PURPLE = 1;
    static final public int PINK = 2;
    static final public int RED = 3;
    static final public int ORANGE = 4;
    static final public int LIGHTGREEN = 7;
    static final public int TEAL = 8;
    static final public int CYAN = 9;
    static final public int LIGHTBLUE = 10;


//    public static void changetheme(Activity activity,int theme) {
//        sTheme = theme;
//        activity.finish();
//        activity.startActivity(new Intent(activity, activity.getClass()));
//    }

    public static void loadCustomTheme(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("theme", Context.MODE_PRIVATE);
        int mainColor = preferences.getInt("color", CYAN);
        int style = R.style.CyanTheme;
        switch (mainColor) {
            case PURPLE:
                style = R.style.PurpleTheme;
                break;
            case PINK:
                style = R.style.PinkTheme;
                break;
            case RED:
                style = R.style.RedTheme;
                break;
            case ORANGE:
                style = R.style.OrangeTheme;
                break;
            case LIGHTGREEN:
                style = R.style.LightGreenTheme;
                break;
            case TEAL:
                style = R.style.TealTheme;
                break;
            case CYAN:
                style = R.style.CyanTheme;
                break;
            case LIGHTBLUE:
                style = R.style.LightBlueTheme;
                break;
        }


        activity.setTheme(style);

}

}
