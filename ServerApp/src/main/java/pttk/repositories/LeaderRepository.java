package pttk.repositories;

import org.springframework.data.jpa.repository.Query;
import pttk.model.Employee;
import org.springframework.data.repository.CrudRepository;
import pttk.model.Leader;
import pttk.model.MountainGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public interface LeaderRepository extends CrudRepository<Leader, Long> {

    public Iterable<Leader> findAllByRights(MountainGroup mg);

    @Query(value = "SELECT * FROM leader natural join tourist natural join leaders_rights WHERE id_mountain_group =?1", nativeQuery = true)
    public ArrayList<Leader> findByMG(MountainGroup mg);

}
