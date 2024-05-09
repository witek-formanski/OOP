package pl.edu.mimuw.city;

import pl.edu.mimuw.simulation.Simulation;
import pl.edu.mimuw.timeline.IEventObject;
import pl.edu.mimuw.timeline.TramArrivesAtStopEvent;
import pl.edu.mimuw.utils.Time;

public class Tram extends Vehicle implements IEventObject {
    private final int sideNumber;
    private final int capacity;
    private final Line line;
    private boolean directionForwards;
    private int currentStop;

    public Tram(int sideNumber, int capacity, Line line) {
        this.sideNumber = sideNumber;
        this.capacity = capacity;
        this.line = line;
    }

    public void setDirectionForwards(boolean directionForwards) {
        this.directionForwards = directionForwards;
    }

    public void arriveAtStop(Time time, int stopNumber) {
        currentStop = stopNumber;
        Simulation.insertEvent(new TramArrivesAtStopEvent(time, this));
    }

    public Line getLine() {
        return line;
    }

    public int getSideNumber() {
        return sideNumber;
    }

    public int getCurrentStop() {
        return currentStop;
    }
}
