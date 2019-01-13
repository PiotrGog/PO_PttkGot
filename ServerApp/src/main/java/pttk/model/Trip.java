package pttk.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
public class Trip {

    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Id
    private Integer tripId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private Date startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private Date finishDate;

    @ManyToOne
    @MapsId("route")
    private Route route;


    @ManyToMany(mappedBy = "touristTrips")
    private Set<Tourist> tourists;


    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Trip() {
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getSimpleDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (startDate.equals(finishDate))
            return dateFormat.format(startDate);
        else
            return dateFormat.format(startDate) + "->" + dateFormat.format(finishDate);
    }

    public Set<Tourist> getTourists() {
        return tourists;
    }

    public void setTourists(Set<Tourist> tourists) {
        this.tourists = tourists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Objects.equals(tripId, trip.tripId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tripId);
    }
}
