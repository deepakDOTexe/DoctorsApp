package ai.kitt.snowboy;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import ai.kitt.snowboy.demo.R;

public class qr_scanner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);

        //request for permissions
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.VIBRATE},1000);
        SurfaceView surfaceView=findViewById(R.id.cameraView);
        final ImageView imageView=findViewById(R.id.imageView);
        final CameraSource cameraSource;


        BarcodeDetector barcodeDetector=new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        //is set preview size needed
        cameraSource=new CameraSource.Builder(this,barcodeDetector).setRequestedPreviewSize(640,480).setAutoFocusEnabled(true).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrs=detections.getDetectedItems();
                if(qrs.size()!=0)
                {
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator= (Vibrator) getApplicationContext().getSystemService(VIBRATOR_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                vibrator.vibrate(VibrationEffect.createOneShot(150,10));
                            }
                            Log.i("mytag",qrs.valueAt(0).displayValue);
                            Intent intent=new Intent(getApplicationContext(),FormActivity.class);
                            startActivity(intent);
                            Log.i("mytag","here");
                        }
                    });


                }
            }
        });

    }
}
