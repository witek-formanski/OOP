package pl.edu.mimuw.main;

import pl.edu.mimuw.system.TradingSystem;

public class Main {
    public static void main(String[] args) {
        var tradingSystem = new TradingSystem(args[0], Integer.parseInt(args[1]));
        tradingSystem.simulateStockMarket();
    }
}
