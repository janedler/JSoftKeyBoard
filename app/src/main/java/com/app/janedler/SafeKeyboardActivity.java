package com.app.janedler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.safe.keyboard.util.KeyboardType;
import com.safe.keyboard.util.LogUtil;
import com.safe.keyboard.view.InputEditText;

/**
 * Created by janedler on 16/7/16.
 */
public class SafeKeyboardActivity extends AppCompatActivity {

    private InputEditText mInputEditText;
    private Button mOpen;
    private Button mClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.safe_keyboard_layout);

        mInputEditText = (InputEditText) findViewById(R.id.inputEditText);
        mOpen = (Button) findViewById(R.id.open);
        mClose = (Button) findViewById(R.id.close);

        mInputEditText.setKeyboardMode(KeyboardType.NUM_KEYBOARD);

        mInputEditText.setKeyboardDisableBack(false);
        mInputEditText.setKeyboardOnTouchOutsideCanceled(false);
        mInputEditText.setKeyboardPwdShow(true);
        mInputEditText.setTopResLayout(R.layout.item_safe_keyboard, new InputEditText.KeyboardTopContentListener() {
            @Override
            public void showTopContent(View view) {
                TextView textView = (TextView) view.findViewById(R.id.textview);
                textView.setText("测试标题");
            }
        });


        mOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputEditText.showKeyboard();
            }
        });

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputEditText.hideKeyboard();
            }
        });

    }

}
