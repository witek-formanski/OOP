package pl.edu.mimuw.company;

import pl.edu.mimuw.iostream.Logger;
import pl.edu.mimuw.order.BinaryOrder;
import pl.edu.mimuw.order.DefiniteOrder;
import pl.edu.mimuw.order.ImmediateOrder;
import pl.edu.mimuw.order.Order;
import pl.edu.mimuw.order.purchase.Purchase;
import pl.edu.mimuw.order.sale.Sale;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Company {
    private final Queue<Purchase> purchases;
    private final Queue<Sale> sales;
    private int lastPriceOfShare;
    private final String name;

    public Company(String name, int lastPriceOfShare) {
        this.name = name;
        this.lastPriceOfShare = lastPriceOfShare;
        purchases = new PriorityQueue<>();
        sales = new PriorityQueue<>();
    }

    public int getLastPriceOfShare() {
        return lastPriceOfShare;
    }

    public void setLastPriceOfShare(int price) {
        lastPriceOfShare = price;
    } //ToDo

    public void addOrder(Order order) {
        if (order instanceof Sale) {
            sales.add((Sale) order);
        } else if (order instanceof Purchase) {
            purchases.add((Purchase) order);
        } else {
            throw new IllegalArgumentException("The order type " + order.getClass().getName() + " is not supported.");
        }
    }

    public String getName() {
        return name;
    }

    public void realizeTransactions() {
        while (!purchases.isEmpty() && !sales.isEmpty()) {
            if (purchases.peek().getPriceLimit() < sales.peek().getPriceLimit()) {
                Logger.log(""); //ToDo: logging
                return;
            }
            tryCreateTransaction();
        }
        cleanupOrders();
    }

    private void cleanupOrders() {
        cleanupQueue(purchases);
        cleanupQueue(sales);
    }

    private void cleanupQueue(Queue<? extends Order> orders) {
        orders.removeIf(order -> order instanceof BinaryOrder
                || order instanceof ImmediateOrder
                || (order instanceof DefiniteOrder && !((DefiniteOrder) order).decrement())
        );
    }

    private void tryCreateTransaction() { //ToDo: binary orders
        Purchase purchase = purchases.peek();
        Sale sale = sales.peek();

        if (purchase instanceof BinaryOrder || sale instanceof BinaryOrder) {
            tryCreateBinaryTransaction();
            return;
        }

        int sharesCount = Math.min(purchase.getSharesCount(), sale.getSharesCount());
        int price = discussPrice(purchase, sale);

        purchase.buyShare(sharesCount, price);
        sale.sellShare(sharesCount, price);
        if (purchase.getSharesCount() == 0) {
            purchases.poll();
        }
        if (sale.getSharesCount() == 0) {
            sales.poll();
        }

        Logger.log("Transaction was completed.");
    }

    private void tryCreateBinaryTransaction() {
        BinaryTransactionsResolver resolver = new BinaryTransactionsResolver();
        resolver.tryResolve();
    }

    private int discussPrice(Purchase purchase, Sale sale) {
        return (purchase.getOrderNumber() < sale.getOrderNumber()) ? purchase.getPriceLimit() : sale.getPriceLimit();
    }

    private class BinaryTransactionsResolver {
        List<OrderPair<Purchase>> purchaseList;
        List<OrderPair<Sale>> saleList;

        private BinaryTransactionsResolver() {
            purchaseList = new ArrayList<>();
            saleList = new ArrayList<>();
        }

        public void tryResolve() {

        }
    }

    private class OrderPair<T extends Order> {
        private T order;
        private boolean isPossible;

        private OrderPair(T order, boolean isPossible) {
            this.order = order;
            this.isPossible = isPossible;
        }
    }
}
