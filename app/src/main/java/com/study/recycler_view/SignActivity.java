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

public class SignActivity extends Activity {

    public static final String AUTH_DATA_KEY = "AUTH_DATA_KEY";

    // 사용자정보를 여기에 저장하세요.

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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
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




        btnSignUp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                final ProgressDialog mDialog = new ProgressDialog(SignActivity.this);

                mDialog.setMessage("Please Waiting");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(edtId.getText().toString()).exists()){
                            mDialog.dismiss();
//                            Toast.makeText(SignUp.this,"already register",Toast.LENGTH_LONG).show();
                            Toast.makeText(SignActivity.this,"already register",Toast.LENGTH_LONG).show();
                        }else{
                            mDialog.dismiss();
//                            UserDTO user = new UserDTO (edtName.getText().toString(),edtPassword.getText().toString());
                            mAuthEntity.name = edtName.getText().toString();
                            mAuthEntity.password = edtPassword.getText().toString();
                            mAuthEntity.age = Integer.parseInt(edtAge.getText().toString());
                            mAuthEntity.email = edtEmail.getText().toString();
                            mAuthEntity.phone = edtPhone.getText().toString();
                            table_user.child(edtId.getText().toString()).setValue(mAuthEntity);
//                            Toast.makeText(SignUp.this,"sign up successfully",Toast.LENGTH_LONG).show();
                            Toast.makeText(SignActivity.this,"sign up successfully",Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(SignActivity.this, LoginActivity.class);
                            intent.putExtra(AUTH_DATA_KEY,mAuthEntity);
                            startActivity(intent);
                            finish();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



    }

}