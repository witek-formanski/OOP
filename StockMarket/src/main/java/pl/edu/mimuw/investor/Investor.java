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

    public void updateShares(String shareName, int quantity) {
        int finalQuantity = shares.getOrDefault(shareName, 0) + quantity;
        if (finalQuantity < 0) {
            throw new IllegalStateException("Shares quantity cannot be negative.");
        }
        shares.put(shareName, finalQuantity);
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

    public void buyShare(String shareName, int sharesCount, int price) {
        updateMoney(-price * sharesCount);
        updateShares(shareName, sharesCount);
    }

    public void sellShare(String shareName, int sharesCount, int price) {
        updateMoney(price * sharesCount);
        updateShares(shareName, -sharesCount);
    }
}
