package pttk.model;

import java.util.ArrayList;

public class ReportWrapper {
    private ArrayList<Report> reports;

    public ReportWrapper() {}

    public ReportWrapper(ArrayList<Report> reports) {
        this.reports = reports;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }
}
