package pl.edu.mimuw.order.sale;

import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.order.Order;

public abstract class Sale extends Order {
    public Sale(String shareName, int sharesCount, int priceLimit, Investor investor) {
        super(shareName, sharesCount, priceLimit, investor);
    }

    @Override
    public int compareTo(Order other) {
        if (getPriceLimit() != other.getPriceLimit()) {
            return Integer.compare(getPriceLimit(), other.getPriceLimit());
        }
        return Integer.compare(getOrderNumber(), other.getOrderNumber());
    }

    public void sellShare(int sharesCount, int price) {
        investor.sellShare(shareName, sharesCount, price);
        if (this.sharesCount - sharesCount < 0) {
            throw new IllegalStateException("Sold more shares than ordered.");
        }
        this.sharesCount -= sharesCount;
    }
}
