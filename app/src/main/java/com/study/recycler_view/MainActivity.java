package com.study.recycler_view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
        initData();
    }

    private void initLayout() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }


    private void initData() {
        List<Album> mAlbumList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Album album = new Album();
            album.setTitle("Brown City");
            album.setArtist("김예림");
            album.setImage(R.drawable.ic_launcher);
            mAlbumList.add(album);
        }

//        mRecyclerView.setAdapter(new MyRecyclerAdapter(mAlbumList, R.layout.row_album));
        mRecyclerView.setAdapter(new MyRecyclerAdapter(mAlbumList));

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        // 액티비티 안에서 Context 를 사용할 때는  ApplicationContext 를 사용하기보다 액티비티의 Context 를 사용하는게 좋음
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

}