package pl.edu.mimuw.events;

import pl.edu.mimuw.city.Passenger;
import pl.edu.mimuw.utils.Logger;
import pl.edu.mimuw.utils.Time;

public class PassengerWakesUpEvent extends Event {

    public PassengerWakesUpEvent(Time time, Passenger object) {
        super(time, object, "Passenger " + object.getId() + " woke up.");
    }

    public PassengerWakesUpEvent(int time) {
        super(new Time(0, time), null, null);
    }

    @Override
    public void act() {
        if (((Passenger) getObject()).getStop().isFull()) {
            Logger.log(getTime(), "Passenger " + ((Passenger) getObject()).getId() + " didn't travel on this day, because his stop (" + ((Passenger) getObject()).getStop().getName() + ") was full.");
            return;
        }

        Logger.log(getTime(), "Passenger " + ((Passenger) getObject()).getId() + " went to his stop (" + ((Passenger) getObject()).getStop().getName() + ") and started waiting for a tram.");
        ((Passenger) getObject()).getStop().addPassenger(((Passenger) getObject()));
    }
}
