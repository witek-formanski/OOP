package pl.edu.mimuw.events;

import pl.edu.mimuw.city.Passenger;
import pl.edu.mimuw.utils.Time;

public class PassengerLeavesTramEvent extends Event {

    public PassengerLeavesTramEvent(Time time, Passenger object) {
        super(time, object, "Passenger " + object.getId() + " left the tram of line number " + object.getCurrentTram().getLine().getNumber() + " (side number: " + object.getCurrentTram().getSideNumber() + ") at stop "
                + object.getCurrentTram().getLine().getRoute().getStopOfIndex(object.getCurrentTram().getCurrentStopIndex()).getName() + ".");
    }

    @Override
    public void act() {
        ((Passenger) getObject()).setCurrentTram(null);
    }
}
