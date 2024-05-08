package pl.edu.mimuw.simulation;

import pl.edu.mimuw.timeline.Event;

public class Main {
    public static void main(String[] args) {

        System.out.println(Integer.compare(5, 10));
        System.out.println(Integer.compare(10, 10));
        System.out.println(Integer.compare(20, 10));
        System.out.println( -1 / 2);

        Event less = new Event(5);
        Event more = new Event(10);
        System.out.println(less.compareTo(more));
        System.out.println(less.compareTo(less));
        System.out.println(more.compareTo(less));

        Simulation simulation = new Simulation();
        simulation.run();
    }
}
