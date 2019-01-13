package pttk.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Leader extends Tourist {
    @Column(unique = true)
    @NotNull
    private int leaderId;

    @OneToMany(mappedBy = "leader")
    private Set<Report> reports;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "leaders_rights",
            joinColumns = {@JoinColumn(name="leaderId")},
            inverseJoinColumns = {@JoinColumn(name = "id_mountain_group")}
    )
    private Set<MountainGroup> rights;

    public Leader() {
    }

    public int getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    public Set<MountainGroup> getRights() {
        return rights;
    }

    public void setRights(Set<MountainGroup> rights) {
        this.rights = rights;
    }
}
