package com.study.recycler_view.jioovoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.study.recycler_view.JiooApplication;
import com.study.recycler_view.voice.Utils;

public class JiooVoiceActivity extends Activity {

    //변수 정의
    SpeechRecognizer mSpeech;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 초기화
        mDatabase = JiooApplication.getDirayFirebase();
        //초기화
        startRecoding();


    }

    void startRecoding(){
        //음성인식 객체
        mSpeech = SpeechRecognizer.createSpeechRecognizer(this);
        // 음성인식 리스너 설정
        mSpeech.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
//                mDatabase.child(Utils.getDate()).setValue("onReadyForSpeech", new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//                        print("onReadyForSpeech");
//
//                    }
//                });

            }

            @Override
            public void onBeginningOfSpeech() {
                print("onBeginningOfSpeech");

            }

            @Override
            public void onRmsChanged(float rmsdB) {
                print("onRmsChanged " +rmsdB);

            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                print("onBufferReceived");

            }

            @Override
            public void onEndOfSpeech() {
                print("onEndOfSpeech");

            }

            @Override
            public void onError(int error) {
                print("onError" +error);

            }

            @Override
            public void onResults(Bundle results) {
                mDatabase.setValue(Utils.getVoice(results), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        print("onResults");
                    }
                });

            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                print("onPartialResults");

            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                print("onEvent");
            }
        });
        // 음성인식 intent 생성
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // 음성인식 언어 설정
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"ko-KR");
        //데이터 설정 키, 값
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        // 중간에 말하는 거 데이터로 받아온다. 중간중간 받아오려고 값 : true
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        // 음성인식 시작 intent로 보이스 레코더 실행

        mSpeech.startListening(intent);
    }

    void print(String message) {
        System.out.println("Jioo  : " + message);
    }

}
