package pl.edu.mimuw.timeline;

public interface ITimeline {
    boolean isEmpty();
    TimelineElement extractMin();
    void insert(TimelineElement value);
    void clear();
}
