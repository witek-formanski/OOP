package pl.edu.mimuw.investor;

import pl.edu.mimuw.system.TradingSystem;

import java.util.HashMap;
import java.util.Map;

public abstract class Investor {
    protected Map<String, Integer> shares;
    protected int money;

    public Investor(int money) {
        this.money = money;
        shares = new HashMap<>();
    }

    public void addShare(String shareName, int quantity) {
        shares.put(shareName, quantity);
    }

    public abstract void playRound(TradingSystem system);
    public void updateMoney(int change) {
        if (money + change < 0) {
            throw new IllegalStateException("Money cannot be negative.");
        }
        money += change;
    }

    public String getShareOfIndex(int index) {
        if (index >= shares.size()) {
            throw new ArrayIndexOutOfBoundsException("Tried to access company at index " + index + ", but the companies count is " + shares.size() + ".");
        }
        return shares.keySet().stream().toList().get(index);
    }
}
