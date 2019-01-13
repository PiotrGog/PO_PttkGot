package pttk.repositories;

import org.springframework.data.repository.CrudRepository;
import pttk.model.Route;
import pttk.model.RouteSection;
import pttk.model.Section;

public interface RouteRepository extends CrudRepository<Route, Integer> {
}