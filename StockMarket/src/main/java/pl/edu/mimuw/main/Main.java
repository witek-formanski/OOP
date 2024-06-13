package pl.edu.mimuw.main;

import pl.edu.mimuw.iostream.InputReader;
import pl.edu.mimuw.system.TradingSystem;

public class Main {
    public static void main(String[] args) {
        TradingSystem tradingSystem = new TradingSystem(new InputReader(), "C:\\Users\\wformans\\HxGN\\00-organizational\\UW\\OOP\\input.txt", 10000);
        tradingSystem.simulateStockMarket();
    }
}
