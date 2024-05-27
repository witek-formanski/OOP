package pl.edu.mimuw.system;

import pl.edu.mimuw.investor.Investor;

import java.util.List;

public class TradingSystem {
    private int currentRound;
    private int roundsCount;
    private List<Investor> investors;

    public TradingSystem(String fileName, int roundsCount) {
        this.roundsCount = roundsCount;
    }

    public void simulateStockMarket() {
        for (currentRound = 1; currentRound <= roundsCount; currentRound++) {
            simulateRound();
        }
    }

    private void simulateRound() {

    }
}
