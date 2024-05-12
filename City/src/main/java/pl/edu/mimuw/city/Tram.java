package pl.edu.mimuw.city;

import pl.edu.mimuw.events.TramDepartsFromStopEvent;
import pl.edu.mimuw.simulation.Simulation;
import pl.edu.mimuw.timeline.IEventObject;
import pl.edu.mimuw.events.TramArrivesAtStopEvent;
import pl.edu.mimuw.events.TramEndsRideEvent;
import pl.edu.mimuw.events.TramStartsFromDepotEvent;
import pl.edu.mimuw.utils.Time;

import static java.lang.Math.min;

public class Tram extends Vehicle implements IEventObject {
    private final int sideNumber;
//    private final int capacity;
    private final TramLine tramLine;
    private boolean directionForwards;
    private int currentStopIndex;
    private final Passenger[] passengers;
    private int passengersCount;
//    private boolean inDepot;

    public int getPassengersCount() {
        return passengersCount;
    }

    public Tram(int sideNumber, int capacity, TramLine tramLine) {
        this.sideNumber = sideNumber;
//        this.capacity = capacity;
        this.tramLine = tramLine;
        passengers = new Passenger[capacity];
        passengersCount = 0;
//        inDepot = true;
    }

    public void setDirectionForwards(boolean directionForwards) {
        this.directionForwards = directionForwards;
    }

    public void setCurrentStopIndex(int stopIndex) {
        currentStopIndex = stopIndex;
    }

    public boolean getDirectionForwards() {
        return directionForwards;
    }

//    public void arriveAtStop(Time time, int stopNumber) {
//        currentStopIndex = stopNumber;
//        Simulation.insertEvent(new TramArrivesAtStopEvent(time, this));
//    }

    public TramLine getLine() {
        return tramLine;
    }

    public int getSideNumber() {
        return sideNumber;
    }

    public int getCurrentStopIndex() {
        return currentStopIndex;
    }

    private IStop getCurrentStop() {
        return tramLine.getRoute().getStopOfIndex(currentStopIndex);
    }

    public void leavePassengers(Time time) {
        int i = passengersCount - 1;
        while (getCurrentStop().getAvailablePlacesCount() > 0 && i >= 0) {
            if (passengers[i].getChosenStopIndex() == currentStopIndex) {
                passengers[i].leaveTram(time);
                passengers[i] = passengers[passengersCount - 1];
                passengers[passengersCount - 1] = null;
                passengersCount--;
            }
            i--;
        }
    }

    public void takePassengers(Time time) {
        int passengersToTakeCount = min(getAvailablePlacesCount(), getCurrentStop().getPassengersCount());
        for (int i = 0; i < passengersToTakeCount; i++) {
            takePassenger(time);
        }
    }

    private void takePassenger(Time time) {
        passengers[passengersCount] = getCurrentStop().removePassenger();
        passengers[passengersCount].enterTram(time, this);
        passengersCount++;
    }

    private int getAvailablePlacesCount() {
        return passengers.length - passengersCount;
    }

    public void goToNextStop(Time time) {
        Simulation.insertEvent(new TramDepartsFromStopEvent(time, this));

        int shift = directionForwards ? 1 : -1;
        currentStopIndex += shift;
        Simulation.insertEvent(new TramArrivesAtStopEvent(new Time(time, getLine().getRoute().getTravelTime(currentStopIndex, currentStopIndex - shift)), this));
    }

    public boolean isCurrentStopFirstDepot() {
        return currentStopIndex == 0;
    }

    public boolean isCurrentStopSecondDepot() {
        return currentStopIndex == getLine().getRoute().getStopsCount() - 1;
    }

    public void startFromDepot(boolean first, Time time) {
        if (first) {
            setDirectionForwards(false);
            setCurrentStopIndex(0);
        } else {
            setDirectionForwards(true);
            setCurrentStopIndex(getLine().getRoute().getStopsCount() - 1);
        }

        Simulation.insertEvent(new TramStartsFromDepotEvent(time, this));
    }

    public void endRide(Time time) {
        Simulation.insertEvent(new TramEndsRideEvent(time, this));
    }

    public void clearAllPassengers() {
        for (int i = 0; i < passengersCount; i++) {
            passengers[i] = null;
        }

        passengersCount = 0;
    }

    public void turnBack() {
        directionForwards = !directionForwards;
    }
}
