package pl.edu.mimuw.timeline;

public interface ITimeline {
    public abstract boolean isEmpty();

    public abstract TimelineElement extractMin();
    public abstract void insert(TimelineElement value);
}
