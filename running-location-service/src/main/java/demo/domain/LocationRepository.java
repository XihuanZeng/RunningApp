package demo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by xihuan on 18-6-2.
 */

// <Object type, Id type>
// Location is the class name that you add @Entity on
// can look into the interface JpaRepository that has findAll, save, delete
// the ultra parent class is CrudRepository, but extend that class you will not have paginzation
@RepositoryRestResource(path = "locations")
public interface LocationRepository extends JpaRepository<Location, Long> {
    // translate to SQLQuery, it is "select * from Locations where RunnerMovementType = movementType"
    // JPA will translate that for you with page information
    // naming is important as JPA will look into function name to do things
    // we don't care what the backend server(Oracle, MySQL, Mongo), we just need configure the JPA
    // this enables search
    @RestResource(path = "runners")
    Page<Location> findByRunnerMovementType(@Param("movementType") Location.RunnerMovementType movementType, Pageable pageable);
    //
    Page<Location> findByUnitInfoRunningId(@Param("runningId") String runningId, Pageable pageable);
}
