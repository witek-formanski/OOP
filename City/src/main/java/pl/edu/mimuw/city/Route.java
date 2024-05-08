package pl.edu.mimuw.city;

public class Route {
    private IStop[] stops;
    private int[] travelTimes;
    private int depotTime;

    public Route(int routeLength) {
        stops = new IStop[routeLength];
        travelTimes = new int[routeLength - 1];
    }

    public void addStop(int stopIndex, IStop stop) {
        stops[stopIndex] = stop;
    }

    public void addTravelTime(int index, int travelTime) {
        travelTimes[index] = travelTime;
    }

    public void setDepotTime(int depotTime) {
        this.depotTime = depotTime;
    }
}
