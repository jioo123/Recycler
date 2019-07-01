package com.study.recycler_view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.support.constraint.Constraints.TAG;
// 액티비티 상속
public class SignActivity extends Activity {

    public static final String AUTH_DATA_KEY = "AUTH_DATA_KEY";

    // 사용자정보를 여기에 저장하세요.
    // 변수명 설정
    AuthEntity mAuthEntity;
    EditText edtId;
    EditText edtAge;
    EditText edtEmail;
    EditText edtPhone;
    EditText edtName;
    EditText edtPassword;
    Button btnSignUp;
    DatabaseReference mDatabaseReference;
    FirebaseAuth firebaseAuth;


//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity);
        mAuthEntity = new AuthEntity();


        //initializing views
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtId = (EditText) findViewById(R.id.edtId);
        edtAge = (EditText) findViewById(R.id.edtAge);
        edtEmail = (EditText) findViewById(R.id.edtEmail);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);




        // 파이어베이스 데이터 서버전송
        // 파이어베이스 객체 생성, 초기화
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //User 테이블로 접근 할 때 참
        final DatabaseReference table_user= database.getReference("User");


        // 파이어베이스 데이터 서버전송
//        mDatabaseReference = database.getReference("User").child("Phone");
//        mDatabaseReference.setValue("zz");
//        mDatabaseReference = database.getReference("User").child("Phone").child("Email");
//        mDatabaseReference.setValue("aa@gamil.com");
//        mDatabaseReference = database.getReference("User").child("Phone").child("Password");
//        mDatabaseReference.setValue("zz");
//
//
//        mDatabaseReference.push();



        // 회원가입 버튼을 눌렀을 때
        btnSignUp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);

                final ProgressDialog mDialog = new ProgressDialog(SignActivity.this);

                mDialog.setMessage("Please Waiting");
                mDialog.show();
                //특정 경로의 데이터를 읽고 변경을 수신 대기하려면
                // addValueEventListener 메소드를 사용하여 DatabaseReference에 ValueEventListener를 추가
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    // onDataChange는 이벤트 콜백
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // 받은 id가 이미 존재하면 토스트 띄우기
                        if(dataSnapshot.child(edtId.getText().toString()).exists()){
                            mDialog.dismiss();
//                            Toast.makeText(SignUp.this,"already register",Toast.LENGTH_LONG).show();
                            Toast.makeText(SignActivity.this,"already register",Toast.LENGTH_LONG).show();
                        }else{
                            //QUESTION
                            // Please Waiting 이라는 문구를 보여주고 있는
                            // ProgressDialog 다이얼로그를 닫음

                            mDialog.dismiss();
//                            UserDTO user = new UserDTO (edtName.getText().toString(),edtPassword.getText().toString());
                            // 이름, 비번, 나이 이메일 폰 초기화
                            mAuthEntity.name = edtName.getText().toString();
                            mAuthEntity.password = edtPassword.getText().toString();

                            mAuthEntity.age = Integer.parseInt(edtAge.getText().toString());
                            mAuthEntity.email = edtEmail.getText().toString();
                            mAuthEntity.phone = edtPhone.getText().toString();
                            table_user.child(edtId.getText().toString()).setValue(mAuthEntity);//id 아래 값들 넣기
//                            Toast.makeText(SignUp.this,"sign up successfully",Toast.LENGTH_LONG).show();

                            // 회원가입 성공 메세지 띄우기
                            Toast.makeText(SignActivity.this,"sign up successfully",Toast.LENGTH_LONG).show();

                            // signativity에서 login 액티비티로 페이지 이동
                            Intent intent = new Intent(SignActivity.this, LoginActivity.class);
                            intent.putExtra(AUTH_DATA_KEY,mAuthEntity);
                            startActivity(intent);

                            // 액티비티 끝내기
                            finish();

                        }
                    }
                    // 에러가 났을 때
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



    }

}