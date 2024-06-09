package pl.edu.mimuw.order.purchase;

import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.order.IndefiniteOrder;

public class IndefinitePurchase extends Purchase implements IndefiniteOrder {
    public IndefinitePurchase(String shareName, int sharesCount, int priceLimit, Investor investor) {
        super(shareName, sharesCount, priceLimit, investor);
    }
}
