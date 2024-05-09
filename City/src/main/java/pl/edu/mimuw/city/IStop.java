package pl.edu.mimuw.city;

public interface IStop {
    public abstract boolean isFull();
    public abstract String getName();
    public abstract void addPassenger(Passenger passenger);
}
