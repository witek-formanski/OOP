package pl.edu.mimuw.city;

import pl.edu.mimuw.events.PassengerChoosesTramEvent;
import pl.edu.mimuw.events.PassengerEntersTramEvent;
import pl.edu.mimuw.events.PassengerLeavesTramEvent;
import pl.edu.mimuw.events.PassengerWakesUpEvent;
import pl.edu.mimuw.main.Losowanie;
import pl.edu.mimuw.simulation.Simulation;
import pl.edu.mimuw.timeline.*;
import pl.edu.mimuw.utils.Time;

public class Passenger implements IEventObject {
    private final IStop stop;
    private final int id;
    private Tram currentTram;
    private int chosenStopIndex;

    public Passenger(IStop stop, int id) {
        this.stop = stop;
        this.id = id;
        currentTram = null;
    }

    public IStop getStop() {
        return stop;
    }

    public int getId() {
        return id;
    }

    public void startDay(int day) {
        Simulation.insertEvent(new PassengerWakesUpEvent(getWakeUpTime(day), this));
    }

    private Time getWakeUpTime(int day) {
        return new Time(day, Losowanie.losuj(6*60, 12*60));
    }

    public void enterTram(Time time, Tram tram) {
        currentTram = tram;
        Simulation.insertEvent(new PassengerEntersTramEvent(time, this, tram));
    }

    public void chooseStop(Time time, Tram tram) {
        chosenStopIndex = tram.getDirectionForwards()
                ? Losowanie.losuj(tram.getCurrentStopIndex(), tram.getLine().getRoute().getStopsCount() - 1)
                : Losowanie.losuj(0, tram.getCurrentStopIndex());

        Simulation.insertEvent(new PassengerChoosesTramEvent(time, this, tram));
    }

    public int getChosenStopIndex() {
        return chosenStopIndex;
    }

    public void leaveTram(Time time) {
        currentTram.getLine().getRoute().getStopOfIndex(currentTram.getCurrentStopIndex()).addPassenger(this);
        Simulation.insertEvent(new PassengerLeavesTramEvent(time, this));
    }

    public Tram getCurrentTram() {
        return currentTram;
    }

    public void setCurrentTram(Tram currentTram) {
        this.currentTram = currentTram;
    }
}
