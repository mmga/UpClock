package com.mmga.upclock.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mmga.upclock.R;

/**
 * Created by mmga on 2015/5/10.
 */
public class ListViewAdapter extends BaseAdapter {


    private ListCellData[] data = new ListCellData[]{
        new ListCellData(true, 15, 7),
        new ListCellData(true, 30, 6),
        new ListCellData(false, 30, 8)};


    private Context context = null;

    public ListViewAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }


    /*public void add(ListCellData data1) {

    }*/

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {



        LinearLayout ll = null;
        if (convertView != null) {
            ll = (LinearLayout) convertView;
        } else {
            ll = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.alarm_list_cell,null);
        }

        ListCellData data = (ListCellData) getItem(position);

        TextView time = (TextView) ll.findViewById(R.id.textview);
        String lable = String.format("%d:%d", data.getHour(), data.getMinite());
        time.setText(lable);

        return ll;}
}


