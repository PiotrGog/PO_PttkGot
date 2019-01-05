package pttk.service;

import org.jgrapht.graph.DefaultWeightedEdge;
import pttk.model.Location;
import pttk.model.Section;

public class CustomWeightedEdge extends DefaultWeightedEdge {
    private Section section_ = null;

    public Section getSection() {
        return section_;
    }

    public void setSection(Section section_) {
        this.section_ = section_;
    }
}
