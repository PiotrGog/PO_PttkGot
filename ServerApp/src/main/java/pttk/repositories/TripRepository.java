package pttk.repositories;

import org.springframework.data.repository.CrudRepository;
import pttk.model.Section;
import pttk.model.Trip;

public interface TripRepository extends CrudRepository<Trip, Integer> {
    Trip findByTripId(int tripId);
}
