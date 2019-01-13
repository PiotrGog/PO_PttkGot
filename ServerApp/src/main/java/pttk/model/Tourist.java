package pttk.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Tourist extends User {

    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int touristId;


    @ManyToMany()
    @JoinTable(
            name = "tourist_trips",
            joinColumns = {@JoinColumn(name="touristId")},
            inverseJoinColumns = {@JoinColumn(name = "tripId")}
    )
    private Set<Trip> touristTrips;

    public Tourist() {
    }

    public int getTouristId() {
        return touristId;
    }

    public void setTouristId(int touristId) {
        this.touristId = touristId;
    }

    public Set<Trip> getTouristTrips() {
        return touristTrips;
    }

    public void setTouristTrips(Set<Trip> touristTrips) {
        this.touristTrips = touristTrips;
    }
}
