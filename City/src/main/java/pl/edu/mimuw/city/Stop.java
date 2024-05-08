package pl.edu.mimuw.city;

public class Stop implements IStop {
    private String name;
    private int capacity;
    private int passengersCount;

    public Stop(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }
}
