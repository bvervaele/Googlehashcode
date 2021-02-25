package be.dhofief.farmwatchbackend.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static be.dhofief.farmwatchbackend.service.Intersection.INTERSECTIONS_WITH_TRAFFICLIGHTS;
import static java.util.stream.Collectors.toList;

public class Task1 {

    private List<String> inputLines;
    private long simulationLengthSeconds = 0;
    private long intersectionCount = 0;
    private long streetCount = 0;
    private long carCount = 0;
    private long pointsPerReachedDestination = 0;

    private FileWriter out;

    public Task1(Collection<String> inputLines, FileWriter fileWriter) throws IOException {
        this.inputLines = new ArrayList<>(inputLines);
        this.out = fileWriter;
        processInputLines();
    }

    public void processInputLines() throws IOException {
        String[] configLine = inputLines.get(0).split(" ");
        simulationLengthSeconds = Long.parseLong(configLine[0]);
        intersectionCount = Long.parseLong(configLine[1]);
        streetCount = Long.parseLong(configLine[2]);
        carCount = Long.parseLong(configLine[3]);
        pointsPerReachedDestination = Long.parseLong(configLine[4]);

        for(int i = 1; i < streetCount+1; i++){
            String[] input = inputLines.get(i).split(" ");
            Intersection.linkIntersections(Long.parseLong(input[0]), Long.parseLong(input[1]), input[2], Long.parseLong(input[3]));
        }

        // init cars & populate traffic light counts
        for(long i = streetCount+1; i < inputLines.size(); i++){
            String[] input = inputLines.get((int) i).split(" ");
            List<String> remaningInput = Arrays.stream(input).skip(1).collect(toList());
            Car car = new Car(i, remaningInput);
            car.init();
        }

        // Apply cycle strategy
        for(Intersection intersection : Intersection.INTERSECTION_BY_ID.values()){
            intersection.createTrafficLightProcentVerdeling();
        }

        // Output decisions
        // There are X intersactions with traffic light schedules:
        writeLine(
                INTERSECTIONS_WITH_TRAFFICLIGHTS.get().count()
        );
        // On intersection 1 the incoming streets are
        for(Intersection intersection : INTERSECTIONS_WITH_TRAFFICLIGHTS.get().collect(Collectors.toList())){
            writeLine(intersection.getId());
            writeLine(intersection.getNumberOfTrafficLights());
            for(TrafficLight trafficLight : intersection.getTrafficLightMap().values()){
                writeLine(trafficLight.toString());
            }
        }

        out.flush();
        out.close();

    }

    public void doStuff(){
        // ..
    }

    public void writeLine(Object line) throws IOException {
        out.write(line.toString() + "\n");
    }

}
