package com.safe.keyboard.listener;

import android.inputmethodservice.KeyboardView;
import android.text.Editable;

import com.safe.keyboard.controller.IController;
import com.safe.keyboard.controller.NumKeyboardController;
import com.safe.keyboard.util.KeyboardCode;
import com.safe.keyboard.view.InputEditText;

/**
 * Created by janedler on 16/7/15.
 */
public class CustomNumKeyboardActionListenr implements KeyboardView.OnKeyboardActionListener {


    private IController mController;

    public CustomNumKeyboardActionListenr(NumKeyboardController controller){
        this.mController = controller;
    }


    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

        InputEditText editText = (InputEditText) mController.getEditText();
        Editable localEditable = editText.getText();
        int start = editText.getSelectionStart();


        switch (primaryCode) {

            case KeyboardCode.KEYBOARD_DELETE:// 回退
                rollBack(localEditable, start);
                break;

            case KeyboardCode.KEYBOARD_CONFIRM: //完成
                editText.hideKeyboard();
                break;

            case 57419:
                if (start > 0) {
                    editText.setSelection(start - 1);
                }
                break;

            case 57421:
                if (start < editText.length()) {
                    editText.setSelection(start + 1);
                }
                break;

            default:
                localEditable.insert(start, Character.toString((char) primaryCode));
                break;
        }
    }

    @Override
    public void onPress(int primaryCode) {}

    @Override
    public void onRelease(int primaryCode) {}

    @Override
    public void onText(CharSequence text) {}

    @Override
    public void swipeLeft() {}

    @Override
    public void swipeRight() {}

    @Override
    public void swipeDown() {}

    @Override
    public void swipeUp() {}


    /**
     * 回退
     */
    public void rollBack(Editable editable,int start){
        if (editable != null && editable.length() > 0) {
            if (start > 0) {
                editable.delete(start - 1, start);
            }
        }
    }


}
