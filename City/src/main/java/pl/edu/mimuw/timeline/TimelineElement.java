package pl.edu.mimuw.timeline;

import pl.edu.mimuw.utils.Time;

public abstract class TimelineElement {
    public abstract Time getTime();
    public abstract int getId();
    public abstract int compareTo(TimelineElement other);
}
