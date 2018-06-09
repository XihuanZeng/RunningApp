import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by xihuan on 18-6-6.
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Document
@Data
@RequiredArgsConstructor(onConstructor = @__(@PersistenceConstructor))
public class SupplyLocation {

    // Mongodb's id is String
    @Id
    private String id;
    private String address1;
    private String address2;
    private String city;

    // immutable, will call this constructor PersistenceConstructor instead
    @GeoSpatialIndexed
    @JsonIgnore // we don't pass Point, so there will not be Json map for this field
    private final Point location;
    private String state;
    private String zip;
    private String type;

    public SupplyLocation() { this.location = new Point(0,0); }

    @JsonCreator
    public SupplyLocation(@JsonProperty("longitude") double longitude,
                          @JsonProperty("latitude") double latitude) {
        this.location = new Point(longitude, latitude);
    }

    public double getLongitude() { return this.location.getX(); }

    public double getLatitude() { return this.location.getY(); }
}
