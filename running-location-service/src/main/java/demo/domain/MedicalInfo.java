package demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Embeddable;

/**
 * Created by xihuan on 18-6-2.
 */

// To make this class that can be embedded in the Location, we need to add Embeddable annoation to this class
@JsonInclude(JsonInclude.Include.NON_NULL) // empty field will not map to our object
@Data
@Embeddable
public class MedicalInfo {
    private long bfr;

    private long fmi;

    // Jackson requires this
    public MedicalInfo() {
    }

    public MedicalInfo(long bfr, long fmi) {
        this.bfr = bfr;
        this.fmi = fmi;
    }
}
