package com.study.recycler_view.voice;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;

public class VoiceListener implements RecognitionListener{

    static VoiceListener mInstance;
    static SpeechListener mSpeechListener;

    public static VoiceListener getInstance(SpeechListener listener) {
        if (mInstance == null) {
            synchronized (VoiceListener.class) {
                mInstance = new VoiceListener();
                mSpeechListener = listener;
            }
        }
        return mInstance;
    }

    static String getSpeechMessage(Bundle bundle) {
        ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String result = "";
        for (int i = 0; i < matches.size(); i++) {
            result = result + matches.get(i);
        }
        return result;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        mSpeechListener.ready();
    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {
        String message;

        switch (error) {

            case SpeechRecognizer.ERROR_AUDIO:
                message = "ERROR_AUDIO";
                break;

            case SpeechRecognizer.ERROR_CLIENT:
                message = "ERROR_CLIENT";
                break;

            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "ERROR_INSUFFICIENT_PERMISSIONS";
                break;

            case SpeechRecognizer.ERROR_NETWORK:
                message = "ERROR_NETWORK";
                break;

            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "ERROR_NETWORK_TIMEOUT";
                break;

            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "ERROR_NO_MATCH";
                break;

            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "ERROR_RECOGNIZER_BUSY";
                break;

            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "ERROR_SPEECH_TIMEOUT";
                break;

            default:
                message = "UNKNOWN";
                break;
        }

        Log.e("@@@@@@@@@@@@@@@@", message);


    }

    @Override
    public void onResults(Bundle results) {
        mSpeechListener.result(getSpeechMessage(results));

    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        mSpeechListener.getRecodingMessage(getSpeechMessage(partialResults));
    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }

    public interface SpeechListener {
        void ready();

        void getRecodingMessage(String result);

        void result(String result);

    }
}
