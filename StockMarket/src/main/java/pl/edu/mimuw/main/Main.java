package pl.edu.mimuw.main;

import pl.edu.mimuw.system.TradingSystem;

public class Main {
    public static void main(String[] args) {
        var tradingSystem = new TradingSystem("C:\\Users\\wformans\\HxGN\\00-organizational\\UW\\OOP\\input.txt", 100000);
//        var tradingSystem = new TradingSystem(args[0], Integer.parseInt(args[1]));
        tradingSystem.simulateStockMarket();
    }
}
