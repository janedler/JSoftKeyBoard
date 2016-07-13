package com.janedler;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by janedler on 16/7/13.
 */
public class SoftKeyBoardView extends ViewGroup {

    private Context mContext;

    public SoftKeyBoardView(Context context) {
        super(context);
        initView(context);
    }

    public SoftKeyBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SoftKeyBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context){
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.soft_keyboard_view,null);














    }



    public void keySoftClick(View view){
        Log.e("TAG","keySoftClick");
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
