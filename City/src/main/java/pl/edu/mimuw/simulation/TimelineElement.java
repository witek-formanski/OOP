package pl.edu.mimuw.simulation;

public abstract class TimelineElement {
    public abstract int getTime();

    public abstract int compareTo(TimelineElement other);
}
