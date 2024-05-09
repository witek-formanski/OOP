package pl.edu.mimuw.city;

import pl.edu.mimuw.simulation.Losowanie;
import pl.edu.mimuw.simulation.Simulation;
import pl.edu.mimuw.timeline.IEventObject;
import pl.edu.mimuw.timeline.PassengerWakesUpEvent;
import pl.edu.mimuw.utils.Time;

public class Passenger implements IEventObject {
    private final IStop stop;
    private final int id;
    public Passenger(IStop stop, int id) {
        this.stop = stop;
        this.id = id;
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

//    public Line chooseLine() {
//
//    }
}
