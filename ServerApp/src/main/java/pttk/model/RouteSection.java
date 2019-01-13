package pttk.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class RouteSection {
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Id
    private int routeSectionId;

    @ManyToOne
    @MapsId("routeId")
    private Route route;

    @ManyToOne
    @MapsId("id")
    private Section section;

    @OneToMany(mappedBy = "routeSection")
    private Set<Report> reports;

    public RouteSection() {
    }

    public int getRouteSectionId() {
        return routeSectionId;
    }

    public void setRouteSectionId(int routeSectionId) {
        this.routeSectionId = routeSectionId;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }
}
