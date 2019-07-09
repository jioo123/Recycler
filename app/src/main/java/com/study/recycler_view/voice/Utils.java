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
        String dateString;
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd\\EEE",Locale.KOREA);
        dateString=  dateFormat.format(new Date());
        return dateString;
    }
}
