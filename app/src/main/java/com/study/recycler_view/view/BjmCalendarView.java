package com.study.recycler_view.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.recycler_view.R;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by BJM on 2016-09-13.
 */
public class BjmCalendarView extends LinearLayout {

    // 변수 설정
    static final String DAY_OF_WEEK = "dayOfWeek";

    int mYearLayoutBackgroundColor;
    int mMonthLayoutBackgroundColor;
    int mYearLayoutTextColor;
    int mMonthLayoutTextColor;
    int mWeekLayoutBackgroundColor;
    int mWeekSundayTextColor;
    int mWeekSaturdayTextColor;
    int mWeekDaysTextColor;
    //한 주의 첫번째 날은 일요일
    int mFirstDayOfWeek = Calendar.SUNDAY;


    Context mContext;
    View mRootView;
    ImageButton mPreviousMonthButton;
    ImageButton mNextMonthButton;
    TextView mUnderText;
    Calendar mCurrentCalendar;
    CalendarListener mCalendarListener;

    ViewPager mPager;
    MyCalendarVIewPagerAdatper mAdapter;

    int mMaxYear;
    RecyclerView mRecyclerView;
    CalendarYearAdapter mCalYearAdapter;
    SelectedPositionRunnable mSelectedPositionRunnable;

    // 왜 this 쓰는지 모르겠음
    // 아래 BjmCalendarView 에 생성자로 넣기위해 this를 사용
    public BjmCalendarView(Context context) {
        this(context, null);
    }

    public BjmCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        getAttributes(attrs);
        Calendar currentCal = Calendar.getInstance();
        mMaxYear = currentCal.get(Calendar.YEAR) + 100 - (currentCal.get(Calendar.YEAR) % 50); // 2016 => 2100, 2057 => 2150
        initializeCalendar(currentCal);
    }

    // 레이아웃의 속성을 정의
    void getAttributes(AttributeSet attrs) {
        final TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.MyCalendarView, 0, 0);
        mYearLayoutBackgroundColor = typedArray.getColor(R.styleable.MyCalendarView_yearLayoutBackgroundColor, ContextCompat.getColor(mContext, R.color.year_background));
        mMonthLayoutBackgroundColor = typedArray.getColor(R.styleable.MyCalendarView_monthLayoutBackgroundColor, ContextCompat.getColor(mContext, android.R.color.white));
        mYearLayoutTextColor = typedArray.getColor(R.styleable.MyCalendarView_yearLayoutTextColor, ContextCompat.getColor(mContext, android.R.color.white));
        mMonthLayoutTextColor = typedArray.getColor(R.styleable.MyCalendarView_monthLayoutTextColor, ContextCompat.getColor(mContext, android.R.color.black));
        mWeekLayoutBackgroundColor = typedArray.getColor(R.styleable.MyCalendarView_weekLayoutBackgroundColor, ContextCompat.getColor(mContext, android.R.color.white));
        mWeekSundayTextColor = typedArray.getColor(R.styleable.MyCalendarView_weekSundayTextColor, ContextCompat.getColor(mContext, R.color.sunday_textcolor));
        mWeekSaturdayTextColor = typedArray.getColor(R.styleable.MyCalendarView_weekSaturdayTextColor, ContextCompat.getColor(mContext, R.color.saturday_textcolor));
        mWeekDaysTextColor = typedArray.getColor(R.styleable.MyCalendarView_weekDaysTextColor, ContextCompat.getColor(mContext, android.R.color.black));
        typedArray.recycle();
    }

    // 뷰의 항목들 초기화 (버튼이나 달력 리스트뷰 아답터 등)
    void initializeCalendar(Calendar currentCal) {

        final LayoutInflater inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = inflate.inflate(R.layout.calendarview_view_mycalendar, this, true);

        mPreviousMonthButton = mRootView.findViewById(R.id.btn_month_left);
        mNextMonthButton = mRootView.findViewById(R.id.btn_month_right);
        mUnderText = mRootView.findViewById(R.id.dairy_txt);
        mUnderText.setMovementMethod(new ScrollingMovementMethod());
        mPreviousMonthButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentCalendar.add(Calendar.MONTH, -1);

                refreshCalendar(mCurrentCalendar);
            }
        });

        mNextMonthButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentCalendar.add(Calendar.MONTH, +1);
                refreshCalendar(mCurrentCalendar);
            }
        });

        ImageButton cancelButton = (ImageButton) mRootView.findViewById(R.id.btn_cancel);
        ImageButton okButton = (ImageButton) mRootView.findViewById(R.id.btn_ok);

        cancelButton.setOnClickListener(mOnClickListener);
        okButton.setOnClickListener(mOnClickListener);
        findViewById(R.id.dairy_txt).setOnClickListener(mOnClickListener);

        mPager = (ViewPager) mRootView.findViewById(R.id.viewpager);
        int todayYear = currentCal.get(Calendar.YEAR);

        int maxSize = ((todayYear - 1970) + 50) * 12;

        mAdapter = new MyCalendarVIewPagerAdatper(mContext, maxSize, getFirstDayOfWeek());
        mPager.setAdapter(mAdapter);
        mPager.clearOnPageChangeListeners();
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentCalendar = Calendar.getInstance();
                mCurrentCalendar.set(Calendar.YEAR, 1970);
                mCurrentCalendar.set(Calendar.MONTH, Calendar.JANUARY);
                mCurrentCalendar.add(Calendar.MONTH, position);
                refreshCalendar(mCurrentCalendar);

                if (mCalendarListener != null) {
//                    mCalendarListener.onMonthChanged(mCurrentCalendar.getTime()); // 월 바뀜 주석처리.
                }
            }
        });
        mPager.setCurrentItem(((currentCal.get(Calendar.YEAR) - 1970) * 12) + currentCal.get(Calendar.MONTH), false);

        setFirstDayOfWeek(Calendar.SUNDAY);
        // Set weeks days titles
        initializeWeekLayout();
        refreshCalendar(currentCal);

    }

    OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mCalendarListener != null) {
//                if (v.getId() == R.id.btn_cancel) {
//                    mCalendarListener.onCancel();
//                } else if (v.getId() == R.id.btn_ok) {
//                    mCalendarListener.onOkClick(mAdapter.getCurrentDate() == null ?
//                            new Date(System.currentTimeMillis())
//                            : mAdapter.getCurrentDate());
//                }
                if(v.getId() == R.id.dairy_txt){
                    mCalendarListener.inputDairy(mAdapter.getCurrentDate() == null ?
                            new Date(System.currentTimeMillis())
                            : mAdapter.getCurrentDate());
                }
            }
        }
    };

    public void setFirstDayOfWeek(int mFirstDayOfWeek) {
        this.mFirstDayOfWeek = mFirstDayOfWeek;
    }

    public int getFirstDayOfWeek() {
        return mFirstDayOfWeek;
    }

    @SuppressWarnings("WrongConstant")
    public void refreshCalendar(Calendar cal) {
        mCurrentCalendar = cal;
        mCurrentCalendar.setFirstDayOfWeek(getFirstDayOfWeek());

        // Set date title
        initializeMonthLayout();

        if (mAdapter != null) {
            mAdapter.notifyDataItemChanged(mCurrentCalendar, mPager.getCurrentItem());
        }
    }

    // 년월 리스트뷰 초기화
    void initializeMonthLayout() {
        View yearLayout = mRootView.findViewById(R.id.yearLayout);
        yearLayout.setBackgroundColor(mYearLayoutBackgroundColor);
        yearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalYearAdapter.setYear(true);
                toggleYearLayout();
            }
        });

        View monthLayout = mRootView.findViewById(R.id.monthLayout);
        monthLayout.setBackgroundColor(mMonthLayoutBackgroundColor);
        monthLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalYearAdapter.setYear(false);
                toggleYearLayout();
            }
        });

        String yearText = mCurrentCalendar != null ? mCurrentCalendar.get(Calendar.YEAR) + "" : "";
        TextView tvYear = (TextView) yearLayout.findViewById(R.id.tv_year);
        tvYear.setTextColor(mYearLayoutTextColor);
        tvYear.setText(yearText);

        String monthText = mCurrentCalendar != null ? (mCurrentCalendar.get(Calendar.MONTH) + 1) + "월" : "";
        TextView tvMonth = (TextView) monthLayout.findViewById(R.id.tv_month);
        tvMonth.setTextColor(mMonthLayoutTextColor);
        tvMonth.setText(monthText);

        if (mCalYearAdapter == null) {
            mCalYearAdapter = new CalendarYearAdapter(1970, mMaxYear);
        }
        if (mRecyclerView == null) {
            mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.list_year);
            mRecyclerView.setAdapter(mCalYearAdapter);
            mRecyclerView.setItemAnimator(null);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.addOnItemTouchListener(new JmItemTouchListener() {
                @Override
                public void onClick(int position) {
                    int monthPosition = mPager.getCurrentItem() % 12;

                    int calendarPosition;
                    if (mCalYearAdapter.isYear()) {
                        int yearPosition = position * 12;
                        calendarPosition = yearPosition + monthPosition;
                    } else {
                        int yearPosition = mPager.getCurrentItem() - monthPosition;
                        calendarPosition = yearPosition + position;
                    }
                    mPager.setCurrentItem(calendarPosition, false);
                    mRecyclerView.setVisibility(View.GONE);
                }
            });

            // 연도 중앙에 위치한 아이템 변경시 강조 설정 변경을 위한 스크롤 감지
            mRecyclerView.addOnScrollListener(new SnapSelectedListener() {
                @Override
                public void onSnapSelected(RecyclerView recyclerView, int position) {
                    postSelectedPosition(position);
                }
            });

            SnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(mRecyclerView);
        }
    }

    // 요일 초기화
    void initializeWeekLayout() {
        TextView dayOfWeek;
        String dayOfTheWeekString;

        // 배경 설정
        View weekLayout = mRootView.findViewById(R.id.weekLayout);
        weekLayout.setBackgroundColor(mWeekLayoutBackgroundColor);

        final String[] weekDaysArray = new DateFormatSymbols().getShortWeekdays();
        for (int i = 1; i < weekDaysArray.length; i++) {
            dayOfTheWeekString = weekDaysArray[i];
            if (dayOfTheWeekString.length() > 3) {
                dayOfTheWeekString = dayOfTheWeekString.substring(0, 3).toUpperCase();
            }

            dayOfWeek = (TextView) mRootView.findViewWithTag(DAY_OF_WEEK + getWeekIndex(i, mCurrentCalendar));
            dayOfWeek.setText(dayOfTheWeekString);
            if (dayOfTheWeekString.equals("일") || dayOfTheWeekString.equalsIgnoreCase("sun")) {
                dayOfWeek.setTextColor(mWeekSundayTextColor);
            } else if (dayOfTheWeekString.equals("토") || dayOfTheWeekString.equalsIgnoreCase("sat")) {
                dayOfWeek.setTextColor(mWeekSaturdayTextColor);
            } else {
                dayOfWeek.setTextColor(mWeekDaysTextColor);
            }
        }

    }

    // 인덱스
    int getWeekIndex(int weekIndex, Calendar currentCalendar) {
        int firstDayWeekPosition = currentCalendar.getFirstDayOfWeek();
        if (firstDayWeekPosition == Calendar.SUNDAY) {
            return weekIndex;
        } else {
            if (weekIndex == Calendar.SUNDAY) {
                return Calendar.SATURDAY;
            } else {
                return weekIndex - 1;
            }
        }
    }

    public void setCalendarListener(CalendarListener mCalendarListener) {
        this.mCalendarListener = mCalendarListener;
        if (mAdapter != null) {
            mAdapter.setCalendarListener(mCalendarListener);
        }
    }

    public void setUnderText(String text) {
        mUnderText.setText(text);
    }

    /**
     * 연도 레이아웃 토글
     */
    void toggleYearLayout() {
        if (mRecyclerView.getVisibility() == View.VISIBLE) {
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);

            // 선택 연도로 즉시 이동
            if (mRecyclerView.getLayoutManager().getChildCount() > 0) {
                scrollToSelectedPosition();
            }

            // 레이아웃 로딩 후 이동
            else {
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollToSelectedPosition();
                    }
                });
            }
        }
    }

    /**
     * 선택한 연도로 위치로 이동
     */
    void scrollToSelectedPosition() {
        int position = mCalYearAdapter.isYear() ? mPager.getCurrentItem() / 12 : mPager.getCurrentItem() % 12;
        mCalYearAdapter.setSelectedPosition(position);

        View snapView = mRecyclerView.getLayoutManager().getChildAt(0);
        int offset = (mRecyclerView.getHeight() - snapView.getHeight()) / 2;
        LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        llm.scrollToPositionWithOffset(position, offset);
    }

    /**
     * MainThread 에서 mCalendarAdapter.setSelectedPosition() 실행
     */
    void postSelectedPosition(int position) {
        if (mSelectedPositionRunnable == null) {
            mSelectedPositionRunnable = new SelectedPositionRunnable();
        }
        mSelectedPositionRunnable.setPosition(position);
        mRecyclerView.removeCallbacks(mSelectedPositionRunnable);
        mRecyclerView.post(mSelectedPositionRunnable);
    }

    class SelectedPositionRunnable implements Runnable {
        int mPosition;

        public void setPosition(int position) {
            mPosition = position;
        }

        @Override
        public void run() {
            mCalYearAdapter.setSelectedPosition(mPosition);
        }
    }

}
