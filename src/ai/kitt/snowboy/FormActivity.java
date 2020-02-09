package ai.kitt.snowboy;
import ai.kitt.snowboy.audio.RecordingThread;
import ai.kitt.snowboy.audio.PlaybackThread;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import ai.kitt.snowboy.audio.AudioDataSaver;
import ai.kitt.snowboy.demo.R;


public class FormActivity extends Activity {


    private FloatingActionButton record_button;
    private Button play_button;
    private TextView log,name,age,gender,diagnosis,advice,symptoms,prescription;
    private TextView text;
    private ScrollView logView;
    static String strLog = null;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent intent;
    private int preVolume = -1;
    private static long activeTimes = 0;
    private int buttonState=0;
    private RecordingThread recordingThread;
    private PlaybackThread playbackThread;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_form);

        setUI();

//      setProperVolume();

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
//                    text.setText(matches.get(0));
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

                    if(m.containsKey("advice")){
                        String n="";
                        if(myAdd.contains("advice")){
                            n=advice.getText().toString()+" ";
                        }
                        ArrayList<String> a=m.get("advice");
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
        record_button = (FloatingActionButton) findViewById(R.id.mic);
        record_button.setOnClickListener(record_button_handle);
        record_button.setEnabled(true);

//        log = (TextView)findViewById(R.id.mic);
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
//        record_button.setText(R.string.btn1_stop);
    }

    private void stopRecording() {
        recordingThread.stopRecording();
//        updateLog(" ----> recording stopped ", "green");
//        record_button.setText(R.string.btn1_start);
    }

    private void sleep() {
        try { Thread.sleep(500);
        } catch (Exception e) {}
    }

    private OnClickListener record_button_handle = new OnClickListener() {
        // @Override
        public void onClick(View arg0) {

            if(buttonState==0){
                sleep();
                startRecording();
                buttonState=1;
            }else{
                stopRecording();
                sleep();
                buttonState=0;
            }

//            if(record_button.getText().equals(getResources().getString(R.string.btn1_start))) {
////                stopPlayback();
//                sleep();
//                startRecording();
//            } else {
//                stopRecording();
//                sleep();
//            }
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


    public void previewPdf(View view) {
        stopRecording();

        HashMap<String,String>personal = new HashMap<>();
        HashMap<String,String>medicinal = new HashMap<>();

        personal.put("Name",name.getText().toString());
        personal.put("Age",age.getText().toString());
        personal.put("Gender",gender.getText().toString());
        personal.put("Doctor Name","Doc1");

        medicinal.put("Symptoms",symptoms.getText().toString());
        medicinal.put("Diagnosis",diagnosis.getText().toString());
        medicinal.put("Prescriptions",prescription.getText().toString());
        medicinal.put("Advice",advice.getText().toString());

        savepdf(personal,medicinal);

        Intent intent = new Intent(this, PreviewActivity.class);
        startActivity(intent);
    }

    public void savepdf(HashMap<String,String>personal,HashMap<String,String>medicinal)
    {
        //create object of Document class
        Document mDoc =new Document();
        //pdf file name
//        String mFileName = new Date().toString();
        String mFileName = "filename";
        //pdf file path
        String mFilePath= Environment.getExternalStorageDirectory().toString() + "/" + mFileName +".pdf";
        try {

            Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
            //create instance of PdfWriter class
            PdfWriter.getInstance(mDoc,new FileOutputStream(mFilePath));

            //open the document for writing
            mDoc.open();
            //add author of the document (metadata) can also add other data
            mDoc.addAuthor("doctor name");

            //set table details
            PdfPTable pTable=new PdfPTable(2);
            PdfPCell col1;
            PdfPCell col2;

            //add personal details
            mDoc.add(new Paragraph("Personal details : "));
            pTable.setWidths(new int[]{2,3});
            pTable.setWidthPercentage(100);
            pTable.setSpacingBefore(15);
            for(Map.Entry element : personal.entrySet())
            {
                col1=new PdfPCell(new Phrase((String)element.getKey(),boldFont));
                col2=new PdfPCell(new Phrase((String)element.getValue(),normalFont));
                pTable.addCell(col1);
                pTable.addCell(col2);
            }
            mDoc.add(pTable);
            pTable.setSpacingAfter(15);



            mDoc.add(new Paragraph("Medicinal details : "));
            pTable=new PdfPTable(2);
            pTable.setWidths(new int[]{2,3});
            pTable.setWidthPercentage(100);
            pTable.setSpacingBefore(15);
            for(Map.Entry element : medicinal.entrySet())
            {
                col1=new PdfPCell(new Phrase((String)element.getKey(),boldFont));
                col2=new PdfPCell(new Phrase((String)element.getValue(),normalFont));
                pTable.addCell(col1);
                pTable.addCell(col2);
            }
            mDoc.add(pTable);

            //close document
            mDoc.close();

            //show file saved message with file name and path
            Toast.makeText(this, "File saved successfully", Toast.LENGTH_SHORT).show();


        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error in saving file", Toast.LENGTH_SHORT).show();
        }

    }
}
