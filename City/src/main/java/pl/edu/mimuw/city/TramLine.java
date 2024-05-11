package pl.edu.mimuw.city;

import pl.edu.mimuw.simulation.Simulation;
import pl.edu.mimuw.timeline.TramStartsFromDepotEvent;
import pl.edu.mimuw.utils.Logger;
import pl.edu.mimuw.utils.Time;

public class TramLine {
    private final Route route;
    private final int number;
    private final Tram[] trams;
    private int tramFrequency;

    public TramLine(int lineNumber, int numberOfTrams, int routeLength, int lastSideNumber, int tramCapacity) {
        this.number = lineNumber;
        trams = new Tram[numberOfTrams];
        for (int i = 0; i < numberOfTrams; i++) {
            lastSideNumber++;
            trams[i] = new Tram(lastSideNumber, tramCapacity, this);
        }
        route = new Route(routeLength);
    }

    public Route getRoute() {
        return route;
    }

    public int getNumber() {
        return number;
    }

    public void startDay(int day) {
        Time morningTime = new Time(day, 60 * 6);
        for (int i = 0; i < (trams.length + 1) / 2; i++) {
            trams[i].setDirectionForwards(true);
            trams[i].setCurrentStopIndex(0);
            Simulation.insertEvent(new TramStartsFromDepotEvent(new Time(morningTime, i * tramFrequency), trams[i]));
            //ToDo: What if there are so many trains, that some of them arrive at first stop after 23?
        }
        for (int i = (trams.length + 1) / 2; i < trams.length; i++) {
            trams[i].setDirectionForwards(false);
            trams[i].setCurrentStopIndex(route.getStopsCount() - 1);
            Simulation.insertEvent(new TramStartsFromDepotEvent(new Time(morningTime, (i - (trams.length + 1) / 2) * tramFrequency), trams[i]));
        }
    }

    public void setTramFrequency() {
        tramFrequency = route.getTotalTravelTime() / trams.length;
        Logger.log("Trams of line number " + number + " will run every " + tramFrequency + " minutes.");
    }
}
