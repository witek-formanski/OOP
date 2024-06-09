package pl.edu.mimuw.order.sale;

import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.order.DefiniteOrder;

public class DefiniteSale extends Sale implements DefiniteOrder {
    private int roundsLeft;
    public DefiniteSale(String shareName, int sharesCount, int priceLimit, Investor investor, int roundsLeft) {
        super(shareName, sharesCount, priceLimit, investor);
        this.roundsLeft = roundsLeft;
    }

    @Override
    public boolean decrement() {
        roundsLeft--;
        if (roundsLeft < 0) {
            throw new IllegalStateException("Rounds left count should be always non-negative.");
        }
        return roundsLeft > 0;
    }

    @Override
    public String toString() {
        return super.toString() + "The order will expire after " + roundsLeft + " rounds.";
    }
}
