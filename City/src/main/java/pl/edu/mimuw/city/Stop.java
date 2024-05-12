package pl.edu.mimuw.city;

public class Stop implements IStop {
    private final String name;
    private int passengersCount;
    private final Passenger[] passengers;

    public Stop(String name, int capacity) {
        this.name = name;
        passengers = new Passenger[capacity];
    }

    @Override
    public boolean isFull() {
        return passengersCount == passengers.length;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addPassenger(Passenger passenger) {
        passengers[passengersCount] = passenger;
        passengersCount++;
    }

    @Override
    public int getAvailablePlacesCount() {
        return passengers.length - passengersCount;
    }

    @Override
    public Passenger removePassenger() {
        passengersCount--;
        return passengers[passengersCount];
    }

    @Override
    public int getPassengersCount() {
        return passengersCount;
    }

    @Override
    public void clearAllPassengers() {
        for (int i = 0; i < passengersCount; i++) {
            passengers[i] = null;
        }

        passengersCount = 0;
    }
}
