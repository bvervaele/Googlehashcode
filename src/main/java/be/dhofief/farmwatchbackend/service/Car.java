package be.dhofief.farmwatchbackend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static be.dhofief.farmwatchbackend.service.Street.INTERSECTIONS_BY_STREETNAME;

public class Car {

    public static Map<Long, Long> CARPATHSREMAINING = new HashMap<>();

    private Intersection currentLocation;
    private List<String> path;
    private List<Street> streetsToFollow;
    private int intersectionsCrossed = 0;
    private long id;

    public Car(long id, List<String> pathToFollow){
        this.id = id;
        this.path = pathToFollow;
        this.intersectionsCrossed = 0;
        this.streetsToFollow = pathToFollow.stream()
                .map(Intersection.STREETS_BY_NAME::get)
                .collect(Collectors.toList());
    }

    public void init(){
        CARPATHSREMAINING.put(id, calcPath2());
        walkTrafficLights();
    }

    private void walkTrafficLights(){
        for(int i = 0; i < path.size()-1; i++){
            String streetname = path.get(i);
            INTERSECTIONS_BY_STREETNAME.get(streetname).intializeOrUpdateTrafficLight(streetname);
        }
    }

    private Long calcPath2(){
        Long sum = 0L;
        for(int i = 1; i < streetsToFollow.size(); i++){
            sum += streetsToFollow.get(i).getLengthInSeconds();
        }
        return sum;
    }

}
