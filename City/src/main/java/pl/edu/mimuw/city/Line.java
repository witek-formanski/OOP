package pl.edu.mimuw.city;

import pl.edu.mimuw.utils.Logger;
import pl.edu.mimuw.utils.Time;

public class Line {
    private Route route;
    private int number;
    private Tram[] trams;
    private int tramFrequency;

    public Line(int lineNumber, int numberOfTrams, int routeLength, int lastSideNumber, int tramCapacity) {
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
            trams[i].arriveAtStop(new Time(morningTime, i * tramFrequency), 0);
        }
        for (int i = (trams.length + 1) / 2; i < trams.length; i++) {
            trams[i].setDirectionForwards(false);
            trams[i].arriveAtStop(new Time(morningTime, (i - (trams.length + 1) / 2) * tramFrequency), route.getStopsCount() - 1);
        }
    }

    public void setTramFrequency() {
        tramFrequency = route.getTotalTravelTime() / trams.length;
        Logger.log("Trams of line number " + number + " will run every " + tramFrequency + " minutes.");
    }
}
