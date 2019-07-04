package com.study.recycler_view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends Activity {
    //리퀘스트 코드 초기화 임의 설정
    public static final int REQUEST_CODE = 1345;
    // 버튼 변수명 설정
    Button login;
    Button signIn;

    // onCreate 생성
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // layout splah와 연결
        setContentView(R.layout.splah);
        // 버튼 아이디와 변수 연결
        login = findViewById(R.id.login);
        signIn=findViewById(R.id.signin);
        // 로그인 클릭시
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(SplashActivity.this, LoginActivity.class),REQUEST_CODE);

            }
        });
        // 회원가입 클릭시
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // splash 액티비티에서 회원가입 액티비티로 이동
                Intent intent = new Intent(SplashActivity.this, SignActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences prefs = getSharedPreferences(Application.PREF_NAME, MODE_PRIVATE);
        String text = prefs.getString(Application.PREF_LOGIN_ID, "");

        if(!text.isEmpty()){
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
    // 처리된 결과를 반환
    //처리된 결과 코드 (resultCode) 가 RESULT_OK 이면 requestCode 를 판별해 결과 처리를 진행
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //splash 액티비티에서 main 액티비티로 화면 전환
        if(requestCode==REQUEST_CODE && resultCode==Activity.RESULT_OK){
            SharedPreferences prefs = getSharedPreferences(Application.PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(Application.PREF_LOGIN_ID, data.getStringExtra(Application.PREF_LOGIN_ID));
            editor.commit();
            startActivity(new Intent(this, MainActivity.class));
            //종료
            finish();
        }
    }
}
