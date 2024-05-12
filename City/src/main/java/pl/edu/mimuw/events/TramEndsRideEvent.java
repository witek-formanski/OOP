package pl.edu.mimuw.events;

import pl.edu.mimuw.city.Tram;
import pl.edu.mimuw.utils.Time;

public class TramEndsRideEvent extends Event {

    public TramEndsRideEvent(Time time, Tram object) {
        super(time, object,"Tram of line number " + object.getLine().getNumber() + " (side number: " + object.getSideNumber() + ") ended ride in depot "
                + object.getLine().getRoute().getStopOfIndex(object.getCurrentStopIndex()).getName() + ".");
    }

    @Override
    public void act() {
        ((Tram) getObject()).leaveAllPassengers(getTime());
    }
}
