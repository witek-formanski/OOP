package pl.edu.mimuw.system;

import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.utils.IOHandler;

import java.util.List;

import static java.util.Collections.shuffle;

public class TradingSystem {
    private static int currentRound;
    private final int roundsCount;
    private List<Investor> investors;

    public TradingSystem(String fileName, int roundsCount) {
        this.roundsCount = roundsCount;
        readInput(fileName);
    }

    public static int getCurrentRound() {
        return currentRound;
    }

    public void simulateStockMarket() {
        for (currentRound = 1; currentRound <= roundsCount; currentRound++) {
            simulateRound();
        }
    }

    private void simulateRound() {
        askInvestors();
        realizeTransactions();
    }

    private void askInvestors() {
        shuffle(investors);
        for (Investor investor : investors) {
            //...
        }

    }

    private void realizeTransactions() {

    }

    private void readInput(String fileName) {
        investors = IOHandler.readFromFile(fileName);
    }
}
