package pl.edu.mimuw.timeline;

import java.util.Arrays;

public class HeapTimeline implements ITimeline {
    private TimelineElement[] timeline;
    private int size;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 8;

    public HeapTimeline() {
        this(DEFAULT_CAPACITY);
    }
    public HeapTimeline(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        timeline = new TimelineElement[capacity];
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    private void resize() {
        capacity *= 2;
        TimelineElement[] newHeap = Arrays.copyOf(timeline, capacity);
        timeline = newHeap;
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    private boolean hasLeftChild(int index) {
        return getLeftChildIndex(index) < size;
    }

    private boolean hasRightChild(int index) {
        return getRightChildIndex(index) < size;
    }

    private void swap(int index1, int index2) {
        TimelineElement temp = timeline[index1];
        timeline[index1] = timeline[index2];
        timeline[index2] = temp;
    }

    public void insert(int x) {
        insert(new Event(x));
    }

    public void insert(TimelineElement value) {
        if (size == capacity) {
            resize();
        }
        timeline[size] = value;
        size++;
        heapifyUp();
    }

    public TimelineElement peek() {
        assert(!isEmpty());

        return timeline[0];
    }

    @Override
    public TimelineElement extractMin() {
        assert(!isEmpty());

        TimelineElement min = timeline[0];
        timeline[0] = timeline[size - 1];
        size--;
        heapifyDown();
        return min;
    }

    private void heapifyUp() {
        int index = size - 1;
        while (timeline[index].compareTo(timeline[getParentIndex(index)]) == -1) {
            swap(index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    private void heapifyDown() {
        int index = 0;
        while (hasLeftChild(index)) {
            int smallerChildIndex = getLeftChildIndex(index);
            if (hasRightChild(index) && timeline[getRightChildIndex(index)].compareTo(timeline[smallerChildIndex]) == -1) {
                smallerChildIndex = getRightChildIndex(index);
            }
            if (timeline[index].compareTo(timeline[smallerChildIndex]) == -1) {
                break;
            } else {
                swap(index, smallerChildIndex);
            }
            index = smallerChildIndex;
        }
    }

    public void printHeap() {
        for (int i = 0; i < size; i++) {
            System.out.print(timeline[i] + " ");
        }
        System.out.println();
    }
}
