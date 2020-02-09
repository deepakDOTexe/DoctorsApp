package ai.kitt.snowboy;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;

import java.io.File;

import ai.kitt.snowboy.demo.R;

public class PreviewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public void preview(View view) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/filename.pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void share(View view) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/filename.pdf");
        Uri uri = Uri.fromFile(file);


        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setType("application/pdf");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.setPackage("com.whatsapp");

        startActivity(share);
    }

    public void shareEmail(View view) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/filename.pdf");
        Uri uri = Uri.fromFile(file);


        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setType("application/pdf");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.setPackage("com.google.android.gm");

        startActivity(share);

    }
}
