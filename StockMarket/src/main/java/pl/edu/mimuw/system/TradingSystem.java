package pl.edu.mimuw.system;

import pl.edu.mimuw.company.Share;
import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.utils.IOHandler;

import java.util.List;
import java.util.Map;

import static java.util.Collections.shuffle;

public class TradingSystem {
    private Map<String, Share> shares;

    public int getRoundsCount() {
        return roundsCount;
    }
    private int currentRound;
    private final int roundsCount;
    private final List<Investor> investors;

    public TradingSystem(String fileName, int roundsCount) {
        this.roundsCount = roundsCount;
        investors = readInput(fileName);
    }

    public int getSharesCount() {
        return shares.size();
    }

    public int getCurrentRound() {
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
            investor.playRound(this);
        }
    }

    private void realizeTransactions() {

    }

    private List<Investor> readInput(String fileName) {
        return IOHandler.readFromFile(fileName, this);
    }

    public void setShares(Map<String, Share> shares) {
        this.shares = shares;
    }
}
