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

public class LoginActivity extends Activity {

    String id;
    String pw;
    EditText edid;
    EditText edpw;
    Button login_bt;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mDatabaseReference= FirebaseDatabase.getInstance().getReference("User");

        login_bt=findViewById(R.id.login_bt);


        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edid = findViewById(R.id.login_id);
                edpw = findViewById(R.id.login_pw);
                id = edid.getText().toString();
                pw = edpw.getText().toString();


                mDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(id).exists()
                                && dataSnapshot.child(id).child("password").getValue().equals(pw)){
                                    setResult(Activity.RESULT_OK);
                                finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "로그인정보를 확인해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(LoginActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }
}
