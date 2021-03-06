package ai.kitt.snowboy;

import ai.kitt.snowboy.audio.RecordingThread;
import ai.kitt.snowboy.audio.PlaybackThread;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

import ai.kitt.snowboy.audio.AudioDataSaver;
import ai.kitt.snowboy.demo.R;



public class Demo extends Activity {

    private Button record_button;
    private Button play_button;
    private TextView log,name,age,gender,diagnosis,advice,symptoms,prescription;
    private TextView text;
    private ScrollView logView;
    static String strLog = null;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent intent;
    private int preVolume = -1;
    private static long activeTimes = 0;

    private RecordingThread recordingThread;
    private PlaybackThread playbackThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        setUI();
        
//        setProperVolume();

        AppResCopy.copyResFromAssetsToSD(this);
        
        activeTimes = 0;
        recordingThread = new RecordingThread(handle, new AudioDataSaver());
        playbackThread = new PlaybackThread();
    }

    public void getSpeechInput(){
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                mSpeechRecognizer.stopListening();
                startRecording();
            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null){
                    text.setText(matches.get(0));
                    String s=matches.get(0);
                    HashSet<String> myAdd=new HashSet<>();
                    String[] splited=s.split("\\s+");


                    HashMap<String,ArrayList<String>> m=new HashMap<>();

                    String key=splited[0].toLowerCase();


                    for(int i=1;i<splited.length;i++){

                        if(("name".equalsIgnoreCase(splited[i]))||("age".equalsIgnoreCase(splited[i]))||("gender".equalsIgnoreCase(splited[i]))||("symptoms".equalsIgnoreCase(splited[i]))||("diagnosis".equalsIgnoreCase(splited[i]))||("prescription".equalsIgnoreCase(splited[i]))||("advise".equalsIgnoreCase(splited[i]))){
                            key=splited[i].toLowerCase();
                            System.out.println(key);
                            continue;
                        }

                        if(!m.containsKey(key)){
                            m.put(key,new ArrayList<String>());
                        }
                        if(splited[i].equalsIgnoreCase("add")){
                            myAdd.add(key);
                            continue;
                        }
                        if(!splited[i].equalsIgnoreCase("clear")){
                            m.get(key).add(splited[i]);
                        }
                        
                    }

                    if(m.containsKey("name")){
                        String n="";
                        if(myAdd.contains("name")){
                            n=name.getText().toString()+" ";
                        }
                        ArrayList<String> a=m.get("name");
                        for(String i:a) n=n+" "+i;
                        name.setText(n);
                    }

                    if(m.containsKey("age")){
                        String n="";
                        if(myAdd.contains("age")){
                            n=age.getText().toString()+" ";
                        }
                        ArrayList<String> a=m.get("age");
                        for(String i:a) n=n+" "+i;
                        age.setText(n);
                    }

                    if(m.containsKey("gender")){
                        String n="";
                        if(myAdd.contains("gender")){
                            n=gender.getText().toString()+" ";
                        }
                        ArrayList<String> a=m.get("gender");
                        for(String i:a) n=n+" "+i;
                        gender.setText(n);
                    }

                    if(m.containsKey("advise")){
                        String n="";
                        if(myAdd.contains("advise")){
                            n=advice.getText().toString()+" ";
                        }
                        ArrayList<String> a=m.get("advise");
                        for(String i:a) n=n+" "+i;
                        advice.setText(n);
                    }

                    if(m.containsKey("diagnosis")){
                        String n="";
                        if(myAdd.contains("diagnosis")){
                            n=diagnosis.getText().toString()+" ";
                        }
                        ArrayList<String> a=m.get("diagnosis");
                        for(String i:a) n=n+" "+i;
                        diagnosis.setText(n);
                    }

                    if(m.containsKey("prescription")){
                        String n="";
                        if(myAdd.contains("prescription")){
                            n=prescription.getText().toString()+" ";
                        }
                        ArrayList<String> a=m.get("prescription");
                        for(String i:a) n=n+" "+i;
                        prescription.setText(n);
                    }

                    if(m.containsKey("symptoms")){
                        String n="";
                        if(myAdd.contains("symptoms")){
                            n=symptoms.getText().toString()+" ";
                        }
                        ArrayList<String> a=m.get("symptoms");
                        for(String i:a) n=n+" "+i;
                        symptoms.setText(n);
                    }

                }

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
    }


    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    
    private void setUI() {
        record_button = (Button) findViewById(R.id.btn_test1);
        record_button.setOnClickListener(record_button_handle);
        record_button.setEnabled(true);
        
        play_button = (Button) findViewById(R.id.btn_test2);
        play_button.setOnClickListener(play_button_handle);
        play_button.setEnabled(true);
        text=(TextView)findViewById(R.id.textInput) ;
        log = (TextView)findViewById(R.id.log);
        logView = (ScrollView)findViewById(R.id.logView);
        name=(TextView)findViewById(R.id.name) ;
        age=(TextView)findViewById(R.id.age) ;
        gender=(TextView)findViewById(R.id.gender) ;
        symptoms=(TextView)findViewById(R.id.symptoms) ;
        diagnosis=(TextView)findViewById(R.id.diagnosis) ;
        prescription=(TextView)findViewById(R.id.prescription) ;
        advice=(TextView)findViewById(R.id.advice) ;
    }
    
    private void setMaxVolume() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        preVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        updateLog(" ----> preVolume = "+preVolume, "green");
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        updateLog(" ----> maxVolume = "+maxVolume, "green");
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        updateLog(" ----> currentVolume = "+currentVolume, "green");
    }
    
    private void setProperVolume() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        preVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        updateLog(" ----> preVolume = "+preVolume, "green");
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        updateLog(" ----> maxVolume = "+maxVolume, "green");
        int properVolume = (int) ((float) maxVolume * 0.2); 
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, properVolume, 0);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        updateLog(" ----> currentVolume = "+currentVolume, "green");
    }
    
    private void restoreVolume() {
        if(preVolume>=0) {
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, preVolume, 0);
            updateLog(" ----> set preVolume = "+preVolume, "green");
            int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            updateLog(" ----> currentVolume = "+currentVolume, "green");
        }
    }

    private void startRecording() {
        recordingThread.startRecording();
//        updateLog(" ----> recording started ...", "green");
        record_button.setText(R.string.btn1_stop);
    }

    private void stopRecording() {
        recordingThread.stopRecording();
//        updateLog(" ----> recording stopped ", "green");
        record_button.setText(R.string.btn1_start);
    }

//    private void startPlayback() {
//        updateLog(" ----> playback started ...", "green");
//        play_button.setText(R.string.btn2_stop);
//        // (new PcmPlayer()).playPCM();
//        playbackThread.startPlayback();
//    }
//
//    private void stopPlayback() {
//        updateLog(" ----> playback stopped ", "green");
//        play_button.setText(R.string.btn2_start);
//        playbackThread.stopPlayback();
//    }

    private void sleep() {
        try { Thread.sleep(500);
        } catch (Exception e) {}
    }
    
    private OnClickListener record_button_handle = new OnClickListener() {
        // @Override
        public void onClick(View arg0) {
            if(record_button.getText().equals(getResources().getString(R.string.btn1_start))) {
//                stopPlayback();
                sleep();
                startRecording();
            } else {
                stopRecording();
                sleep();
            }
        }
    };
    
    private OnClickListener play_button_handle = new OnClickListener() {
        // @Override
        public void onClick(View arg0) {
            if (play_button.getText().equals(getResources().getString(R.string.btn2_start))) {
                stopRecording();
                sleep();
//                startPlayback();
            } else {
//                stopPlayback();
            }
        }
    };
     
    public Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            MsgEnum message = MsgEnum.getMsgEnum(msg.what);
            switch(message) {
                case MSG_ACTIVE:
                    activeTimes++;
                    stopRecording();
                    getSpeechInput();
                    sleep();
                    mSpeechRecognizer.startListening(intent);

//                    updateLog(" ----> Detected " + activeTimes + " times", "green");
//                    // Toast.makeText(Demo.this, "Active "+activeTimes, Toast.LENGTH_SHORT).show();
//                    showToast("Active "+activeTimes);
                    break;
                case MSG_INFO:
                    updateLog(" ----> "+message);
                    break;
                case MSG_VAD_SPEECH:
                    updateLog(" ----> normal voice", "blue");
                    break;
                case MSG_VAD_NOSPEECH:
                    updateLog(" ----> no speech", "blue");
                    break;
                case MSG_ERROR:
                    updateLog(" ----> " + msg.toString(), "red");
                    break;
                default:
                    super.handleMessage(msg);
                    break;
             }
        }
    };

     public void updateLog(final String text) {

//         log.post(new Runnable() {
//             @Override
//             public void run() {
//                 if (currLogLineNum >= MAX_LOG_LINE_NUM) {
//                     int st = strLog.indexOf("<br>");
//                     strLog = strLog.substring(st+4);
//                 } else {
//                     currLogLineNum++;
//                 }
//                 String str = "<font color='white'>"+text+"</font>"+"<br>";
//                 strLog = (strLog == null || strLog.length() == 0) ? str : strLog + str;
//                 log.setText(Html.fromHtml(strLog));
//             }
//        });
//        logView.post(new Runnable() {
//            @Override
//            public void run() {
//                logView.fullScroll(ScrollView.FOCUS_DOWN);
//            }
//        });
    }


    static int MAX_LOG_LINE_NUM = 200;
    static int currLogLineNum = 0;

    public void updateLog(final String text, final String color) {
        log.post(new Runnable() {
            @Override
            public void run() {
                if(currLogLineNum>=MAX_LOG_LINE_NUM) {
                    int st = strLog.indexOf("<br>");
                    strLog = strLog.substring(st+4);
                } else {
                    currLogLineNum++;
                }
                String str = "<font color='"+color+"'>"+text+"</font>"+"<br>";
                strLog = (strLog == null || strLog.length() == 0) ? str : strLog + str;
                log.setText(Html.fromHtml(strLog));
            }
        });
        logView.post(new Runnable() {
            @Override
            public void run() {
                logView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private void emptyLog() {
        strLog = null;
        log.setText("");
    }

    @Override
     public void onDestroy() {
         restoreVolume();
         recordingThread.stopRecording();
         super.onDestroy();
     }
}
