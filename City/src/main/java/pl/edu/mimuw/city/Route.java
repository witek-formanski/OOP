package pl.edu.mimuw.city;

public class Route {
    private IStop[] stops;
    private int[] travelTimes;
    private int depotTime;
    private int totalTravelTime;

    public Route(int routeLength) {
        stops = new IStop[routeLength];
        travelTimes = new int[routeLength - 1];
    }

    public void setTotalTravelTime() {
        totalTravelTime = 0;
        for (int i = 0; i < travelTimes.length; i++) {
            totalTravelTime += travelTimes[i];
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
    public int getStopsCount() {
        return stops.length;
    }

    public IStop getStopOfIndex(int stopIndex) {
        return stops[stopIndex];
    }
}
