package be.dhofief.farmwatchbackend.service;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Street {

    public static Map<String, Intersection> INTERSECTIONS_BY_STREETNAME = new HashMap<>();

    private String name;
    private Long lengthInSeconds;
    private Long intersectionIdFrom;
    private Long intersectionIdTo;

    public Street(String streetName, Long lengthInSeconds, Long intersectionIdFrom, Long intersectionIdTo){
        this.name = streetName;
        this.lengthInSeconds = lengthInSeconds;
        this.intersectionIdFrom = intersectionIdFrom;
        this.intersectionIdTo = intersectionIdTo;
        INTERSECTIONS_BY_STREETNAME.put(streetName, Intersection.INTERSECTION_BY_ID.get(intersectionIdTo));
    }


}
