package com.study.recycler_view.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by BJM on 2016-09-13.
 */
public abstract class SnapSelectedListener extends RecyclerView.OnScrollListener {

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int mid = getSelectedPosition(recyclerView);
        if (mid >= 0) {
            onSnapSelected(recyclerView, mid);
        }
    }

    /**
     * 선택(중앙) 위치를 반환
     */
    public int getSelectedPosition(RecyclerView recyclerView) {
        LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
        int first = llm.findFirstVisibleItemPosition();
        int last = llm.findLastVisibleItemPosition();
        int mid = (first + last) / 2;
        return mid;
    }

    public abstract void onSnapSelected(RecyclerView recyclerView, int position);
}
