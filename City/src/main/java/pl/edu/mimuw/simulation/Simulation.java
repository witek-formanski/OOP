package pl.edu.mimuw.simulation;

import pl.edu.mimuw.city.IStop;
import pl.edu.mimuw.city.TramLine;
import pl.edu.mimuw.city.Passenger;
import pl.edu.mimuw.city.Stop;
import pl.edu.mimuw.main.Losowanie;
import pl.edu.mimuw.events.Event;
import pl.edu.mimuw.timeline.HeapTimeline;
import pl.edu.mimuw.timeline.ITimeline;
import pl.edu.mimuw.timeline.TimelineElement;
import pl.edu.mimuw.utils.Logger;
import pl.edu.mimuw.utils.Time;

import java.util.Arrays;
import java.util.Scanner;

public class Simulation {
    private static final ITimeline timeline = new HeapTimeline();
    private final int daysCount;
    private final IStop[] stops;
    private final Passenger[] passengers;
    private final TramLine[] tramLines;
    private final static Scanner scanner = new Scanner(System.in);
    private final int[] ridesCounts;
    private final int[] waitingTimes;
    private final int[] waitingPassengersCount;

    public Simulation() {
        daysCount = scanner.nextInt();
        ridesCounts = new int[daysCount];
        waitingTimes = new int[daysCount];
        waitingPassengersCount = new int[daysCount];
        final int stopCapacity, stopsCount, passengersCount, tramCapacity, linesCount;
        stopCapacity = scanner.nextInt();
        stopsCount = scanner.nextInt();
        stops = new IStop[stopsCount];
        for (int i = 0; i < stopsCount; i++) {
            stops[i] = new Stop(scanner.next(), stopCapacity);
        }
        Arrays.sort(stops, (stop1, stop2) -> stop1.getName().compareTo(stop2.getName()));
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

        summarizeInput();
    }

    private void summarizeInput() {
        Logger.log("Days count: " + daysCount);
        Logger.log("Stops count: " + stops.length);
        if (stops.length > 0) {
            Logger.log("The stops are:");
        }
        for (IStop stop : stops) {
            Logger.log("\t" + stop.getName());
        }
        Logger.log("Passengers count: " + passengers.length);
        Logger.log("Tram lines count: " + tramLines.length);
        for (TramLine tramLine : tramLines) {
            Logger.log("\tTrams of line number " + tramLine.getNumber() + " will run every " + tramLine.getTramFrequency() + " minutes.");
            Logger.log("\tTotal travel time of line number " + tramLine.getNumber() + " is " + tramLine.getRoute().getTotalTravelTime() + ".");
            Logger.log("\tThere are " + tramLine.getTramsCount() + " trams in line number " + tramLine.getNumber() + ".");
            Logger.log("\tThe route of the line number " + tramLine.getNumber() + " consist of the following stops:");
            for (int i = 0; i < tramLine.getRoute().getStopsCount(); i++) {
                Logger.log("\t\t" + tramLine.getRoute().getStopOfIndex(i).getName());
            }
        }

        Logger.newLine();
    }

    public void run() {
        for (int day = 1; day <= daysCount; day++) {
            simulateDay(day);
        }
        printSummary();
    }

    private void printSummary() {
        Logger.log("STATS");
        Logger.newLine();
        int totalRidesCount = 0, totalWaitingTime = 0, totalWaitingPassengers = 0;
        Time waitingTime;
        for (int i = 0;  i < daysCount; i++ ) {
            Logger.log("DAY " + (i+1) + ":");
            waitingTime = new Time(waitingTimes[i]);
            Logger.log("Total rides count: " + ridesCounts[i] + ".");
            Logger.log("Total waiting time (hours:minutes): " + waitingTime.toStringInHours() + ".");
            totalRidesCount += ridesCounts[i];
            totalWaitingTime += waitingTimes[i];
            totalWaitingPassengers += waitingPassengersCount[i];
            Logger.newLine();
        }
        Time averageWaitingTime = new Time(totalWaitingTime / totalWaitingPassengers);
        Logger.log("SUMMARY");
        Logger.log("Total rides count: " + totalRidesCount + ".");
        Logger.log("Average waiting time (hours:minutes): " + averageWaitingTime.toStringInHours() + ".");
    }

    private void simulateDay(int day) {
        Logger.log("DAY " + day);

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

        timeline.clear();
        for (IStop stop : stops) {
            stop.clearAllPassengers();
        }

        ridesCounts[day - 1] = 0;
        waitingTimes[day - 1] = 0;
        waitingPassengersCount[day - 1] = 0;
        for (Passenger passenger : passengers) {
            ridesCounts[day - 1] += passenger.getRidesCountToday();
            passenger.endTrackingWaitingTime(new Time(day, 23 * 60));
            if (passenger.hasTraveledToday()) {
                waitingPassengersCount[day - 1]++;
                waitingTimes[day - 1] += passenger.getWaitingTimeToday();
            }
        }


        Logger.newLine();
    }

    public static void insertEvent(Event event) {
        timeline.insert(event);
    }

    private IStop getStop(String stopName) {
        int low = 0;
        int high = stops.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            IStop midStop = stops[mid];
            String midStopName = midStop.getName();

            int comparison = midStopName.compareTo(stopName);

            if (comparison < 0) {
                low = mid + 1;
            } else if (comparison > 0) {
                high = mid - 1;
            } else {
                return midStop;
            }
        }

        throw new IllegalArgumentException("Invalid stop name provided.");
    }

}
