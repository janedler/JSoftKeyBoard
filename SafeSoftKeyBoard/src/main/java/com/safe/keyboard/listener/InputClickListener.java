package com.safe.keyboard.listener;

import android.app.Activity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.safe.keyboard.controller.NumKeyboardController;
import com.safe.keyboard.util.KeyboardType;
import com.safe.keyboard.util.LogUtil;
import com.safe.keyboard.view.InputEditText;

/**
 * 文本控件点击事件
 * a
 */
public final class InputClickListener implements OnClickListener
{

    private InputEditText mInputEditText;

    public InputClickListener(InputEditText inputEditText) {
        this.mInputEditText = inputEditText;
    }

    @Override
    public void onClick(View v) {

        //隐藏系统键盘
        mInputEditText.hintSystemSoftKeyboard();
        int keyboardMode = mInputEditText.getKeyboardMode();
        if (keyboardMode == KeyboardType.NUM_KEYBOARD){  //数字键盘

            if(mInputEditText.getKeyboardController() == null || !mInputEditText.getKeyboardController().isShowing()){
                mInputEditText.setKeyboardController(
                        new NumKeyboardController((Activity)mInputEditText.getContext(),
                                mInputEditText,
                                mInputEditText.getBuffer(),
                                mInputEditText.isKeyboardOnTouchOutsideCanceled(),
                                mInputEditText.isKeyboardDisableBack(),
                                mInputEditText.isKeyboardNoRandom(),
                                mInputEditText.isKeyboardPwdShow()));
                this.mInputEditText.showKeyboard();
            }
        }
    }

}
