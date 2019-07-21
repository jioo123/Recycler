package com.study.recycler_view.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.study.recycler_view.Change;
import com.study.recycler_view.R;


// activity는 항상 상속 받는다
public class InputActivity extends Activity {
    DatabaseReference mDatabaseReference;
    Change mChange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_activity);
        View view = findViewById(R.id.cancel);
//        initData();
        TextView textView = findViewById(R.id.textView4);

        //view를 클릭했을 때 파이어베이스
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                finish();

            }
        });



    }
    }
