package pl.edu.mimuw.timeline;

import pl.edu.mimuw.city.Tram;
import pl.edu.mimuw.simulation.Simulation;
import pl.edu.mimuw.utils.Time;

public class TramArrivesAtStopEvent extends Event {
    private Tram object;

    public TramArrivesAtStopEvent(Time time, Tram object) {
        super(time, object,"Tram of line number " + object.getLine().getNumber() + " (side number: " + object.getSideNumber() + ") arrived at stop "
                + object.getLine().getRoute().getStopOfIndex(object.getCurrentStopIndex()).getName() + ".");
    }

    @Override
    public void act() {
        object.leavePassengers();
        if (object.isCurrentStopDepot()) {
            Simulation.insertEvent(new TramStartsFromDepotEvent(new Time(getTime().toInt() + object.getLine().getRoute().getDepotTime()), object));
            return;
        }

        object.takePassengers();
        object.goToNextStop();
    }
}
