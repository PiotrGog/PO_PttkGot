package pttk.service;

import org.springframework.data.util.Pair;
import pttk.model.Location;
import pttk.model.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which holds Path between nodes.
 */
public class GraphPathDecorator {
    public ArrayList<Pair<Integer, Location>> locations = null; /** locations in path */
    public ArrayList<Pair<Integer, Section>> sections = null; /** sections which build this path */

    public Integer altitudePoints = 0; /** altitude points for that path */
    public Integer distancePoints = 0; /** distance points for that path */

    /**
     * Default GraphPathDecorator constructor. initialize empty locations and sections lists.
     */
    public GraphPathDecorator() {
        locations = new ArrayList<>();
        sections = new ArrayList<>();
    }

    /**
     * Check if path has given location.
     * @param locationId id of location to check.
     * @return
     * true - path has location with the given Id
     * false - path does not have location with the given Id
     */
    public boolean hasLocation(Integer locationId) {
        for (Pair<Integer, Location> loc : locations) {
            if (loc.getSecond().getId().equals(locationId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if path has all locations.
     * @param locationsId ids of locations to check.
     * @return
     * true - path has locations with the given Ids
     * false - path does not have locations with the given Ids
     */
    public boolean hasLocations(List<Integer> locationsId) {
        for (Integer locationId : locationsId) {
            if (!this.hasLocation(locationId)) {
                return false;
            }
        }
        return true;
    }

}
