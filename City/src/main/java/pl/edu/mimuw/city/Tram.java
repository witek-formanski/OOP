package pl.edu.mimuw.city;

import pl.edu.mimuw.simulation.Simulation;
import pl.edu.mimuw.timeline.IEventObject;
import pl.edu.mimuw.timeline.TramArrivesAtStopEvent;
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

//    public boolean getDirectionForwards() {
//        return directionForwards;
//    }

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

    public void leavePassengers() {
        int passengersToLeaveCount = min(getCurrentStop().getAvailablePlacesCount(), passengersCount);
        for (int i = 0; i < passengersToLeaveCount; i++) {
            leavePassenger();
        }
    }

    public void takePassengers() {
        int passengersToTakeCount = min(getAvailablePlacesCount(), getCurrentStop().getPassengersCount());
        for (int i = 0; i < passengersToTakeCount; i++) {
            takePassenger();
        }
    }

    private void leavePassenger() {
        getCurrentStop().addPassenger(passengers[passengersCount - 1]);
        passengers[passengersCount - 1] = null;
        passengersCount--;
    }

    private void takePassenger() {
        passengers[passengersCount] = getCurrentStop().removePassenger();
        passengersCount++;
    }

    private int getAvailablePlacesCount() {
        return passengers.length - passengersCount;
    }

    public void goToNextStop(Time time) {
//        if (isCurrentStopDepot()) {
//            waitInDepot();
//        }
        Simulation.insertEvent(new TramArrivesAtStopEvent(new Time(time, getLine().getRoute().getTravelTime(currentStopIndex)), this));

        if (directionForwards) {
            currentStopIndex++;
        } else {
            currentStopIndex--;
        }
    }

    public boolean isCurrentStopDepot() {
        return (currentStopIndex == 0 || currentStopIndex == getLine().getRoute().getStopsCount() - 1);
    }

//    private void waitInDepot() {
//        getLine().getRoute().getDepotTime();
//    }
}
