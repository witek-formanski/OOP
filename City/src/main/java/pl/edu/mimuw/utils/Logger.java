package pl.edu.mimuw.utils;

public class Logger {
    public static void log(String message) {
        System.out.println(message);
    }

    public static void log(Time time, String message) {
        System.out.println(time.toString() + ": " + message);
    }

    public static void newLine() {
        System.out.println();
    }
}
