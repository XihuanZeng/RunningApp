import org.springframework.beans.factory.parsing.Location;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by xihuan on 18-6-6.
 */
@RepositoryRestResource(path = "supply")
public class SupplyLocationRepository extends PagingAndSortingRepository<SupplyLocation, String> {
    SupplyLocation findFirstByLocationNear(@Param("location") Point location);
}
