package ai.kitt.snowboy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.time.LocalDate;

import ai.kitt.snowboy.demo.R;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        AppointmentData[] list = new AppointmentData[]{
                new AppointmentData("Sharat Sharma", "1 Feb"),
                new AppointmentData("Dhairya Patel", "1 Feb")
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        AppointmentAdapter adapter = new AppointmentAdapter(list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);

        PrescriptionData[] list2 = new PrescriptionData[]{
                new PrescriptionData("Bronchitis", "1 Feb")
        };

        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);
        PrescriptionAdapter adapter2 = new PrescriptionAdapter(list2);
        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager lm2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(lm2);
        recyclerView2.setAdapter(adapter2);

    }

    public void opnQRActivity(View view) {
        Intent intent = new Intent(this, qr_scanner.class);
        startActivity(intent);
    }
}
