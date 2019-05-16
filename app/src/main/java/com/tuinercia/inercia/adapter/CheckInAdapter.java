package com.tuinercia.inercia.adapter;

import android.content.Context;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ricar on 18/10/2017.
 */

public abstract class CheckInAdapter extends BaseAdapter {

    int _layout;
    Context mContext;
    ArrayList<?> objects;

    public CheckInAdapter(int _layout, Context mContext, ArrayList<?> objects) {
        this._layout = _layout;
        this.mContext = mContext;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView mHolder = null;
        if (convertView == null){
            try{

                LayoutInflater v = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = v.inflate(_layout,null);
                mHolder = new HolderView();

                onEntradaSet(convertView,mHolder);
                convertView.setTag(mHolder);

            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            mHolder = (HolderView) convertView.getTag();
        }
        onEntrada(objects.get(position),mHolder,position);

        return convertView;
    }


    public abstract void onEntradaSet(View v, HolderView mHolder);
    public abstract void onEntrada(Object objects, HolderView mHolder, int position);

    public class HolderView{
        public ImageView mis_clases_orientame;
        public TextView fecha,hora,estudio,clase,entrador;
        public boolean isSelected;
        public Button button;
    }
}
