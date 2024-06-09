package pl.edu.mimuw.order.purchase;

import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.order.DefiniteOrder;

public class DefinitePurchase extends Purchase implements DefiniteOrder {
    private int roundsLeft;
    public DefinitePurchase(String shareName, int sharesCount, int priceLimit, Investor investor, int roundsLeft) {
        super(shareName, sharesCount, priceLimit, investor);
        this.roundsLeft = roundsLeft;
    }
}
