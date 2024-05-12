package pl.edu.mimuw.events;

import pl.edu.mimuw.city.Tram;
import pl.edu.mimuw.simulation.Simulation;
import pl.edu.mimuw.utils.Time;

public class TramDepartsFromStopEvent extends Event {
    public TramDepartsFromStopEvent(Time time, Tram object) {
        super(time, object,"Tram of line number " + object.getLine().getNumber() + " (side number: " + object.getSideNumber() + ") departed from stop "
                + object.getLine().getRoute().getStopOfIndex(object.getCurrentStopIndex()).getName() + ". Number of travelling passengers: " + object.getPassengersCount() + ".");
    }

    @Override
    public void act() {}
}
