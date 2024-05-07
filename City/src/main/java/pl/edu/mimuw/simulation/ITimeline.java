package pl.edu.mimuw.simulation;

public interface ITimeline {
    public abstract boolean isEmpty();

    public abstract TimelineElement extractMin();
}
