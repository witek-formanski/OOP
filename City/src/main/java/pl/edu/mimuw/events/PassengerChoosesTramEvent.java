package pl.edu.mimuw.events;

import pl.edu.mimuw.city.Passenger;
import pl.edu.mimuw.city.Tram;
import pl.edu.mimuw.utils.Time;

public class PassengerChoosesTramEvent extends Event {

    public PassengerChoosesTramEvent(Time time, Passenger object, Tram tram) {
        super(time, object, "Passenger " + object.getId() + " after entering the tram of line number " + tram.getLine().getNumber() + " (side number: " + tram.getSideNumber() + ") at stop "
                + tram.getLine().getRoute().getStopOfIndex(tram.getCurrentStopIndex()).getName() + " chose to travel to the stop " + tram.getLine().getRoute().getStopOfIndex(object.getChosenStopIndex()).getName() + ".");
    }

    @Override
    public void act() {}
}
