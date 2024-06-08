package pl.edu.mimuw.order;

public abstract class Order {
    protected final String shareName;
    protected final int sharesCount;
    protected final int priceLimit;
    private static int ordersCount = 0;
    protected final int orderNumber;

    public Order(String shareName, int sharesCount, int priceLimit) {
        this.shareName = shareName;
        this.sharesCount = sharesCount;
        this.priceLimit = priceLimit;
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
}
