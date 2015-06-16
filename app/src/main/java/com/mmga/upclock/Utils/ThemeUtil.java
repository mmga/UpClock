package com.mmga.upclock.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.mmga.upclock.R;

public class ThemeUtil {

    static final private int PURPLE = 1;
    static final private int PINK = 2;
    static final private int RED = 3;
    static final private int ORANGE = 4;
    static final private int YELLOW = 5;
    static final private int Lime = 6;
    static final private int LIGHTGREEN = 7;
    static final private int TEAL = 8;
    static final private int CYAN = 9;
    static final private int LIGHTBLUE = 10;

public static void loadCustomTheme(Activity activity) {
    SharedPreferences preferences = activity.getSharedPreferences("theme", Context.MODE_PRIVATE);
    int mainColor = preferences.getInt("color", 2);
    int style = R.style.PurpleTheme;
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
        case YELLOW:
            style = R.style.YellowTheme;
            break;
        case Lime:
            style = R.style.LimeTheme;
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
