package pl.edu.mimuw.order;

import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.iostream.Logger;

public abstract class Order implements Comparable<Order> {
    protected final String shareName;
    protected int sharesCount;
    protected final int priceLimit;
    protected final Investor investor;
    private static int ordersCount = 0;
    protected final int orderNumber;

    public Order(String shareName, int sharesCount, int priceLimit, Investor investor) {
        this.shareName = shareName;
        this.sharesCount = sharesCount;
        this.priceLimit = priceLimit;
        this.investor = investor;
        orderNumber = ++ordersCount;
        Logger.log("Created " + this);
    }

    public int getPriceLimit() {
        return priceLimit;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getShareName() {
        return shareName;
    }

    public int getSharesCount() {
        return sharesCount;
    }

    public int getTotalPriceLimit() {
        return priceLimit * sharesCount;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + ": " + sharesCount + " shares of " + shareName + ", " + priceLimit + " for each. The order number is: " + orderNumber + ".";
    }
}
