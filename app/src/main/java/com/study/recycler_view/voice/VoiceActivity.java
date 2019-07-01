package com.study.recycler_view.voice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

public class VoiceActivity extends Activity {

    SpeechRecognizer mSpeech;
    VoiceListener.SpeechListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSpeech();

    }

    void initSpeech() {
        mListener = new VoiceListener.SpeechListener() {
            @Override
            public void ready() {
                System.out.println("@@@@@@@@@@  ready ");
            }

            @Override
            public void getRecodingMessage(String result) {
                System.out.println("@@@@@@@@@@  getRecodingMessage " + result);
            }

            @Override
            public void result(String result) {
                System.out.println("@@@@@@@@@@  result " + result);
            }
        };
        mSpeech = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeech.setRecognitionListener(VoiceListener.getInstance(mListener));
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "ko-KR");
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        mSpeech.startListening(intent);
    }
}
