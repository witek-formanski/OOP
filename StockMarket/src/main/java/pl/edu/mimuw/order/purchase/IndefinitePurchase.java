package pl.edu.mimuw.order.purchase;

import pl.edu.mimuw.order.IndefiniteOrder;

public class IndefinitePurchase extends Purchase implements IndefiniteOrder {
    public IndefinitePurchase(String shareName, int sharesCount, int priceLimit) {
        super(shareName, sharesCount, priceLimit);
    }
}
