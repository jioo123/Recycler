package com.study.recycler_view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends Activity { // activity class 상속

    String id; // 변수를 지정한다
    String pw;
    EditText edid;
    EditText edpw;
    Button login_bt;
    DatabaseReference mDatabaseReference;//파이어베이스 DB에서  데이터를 읽거나 쓰려면 DatabaseReference 인스턴스가 필요

    @Override
    protected void onCreate(Bundle savedInstanceState) {// 클래스 생성하면 onCreate는 무조건 오버라이딩 ,Activity간에 데이터를 주고 받을 때 Bundle 클래스를 사용
        super.onCreate(savedInstanceState);//activity 만드는 기본 코드 실행
        setContentView(R.layout.login_activity);//layout과 연결을 해준다

        mDatabaseReference= FirebaseDatabase.getInstance().getReference("User");// 파이어 베이스에서 User 참조

        login_bt=findViewById(R.id.login_bt);// 로그인 버튼 id와 연결


        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // log in 버튼 누를 때
                edid = findViewById(R.id.login_id); // 로그인 id edit view와 연결
                edpw = findViewById(R.id.login_pw);
                id = edid.getText().toString(); //String으로 타입을 바꾼다
                pw = edpw.getText().toString();// toString 하기 전에는 editable


                mDatabaseReference.addValueEventListener(new ValueEventListener() {//용도 경로의 전체 내용에 대한 변경 사항을 읽고 수신 대기
                    @Override
                    // onDataChange() 메소드는 해당위치에서 하위를 포함한 데이터가 변경 될 때마다 호출됨,datasnapshot data 전체
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(id).exists() // id 가 존재하고 id의 password가 pw과 같으면
                                && dataSnapshot.child(id).child("password").getValue().equals(pw)){
                                    setResult(Activity.RESULT_OK, new Intent().putExtra(Application.PREF_LOGIN_ID, dataSnapshot.child(id).getKey()));
                                finish();
                        } else {// 위에 거의 예외는 토스트 메세지를 띄운다
                            Toast.makeText(LoginActivity.this, "로그인 정보를 확인해 주세요", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override // 에러가 나면 다음과 같은 에러메세지 창을 띄운다
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(LoginActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }
}
