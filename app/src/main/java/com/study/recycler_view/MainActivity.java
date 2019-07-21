package com.study.recycler_view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.study.recycler_view.jioovoice.JiooVoiceActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class MainActivity extends Activity { // 액티비티를 상속

    private RecyclerView mRecyclerView; // 변수명 지정
    Change mChange;
    DatabaseReference mDatabaseReference;
    Button mLogoutButton ;
    Button mCalendar;


    // 파이어베이스에서 데이터 리퍼런스

    @Override
    protected void onCreate(Bundle savedInstanceState) { // onCreate 생성
        super.onCreate(savedInstanceState); // 액티비티 만드는 기본코드 실행
        setContentView(R.layout.activity_main); // layout과 activity 연결
        initLayout();// 초화
        initData();

        // AuthEntity 객체 생성과 동시에 초기 , 액티비티 시작할 때 intent 반환
        AuthEntity authEntity = getIntent().getParcelableExtra(SignActivity.AUTH_DATA_KEY);

        //intent는 액티비티나 서비스 브로드케스트리시 같은 컨포넌트 끼리 데이터 주고받고 통신하는 클래스
        //intent는 액티비티 실행시키는 곳, 서비스 생성할 때 등등에 사용

    }


    // 이렇게 하면 해당 아이템의 위치를 알수없어서 안됨
    // 리스트뷰같은 형태에선 잘못된 사용
    public void onItemClick(View view) {





        Intent intent = new Intent(MainActivity.this, JiooVoiceActivity.class);
        startActivity(intent);

        // 데이터 가져오는부분
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            // onDataChange() 메소드는 해당위치에서 하위를 포함한 데이터가 변경 될 때마다 호출됨,datasnapshot data 전체
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 객체를 생성, 초기화
                //Value를 String.class 타입으로 형변환
//                HashMap value = dataSnapshot.getValue(HashMap.class);
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

    }
    // layout 초기화하는 메소드
    private void initLayout() {
        // recyclerView 아이디 연결
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mLogoutButton = (Button)findViewById(R.id.login_button);
        // 로그아웃 클릭했을 때
        mLogoutButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //SharedPreference를 이용하여 간단한 데이터들을 저장하고 불러올 수 있다
                        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.Jio
                        SharedPreferences prefs = getSharedPreferences(JiooApplication.PREF_NAME,MODE_PRIVATE);
                        // 수정하기 위해 작성
                        SharedPreferences.Editor editor = prefs.edit();
                        // 아이디를 없앤다
                        editor.putString(JiooApplication.PREF_LOGIN_ID,"");
                        // 바뀐 정보들 넣기
                        editor.commit();
                        // intent로 화면 전환
                        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }
        );

//        mCalendar=(Button)findViewById(R.id.calendar_view);
//        mCalendar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Calendar1.class);
//                startActivity(intent);
//
//            }
//        });










    }



    //데이터를 초기화
    private void initData() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mChange = new Change() {
            @Override
            public void click(int position) {
//                Intent intent= new Intent(getApplicationContext(
//                        NewPage.class);

                // 해당 액티비티의 Context를 사용
                // 인터페이스안에 메소드라서 단순 this 를 사용하면 해당 인터페이스를 가르킴
                // 따라서 MainActivity.this 로 클래스명.this 로 사용
             //   Intent intent = new Intent(MainActivity.this, NewPage.class);
              //  startActivity(intent);
            }
        };
        // 아답터 객체를 초기화 하고 생성
        final MyRecyclerAdapter adapter = new MyRecyclerAdapter();
        adapter.setInterface(mChange);      //미리 초기화한 인터페이스를 넣어줌
        // mAlumList를 생성, 초기화
        final List<Dairy> dairyList = new ArrayList<>();
        mRecyclerViehttps://blog.naver.com/opicnojam/221537733279 w.setAdapter(adapter);

        JiooApplication.getDirayFirebase().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                HashMap<String, String> diaryHasmap = (HashMap<String, String>) dataSnapshot.getValue();



                Iterator<String> iter=diaryHasmap.keySet().iterator();
                while(iter.hasNext()){
                    Dairy dairy = new Dairy();
                    dairy.mDate=iter.next();
//                    dairy.mDate = mDatabaseReference.child("Dairy").child(mId).getKey();
                    dairy.mContent =diaryHasmap.values().iterator().next();
                    dairyList.add(dairy);
                }
                // 해쉬맵 keySet Iterator 사용해서 데이터 추출


                adapter.setList(dairyList);
                adapter.notifyDataSetChanged();
            }
            //에러가 났을 때는 취소 시킨다
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

            //다이어리 클래스의 변수를 초기화

//
//             for (int i = 0; i < diaryHasmap.keySet().size(); i++) {
//             Dairy dairy = new Dairy();
//             dairy.mDate = diary.keySet();
//             dairy.mContent ="일기내용";
//             dairyList.add(dairy);
//
//             }
        // adapter List 설정
        adapter.setList(dairyList);
//        mRecyclerView.setAdapter(new MyRecyclerAdapter(mAlbumList, R.layout.row_album));

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        // 액티비티 안에서 Context 를 사용할 때는  ApplicationContext 를 사용하기보다 액티비티의 Context 를 사용하는게 좋음
        // 리사이클러뷰 setLayoutManager, setItemAnimator 객체 생성과 초기화
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

}