package pttk.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Route {
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Id
    private int routeId;

    private int routePasses;

    private boolean isPopular;

    @OneToMany(mappedBy = "route")
    private Set<RouteSection> routeSections;

    public Route() {
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getRoutePasses() {
        return routePasses;
    }

    public void setRoutePasses(int routePasses) {
        this.routePasses = routePasses;
    }

    public boolean isPopular() {
        return isPopular;
    }

    public void setPopular(boolean popular) {
        isPopular = popular;
    }

    public Set<RouteSection> getRouteSections() {
        return routeSections;
    }

    public void setRouteSections(Set<RouteSection> routeSections) {
        this.routeSections = routeSections;
    }
}
