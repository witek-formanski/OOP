package pl.edu.mimuw.order.sale;

import pl.edu.mimuw.order.ImmediateOrder;

public class ImmediateSale extends Sale implements ImmediateOrder {
    public ImmediateSale(String shareName, int sharesCount, int priceLimit) {
        super(shareName, sharesCount, priceLimit);
    }
}
