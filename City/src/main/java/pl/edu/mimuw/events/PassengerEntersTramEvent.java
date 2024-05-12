package pl.edu.mimuw.events;

import pl.edu.mimuw.city.Passenger;
import pl.edu.mimuw.city.Tram;
import pl.edu.mimuw.utils.Time;

public class PassengerEntersTramEvent extends Event {
    private Tram tram;

    public PassengerEntersTramEvent(Time time, Passenger object, Tram tram) {
        super(time, object, "Passenger " + object.getId() + " entered the tram of line number " + tram.getLine().getNumber() + " (side number: " + tram.getSideNumber() + ") at stop "
                + tram.getLine().getRoute().getStopOfIndex(tram.getCurrentStopIndex()).getName() + ".");
        this.tram = tram;
    }

    @Override
    public void act() {
        ((Passenger) getObject()).setCurrentTram(this.tram);
        ((Passenger) getObject()).incrementRidesCount();
        ((Passenger) getObject()).endTrackingWaitingTime(getTime());
    }
}
