package com.safe.keyboard.controller;

import android.view.View;
import android.widget.EditText;

/**
 * Created by janedler on 16/7/15.
 */
public interface IController {

    /**
     * 得到输入的信息
     * @return
     */
    public StringBuilder getBuffer();

    /**
     * 获得到输入的文本控件
     * @return
     */
    public EditText getEditText();

    /**
     * 隐藏键盘
     */
    public void hideKeyborad();

    /**
     * 键盘显示
     */
    public void showKeyborad();

    /**
     * 判断键盘是否显示
     * @return
     */
    public boolean isShowing();

    /**
     * 设置顶部的view
     */
    public void setTopView(View view);

}
