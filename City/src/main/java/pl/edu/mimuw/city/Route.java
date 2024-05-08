package pl.edu.mimuw.city;

public class Route {
    private Stop[] stops;
    private int[] travelTimes;
    private int depotTime;

    public Route(int routeLength) {
        stops = new Stop[routeLength];
    }

    public void addStop(int stopIndex, String Name) {

    }

    public void addTravelTime(int index, int travelTime) {

    }

    public void setDepotTime(int depotTime) {
        this.depotTime = depotTime;
    }
}
