package demo.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * Created by xihuan on 18-6-2.
 */


// Object Model 1 field - 1 column
// One to many, many to one, one to one
// Map to the table LOCATIONS,
@JsonInclude(JsonInclude.Include.NON_NULL) // empty field will not map to our object
@Entity
@Data
@javax.persistence.Table(name = "LOCATIONS")
public class Location {
    public enum GpsStatus{
        EXCELLENT, OK, UNRELIABLE, BAD, NOFIX, UNKNOWN;
    }
    public enum RunnerMovementType{
        STOPPED, IN_MOTION;
    }

    @Id
    @GeneratedValue
    private long id;

    @Embedded
    private MedicalInfo medicalInfo;

    @Embedded
    @AttributeOverride(name = "bandmake", column = @Column(name = "unit_band_name"))
    private UnitInfo unitinfo;

    private double latitude;
    private double longitude;
    private String heading;
    private double gpsSpeed;
    private GpsStatus gpsStatus;
    private double odometer;
    private double totalRunningTime;
    private double totalIdelTime;
    private double totalCalorieBurnt;
    private String address;
    private Date timestamp = new Date();
    private String gearProvider;
    private RunnerMovementType runnerMovementType;
    private String serviceType;

    // when json passed into service, we need a constructor class to init the object
    @JsonCreator
    public Location(@JsonProperty("runningId") String runningId) {
        this.unitinfo = new UnitInfo((runningId));
    }

    public Location(UnitInfo unitinfo) {this.unitinfo = unitinfo;}

    public String getRunningId() {return this.unitinfo == null? null: this.unitinfo.getRunningId();}


}
