package com.study.recycler_view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class FirstSplash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_splash);
        // 핸들러 변수 생성, 초기화
        Handler handler = new Handler();
        // 클릭했을 때 딜레이
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FirstSplash.this,SplashActivity.class);
                startActivity(intent);
                finish();
            }
        },1500);
        //0.9초로 저장

    }
}
