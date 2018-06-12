import org.springframework.beans.factory.parsing.Location;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by xihuan on 18-6-6.
 */

// Note here for simplicity, we don't use the service layer, just implement this interface
@RepositoryRestResource(path = "supplyLocations")  // browser can direct request, instead of using service since we don't have service layer
public interface SupplyLocationRepository extends PagingAndSortingRepository<SupplyLocation, String> {
    // find the nearest location from current location
    // findFirstBy follows naming standard
    SupplyLocation findFirstByLocationNear(@Param("location") Point location);
}


