package com.study.recycler_view.utils;

import com.study.recycler_view.view.DayView;
//날짜 하루하루 한칸씩 데코레이터 개념으로 해당 날짜를 ㅌ데코레이터 패턴으로
public interface DayDecorator {
    void decorate(DayView cell);
}
