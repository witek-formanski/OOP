package pl.edu.mimuw.simulation;

import pl.edu.mimuw.city.IStop;
import pl.edu.mimuw.city.TramLine;
import pl.edu.mimuw.city.Passenger;
import pl.edu.mimuw.city.Stop;
import pl.edu.mimuw.timeline.Event;
import pl.edu.mimuw.timeline.HeapTimeline;
import pl.edu.mimuw.timeline.ITimeline;
import pl.edu.mimuw.timeline.TimelineElement;
import pl.edu.mimuw.utils.Logger;

import java.util.Scanner;

public class Simulation {
    private static final ITimeline timeline = new HeapTimeline();
    private final int daysCount;
    private final IStop[] stops;
    private final Passenger[] passengers;
    private final TramLine[] tramLines;
    private final static Scanner scanner = new Scanner(System.in);

    public Simulation() {
        daysCount = scanner.nextInt();
        final int stopCapacity, stopsCount, passengersCount, tramCapacity, linesCount;
        stopCapacity = scanner.nextInt();
        stopsCount = scanner.nextInt();
        stops = new IStop[stopsCount];
        for (int i = 0; i < stopsCount; i++) {
            stops[i] = new Stop(scanner.next(), stopCapacity);
        }
        passengersCount = scanner.nextInt();
        passengers = new Passenger[passengersCount];
        for (int i = 0; i < passengersCount; i++) {
            passengers[i] = new Passenger(stops[Losowanie.losuj(0, stopsCount - 1)], i);
        }
        tramCapacity = scanner.nextInt();
        linesCount = scanner.nextInt();
        tramLines = new TramLine[linesCount];
        int numberOfTrams, routeLength, lastSideNumber = 0;
        for (int i = 0; i < linesCount; i++) {
            numberOfTrams = scanner.nextInt();
            routeLength = scanner.nextInt();
            tramLines[i] = new TramLine(i, numberOfTrams, routeLength, lastSideNumber, tramCapacity);
            lastSideNumber += numberOfTrams;
            String stopName;
            for (int j = 0; j < routeLength - 1; j++) {
                stopName = scanner.next();
                tramLines[i].getRoute().addStop(j, getStop(stopName));
                tramLines[i].getRoute().addTravelTime(j, scanner.nextInt());
            }
            stopName = scanner.next();
            tramLines[i].getRoute().addStop(routeLength - 1, getStop(stopName));
            tramLines[i].getRoute().setDepotTime(scanner.nextInt());
            tramLines[i].getRoute().setTotalTravelTime();
            tramLines[i].setTramFrequency();
        }
    }

    public void run() {
        for (int day = 0; day < daysCount; day++) {
            simulateDay(day);
        }
    }

    private void simulateDay(int day) {
        for (Passenger passenger : passengers) {
            passenger.startDay(day);
        }
        for (TramLine tramLine : tramLines) {
            tramLine.startDay(day);
        }

        TimelineElement event;
        while (!timeline.isEmpty()) {
            event = timeline.extractMin();
            Logger.log(event.toString());
            event.act();
        }
    }

    public static void insertEvent(Event event) {
        timeline.insert(event);
    }

    private Stop getStop(String stopName) {
        // ToDo: implement dictionary??
        return new Stop(stopName, 0);
    }
}
