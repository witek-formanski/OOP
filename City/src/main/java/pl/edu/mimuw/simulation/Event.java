package pl.edu.mimuw.simulation;

public class Event extends TimelineElement {
    private int time;
    private Object object;

    @Override
    public int getTime() {
        return time;
    }

    public Event(int time) {
        this.time = time;
        this.object = null;
    }
}
