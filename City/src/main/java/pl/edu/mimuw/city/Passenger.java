package pl.edu.mimuw.city;

import pl.edu.mimuw.simulation.Losowanie;
import pl.edu.mimuw.utils.Logger;

public class Passenger {
    private Stop stop;
    public Passenger(Stop stop) {
        this.stop = stop;
    }

    public void startDay(int day) {
        int morningTime = getMorningTime();
        Logger.log("Passenger woke up.");
    }

    private int getMorningTime() {
        return Losowanie.losuj(6*60, 12*60);
    }

}
