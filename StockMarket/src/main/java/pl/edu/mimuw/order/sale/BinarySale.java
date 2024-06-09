package pl.edu.mimuw.order.sale;

import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.order.BinaryOrder;

public class BinarySale extends Sale implements BinaryOrder {
    public BinarySale(String shareName, int sharesCount, int priceLimit, Investor investor) {
        super(shareName, sharesCount, priceLimit, investor);
    }
}
