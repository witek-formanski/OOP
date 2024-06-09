package pl.edu.mimuw.order;

import pl.edu.mimuw.investor.Investor;

public abstract class Order {
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
    }

    public abstract int compareTo(Order other);

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
}
