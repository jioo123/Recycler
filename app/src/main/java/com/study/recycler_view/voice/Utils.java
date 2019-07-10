package com.study.recycler_view.voice;

import android.os.Bundle;
import android.speech.SpeechRecognizer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String getVoice(Bundle recBundle){
        ArrayList<String> matches = recBundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String result = "";
        // 반복문 돌리면서 그때그때 들어오는 단어들 합치기
        for (int i = 0; i < matches.size(); i++) {
            result = result + matches.get(i);
        }
        return result;
    }

    public static String getDate(){
        //날짜변수 생성
        String dateString;
        //데이터포멧 초기화 생성
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd\\EEE",Locale.KOREA);
        //현재 날짜 설정
        dateString=  dateFormat.format(new Date());
        //값 리턴
        return dateString;
    }
}
