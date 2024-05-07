package pl.edu.mimuw.simulation;

public abstract class TimelineElement {
    private int time;

    public int getTime() {
        return time;
    }
    public int compareTo(TimelineElement other) {
        return Integer.compare(this.time, other.getTime());
    }
}
