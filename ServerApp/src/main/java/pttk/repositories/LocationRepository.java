package pttk.repositories;

import org.springframework.data.repository.CrudRepository;
import pttk.model.Location;

public interface LocationRepository extends CrudRepository<Location, Integer> {
}
