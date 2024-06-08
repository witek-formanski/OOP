package pl.edu.mimuw.order.sale;

import pl.edu.mimuw.order.DefiniteOrder;

public class DefiniteSale extends Sale implements DefiniteOrder {
    private int roundsLeft;
    public DefiniteSale(String shareName, int sharesCount, int priceLimit, int roundsLeft) {
        super(shareName, sharesCount, priceLimit);
        this.roundsLeft = roundsLeft;
    }
}
