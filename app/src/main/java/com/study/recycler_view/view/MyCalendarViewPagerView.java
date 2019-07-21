package com.study.recycler_view.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.recycler_view.R;
import com.study.recycler_view.utils.CalendarUtils;
import com.study.recycler_view.utils.StringUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by BJM on 2016-09-13.
 * 달력 뷰페이저 아답터에 들어갈 달력레이아웃
 */
public class MyCalendarViewPagerView extends LinearLayout {

    private int disabledDayBackgroundColor;
    private int disabledDayTextColor;
    private int calendarBackgroundColor;
    private int dayOfWeekTextColor;

    private int firstDayOfWeek = Calendar.SUNDAY;

    private static final String DAY_OF_MONTH_TEXT = "DAY_OF_MONTH_TEXT";
    private static final String DAY_OF_MONTH_CONTAINER = "DAY_OF_MONTH_CONTAINER";

    private Context mContext;
    private View view;
    private Calendar mCurrentCalendar;
    private Date lastSelectedDay;
    private MyCalendarVIewPagerAdatper.CalendarSelectedListener calendarListener;

    public MyCalendarViewPagerView(Context context) {
        this(context, null);
    }

    public MyCalendarViewPagerView(Context context, AttributeSet attr) {
        super(context, attr);
        mContext = context;
        calendarBackgroundColor = ContextCompat.getColor(mContext, android.R.color.white);
        dayOfWeekTextColor = ContextCompat.getColor(mContext, android.R.color.black);
        disabledDayBackgroundColor = ContextCompat.getColor(mContext, android.R.color.white);
        disabledDayTextColor = ContextCompat.getColor(mContext, R.color.cal_enabled_textcolor);
        initializeCalendar();
    }

    private void initializeCalendar() {
        final LayoutInflater inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflate.inflate(R.layout.calendarview_view_mycalendar_viewpagerview, this, true);

        if (mCurrentCalendar == null) {
            mCurrentCalendar = Calendar.getInstance();
        }

        refreshCalendar(mCurrentCalendar);
    }

    public void setFirstDayOfWeek(int firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }

    public int getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    @SuppressWarnings("WrongConstant")
    public void refreshCalendar(Calendar cal) {
        mCurrentCalendar = cal;
        mCurrentCalendar.setFirstDayOfWeek(getFirstDayOfWeek());
        setDaysInCalendar();
    }

    @SuppressWarnings("WrongConstant")
    private void setDaysInCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCurrentCalendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.setFirstDayOfWeek(getFirstDayOfWeek());

        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        int dayOfMonthIndex = getWeekIndex(firstDayOfMonth, calendar);
        final Calendar startCalendar = (Calendar) calendar.clone();
        startCalendar.add(Calendar.DATE, -(dayOfMonthIndex - 1));

        DayView dayView;
        ViewGroup dayOfMonthContainer;
        for (int i = 1; i < 43; i++) {
            dayOfMonthContainer = (ViewGroup) view.findViewWithTag(DAY_OF_MONTH_CONTAINER + i);
            dayView = (DayView) view.findViewWithTag(DAY_OF_MONTH_TEXT + i);
            if (dayView == null)
                continue;

            dayOfMonthContainer.setOnClickListener(null);
            dayView.bind(startCalendar.getTime());
            dayView.setVisibility(View.VISIBLE);

            if (CalendarUtils.isSameMonth(calendar, startCalendar)) {
                dayView.setmMonth(true);
                dayOfMonthContainer.setOnClickListener(onDayOfMonthClickListener);
                dayView.setBackgroundColor(calendarBackgroundColor);
                dayView.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
                if (Integer.parseInt(dayView.getTag().toString().replace(DAY_OF_MONTH_TEXT, "")) % 7 == 1) {
                    dayView.setTextColor(getResources().getColor(R.color.saturday_textcolor));
                } else if (Integer.parseInt(dayView.getTag().toString().replace(DAY_OF_MONTH_TEXT, "")) % 7 == 0) {
                    dayView.setTextColor(getResources().getColor(R.color.sunday_textcolor));
                }else {
                    dayView.setTextColor(Color.BLACK);

                }

                markDayAsCurrentDay(startCalendar);
            } else {
                dayView.setmMonth(false);
                dayView.setBackgroundColor(disabledDayBackgroundColor);
                dayView.setTextColor(disabledDayTextColor);
            }

            startCalendar.add(Calendar.DATE, 1);
            dayOfMonthIndex++;
        }

        ViewGroup weekRow = (ViewGroup) view.findViewWithTag("weekRow6");
        dayView = (DayView) view.findViewWithTag("DAY_OF_MONTH_TEXT36");
        if (dayView.getVisibility() != VISIBLE) {
            weekRow.setVisibility(GONE);
        } else {
            weekRow.setVisibility(VISIBLE);
        }
    }

    private int getWeekIndex(int weekIndex, Calendar currentCalendar) {
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


    public void setCalendarListener(MyCalendarVIewPagerAdatper.CalendarSelectedListener calendarListener) {
        this.calendarListener = calendarListener;
    }

    private OnClickListener onDayOfMonthClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            String tagId = (String) view.getTag();
            tagId = tagId.substring(DAY_OF_MONTH_CONTAINER.length(), tagId.length());
            final TextView dayOfMonthText = (TextView) view.findViewWithTag(DAY_OF_MONTH_TEXT + tagId);
            final Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(getFirstDayOfWeek());
            calendar.setTime(mCurrentCalendar.getTime());
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(StringUtils.cutString(dayOfMonthText.getText().toString(), "\n", true)));

            markDayAsSelectedDay(calendar.getTime());

            markDayAsCurrentDay(mCurrentCalendar);

            if (calendarListener != null)
                calendarListener.onDateSelected(dayOfMonthText, calendar.getTime());
        }
    };

    public void markDayAsSelectedDay(Date currentDate) {
        final Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setFirstDayOfWeek(getFirstDayOfWeek());
        currentCalendar.setTime(currentDate);
        clearDayOfTheMonthStyle(lastSelectedDay);
        storeLastValues(currentDate);
    }

    public void markDayAsCurrentDay(Calendar calendar) {
        if (calendar != null && CalendarUtils.isToday(calendar)) {
            DayView dayOfMonth = getDayOfMonthText(calendar);
            dayOfMonth.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        }
    }

    private DayView getDayOfMonthText(Calendar currentCalendar) {
        return (DayView) getView(DAY_OF_MONTH_TEXT, currentCalendar);
    }

    private View getView(String key, Calendar currentCalendar) {
        int index = getDayIndexByDate(currentCalendar);
        View childView = view.findViewWithTag(key + index);
        return childView;
    }

    private int getDayIndexByDate(Calendar currentCalendar) {
        int monthOffset = getMonthOffset(currentCalendar);
        int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        int index = currentDay + monthOffset;
        return index;
    }

    private int getMonthOffset(Calendar currentCalendar) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(getFirstDayOfWeek());
        calendar.setTime(currentCalendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayWeekPosition = calendar.getFirstDayOfWeek();
        int dayPosition = calendar.get(Calendar.DAY_OF_WEEK);

        if (firstDayWeekPosition == Calendar.SUNDAY) {
            return dayPosition - 1;
        } else {
            if (dayPosition == Calendar.SUNDAY) {
                return Calendar.FRIDAY;
            } else {
                return dayPosition - 2;
            }
        }
    }

    private void storeLastValues(Date currentDate) {
        lastSelectedDay = currentDate;
    }

    private void clearDayOfTheMonthStyle(Date currentDate) {
        if (currentDate != null) {
            final Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(getFirstDayOfWeek());
            calendar.setTime(currentDate);

            final DayView dayView = getDayOfMonthText(calendar);
            dayView.setBackgroundColor(calendarBackgroundColor);
            dayView.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
            dayView.setTextColor(dayOfWeekTextColor);
        }
    }

}
