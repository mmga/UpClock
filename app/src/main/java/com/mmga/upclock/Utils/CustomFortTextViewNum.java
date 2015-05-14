package com.mmga.upclock.Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mmga on 2015/5/14.
 */
public class CustomFortTextViewNum extends TextView {

    public CustomFortTextViewNum(Context context) {
        super(context);
        init(context);

    }

    public CustomFortTextViewNum(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomFortTextViewNum(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        AssetManager assertMgr = context.getAssets();
        Typeface font = Typeface.createFromAsset(assertMgr, "fonts/Helvetica.ttf");
        setTypeface(font);

    }
}
