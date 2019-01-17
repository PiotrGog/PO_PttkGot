package pttk.service;

import org.jgrapht.graph.DefaultWeightedEdge;
import pttk.model.Location;
import pttk.model.Section;

/**
 * Custom weighted edge implementation based on DefaultWeightedEdge class.
 * CustomWeightedEdge contains section additionally.
 */
public class CustomWeightedEdge extends DefaultWeightedEdge {
    private Section section_ = null;

    /**
     * section getter
     * @return section which is represented by edge
     */
    public Section getSection() {
        return section_;
    }

    /**
     * section setter
     * @param section represented by edge
     */
    public void setSection(Section section) {
        this.section_ = section;
    }
}
