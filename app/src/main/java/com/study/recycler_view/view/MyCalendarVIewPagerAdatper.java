package com.study.recycler_view.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;


import java.util.Calendar;
import java.util.Date;

/**
 * Created by BJM on 2016-09-13.
 * 달력 뷰페이저 아답터
 */
public class MyCalendarVIewPagerAdatper extends PagerAdapter {

    Context mContext;

    CalendarListener mCalendarListener = null;

    SparseArray<MyCalendarViewPagerView> mViewList = new SparseArray<MyCalendarViewPagerView>();

    Date mCurrentDate;
    int mMaxSize = 0;
    int mFirstDayOfWeek;

    public interface CalendarSelectedListener {
        void onDateSelected(View view, Date date);
    }

    public MyCalendarVIewPagerAdatper(Context context, int maxSize, int firstDayOfWeek) {
        mContext = context;
        mMaxSize = maxSize;
        this.mFirstDayOfWeek = firstDayOfWeek;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Calendar cal = Calendar.getInstance();
        cal.set(1970, Calendar.JANUARY, 1, 0, 0, 0);
        cal.add(Calendar.MONTH, position);
        final MyCalendarViewPagerView view = new MyCalendarViewPagerView(mContext);
        view.setFirstDayOfWeek(mFirstDayOfWeek);
        view.setCalendarListener(new CalendarSelectedListener() {
            @Override
            public void onDateSelected(View day, Date date) {
                if (mCalendarListener != null) {
                    mCalendarListener.onDateSelected(day ,date);
                }
                mCurrentDate = date;
            }
        });
        view.refreshCalendar(cal);
        ((ViewPager) container).addView(view);
        mViewList.put(position, view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViewList.remove(position);
    }

    public void notifyDataItemChanged(Calendar cal, int position) {
        MyCalendarViewPagerView view = mViewList.get(position);
        if (view == null) {
            return;
        }
        view.refreshCalendar(cal);
//		this.notifyDataSetChanged();	// 속도가 느려지는 문제로 인해서 전부다 로드하지 말고 생성된 view만 저장하고 있다가 해당 view만 업데이트
    }

    public Date getCurrentDate() {
        return mCurrentDate;
    }

    @Override
    public int getCount() {
        return mMaxSize;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    public void setCalendarListener(CalendarListener listener) {
        mCalendarListener = listener;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
