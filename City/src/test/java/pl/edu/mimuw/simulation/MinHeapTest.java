package pl.edu.mimuw.simulation;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinHeapTest {

    @Test
    public void testInsertAndPeek() {
        MinHeap minHeap = new MinHeap(1);
        minHeap.insert(10);
        minHeap.insert(5);
        minHeap.insert(15);
        minHeap.insert(3);
        minHeap.insert(7);

        assertEquals(3, minHeap.peek());
    }

    @Test
    public void testExtractMin() {
        MinHeap minHeap = new MinHeap(3);
        minHeap.insert(10);
        minHeap.insert(5);
        minHeap.insert(15);
        minHeap.insert(3);
        minHeap.insert(7);

        assertEquals(3, minHeap.extractMin());
        assertEquals(5, minHeap.extractMin());
        assertEquals(7, minHeap.extractMin());
        assertEquals(10, minHeap.extractMin());
        assertEquals(15, minHeap.extractMin());
    }

    @Test
    public void testRandomInsertionAndExtraction() {
        Random rand = new Random();
        int[] array = new int[100];
        MinHeap minHeap = new MinHeap();

        for (int i = 0; i < array.length; i++) {
            int num = rand.nextInt(1000);
            array[i] = num;
            minHeap.insert(num);
        }

        Arrays.sort(array);

        for (int i = 0; i < array.length; i++) {
            int extractedMin = minHeap.extractMin();
            assertEquals(array[i], extractedMin);
        }
    }
}

