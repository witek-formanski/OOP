package pl.edu.mimuw.city;

import pl.edu.mimuw.utils.Logger;
import pl.edu.mimuw.utils.Time;

public class TramLine {
    private final Route route;
    private final int number;
    private final Tram[] trams;
    private int tramFrequency;

    public int getTramFrequency() {
        return tramFrequency;
    }

    public TramLine(int lineNumber, int numberOfTrams, int routeLength, int lastSideNumber, int tramCapacity) {
        this.number = lineNumber;
        trams = new Tram[numberOfTrams];
        for (int i = 0; i < numberOfTrams; i++) {
            trams[i] = new Tram(lastSideNumber, tramCapacity, this);
            lastSideNumber++;
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
            trams[i].startFromDepot(true, new Time(morningTime, i * tramFrequency));
            //ToDo: What if there are so many trains, that some of them arrive at first stop after 23?
        }
        for (int i = (trams.length + 1) / 2; i < trams.length; i++) {
            trams[i].startFromDepot(false, new Time(morningTime, (i - (trams.length + 1) / 2) * tramFrequency));
        }
    }

    public void setTramFrequency() {
        tramFrequency = route.getTotalTravelTime() / trams.length;
    }

    public int getTramsCount() {
        return trams.length;
    }
}
