package com.study.recycler_view.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.study.recycler_view.FirstSplash;
import com.study.recycler_view.R;


// activity는 항상 상속 받는다
public class InputActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_activity);
        View view = findViewById(R.id.cancel);

        TextView textView = findViewById(R.id.textView4);

        //view를 클릭했을 때
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        //intent로 값 받기
        Intent intent = getIntent();
        //FirstSplash의 멤버 변수 값 가져오기
        String content_diary = intent.getStringExtra(FirstSplash.mData_key);
        //textView에 값 설정
        textView.setText(content_diary);


    }

}
