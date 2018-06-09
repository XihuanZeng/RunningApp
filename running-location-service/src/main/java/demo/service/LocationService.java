package demo.service;

import demo.domain.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

// save Location to database
public interface LocationService {
    List<Location> saveRunningLocation(List<Location> runningLocations);

    void deleteAll();

    // service layer, will call the Repository one finally, but will do some abstraction
    Page<Location> findByRunnerMovementType(String movementType, Pageable pageable);

    Page<Location> findByRunningId(String runningId, Pageable pageable);
}
