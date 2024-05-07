package pl.edu.mimuw.simulation;

import java.util.Arrays;

public class MinHeap {
    private TimelineElement[] heap;
    private int size;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 8;

    public MinHeap() {
        this(DEFAULT_CAPACITY);
    }
    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        heap = new TimelineElement[capacity];
    }
    private boolean isEmpty() {
        return (size == 0);
    }

    private void resize() {
        capacity *= 2;
        TimelineElement[] newHeap = Arrays.copyOf(heap, capacity);
        heap = newHeap;
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

    private boolean hasParent(int index) {
        return getParentIndex(index) >= 0;
    }

    private boolean hasLeftChild(int index) {
        return getLeftChildIndex(index) < size;
    }

    private boolean hasRightChild(int index) {
        return getRightChildIndex(index) < size;
    }

    private void swap(int index1, int index2) {
        TimelineElement temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    public void insert(int x) {
        insert(new Event(x));
    }

    public void insert(TimelineElement value) {
        if (size == capacity) {
            resize();
        }
        heap[size] = value;
        size++;
        heapifyUp();
    }

    public TimelineElement peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap[0];
    }

    public TimelineElement extractMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        TimelineElement min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown();
        return min;
    }

    private void heapifyUp() {
        int index = size - 1;
        while (hasParent(index) && heap[index].compareTo(heap[getParentIndex(index)]) == -1) {
            swap(index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    private void heapifyDown() {
        int index = 0;
        while (hasLeftChild(index)) {
            int smallerChildIndex = getLeftChildIndex(index);
            if (hasRightChild(index) && heap[getRightChildIndex(index)].compareTo(heap[smallerChildIndex]) == -1) {
                smallerChildIndex = getRightChildIndex(index);
            }
            if (heap[index].compareTo(heap[smallerChildIndex]) == -1) {
                break;
            } else {
                swap(index, smallerChildIndex);
            }
            index = smallerChildIndex;
        }
    }

    public void printHeap() {
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }
}
