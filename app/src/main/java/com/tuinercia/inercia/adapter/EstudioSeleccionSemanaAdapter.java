package com.tuinercia.inercia.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tuinercia.inercia.R;
import com.tuinercia.inercia.utils.TypeFaceCustom;

/**
 * Created by ricar on 29/09/2017.
 */

public class EstudioSeleccionSemanaAdapter extends BaseAdapter {
    Context mContext;
    String[] array_list;

    public EstudioSeleccionSemanaAdapter(Context mContext,String[] array_list) {
        this.mContext = mContext;
        this.array_list = array_list;
    }

    @Override
    public int getCount() {
        return array_list.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null){
            textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER);
            textView.setHeight(130);
            textView.setTextColor(ContextCompat.getColor(mContext,R.color.transWHITE));
            textView.setTypeface(TypeFaceCustom.getInstance(mContext).UBUNTU_TYPE_FACE);
        }else{
            textView = (TextView) convertView;
        }
        textView.setText(array_list[position]);
        return textView;
    }

}
