package pl.edu.mimuw.city;

import pl.edu.mimuw.simulation.Losowanie;
import pl.edu.mimuw.simulation.Simulation;
import pl.edu.mimuw.timeline.Event;
import pl.edu.mimuw.timeline.IEventObject;
import pl.edu.mimuw.utils.Time;

public class Passenger implements IEventObject {
    private IStop stop;
    private int id;
    public Passenger(IStop stop, int id) {
        this.stop = stop;
        this.id = id;
    }

    public void startDay(int day) {
        Time morningTime = getMorningTime(day);

        Simulation.insertTimelineElement(new Event(morningTime, this , "Passenger " + id + " woke up."));
    }

    private Time getMorningTime(int day) {
        return new Time(day, Losowanie.losuj(6*60, 12*60));
    }
}
