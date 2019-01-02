package pttk.repositories;

import org.springframework.data.repository.CrudRepository;
import pttk.model.MountainGroup;
import pttk.model.MountainRange;

public interface MountainGroupRepository extends CrudRepository<MountainGroup, Integer> {
}
