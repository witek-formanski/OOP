package pl.edu.mimuw.city;

public class Route {
    private final IStop[] stops;
    private final int[] travelTimes;
    private int depotTime;
    private int totalTravelTime;

    public Route(int routeLength) {
        stops = new IStop[routeLength];
        travelTimes = new int[routeLength - 1];
    }

    public void setTotalTravelTime() {
        totalTravelTime = 0;
        for (int travelTime : travelTimes) {
            totalTravelTime += travelTime;
        }
        totalTravelTime += depotTime;
        totalTravelTime *= 2;
    }

    public int getTotalTravelTime() {
        return totalTravelTime;
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

    public int getDepotTime() {
        return depotTime;
    }

    public int getStopsCount() {
        return stops.length;
    }

    public IStop getStopOfIndex(int stopIndex) {
        return stops[stopIndex];
    }

    public int getTravelTime(int stopIndex) {
        return travelTimes[stopIndex]; //ToDo: verify, if it's correct
    }
}
