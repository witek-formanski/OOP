package pl.edu.mimuw.order.purchase;

import pl.edu.mimuw.order.Order;

public abstract class Purchase extends Order {
    @Override
    public int compareTo(Order other) {
        if (getPriceLimit() != other.getPriceLimit()) {
            return -Integer.compare(getPriceLimit(), other.getPriceLimit());
        }
        return Integer.compare(getOrderNumber(), other.getOrderNumber());
    }
}
