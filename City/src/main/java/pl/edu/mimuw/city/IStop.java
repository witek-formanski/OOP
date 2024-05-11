package pl.edu.mimuw.city;

public interface IStop {
    boolean isFull();
    String getName();
    void addPassenger(Passenger passenger);
    int getAvailablePlacesCount();
    Passenger removePassenger();
    int getPassengersCount();
}
