package com.study.recycler_view.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.study.recycler_view.Change;
import com.study.recycler_view.R;

import java.util.HashMap;

import static android.support.constraint.Constraints.TAG;


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
// 데이터 가져오는부분
                mDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    // onDataChange() 메소드는 해당위치에서 하위를 포함한 데이터가 변경 될 때마다 호출됨,datasnapshot data 전체
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // 객체를 생성, 초기화
                        //Value를 String.class 타입으로 형변환
                        HashMap value = dataSnapshot.getValue(HashMap.class);
//                 데이터 값을 띄운다
                        Log.d(TAG, "Value is: " + dataSnapshot);
                    }

                    // 에러가 났을 때
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });


                finish();

            }
        });



    }
    }
