package com.study.recycler_view;

import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Application Class는  어플리케이션 컴포넌트들 사이에서 공동으로 멤버들을 사용할 수 있게 해주는 편리한 공유 클래스를 제공
//
public class JiooApplication extends android.app.Application {

    public static final String PREF_NAME = "PREF_NAME";
    public static final String PREF_LOGIN_ID = "PREF_LOGIN_ID";

    static DatabaseReference mDatabaseReference;
    static String mId;

    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
        mId = getSharedPreferences(PREF_NAME, MODE_PRIVATE).getString(PREF_LOGIN_ID, "");
    }

    private void initFirebase() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public static DatabaseReference getDirayFirebase(){
        if (!mId.equals("")) {
            mDatabaseReference = mDatabaseReference.child("Dairy").child(mId);
        }
        return mDatabaseReference;

    }


}
