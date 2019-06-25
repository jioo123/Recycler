package com.study.recycler_view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignActivity extends Activity {

    // 사용자정보를 여기에 저장하세요.

    AuthEntity mAuthEntity;
    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity);
        mAuthEntity = new AuthEntity();


        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);




    }
}
