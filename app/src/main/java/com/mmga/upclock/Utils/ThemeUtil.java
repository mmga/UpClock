package com.mmga.upclock.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;

import com.mmga.upclock.R;

/**
 * Created by mmga on 2015/6/15.
 */
public class ThemeUtil {

    static final private int PURPLE = 1;
    static final private int PINK = 2;

//
//    Context context;
//
//    public Context getContext() {
//        return context;
//    }
//
//    public ThemeUtil(Context context) {
//        this.context = context;
//    }

    public static void loadCustomTheme(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("theme", Context.MODE_PRIVATE);
        int mainColor = preferences.getInt("color", 2);
        int color = R.color.purple;
        switch (mainColor) {
            case PURPLE:
                color = R.color.purple;
                break;
            case PINK:
                color = R.color.pink;
                break;

        }


        activity.getWindow().setBackgroundDrawableResource(color);
        if (activity.findViewById(R.id.nav_header) != null) {
            ImageView header = (ImageView) activity.findViewById(R.id.nav_header);
            header.setBackgroundResource(color);
        }
    }
}
