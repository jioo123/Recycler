package com.study.recycler_view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.study.recycler_view.ui.InputActivity;
import com.study.recycler_view.view.BjmCalendarView;
import com.study.recycler_view.view.CalendarListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FirstSplash extends AppCompatActivity {
    public static final String mData_key ="data_key" ;

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
//        0.9초로 저장


       final BjmCalendarView calendarView = findViewById(R.id.calendar_view);
        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(View view, Date date) {
                TextView textView = (TextView)view;
                textView.setBackgroundColor(Color.GREEN);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd\\EEE", Locale.KOREA);
                calendarView.setUnderText(dateFormat.format(date));

            }

            @Override
            public void inputDairy(Date date) {
                Toast.makeText(FirstSplash.this, date.toString() , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FirstSplash.this, InputActivity.class);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd\\EEE", Locale.KOREA);
                intent.putExtra(mData_key,dateFormat.format(date));
                startActivity(intent);

            }

        });
    }
}
