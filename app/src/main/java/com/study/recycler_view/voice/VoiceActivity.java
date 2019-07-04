package com.study.recycler_view.voice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

public class VoiceActivity extends Activity {
    //변수 선언
    SpeechRecognizer mSpeech; // 음성인식 객체
    VoiceListener.SpeechListener mListener;

    @Override
    // class니까 onCreate 생성
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSpeech(); // 메소드 초기화

    }
    // initSpeech 함수 생성
    void initSpeech() {
        // QUESTION : 생성하고 초기화
        mListener = new VoiceListener.SpeechListener() {
            // 준비
            @Override
            public void ready() {
                System.out.println("@@@@@@@@@@  ready ");
            }
            // 메세지 녹음
            @Override
            public void getRecodingMessage(String result) {
                System.out.println("@@@@@@@@@@  getRecodingMessage " + result);
            }
//+ result.equals("시리")
            @Override
            //결과를 보여준다
            public void result(String result) {
                System.out.println("@@@@@@@@@@  result " + result);

            }
            // 에러 났을 때
            @Override
            public void onError(String errorMessage) {
                System.out.println("@@@@@@@@@@  result " + errorMessage);
                if(errorMessage.equals("스피치오류면 다시실행")){
                    speechStart();
                }
            }
        };
        speechStart();
    }

    void speechStart(){
        //음성인식 객체
        mSpeech = SpeechRecognizer.createSpeechRecognizer(this);
        // 음성인식 리스너 설정
        mSpeech.setRecognitionListener(VoiceListener.getInstance(mListener));
        // 음성인식 intent 생성
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // 음성인식 언어 설정
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "ko-KR");
        //데이터 설정
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        // 중간에 말하는 거 데이터로 받아온다. 중간중간 받아오려고 값 : true
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        // 음성인식 시작 intent로 보이스 레코더 실행
        mSpeech.startListening(intent);
    }
}
