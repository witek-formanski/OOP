package pl.edu.mimuw.events;

import pl.edu.mimuw.city.Tram;
import pl.edu.mimuw.utils.Time;

public class TramStartsFromDepotEvent extends Event {

    public TramStartsFromDepotEvent(Time time, Tram object) {
        super(time, object,"Tram of line number " + object.getLine().getNumber() + " (side number: " + object.getSideNumber() + ") finished waiting in depot "
                + object.getLine().getRoute().getStopOfIndex(object.getCurrentStopIndex()).getName() + ".");
    }

    @Override
    public void act() {
        if (((Tram) getObject()).isCurrentStopFirstDepot() && getTime().isAfter23()) {
            ((Tram) getObject()).endRide(getTime());
            return;
        }

        ((Tram) getObject()).turnBack();
        ((Tram) getObject()).takePassengers(getTime());
        ((Tram) getObject()).leavePassengers(getTime());
        ((Tram) getObject()).goToNextStop(getTime());
    }
}
