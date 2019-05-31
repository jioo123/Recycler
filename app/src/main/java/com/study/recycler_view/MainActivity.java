package com.study.recycler_view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.StaggeredGridLayoutManager;

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

    private void initLayout(){

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
    }


    private void initData(){

        List<Album> mAlbumList = new ArrayList<Album>();

        for (int i =0; i<20; i ++){

            Album album = new Album();
            album.setTitle("Brown City");
            album.setArtist("김예림");
            album.setImage(R.drawable.ic_launcher);
            mAlbumList.add(album);
        }

        mRecyclerView.setAdapter(new MyRecyclerAdapter(mAlbumList,R.layout.row_album));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

}