package com.study.recycler_view.voice;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
// 콜백 리스너 RecognitionListener
public class VoiceListener implements RecognitionListener{
    //변수 선언
    static VoiceListener mInstance;
    static SpeechListener mSpeechListener;

    // 보이스 리스너의 경우 마이크를 켜놓으면 다른 곳에서 마이크 접근이 안된다
    // 그래서 이걸 앱 내에서 다 똑같은 객체로 사용할 수 있게 보이스 리스너를 싱크로나이즈 맞추고
    // 하나의 리스너로 다 사용할 수 있게 한 것이다.
    public static VoiceListener getInstance(SpeechListener listener) {
        //mInstance이 null일 때
        if (mInstance == null) {
            //스레드를 동기화
            synchronized (VoiceListener.class) {
                // 객체 초기화
                mInstance = new VoiceListener();
                mSpeechListener = listener;
            }
        }
        // 값 리턴
        return mInstance;
    }

    static String getSpeechMessage(Bundle bundle) {
        //음성인식 결과 저장할 list , 인식된 데이터 리스트 받아옴
        ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String result = "";
        // 반복문 돌리면서 그때그때 들어오는 단어들 합치기
        for (int i = 0; i < matches.size(); i++) {
            result = result + matches.get(i);
        }
        return result;
    }

    @Override
    //말을 들을 준비가 되었다는 콜백
    public void onReadyForSpeech(Bundle params) {
        mSpeechListener.ready();
    }

    // 입력이 시작되면
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
    //음성이 끝났을 때 호출
    public void onEndOfSpeech() {

    }

    @Override
    //에러 났을 때 호출
    public void onError(int error) {
        //메세지 변수
        String message;

        switch (error) {
            //오디오 에러
            case SpeechRecognizer.ERROR_AUDIO:
                message = "ERROR_AUDIO";
                break;
            //클라이언트 에러
            case SpeechRecognizer.ERROR_CLIENT:
                message = "ERROR_CLIENT";
                break;
            //퍼미션없음
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "ERROR_INSUFFICIENT_PERMISSIONS";
                break;
            //네트워크 에러
            case SpeechRecognizer.ERROR_NETWORK:
                message = "ERROR_NETWORK";
                break;
            //네트워크 타임아웃
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "ERROR_NETWORK_TIMEOUT";
                break;
            // 찾을 수 없음
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "ERROR_NO_MATCH";
                break;
            // 바쁨
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "ERROR_RECOGNIZER_BUSY";
                break;
            // 말하는 시간 초과
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "ERROR_SPEECH_TIMEOUT";
                break;
            // 알 수 없음
            default:
                message = "UNKNOWN";
                break;
        }
        // 에러 메세지를 띄어준다
        mSpeechListener.onError(message);
        Log.e("@@@@@@@@@@@@@@@@", message);


    }
    // 음성인식 결과를 받음
    @Override
    public void onResults(Bundle results) {
        mSpeechListener.result(getSpeechMessage(results));

    }
    // 중간중간 말하는 거 받아오는 부분
    @Override
    public void onPartialResults(Bundle partialResults) {
        mSpeechListener.getRecodingMessage(getSpeechMessage(partialResults));
    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
    // SpeechListener 인터페이스 생성
    public interface SpeechListener {
        void ready();

        void getRecodingMessage(String result);

        void result(String result);

        void onError(String errorMessage);

    }
}
