package pl.edu.mimuw.order.sale;

import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.order.IndefiniteOrder;

public class IndefiniteSale extends Sale implements IndefiniteOrder {
    public IndefiniteSale(String shareName, int sharesCount, int priceLimit, Investor investor) {
        super(shareName, sharesCount, priceLimit, investor);
    }
}
