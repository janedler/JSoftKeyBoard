package com.safe.keyboard.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.safe.keyboard.R;
import com.safe.keyboard.listener.CustomNumKeyboardActionListenr;
import com.safe.keyboard.listener.InputDismissListener;
import com.safe.keyboard.util.LogUtil;
import com.safe.keyboard.util.RUtil;
import com.safe.keyboard.view.CustomNumKeyboardView;

import java.util.List;

public final class NumKeyboardController implements IController{
    private static int o = -1;
    private static int p = -1;
    private static int q = -1;
    private static int r = -1;
    private static float s = 1.0F;
    private static int t = 160;
    private Window mWindow; //a
    private View mDecorView; //b
    private View mDialogView;//c
    private Dialog mDialog; //d
    private StringBuilder mBuffer;//e
    private Keyboard mKeyboard;//f
    private boolean isCanceledOnTouchOutside;
    private boolean isCancelable;
    private boolean isKeyboardPwdShow = false;
    private boolean isKeyboardNoRandom = false;
    private Context mContext;//k
    private Activity mActivity; //l
    private EditText mEditText; //m
    private FrameLayout mTopContentLayout;
    private CustomNumKeyboardView mCustomNumKeyboardView; //n
    private int u = 0;

    private KeyboardView.OnKeyboardActionListener mKeyboardListener = new CustomNumKeyboardActionListenr(this);

    public NumKeyboardController(Activity activity,
                                 EditText editText,
                                 StringBuilder stringBuilder,
                                 boolean isCanceledOnTouchOutside, //设置是否可以通过点击外部区域关闭对话框
                                 boolean isCancelable, //设置是否可以按物理返回键关闭对话框
                                 boolean isKeyboardNoRandom,//设置是否是随机键盘
                                 boolean isKeyboardPwdShow){ //设置是否可以显示明文

        Log.e(LogUtil.TAG,"isCanceledOnTouchOutside>>"+isCanceledOnTouchOutside
                        +"  isCancelable>>"+isCancelable
                        +"  isKeyboardNoRandom>>"+isKeyboardNoRandom
                        +"  isKeyboardPwdShow>>"+isKeyboardPwdShow);

        this.mActivity = activity;
        this.mContext = activity;
        this.mEditText = editText;
        this.mBuffer = stringBuilder;
        this.isCanceledOnTouchOutside = isCanceledOnTouchOutside;
        this.isCancelable = isCancelable;
        this.isKeyboardPwdShow = isKeyboardPwdShow;
        this.mWindow = activity.getWindow();
        this.mDecorView = this.mWindow.getDecorView();
        this.mDialogView = mDecorView.findViewById(16908290);
        this.mKeyboard = new Keyboard(mContext, RUtil.xml(mContext, "num_keyboard"));
        this.mCustomNumKeyboardView = ((CustomNumKeyboardView) LayoutInflater.from(mContext).inflate(R.layout.num_safe_keyboard,null));
        this.mCustomNumKeyboardView.setPreviewEnabled(false);
        this.mCustomNumKeyboardView.setEnabled(true);
        this.mCustomNumKeyboardView.setOnKeyboardActionListener(this.mKeyboardListener);
        this.mEditText.setTransformationMethod(isKeyboardPwdShow ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
        LinearLayout linearLayout = (LinearLayout)LayoutInflater.from(mContext).inflate(R.layout.num_safe_keyboard_layout,null);
        mTopContentLayout = (FrameLayout) linearLayout.findViewById(R.id.num_safe_keyboard_top_content);
        FrameLayout contentLayout = (FrameLayout) linearLayout.findViewById(R.id.num_safe_keyboard_content);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, -2);
        contentLayout.addView(mCustomNumKeyboardView, params);

        this.mDialog = new Dialog(activity,R.style.IJMDialog);
        this.mDialog.getWindow().setFlags(8, 8);
        this.mDialog.requestWindowFeature(1);
        this.mDialog.getWindow().setGravity(Gravity.BOTTOM);
        this.mDialog.getWindow().setWindowAnimations(R.style.AnimBottom);
        this.mDialog.addContentView(linearLayout, params);
        this.mDialog.setOnDismissListener(new InputDismissListener(this));

    }

    /**
     * 设置顶部的view
     */
    @Override
    public void setTopView(View view) {
        if (mTopContentLayout != null){
            mTopContentLayout.addView(view);
        }
    }

    /**
     * 隐藏键盘
     */
    @Override
    public void hideKeyborad(){
        if ((this.mDialog != null) && (this.mDialog.isShowing())){
            this.mDialog.dismiss();
            this.mDialog = null;
            this.mCustomNumKeyboardView = null;
            this.mWindow = null;
            this.mDecorView = null;
        }
    }

    /**
     * 得到输入的信息
     * @return
     */
    @Override
    public StringBuilder getBuffer()
    {
        return this.mBuffer;
    }

    /**
     * 获得到输入的文本控件
     * @return
     */
    @Override
    public EditText getEditText() {
        return mEditText;
    }

    /**
     * 判断键盘是否显示
     * @return
     */
    @Override
    public final boolean isShowing(){
        if (this.mDialog != null) {
            return this.mDialog.isShowing();
        }
        return false;
    }

    /**
     * 键盘显示
     */
    @Override
    public void showKeyborad() {
        List keysList = this.mKeyboard.getKeys();

        this.mCustomNumKeyboardView.setKeyboard(this.mKeyboard);
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = this.mDialog.getWindow().getAttributes();
        layoutParams.width = display.getWidth();
        mDialog.getWindow().setAttributes(layoutParams);
        this.mDialog.show();
        this.mDialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        this.mDialog.setCancelable(isCancelable);
    }

}
