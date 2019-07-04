package com.study.recycler_view;

//Application Class는  어플리케이션 컴포넌트들 사이에서 공동으로 멤버들을 사용할 수 있게 해주는 편리한 공유 클래스를 제공
//
public class Application extends android.app.Application {

    public static final String PREF_NAME = "PREF_NAME";
    public static final String PREF_LOGIN_ID = "PREF_LOGIN_ID";

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
