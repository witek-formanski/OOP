package pl.edu.mimuw.order.purchase;

import pl.edu.mimuw.order.BinaryOrder;

public class BinaryPurchase extends Purchase implements BinaryOrder {

    public BinaryPurchase(String shareName, int sharesCount, int priceLimit) {
        super(shareName, sharesCount, priceLimit);
    }
}
