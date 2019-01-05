package pttk.service;

import org.springframework.data.util.Pair;
import pttk.model.Location;
import pttk.model.Section;

import java.util.ArrayList;

public class GraphPathDecorator {
    public ArrayList<Pair<Integer, Location>> locations = null;
    public ArrayList<Pair<Integer, Section>> sections = null;

    public GraphPathDecorator() {
        locations = new ArrayList<>();
        sections = new ArrayList<>();
    }
}
