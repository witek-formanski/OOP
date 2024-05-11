package pl.edu.mimuw.timeline;

import pl.edu.mimuw.city.Tram;
import pl.edu.mimuw.utils.Time;

public class TramStartsFromDepotEvent extends Event {
    private Tram object;

    public TramStartsFromDepotEvent(Time time, Tram object) {
        super(time, object,"Tram of line number " + object.getLine().getNumber() + " (side number: " + object.getSideNumber() + ") started from depot "
                + object.getLine().getRoute().getStopOfIndex(object.getCurrentStopIndex()).getName() + ".");
    }

    @Override
    public void act() {
        object.takePassengers();
        object.goToNextStop();
    }
}
