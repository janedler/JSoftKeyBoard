package com.janedler;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.zhy.autolayout.AutoFrameLayout;

import java.lang.reflect.Method;

/**
 * Created by janedler on 16/7/13.
 */
public class SoftKeyBoardUtil implements View.OnClickListener{


    private Activity mContext;
    private EditText mEditText;
    private PopupWindow mPopupWindow;
    private View mView;


    private static final int KEYCODE_DELETE = -1;
    private static final int KEYCODE_CONFIRM = -2;
    private static final int KEYCODE_HINT = -3;

    private int[] mRs = {
            R.id.soft_keyboard_btn_0,
            R.id.soft_keyboard_btn_1,
            R.id.soft_keyboard_btn_2,
            R.id.soft_keyboard_btn_3,
            R.id.soft_keyboard_btn_4,
            R.id.soft_keyboard_btn_5,
            R.id.soft_keyboard_btn_6,
            R.id.soft_keyboard_btn_7,
            R.id.soft_keyboard_btn_8,
            R.id.soft_keyboard_btn_9,
            R.id.soft_keyboard_btn_delete,
            R.id.soft_keyboard_btn_hint,
            R.id.soft_keyboard_btn_confirm
    };



    public SoftKeyBoardUtil(Activity context,EditText editText){
        this.mContext = context;
        this.mEditText = editText;
        mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        hintSystemSoftKeyboard();
    }

    private View buildSoftKeyBoardView(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.soft_keyboard_view, null);
        AutoFrameLayout contentView = (AutoFrameLayout) view.findViewById(R.id.soft_keyboard_top_content_layout);
        contentView.removeAllViews();
        contentView.addView(getTitleView());
        registerClickListener(view);
        return view;
    }


    private View getTitleView(){
        View view  = LayoutInflater.from(mContext).inflate(R.layout.soft_keyboard_title_view,null);
        return view;
    }



    public void registerSoftKeyBoard(){
        Log.e("TAG","注册键盘");
        if (mEditText != null){
            mEditText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        showSoftKeyBoard();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            },2000);
        }
    }


    public void unRegisterSoftKeyBoard(){
        Log.e("TAG","取消注册键盘");
        hideSoftKeyBoard();
    }



    public synchronized void showSoftKeyBoard(){

        if (mView != null || mPopupWindow != null){
            return;
        }

        mView = buildSoftKeyBoardView();
        mPopupWindow = new PopupWindow(mContext, null, 0);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setFocusable(false);
        mPopupWindow.setAnimationStyle((R.style.AnimBottom));
        mPopupWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable color = new ColorDrawable(0x00000000);
        mPopupWindow.setBackgroundDrawable(color);
        mPopupWindow.setContentView(mView);
        mPopupWindow.showAtLocation(mContext.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        Log.e("TAG","显示键盘");
    }

    /**
     * 关闭键盘
     */
    public void hideSoftKeyBoard(){
        if (mPopupWindow == null){
            return;
        }
        Log.e("TAG","关闭键盘");
        mPopupWindow.dismiss();
        mPopupWindow = null;
        mView = null;
    }


    private void registerClickListener(View view){
        for (int Rs: mRs) {
            view.findViewById(Rs).setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View v) {

        for (int Rs: mRs) {
            if(v.getId() == Rs){
                int primaryCode = Integer.parseInt(v.getTag()+"");
                Log.e("TAG","primaryCode>>"+primaryCode);
                Editable editable = mEditText.getText();
                int start = mEditText.getSelectionStart();
                if (primaryCode >= 48 && primaryCode <= 57){
                    editable.insert(start, Character.toString((char) primaryCode));
                }else if (primaryCode == KEYCODE_DELETE){
                    rollBack(editable, start);
                }else if (primaryCode == KEYCODE_CONFIRM || primaryCode == KEYCODE_HINT){
                    hideSoftKeyBoard();
                }

            }
        }
    }


    /**
     * 回退
     */
    private void rollBack(Editable editable, int start){
        if (editable != null && editable.length() > 0) {
            if (start > 0) {
                editable.delete(start - 1, start);
            }
        }
    }


    private void hintSystemSoftKeyboard(){


        if (android.os.Build.VERSION.SDK_INT <= 10) {//4.0以下
            mEditText.setInputType(InputType.TYPE_NULL);
        } else {
            mContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                if (android.os.Build.VERSION.SDK_INT < 17){
                    setShowSoftInputOnFocus = cls.getMethod("setSoftInputShownOnFocus",boolean.class);
                }else{
                    setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",boolean.class);
                }
                setShowSoftInputOnFocus.setAccessible(false);
                setShowSoftInputOnFocus.invoke(mEditText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }






    }


    public boolean isShowSoftKeyBorad(){
        return mPopupWindow != null && mView != null;
    }


}
