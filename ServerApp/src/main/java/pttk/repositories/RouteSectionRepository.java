package pttk.repositories;

import org.springframework.data.repository.CrudRepository;
import pttk.model.RouteSection;
import pttk.model.Section;

public interface RouteSectionRepository extends CrudRepository<RouteSection, Integer> {
}
