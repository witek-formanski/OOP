package pl.edu.mimuw.main;

import pl.edu.mimuw.iostream.InputReader;
import pl.edu.mimuw.system.TradingSystem;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Not enough arguments provided.");
        }

        TradingSystem tradingSystem = new TradingSystem(new InputReader(), args[0], Integer.parseInt(args[1]));
        tradingSystem.simulateStockMarket();
    }
}
