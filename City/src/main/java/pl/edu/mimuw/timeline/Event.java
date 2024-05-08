package pl.edu.mimuw.timeline;

public class Event extends TimelineElement {
    private int time;
    private int id;
    private static int globalId = 0;
    private Object object;

    public Event(int time) {
        this(time, null);
    }

    public Event(int time, Object object) {
        this.time = time;
        this.object = object;
        id = globalId;
        globalId++;
    }

    @Override
    public int getTime() {
        return time;
    }

    public int getId() { return id; }

    @Override
    public int compareTo(TimelineElement other) {
        if (time == other.getTime()) {
            return (id < other.getId()) ? -1 : 1;
        }
        return (time < other.getTime()) ? -1 : 1;
    }

    @Override
    public String toString() {
        return object.toString();
    }
}
