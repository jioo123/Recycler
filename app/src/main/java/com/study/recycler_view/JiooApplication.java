package com.study.recycler_view;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Application Class는  어플리케이션 컴포넌트들 사이에서 공동으로 멤버들을 사용할 수 있게 해주는 편리한 공유 클래스를 제공
//
public class JiooApplication extends android.app.Application {
    // 변수를 초기화 해준다
    public static final String PREF_NAME = "PREF_NAME";
    public static final String PREF_LOGIN_ID = "PREF_LOGIN_ID";
    // 파이어베이스에서 데이터 리퍼런스
    static DatabaseReference mDatabaseReference;
    static String mId;
    static boolean mCheck =true;    // 멤버변수로 선언해야됨

    @Override
    public void onCreate() {
        super.onCreate();
        //초기화
        initFirebase();

        mId = getSharedPreferences(PREF_NAME, MODE_PRIVATE).getString(PREF_LOGIN_ID, "");
    }
    // 파이어베이스를 초기화 하는 함수
    private void initFirebase() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public static DatabaseReference getDirayFirebase(){
//        boolean check =true;  지역변수로 선언시 getDirayFirebase() 호출때마다 변수가 true로 초기화되서
//                              의미없음...

        // 아이디가 비어있지 않으면 내용을 가져온다
//        if (!mId.equals("") && check ==true) {
        if (!mId.equals("") && mCheck) {    // 이미 true 나 false 의 값을 가지고 있기때문에 비교할 필요 없음

            mDatabaseReference = mDatabaseReference.child("Dairy").child(mId);
            mCheck = false;
//            check=false;
        }


        return mDatabaseReference;

    }


}
