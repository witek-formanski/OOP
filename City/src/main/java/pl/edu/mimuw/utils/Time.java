package pl.edu.mimuw.utils;

public class Time {
    private int day;
    private int minutesTotal;

    public Time(int day, int minutesTotal) {
        this.day = day;
        this.minutesTotal = minutesTotal;
    }

    public String toString() {
        int hour = minutesTotal / 60;
        int minutes = minutesTotal - hour * 60;

        return day + ", " + hour + ":" + minutes;
    }

    public int toInt() {
        return day * 24 * 60 + minutesTotal;
    }
}
