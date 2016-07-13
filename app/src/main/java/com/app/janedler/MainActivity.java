package com.app.janedler;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.janedler.SoftKeyBoardUtil;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {


    private EditText mEditText;
    private SoftKeyBoardUtil mSoftKeyBoardUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.editText);

        mSoftKeyBoardUtil = new SoftKeyBoardUtil(this,mEditText);

        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("TAG","setOnTouchListener");
                mSoftKeyBoardUtil.showSoftKeyBoard();
                return false;
            }
        });


        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Toast.makeText(MainActivity.this,"内容>>"+s,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }






    @Override
    protected void onResume() {
        super.onResume();
        mSoftKeyBoardUtil.registerSoftKeyBoard();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mSoftKeyBoardUtil.unRegisterSoftKeyBoard();
    }


    @Override
    public void onBackPressed() {
        if (mSoftKeyBoardUtil.isShowSoftKeyBorad()){
            mSoftKeyBoardUtil.hideSoftKeyBoard();
        }else{
            mSoftKeyBoardUtil.unRegisterSoftKeyBoard();
            super.onBackPressed();
        }

    }
}
