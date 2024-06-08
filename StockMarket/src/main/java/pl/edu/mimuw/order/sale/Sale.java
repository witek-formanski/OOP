package pl.edu.mimuw.order.sale;

import pl.edu.mimuw.order.Order;

public class Sale extends Order {
    public Sale(String shareName, int sharesCount, int priceLimit) {
        super(shareName, sharesCount, priceLimit);
    }

    @Override
    public int compareTo(Order other) {
        if (getPriceLimit() != other.getPriceLimit()) {
            return Integer.compare(getPriceLimit(), other.getPriceLimit());
        }
        return Integer.compare(getOrderNumber(), other.getOrderNumber());
    }
}
