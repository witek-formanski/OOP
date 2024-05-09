package pl.edu.mimuw.simulation;

import org.junit.jupiter.api.Test;
import pl.edu.mimuw.timeline.HeapTimeline;
import pl.edu.mimuw.timeline.PassengerWakesUpEvent;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeapTimelineTest {

    @Test
    public void testInsertAndPeek() {
        HeapTimeline minHeap = new HeapTimeline(1);
        minHeap.insert(new PassengerWakesUpEvent(10));
        minHeap.insert(new PassengerWakesUpEvent(5));
        minHeap.insert(new PassengerWakesUpEvent(15));
        minHeap.insert(new PassengerWakesUpEvent(3));
        minHeap.insert(new PassengerWakesUpEvent(7));

        assertEquals(3, minHeap.peek().getTime().toInt());
    }

    @Test
    public void testExtractMin() {
        HeapTimeline minHeap = new HeapTimeline(3);
        minHeap.insert(new PassengerWakesUpEvent(10));
        minHeap.insert(new PassengerWakesUpEvent(5));
        minHeap.insert(new PassengerWakesUpEvent(15));
        minHeap.insert(new PassengerWakesUpEvent(3));
        minHeap.insert(new PassengerWakesUpEvent(7));

        assertEquals(3, minHeap.extractMin().getTime().toInt());
        assertEquals(5, minHeap.extractMin().getTime().toInt());
        assertEquals(7, minHeap.extractMin().getTime().toInt());
        assertEquals(10, minHeap.extractMin().getTime().toInt());
        assertEquals(15, minHeap.extractMin().getTime().toInt());
    }

    @Test
    public void testRandomInsertionAndExtraction() {
        Random rand = new Random();
        int[] array = new int[100];
        HeapTimeline minHeap = new HeapTimeline();

        for (int i = 0; i < array.length; i++) {
            int num = rand.nextInt(1000);
            array[i] = num;
            minHeap.insert(new PassengerWakesUpEvent(num));
        }

        Arrays.sort(array);

        for (int i = 0; i < array.length; i++) {
            int extractedMin = minHeap.extractMin().getTime().toInt();
            assertEquals(array[i], extractedMin);
        }
    }

    @Test
    public void testTheSameTimeEvents() {
        PassengerWakesUpEvent first = new PassengerWakesUpEvent(0);
        PassengerWakesUpEvent second = new PassengerWakesUpEvent(0);
        PassengerWakesUpEvent third = new PassengerWakesUpEvent(0);

        HeapTimeline minHeap = new HeapTimeline(300);
        minHeap.insert(second);
        minHeap.insert(first);
        minHeap.insert(third);

        int firstId = minHeap.peek().getId();

        assertEquals(first, minHeap.peek());
        assertEquals(firstId, minHeap.extractMin().getId());
        assertEquals(second, minHeap.peek());
        assertEquals(firstId + 1, minHeap.extractMin().getId());
        assertEquals(third, minHeap.peek());
        assertEquals(firstId + 2, minHeap.extractMin().getId());
    }
}

