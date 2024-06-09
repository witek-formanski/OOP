package pl.edu.mimuw.order.sale;

import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.order.DefiniteOrder;

public class DefiniteSale extends Sale implements DefiniteOrder {
    private int roundsLeft;
    public DefiniteSale(String shareName, int sharesCount, int priceLimit, Investor investor, int roundsLeft) {
        super(shareName, sharesCount, priceLimit, investor);
        this.roundsLeft = roundsLeft;
    }
}
