package pl.edu.mimuw.timeline;

import pl.edu.mimuw.city.Passenger;
import pl.edu.mimuw.utils.Logger;
import pl.edu.mimuw.utils.Time;

public class PassengerWakesUpEvent extends Event {
    private Passenger object;

    public PassengerWakesUpEvent(Time time, Passenger object) {
        super(time, object, "Passenger " + object.getId() + " woke up.");
    }

    public PassengerWakesUpEvent(int time) {
        super(new Time(0, time), null, null);
    }

    @Override
    public void act() {
        if (object.getStop().isFull()) {
            Logger.log(getTime(), "Passenger " + object.getId() + " didn't travel on this day, because his stop (" + object.getStop().getName() + ") was full.");
            return;
        }

        Logger.log(getTime(), "Passenger " + object.getId() + " went to his stop (" + object.getStop().getName() + ") and started waiting for a tram.");
        object.getStop().addPassenger(object);
    }
}
