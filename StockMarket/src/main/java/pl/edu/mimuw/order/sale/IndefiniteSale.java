package pl.edu.mimuw.order.sale;

import pl.edu.mimuw.order.IndefiniteOrder;

public class IndefiniteSale extends Sale implements IndefiniteOrder {
    public IndefiniteSale(String shareName, int sharesCount, int priceLimit) {
        super(shareName, sharesCount, priceLimit);
    }
}
