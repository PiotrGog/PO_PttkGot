package pttk.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Report {
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int reportId;

    @Lob
    private byte[] evidenceIm;

    @ManyToOne
//    @MapsId("tripId")
    @JoinColumn(name="trip_trip_id")
    private Trip trip;

    @ManyToOne
//    @MapsId("routeSectionId")
    @JoinColumn(name="route_section_route_section_id")
    private RouteSection routeSection;

    @ManyToOne
//    @MapsId("leaderId")
    @JoinColumn(name="leader_tourist_id")
    private Leader leader;

    public Report() {
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public byte[] getEvidenceIm() {
        return evidenceIm;
    }

    public void setEvidenceIm(byte[] evidenceIm) {
        this.evidenceIm = evidenceIm;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public RouteSection getRouteSection() {
        return routeSection;
    }

    public void setRouteSection(RouteSection routeSection) {
        this.routeSection = routeSection;
    }

    public Leader getLeader() {
        return leader;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }
}
