package ai.kitt.snowboy;

import java.util.Date;

public class PrescriptionData {
    String diseaseName;
    String date;

    public PrescriptionData(String patientName, String date) {
        this.diseaseName = patientName;
        this.date = date;
    }

    public String getPatientName() {
        return diseaseName;
    }

    public void setPatientName(String patientName) {
        this.diseaseName = patientName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
