package demo.domain;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Embeddable;


/**
 * Created btay xihuan on 18-6-2.
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // empty field will not map to our object
@Data
@Embeddable
public class UnitInfo {
    private final String runningId;
    private String bandmake;
    private String customerName;
    private String unitNumber;

    public UnitInfo() {this.runningId="";}

    public UnitInfo(String runningId) {this.runningId = runningId;}

    public UnitInfo(String runningId, String bandmake, String customerName, String unitNumber){
        this.runningId = runningId;
        this.bandmake = bandmake;
        this.customerName = customerName;
        this.unitNumber = unitNumber;
    }

}
