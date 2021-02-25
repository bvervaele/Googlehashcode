package be.dhofief.farmwatchbackend.service;

import lombok.Data;
import lombok.ToString;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static be.dhofief.farmwatchbackend.service.Street.INTERSECTIONS_BY_STREETNAME;

@Data
@ToString
public class Intersection {

    public static HashMap<Long, Intersection> INTERSECTION_BY_ID = new HashMap<>();
    public static HashMap<String, Street> STREETS_BY_NAME = new HashMap<>();
    public static Supplier<Stream<Intersection>> INTERSECTIONS_WITH_TRAFFICLIGHTS = () ->
            INTERSECTION_BY_ID.values()
                    .stream()
                    .filter(intersection -> intersection.getNumberOfTrafficLights() > 0);

    private Map<String, Intersection> nextintersections = new HashMap<>();

    private Map<String, TrafficLight> trafficLightMap = new HashMap<>();

    private long id;


    public Intersection(long id) {
        this.id = id;
    }

    public Collection<String> getNextintersections(){
        return nextintersections.keySet();
    }

    public void createTrafficLightProcentVerdeling(){
        long totalScores = 0L;
        for(Map.Entry<String, TrafficLight> trafficLightEntry : trafficLightMap.entrySet()){
            String streetName = trafficLightEntry.getKey();
            TrafficLight light = trafficLightEntry.getValue();
            totalScores += light.getScore();
        }
        for(Map.Entry<String, TrafficLight> trafficLightEntry : trafficLightMap.entrySet()){
            String streetName = trafficLightEntry.getKey();
            TrafficLight light = trafficLightEntry.getValue();
            light.setCycleValue(
                    stategy1(totalScores, light.getScore())
            );
        }
    }

    private long stategy1(long totalScores, long score) {
        double percent = 100 * ((double) score / (double) totalScores);
        if(percent < 10L){
            return 1L;
        }
        return (long) Math.floor((double) percent / (double) 10.0);
    }

    public long getNumberOfTrafficLights(){
        return trafficLightMap.values().stream()
                .filter(light -> light.getScore() > 0L)
                .count();
    }

    public static void linkIntersections(Long intersectionFrom, Long intersectionTo, String streetName, Long streetLengthInSeconds) {
        if(!INTERSECTION_BY_ID.containsKey(intersectionFrom)){
            INTERSECTION_BY_ID.put(intersectionFrom, new Intersection(intersectionFrom));
        }
        if(!INTERSECTION_BY_ID.containsKey(intersectionTo)){
            INTERSECTION_BY_ID.put(intersectionTo, new Intersection(intersectionTo));
        }
        Street street = new Street(streetName, streetLengthInSeconds, intersectionFrom, intersectionTo);
        INTERSECTIONS_BY_STREETNAME.get(streetName).addStreet(INTERSECTION_BY_ID.get(intersectionTo), streetName);
        STREETS_BY_NAME.put(streetName, street);
    }

    private void addStreet(Intersection other, String streetName) {
        this.nextintersections.put(streetName, other);
    }

    public void intializeOrUpdateTrafficLight(String streetname) {
        if(!getTrafficLightMap().containsKey(streetname)){
            getTrafficLightMap().put(streetname, new TrafficLight(streetname));
        }
        getTrafficLightMap().get(streetname).usedByCar();
    }
}
