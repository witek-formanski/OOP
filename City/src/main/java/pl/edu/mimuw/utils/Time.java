package pl.edu.mimuw.utils;

public class Time {
    private int day;
    private int minutesTotal;

    public Time(int day, int minutesTotal) {
        this.day = day;
        this.minutesTotal = minutesTotal;
    }

    public Time(Time time, int incrementedMinutes) {
        this(time.toInt() + incrementedMinutes);
    }

    public String toString() {
        return day + ", " + minutesTotal / 60 + ":" + ((minutesTotal % 60 < 10) ? "0" + minutesTotal % 60 : minutesTotal % 60);
    }

    public int toInt() {
        return day * 24 * 60 + minutesTotal;
    }

    public Time(int timeInt) {
        this(timeInt / (24 * 60), timeInt % (24 * 60));
    }

    public boolean isAfter23() {
        return minutesTotal > 23 * 60;
    }

}
