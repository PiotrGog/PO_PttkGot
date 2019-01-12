package pttk.service;

import org.springframework.data.util.Pair;
import pttk.model.Location;
import pttk.model.Section;

import java.util.ArrayList;
import java.util.List;

public class GraphPathDecorator {
    public ArrayList<Pair<Integer, Location>> locations = null;
    public ArrayList<Pair<Integer, Section>> sections = null;

    public Integer altitudePoints = 0;
    public Integer distancePoints = 0;

    public GraphPathDecorator() {
        locations = new ArrayList<>();
        sections = new ArrayList<>();
    }

    public boolean hasLocation(Integer locationId) {
        for (Pair<Integer, Location> loc : locations) {
            if (loc.getSecond().getId().equals(locationId)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasLocations(List<Integer> locationsId) {
        for (Integer locationId : locationsId) {
            if (!this.hasLocation(locationId)) {
                return false;
            }
        }
        return true;
    }

}
