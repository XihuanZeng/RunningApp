package demo.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by xihuan on 18-6-8.
 */
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Point {

    private Double latitude;
    private Double longitude;
}
