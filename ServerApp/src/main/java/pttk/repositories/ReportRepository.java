package pttk.repositories;

import org.springframework.data.repository.CrudRepository;
import pttk.model.Report;
import pttk.model.RouteSection;
import pttk.model.Section;

public interface ReportRepository extends CrudRepository<Report, Integer> {
    public boolean existsByRouteSection(RouteSection rs);
}
