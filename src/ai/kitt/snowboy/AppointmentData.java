package ai.kitt.snowboy;

import java.util.Date;

public class AppointmentData {
    String patientName;
    String date;

    public AppointmentData(String patientName, String date) {
        this.patientName = patientName;
        this.date = date;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
