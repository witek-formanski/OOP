package pl.edu.mimuw.simulation;

public class Event extends TimelineElement {
    private int time;
    private Object object;

    public Event(int time) {
        this(time, null);
    }

    public Event(int time, Object object) {
        this.time = time;
        this.object = object;
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public int compareTo(TimelineElement other) {
        return Integer.compare(time, other.getTime());
    }

    @Override
    public String toString() {
        return object.toString();
    }
}
