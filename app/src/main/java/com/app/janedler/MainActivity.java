package com.app.janedler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.janedler.SoftKeyBoardUtil;
import com.safe.keyboard.util.KeyboardType;
import com.safe.keyboard.view.InputEditText;

public class MainActivity extends AppCompatActivity {


    private Button customBtn;
    private Button complexBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customBtn = (Button) findViewById(R.id.btn1);
        complexBtn = (Button) findViewById(R.id.btn2);

        customBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,KeyboardActivity.class);
                startActivity(intent);
            }
        });

        complexBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,SafeKeyboardActivity.class);
                startActivity(intent);
            }
        });

    }

}
