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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ricar on 22/09/2017.
 */

public abstract class ReservacionClasesAdapter extends BaseAdapter {

    int _layout;
    ArrayList<?> clases;
    Context mContext;

    public ReservacionClasesAdapter(int _layout, ArrayList<?> clases, Context mContext) {
        this._layout = _layout;
        this.clases = clases;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return clases.size();
    }

    @Override
    public Object getItem(int position) {
        return clases.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mHolder = null;
        if (convertView == null){
            try{


                WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                Display d = wm.getDefaultDisplay();
                Point size = new Point();
                d.getSize(size);
                int mHeight = size.y;
                int actionBarHeight = 0;

                TypedValue tv = new TypedValue();
                if (mContext.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                    actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,mContext.getResources().getDisplayMetrics());
                }

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(_layout,null);
                convertView.setMinimumHeight(((mHeight/4))-(actionBarHeight/3));

                mHolder = new ViewHolder();
                onEntradaSet(convertView,mHolder);
                convertView.setTag(mHolder);

            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            mHolder = (ViewHolder) convertView.getTag();
        }
        onEntrada(clases.get(position),mHolder,position);

        return convertView;
    }


    public abstract void onEntradaSet(View v, ViewHolder mHolder);
    public abstract void onEntrada(Object clases, ViewHolder mHolder, int position);

    public class ViewHolder{
        public TextView nombreClase;
        public ImageView imagenClase;
    }
}
