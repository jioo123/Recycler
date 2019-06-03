package com.study.recycler_view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private RecyclerView mRecyclerView;
    Change mchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
        initData();

        mchange = new Change() {
            @Override
            public void click(int position) {
                Intent intent= new Intent(getApplicationContext(),
                        NewPage.class);
                startActivity(intent);
            }
        };




    }
    public void onItemClick(View view){
        Toast.makeText(this,"버튼을 눌렀습니다.",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),NewPage.class);


    }

    private void initLayout() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }


    private void initData() {
        MyRecyclerAdapter adapter = new MyRecyclerAdapter();
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