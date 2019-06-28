package com.study.recycler_view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

public class NewPage extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntent();
        // click_page와 연결을 시킨다
        setContentView(R.layout.click_page);
        // 버튼과 layout에 있는 버튼 아이디 연결
        Button btnReturn = (Button)findViewById(R.id.btnReturn);
        // 버튼을 눌렀을 때 페이지 끝내기
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
