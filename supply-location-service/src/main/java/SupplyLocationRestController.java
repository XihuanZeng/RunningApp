import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by xihuan on 18-6-6.
 */

@RestController
public class SupplyLocationRestController {

    private SupplyLocationRepository repository;

    // dependency injection
    @Autowired
    public SupplyLocationRestController(SupplyLocationRepository repository){
        this.repository = repository;
    }

    @RequestMapping(value = "bulk/supplyLocation", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestBody List<SupplyLocation> locations) {
        this.repository.save(locations);
    }

    @RequestMapping(value = "/purge", method = RequestMethod.DELETE)
    public void delete() {
        this.repository.deleteAll();
    }
}
