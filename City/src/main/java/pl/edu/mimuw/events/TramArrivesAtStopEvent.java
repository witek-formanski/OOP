package pl.edu.mimuw.events;

import pl.edu.mimuw.city.Tram;
import pl.edu.mimuw.simulation.Simulation;
import pl.edu.mimuw.utils.Time;

public class TramArrivesAtStopEvent extends Event {

    public TramArrivesAtStopEvent(Time time, Tram object) {
        super(time, object,"Tram of line number " + object.getLine().getNumber() + " (side number: " + object.getSideNumber() + ") arrived at stop "
                + object.getLine().getRoute().getStopOfIndex(object.getCurrentStopIndex()).getName() + ". Number of travelling passengers: " + object.getPassengersCount() + ".");
    }

    @Override
    public void act() {
        ((Tram) getObject()).leavePassengers(getTime());
        if (((Tram) getObject()).isCurrentStopFirstDepot() || ((Tram) getObject()).isCurrentStopSecondDepot()) {
            Simulation.insertEvent(new TramStartsFromDepotEvent(new Time(getTime(), ((Tram) getObject()).getLine().getRoute().getDepotTime()), ((Tram) getObject())));
            return;
        }

        ((Tram) getObject()).takePassengers(getTime());
        ((Tram) getObject()).leavePassengers(getTime());
        ((Tram) getObject()).goToNextStop(getTime());
    }
}
