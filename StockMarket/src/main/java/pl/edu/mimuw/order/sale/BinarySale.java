package pl.edu.mimuw.order.sale;

import pl.edu.mimuw.order.BinaryOrder;

public class BinarySale extends Sale implements BinaryOrder {
    public BinarySale(String shareName, int sharesCount, int priceLimit) {
        super(shareName, sharesCount, priceLimit);
    }
}
