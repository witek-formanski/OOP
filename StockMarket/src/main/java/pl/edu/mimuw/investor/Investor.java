package pl.edu.mimuw.investor;

import pl.edu.mimuw.iostream.Logger;
import pl.edu.mimuw.system.TradingSystem;

import java.util.HashMap;
import java.util.Map;

public abstract class Investor {
    protected Map<String, Integer> shares;
    protected int money;
//    protected int availableMoney;

    public Investor(int money) {
        this.money = money;
//        availableMoney = money;
        shares = new HashMap<>();
    }

    public void updateShares(String shareName, int quantity) {
        int finalQuantity = shares.getOrDefault(shareName, 0) + quantity;
        if (finalQuantity < 0) {
            throw new IllegalStateException("Shares quantity cannot be negative.");
        }
        if (quantity > 0) {
            Logger.log("An investor obtained " + quantity + " new shares of " + shareName + ".");
        } else {
            Logger.log("An investor disposed of " + (-quantity) + " shares of " + shareName + ".");
        }

        shares.put(shareName, finalQuantity);
    }

    public abstract void playRound(TradingSystem system);
    public void updateMoney(int change) {
        if (money + change < 0) {
            throw new IllegalStateException("Money cannot be negative.");
        }
        if (change > 0) {
            Logger.log("A transfer of " + change + " was transferred to the investor's account.");
        } else {
            Logger.log("A transfer of " + (-change) + " was send from the investor's account.");
        }
        money += change;
    }

//    public void blockMoney(int funds) {
//        if (availableMoney - funds < 0) {
//            throw new IllegalStateException("Cannot block more money than available.");
//        }
//        availableMoney -= funds;
//    }

    public String getShareOfIndex(int index) {
        if (index >= shares.size()) {
            throw new ArrayIndexOutOfBoundsException("Tried to access company at index " + index + ", but the companies count is " + shares.size() + ".");
        }
        return shares.keySet().stream().toList().get(index);
    }

    public void buyShare(String shareName, int sharesCount, int price) {
        updateMoney(-price * sharesCount);
        updateShares(shareName, sharesCount);
        Logger.log("An investor bought " + sharesCount + " shares of " + shareName + " for " + price + " each.");
    }

    public void sellShare(String shareName, int sharesCount, int price) {
        updateMoney(price * sharesCount);
        updateShares(shareName, -sharesCount);
        Logger.log("An investor sold " + sharesCount + " shares of " + shareName + " for " + price + " each.");
    }

    public boolean hasFunds(int money) {
        return this.money >= money;
    }

    public boolean hasShares(String shareName, int sharesCount) {
        return shares.containsKey(shareName) && shares.get(shareName) >= sharesCount;
    }
}
