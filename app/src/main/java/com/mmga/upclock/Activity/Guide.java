package com.mmga.upclock.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.mmga.upclock.R;
import com.mmga.upclock.Utils.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class Guide extends Activity implements ViewPager.OnPageChangeListener{

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private List<View> views;
    private ImageView[] dots;
    private int[] ids = {R.id.iv1, R.id.iv2};
    private ImageView img_ok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);
        initViews();
        initDots();


    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.one,null));
        views.add(inflater.inflate(R.layout.two,null));

        viewPagerAdapter = new ViewPagerAdapter(Guide.this, views);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPagerAdapter);
        img_ok = (ImageView) views.get(1).findViewById(R.id.img_ok);
        img_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Guide.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        viewPager.setOnPageChangeListener(this);

    }

    private void initDots() {
        dots = new ImageView[views.size()];
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) findViewById(ids[i]);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {
    }

    @Override
    public void onPageSelected(int page) {
        for (int i = 0; i < ids.length; i++) {
            if (i == page) {
                dots[i].setImageResource(R.drawable.grayrect);
            } else {
                dots[i].setImageResource(R.drawable.whiterect);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }
}
