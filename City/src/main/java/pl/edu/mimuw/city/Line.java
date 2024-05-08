package pl.edu.mimuw.city;

public class Line {
    private Route route;
    private int lineNumber;
    private Tram[] trams;

    public Line(int lineNumber, int numberOfTrams, int routeLength, int lastSideNumber, int tramCapacity) {
        this.lineNumber = lineNumber;
        trams = new Tram[numberOfTrams];
        for (int i = 0; i < numberOfTrams; i++) {
            lastSideNumber++;
            trams[i] = new Tram(lastSideNumber, tramCapacity);
        }
        route = new Route(routeLength);
    }

    public void addStop(int index, IStop stop) {
        route.addStop(index, stop);
    }
    public void addStop(int index, IStop stop, int travelTime) {
        route.addStop(index, stop);
        route.addTravelTime(index, travelTime);
    }

    public void setDepotTime(int depotTime) {
        route.setDepotTime(depotTime);
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
