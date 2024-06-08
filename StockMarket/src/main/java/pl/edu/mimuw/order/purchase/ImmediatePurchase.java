package pl.edu.mimuw.order.purchase;

import pl.edu.mimuw.order.ImmediateOrder;

public class ImmediatePurchase extends Purchase implements ImmediateOrder {
    public ImmediatePurchase(String shareName, int sharesCount, int priceLimit) {
        super(shareName, sharesCount, priceLimit);
    }
}
