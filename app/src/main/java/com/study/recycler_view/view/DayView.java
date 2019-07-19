package com.study.recycler_view.view;

import android.content.Context;
import android.util.AttributeSet;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by BJM on 2016-09-13.
 */
public class DayView extends android.support.v7.widget.AppCompatTextView {
    private Date mDate;
    private boolean mMonth = false;

    public DayView(Context context) {
        this(context, null, 0);
    }

    public DayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode())
            return;
    }

    public void bind(Date date) {
        this.mDate = date;

        final SimpleDateFormat df = new SimpleDateFormat("d");

        int day = Integer.parseInt(df.format(date));
        setText(String.valueOf(day));
    }

    public void setmMonth(boolean mMonth) {
        this.mMonth = mMonth;
    }

    public boolean isMonth() {
        return mMonth;
    }

    public Date getmDate() {
        return mDate;
    }
}