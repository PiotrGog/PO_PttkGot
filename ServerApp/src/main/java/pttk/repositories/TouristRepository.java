package pttk.repositories;

import org.springframework.data.repository.CrudRepository;
import pttk.model.Section;
import pttk.model.Tourist;
import pttk.model.Trip;

public interface TouristRepository extends CrudRepository<Tourist, Integer> {
    Tourist findByTouristId(int touristId);
}
