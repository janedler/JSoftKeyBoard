package com.safe.keyboard.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import com.safe.keyboard.R;
import com.safe.keyboard.util.KeyboardCode;

import java.util.Iterator;

public class CustomNumKeyboardView extends KeyboardView
{
    private float mTextSize;
    private Drawable mBtnNumKeyDrawable; //b
    private Drawable mBtnNumSpecialKeyDrawable; //c
    private Resources mResources;
    private Context mContext;

    public CustomNumKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomNumKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        this.mTextSize = Math.min(widthPixels / 480.0F, heightPixels / 800.0F);
        this.mResources = mContext.getResources();
        this.mBtnNumKeyDrawable = this.mResources.getDrawable(R.drawable.btn_num_key);
        this.mBtnNumSpecialKeyDrawable = this.mResources.getDrawable(R.drawable.btn_num_special_key);
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Iterator interator = getKeyboard().getKeys().iterator();
        while(interator.hasNext()) {
            Keyboard.Key key = (Keyboard.Key)interator.next();
            canvas.save();
            Rect rect;
            Drawable drawable;
            if ((key.codes[0] == 48)
                    || (key.codes[0] == 49)
                    || (key.codes[0] == 50)
                    || (key.codes[0] == 51)
                    || (key.codes[0] == 52)
                    || (key.codes[0] == 53)
                    || (key.codes[0] == 54)
                    || (key.codes[0] == 55)
                    || (key.codes[0] == 56)
                    || (key.codes[0] == 57)) {
                rect = new Rect(1 + key.x, 1 + key.y, key.x + key.width, key.y + key.height);
                drawable = this.mBtnNumKeyDrawable;
                canvas.clipRect(rect);
                if (drawable != null) {
                    drawable.setState(key.getCurrentDrawableState());
                    drawable.setBounds(rect);
                    drawable.draw(canvas);
                }
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(30.0F * this.mTextSize);
                paint.setColor(mContext.getResources().getColor(R.color.black));
                canvas.drawText(key.label.toString(), key.x + key.width / 2, key.y + (key.height + paint.getTextSize() - paint.descent()) / 2.0F, paint);
                canvas.restore();
            }else if (key.codes[0] == KeyboardCode.KEYBOARD_CONFIRM){ //确认
                rect = new Rect(1 + key.x, 1 + key.y, key.x + key.width, key.y + key.height);
                drawable = this.mBtnNumSpecialKeyDrawable;
                canvas.clipRect(rect);
                if (drawable != null) {
                    drawable.setState(key.getCurrentDrawableState());
                    drawable.setBounds(rect);
                    drawable.draw(canvas);
                }
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(25.0F * this.mTextSize);
                paint.setColor(mContext.getResources().getColor(R.color.black));
                canvas.drawText(key.label.toString(), key.x + key.width / 2, key.y + (key.height + paint.getTextSize() - paint.descent()) / 2.0F, paint);
                canvas.restore();
            }else if(key.codes[0] == KeyboardCode.KEYBOARD_DELETE){ //删除
                rect = new Rect(1 + key.x, 1 + key.y, key.x + key.width, key.y + key.height);
                drawable = this.mBtnNumSpecialKeyDrawable;
                canvas.clipRect(rect);
                canvas.clipRect(rect);
                if (drawable != null) {
                    drawable.setState(key.getCurrentDrawableState());
                    drawable.setBounds(rect);
                    drawable.draw(canvas);
                }
                if (key.icon != null) {
                    int intrinsicWidth = key.icon.getIntrinsicWidth();
                    int intrinsicHeight = key.icon.getIntrinsicHeight();
                    int k = key.x + (key.width - intrinsicWidth) / 2;
                    int m = key.y + (key.height - intrinsicHeight) / 2;
                    key.icon.setBounds(k, m, intrinsicWidth + k, intrinsicHeight + m);
                    key.icon.draw(canvas);
                }
                canvas.restore();
            }
        }
    }
}
