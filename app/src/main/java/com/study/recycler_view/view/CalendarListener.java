package com.study.recycler_view.view;

import android.view.View;

import java.util.Date;

/**
 * Created by BJM on 2016-09-13.
 */
public interface CalendarListener {
    void onDateSelected(View v, Date date);

    void inputDairy(Date date);
}
