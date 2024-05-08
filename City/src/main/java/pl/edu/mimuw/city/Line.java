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

    public void addStop(int index, String stopName) {
        route.addStop(index, stopName);
    }
    public void addStop(int index, String stopName, int travelTime) {
        route.addStop(index, stopName);
        route.addTravelTime(index, travelTime);
    }

    public void setDepotTime(int depotTime) {
        route.setDepotTime(depotTime);
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
