package pl.edu.mimuw.city;

public class Tram extends Vehicle {
    private int sideNumber;
    private int capacity;

    public Tram(int sideNumber, int capacity) {
        this.sideNumber = sideNumber;
        this.capacity = capacity;
    }
}
