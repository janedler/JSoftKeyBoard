package com.safe.keyboard.listener;

import android.app.Activity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.safe.keyboard.controller.NumKeyboardController;
import com.safe.keyboard.util.KeyboardType;
import com.safe.keyboard.view.InputEditText;

/**
 * b
 * Created by janedler on 16/7/14.
 */
public class InputFocusChangeListener implements View.OnFocusChangeListener {

    private InputEditText mInputEditText;

    public InputFocusChangeListener(InputEditText inputEditText) {
        this.mInputEditText = inputEditText;
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {




        //隐藏系统键盘
        mInputEditText.hintSystemSoftKeyboard();
        int keyboardMode = mInputEditText.getKeyboardMode();
        if (keyboardMode == KeyboardType.NUM_KEYBOARD && hasFocus){  //数字键盘
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
