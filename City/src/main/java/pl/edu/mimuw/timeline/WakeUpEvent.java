package pl.edu.mimuw.timeline;

import pl.edu.mimuw.utils.Time;

public class WakeUpEvent extends Event {
    public WakeUpEvent(Time time, IEventObject object, String description) {
        super(time, object, description);
    }

    public WakeUpEvent(Time time, IEventObject object, int id) {
        super(time, object, "Passenger " + id + " woke up.");
    }
}
