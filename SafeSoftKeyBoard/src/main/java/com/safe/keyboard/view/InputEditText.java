package com.safe.keyboard.view;

import android.content.Context;
import android.os.Build;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.safe.keyboard.controller.IController;
import com.safe.keyboard.listener.InputClickListener;
import com.safe.keyboard.listener.InputFocusChangeListener;
import com.safe.keyboard.listener.InputKeyListener;

import java.lang.reflect.Method;

/**
 * 改输入框控件绑定了输入法
 * 点击该输入框就会自动调用我们自定义的输入法
 * Created by janedler on 16/7/14.
 */
public class InputEditText extends EditText
{
    private Context mContext;
    private StringBuilder mInputBuffer = new StringBuilder("");
    private boolean isOnTouch = false;
    private int mKeyboradType = 1; //键盘类型
    private boolean isKeyboardPwdShow = false; //设置密码是否显示
    private boolean isKeyboardOnTouchOutsideCanceled = false; //点击外面是否能被关闭
    private boolean isKeyboardDisableBack = false;
    private boolean isKeyboardNoRandom = false; //是否设置成随机的键盘
    private IController mController;
    private View mItemTopView;
    private int mTopResLayout;
    private KeyboardTopContentListener mKeyboardTopContentListener;

    public InputEditText(Context paramContext){
        super(paramContext);
        initView(paramContext);
    }

    public InputEditText(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initView(paramContext);
    }

    public InputEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        initView(paramContext);
    }

    private void initView(Context paramContext) {
        this.mContext = paramContext;
        setOnClickListener(new InputClickListener(this));
        setOnFocusChangeListener(new InputFocusChangeListener(this));
        setOnKeyListener(new InputKeyListener(this));
    }

    /**
     * 清空数据
     */
    public void keyboardClear(){
        if (this.mInputBuffer != null){
            this.mInputBuffer.delete(0, this.mInputBuffer.length());
            setText("");
        }
    }

    /**
     * 获得到输入的信息
     *
     * @return
     */
    public String getKeyboardText(){
        if (this.mKeyboradType == 2 && this.mController != null) {
            return this.mController.getBuffer().toString().trim();
        }
        return "";
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard(){
        if (this.mKeyboradType == 2 && this.mController != null){
            this.mController.hideKeyborad();
            this.mController = null;
        }
    }

    /**
     * 显示键盘
     */
    public void showKeyboard(){
        if (this.mKeyboradType == 2 ){

            if (this.mController == null){
                this.performClick();
            }

            this.mController.showKeyborad();

        }
    }

    protected void onAttachedToWindow(){
        super.onAttachedToWindow();
    }

    public boolean onTouchEvent(MotionEvent event){
        hintSystemSoftKeyboard();
        if (event.getAction() == 1) {
            this.isOnTouch = true;
        }
        super.onTouchEvent(event);
        return true;
    }

    /**
     * 设置是否点击返回
     *
     * @param keyboardDisableBack
     */
    public void setKeyboardDisableBack(boolean keyboardDisableBack){
        this.isKeyboardDisableBack = keyboardDisableBack;
    }

    /**
     * 获得是否可以按物理返回键关闭对话框
     * @return
     *        true 可以关闭
     *        false 不可以关闭
     */
    public boolean isKeyboardDisableBack(){
        return isKeyboardDisableBack;
    }

    /**
     * 判断键盘外部区域是否可以点击后关闭键盘
     * @return
     */
    public boolean isKeyboardOnTouchOutsideCanceled(){
        return isKeyboardOnTouchOutsideCanceled;
    }




    public boolean isKeyboardNoRandom(){
        return isKeyboardNoRandom;
    }



    /**
     * 设置键盘类型
     *
     * @param keyboardMode
     *        KeyboardType.LETTERS_KEYBOARD  字母键盘（还未实现）
     *        KeyboardType.NUM_KEYBOARD      数字键盘
     */
    public void setKeyboardMode(int keyboardMode) {
        if ((keyboardMode == 1) || (keyboardMode == 2)) {
            this.mKeyboradType = keyboardMode;
        }
    }

    /**
     * 获得到键盘类型
     * @return
     *        KeyboardType.LETTERS_KEYBOARD  字母键盘（还未实现）
     *        KeyboardType.NUM_KEYBOARD      数字键盘
     */
    public int getKeyboardMode(){
        return mKeyboradType;
    }

    /**
     * 是否设置成随机的键盘
     *
     * @param isKeyboardNoRandom
     *        true    随机按键键盘
     *        false   非随机按键键盘
     */
    public void setKeyboardNoRandom(boolean isKeyboardNoRandom){
        this.isKeyboardNoRandom = isKeyboardNoRandom;
    }

    /**
     * 点击外部空白区域是否可以关闭
     * @param isKeyboardOnTouchOutsideCanceled
     */
    public void setKeyboardOnTouchOutsideCanceled(boolean isKeyboardOnTouchOutsideCanceled){
        this.isKeyboardOnTouchOutsideCanceled = isKeyboardOnTouchOutsideCanceled;
    }

    /**
     * 设置密码是否显示
     * @param keyboardPwdShow
     *          true 显示明文
     *          false 显示密文
     */
    public void setKeyboardPwdShow(boolean keyboardPwdShow){
        this.isKeyboardPwdShow = keyboardPwdShow;
        if (isKeyboardPwdShow){
            setText(getKeyboardText());
            setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else{
            setSelection(getText().length());
            setText(getKeyboardText());
            setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    /**
     * 判断输入文本框是否加密
     * @return
     *        true 加密  现在加密主要是把文件换成 *
     *        false 不加密
     */
    public boolean isKeyboardPwdShow(){
        return isKeyboardPwdShow;
    }


    /**
     * 隐藏系统键盘
     */
    public void hintSystemSoftKeyboard(){
        if (getWindowToken() != null) {
            ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getWindowToken(), 2);
        }
        if (Build.VERSION.SDK_INT >= 11) {
            try{
                Class[] arrayOfClass = new Class[1];
                arrayOfClass[0] = Boolean.TYPE;
                Method localMethod = null;
                if (Build.VERSION.SDK_INT < 17){
                    localMethod = EditText.class.getMethod("setSoftInputShownOnFocus", arrayOfClass);
                }else{
                    localMethod = EditText.class.getMethod("setShowSoftInputOnFocus", arrayOfClass);
                }
                localMethod.setAccessible(true);
                Object[] arrayOfObject = new Object[1];
                arrayOfObject[0] = Boolean.valueOf(false);
                localMethod.invoke(this, arrayOfObject);
                return;
            }
            catch (SecurityException localSecurityException) {
                localSecurityException.printStackTrace();
                return;
            }
            catch (NoSuchMethodException localNoSuchMethodException) {
                localNoSuchMethodException.printStackTrace();
                return;
            }
            catch (Exception localException) {
                localException.printStackTrace();
                return;
            }
        }
        setInputType(0);
    }

    /**
     * 设置控制器
     * @param keyboardController
     */
    public void setKeyboardController(IController keyboardController){
        this.mController = keyboardController;
        mController.setTopView(mItemTopView = LayoutInflater.from(mContext).inflate(mTopResLayout,null)); //设置头部区域布局
        if (mKeyboardTopContentListener != null){ mKeyboardTopContentListener.showTopContent(mItemTopView);} //回调头部





    }

    /**
     * 获得控制器
     */
    public IController getKeyboardController(){
        return this.mController;
    }

    public StringBuilder getBuffer(){
        return mInputBuffer;
    }

    /**
     * 设置键盘头部布局文件 并回调接口
     * @param layoutId
     * @param listener
     */
    public void setTopResLayout(int layoutId,KeyboardTopContentListener listener){
        this.mTopResLayout = layoutId;
        this.mKeyboardTopContentListener = listener;
    }

    /**
     * 键盘头部布局文件绘制完成回调接口
     */
    public interface KeyboardTopContentListener{
        public void showTopContent(View view);
    }
}




