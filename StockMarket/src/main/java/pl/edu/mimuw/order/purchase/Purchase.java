package pl.edu.mimuw.order.purchase;

import pl.edu.mimuw.order.Order;

public abstract class Purchase extends Order {
    public Purchase(String shareName, int sharesCount, int priceLimit) {
        super(shareName, sharesCount, priceLimit);
    }

    @Override
    public int compareTo(Order other) {
        if (getPriceLimit() != other.getPriceLimit()) {
            return -Integer.compare(getPriceLimit(), other.getPriceLimit());
        }
        return Integer.compare(getOrderNumber(), other.getOrderNumber());
    }
}
