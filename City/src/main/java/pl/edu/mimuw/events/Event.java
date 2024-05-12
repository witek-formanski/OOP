package pl.edu.mimuw.events;

import pl.edu.mimuw.timeline.IEventObject;
import pl.edu.mimuw.timeline.TimelineElement;
import pl.edu.mimuw.utils.Time;

public abstract class Event extends TimelineElement {
    private Time time;
    private int id;
    private static int globalId = 0;
    private IEventObject object;
    private String description;

    public Event(Time time, IEventObject object, String description) {
        this.time = time;
        this.object = object;
        this.description = description;
        id = globalId;
        globalId++;
    }

    protected IEventObject getObject() {
        return object;
    }

    @Override
    public Time getTime() {
        return time;
    }

    public int getId() { return id; }

    @Override
    public int compareTo(TimelineElement other) {
        if (time.toInt() == other.getTime().toInt()) {
            return (id < other.getId()) ? -1 : 1;
        }
        return (time.toInt() < other.getTime().toInt()) ? -1 : 1;
    }

    @Override
    public String toString() {
        return time.toString() + ": " + description;
    }
}
