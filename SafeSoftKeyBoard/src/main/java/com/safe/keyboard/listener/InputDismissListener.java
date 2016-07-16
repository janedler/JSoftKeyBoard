package com.safe.keyboard.listener;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import com.safe.keyboard.controller.IController;
import com.safe.keyboard.util.LogUtil;

/**
 * 关闭键盘
 * Created by janedler on 16/7/16.
 */
public class InputDismissListener implements DialogInterface.OnDismissListener {

    private IController mIController;

    public InputDismissListener(IController controller){
        this.mIController = controller;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.e(LogUtil.TAG,"onDismiss");
    }
}
