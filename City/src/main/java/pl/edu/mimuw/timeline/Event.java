package pl.edu.mimuw.timeline;

import pl.edu.mimuw.utils.Time;

public class Event extends TimelineElement {
    private Time time;
    private int id;
    private static int globalId = 0;
    private IEventObject object;
    private String description;

    public Event(int time) {
        this(new Time(0, time), null, null);
    }

    public Event(Time time, IEventObject object, String description) {
        this.time = time;
        this.object = object;
        this.description = description;
        id = globalId;
        globalId++;
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
        return time.toString() + description;
    }
}
