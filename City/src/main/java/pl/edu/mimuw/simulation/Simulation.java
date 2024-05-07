package pl.edu.mimuw.simulation;

public class Simulation {
    ITimeline timeline;
    ILogger logger;
    private int remainingDays;
    public void simulate() {
        while (remainingDays > 0) {
            simulateDay();
            remainingDays--;
        }
    }

    private void simulateDay() {
        // timeline...

        while (!timeline.isEmpty()) {
            logger.log(timeline.extractMin().toString());
        }
    }
}
