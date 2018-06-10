package demo.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by xihuan on 18-6-2.
 */


// Object Model 1 field - 1 column
// One to many, many to one, one to one
// Map to the table LOCATIONS,
// Table is optional as JPA can infer it from the class name
@JsonInclude(JsonInclude.Include.NON_NULL) // empty field will not map to our object
@Entity
@Data
@Table(name = "LOCATIONS")
public class Location {
    public enum GpsStatus{
        EXCELLENT, OK, UNRELIABLE, BAD, NOFIX, UNKNOWN;
    }
    public enum RunnerMovementType{
        STOPPED, IN_MOTION;
    }

    // Id here is mandantory
    // generatedValue can do auto generate id for you
    @Id
    @GeneratedValue
    private long id;

    // Embedded makes it get the entire object instance
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fmi", column = @Column(name = "medical_fmi")),
            @AttributeOverride(name = "bfr", column = @Column(name = "medical_bfr"))
    })
    private MedicalInfo medicalInfo;

    // unit band name will be unitInfo
    @Embedded
    @AttributeOverride(name = "bandMake", column = @Column(name = "unit_band_name"))
    private UnitInfo unitInfo;

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

    public Location(){
        this.unitInfo = null;
    }
    // when json passed into service, we need a constructor class to init the object
    // we only take the runningId from the Json get passed
    @JsonCreator
    public Location(@JsonProperty("runningId") String runningId) {
        this.unitInfo = new UnitInfo((runningId));
    }

    public Location(UnitInfo unitinfo) {this.unitInfo = unitinfo;}

    // this is not a getter, hence not covered by lombok
    public String getRunningId() {return this.unitInfo == null? null: this.unitInfo.getRunningId();}


}
