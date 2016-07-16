package com.safe.keyboard.listener;

import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.safe.keyboard.controller.NumKeyboardController;
import com.safe.keyboard.util.KeyboardType;
import com.safe.keyboard.util.LogUtil;
import com.safe.keyboard.view.InputEditText;

/**
 * Created by janedler on 16/7/15.
 */
public class InputKeyListener implements View.OnKeyListener
{
    private InputEditText mInputEditText;

    public InputKeyListener(InputEditText inputEditText) {
        this.mInputEditText = inputEditText;
    }

    public final boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent) {

        if (paramInt == KeyEvent.KEYCODE_BACK){
            int keyboardMode = mInputEditText.getKeyboardMode();
            if (keyboardMode == KeyboardType.NUM_KEYBOARD) {  //数字键盘
                if(mInputEditText.getKeyboardController() != null && mInputEditText.getKeyboardController().isShowing()){
                    mInputEditText.hideKeyboard();
                    return true;
                }
            }
        }
        return false;
    }
}
