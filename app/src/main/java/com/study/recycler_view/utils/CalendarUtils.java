package com.study.recycler_view.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by BJM on 2016-09-13.
 */
public class CalendarUtils {
    // 같은 달인지 판단 하는 함수
    public static boolean isSameMonth(Calendar c1, Calendar c2) {
        // 값이 비었으면 false 리턴
        if (c1 == null || c2 == null)
            return false;
        // 기원,연도, 달이 같은지 판단
        return (c1.get(Calendar.ERA) == c2.get(Calendar.ERA)
                && c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH));
    }

    /**
     * <p>Checks if a calendar is today.</p>
     * @param calendar the calendar, not altered, not null.
     * @return true if the calendar is today.
     * @throws IllegalArgumentException if the calendar is <code>null</code>
     */
    // 오늘인지 판단, 인텐트로 값을 가져온다
    public static boolean isToday(Calendar calendar) {
        return isSameDay(calendar, Calendar.getInstance());
    }
    // 같은 날인지 판단
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        // 값이 비었으면 익셉션이 발생
        if (cal1 == null || cal2 == null)
            throw new IllegalArgumentException("The dates must not be null");
        // 기원, 연도, 날짜 값 같을 때
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    // 과거의 날인지 판단
    public static boolean isPastDay(Date date) {
        // 캘린더에서 instance로 값을 받아온다
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //캘린더 시간 보다 이전이면 true 리턴
        return (date.before(calendar.getTime())) ? true : false;
    }
    // 날짜의 주를 가져온다
    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        // 날짜 설정
        calendar.setTime(date);
        //캘린더의 주를 리턴
        return calendar.get(Calendar.DAY_OF_WEEK);
    }


}
