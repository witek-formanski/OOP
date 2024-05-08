package pl.edu.mimuw.timeline;

public abstract class TimelineElement {
    public abstract int getTime();
    public abstract int getId();
    public abstract int compareTo(TimelineElement other);
}
