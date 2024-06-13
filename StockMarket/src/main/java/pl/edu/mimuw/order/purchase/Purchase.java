package pl.edu.mimuw.order.purchase;

import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.order.Order;

public abstract class Purchase extends Order {
    public Purchase(String shareName, int sharesCount, int priceLimit, Investor investor) {
        super(shareName, sharesCount, priceLimit, investor);
    }

    @Override
    public int compareTo(Order other) {
        if (getPriceLimit() != other.getPriceLimit()) {
            return -Integer.compare(getPriceLimit(), other.getPriceLimit());
        }
        return Integer.compare(getOrderNumber(), other.getOrderNumber());
    }

    public void buyShare(int sharesCount, int price) {
        investor.buyShare(shareName, sharesCount, price);
        if (this.sharesCount - sharesCount < 0) {
            throw new IllegalStateException("Bought more shares than ordered.");
        }
        this.sharesCount -= sharesCount;
    }

    @Override
    public boolean isPossible(int money) {
        return investor.hasFunds(money);
    }
}
