package com.study.recycler_view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class MainActivity extends Activity {

    private RecyclerView mRecyclerView;
    Change mChange;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
        initData();


        // 파이어베이스 데이터 서버전송
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference("test").child("111");
        mDatabaseReference.setValue("Hello, World!");
        mDatabaseReference.push();
    }



    // 이렇게 하면 해당 아이템의 위치를 알수없어서 안됨
    // 리스트뷰같은 형태에선 잘못된 사용
    public void onItemClick(View view) {

        // 파이어베이스 데이터 가져오는부분
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void initLayout() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }


    private void initData() {
        mChange = new Change() {
            @Override
            public void click(int position) {
//                Intent intent= new Intent(getApplicationContext(),
//                        NewPage.class);

                // 해당 액티비티의 Context를 사용
                // 인터페이스안에 메소드라서 단순 this 를 사용하면 해당 인터페이스를 가르킴
                // 따라서 MainActivity.this 로 클래스명.this 로 사용
             //   Intent intent = new Intent(MainActivity.this, NewPage.class);
              //  startActivity(intent);
            }
        };

        MyRecyclerAdapter adapter = new MyRecyclerAdapter();
        adapter.setInterface(mChange);      //미리 초기화한 인터페이스를 넣어줌
        List<Album> mAlbumList = new ArrayList<>();
        mRecyclerView.setAdapter(adapter);

        for (int i = 0; i < 20; i++) {
            Album album = new Album();
            album.setTitle("Brown City");
            album.setArtist(getResources().getString(R.string.app_name));
            album.setImage(R.drawable.ic_launcher);
            mAlbumList.add(album);
        }
        adapter.setList(mAlbumList);
//        mRecyclerView.setAdapter(new MyRecyclerAdapter(mAlbumList, R.layout.row_album));

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        // 액티비티 안에서 Context 를 사용할 때는  ApplicationContext 를 사용하기보다 액티비티의 Context 를 사용하는게 좋음
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

}