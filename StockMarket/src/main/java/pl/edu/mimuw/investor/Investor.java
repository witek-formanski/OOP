package pl.edu.mimuw.investor;

import pl.edu.mimuw.iostream.Logger;
import pl.edu.mimuw.system.TradingSystem;

import java.util.HashMap;
import java.util.Map;

public abstract class Investor {
    protected Map<String, Integer> shares;
    protected int money;

    public Investor(int money) {
        this.money = money;
        shares = new HashMap<>();
        Logger.log("A " + getInvestorShortName() + " investor with initial money " + money + " was created.");
    }

    public void updateShares(String shareName, int quantity) {
        int finalQuantity = shares.getOrDefault(shareName, 0) + quantity;
        if (finalQuantity < 0) {
            throw new IllegalStateException("Shares quantity cannot be negative.");
        }
        if (quantity > 0) {
            Logger.log("A " + getInvestorShortName() + " investor obtained " + quantity + " new shares of " + shareName + ".");
        } else {
            Logger.log("A " + getInvestorShortName() + " investor disposed of " + (-quantity) + " shares of " + shareName + ".");
        }

        shares.put(shareName, finalQuantity);
    }

    public abstract void playRound(TradingSystem system);

    public void updateMoney(int change) {
        if (money + change < 0) {
            throw new IllegalStateException("Money cannot be negative.");
        }
        if (change > 0) {
            Logger.log("A transfer of " + change + " was transferred to the " + getInvestorShortName() + " investor's account.");
        } else {
            Logger.log("A transfer of " + (-change) + " was send from the " + getInvestorShortName() + " investor's account.");
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

    public void printSummary() {
        Logger.log(getInvestorShortName() + ": ");
        Logger.log("Funds: " + money);
        Logger.log("Shares: " + getSharesSummary());
    }

    public abstract String getInvestorShortName();

    public String getSharesSummary() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> share : shares.entrySet()) {
            stringBuilder.append(share.getKey()).append(":").append(share.getValue()).append(" ");
        }
        return stringBuilder.toString();
    }
}
