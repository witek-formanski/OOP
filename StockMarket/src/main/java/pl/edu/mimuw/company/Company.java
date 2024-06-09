package pl.edu.mimuw.company;

import pl.edu.mimuw.iostream.Logger;
import pl.edu.mimuw.order.BinaryOrder;
import pl.edu.mimuw.order.DefiniteOrder;
import pl.edu.mimuw.order.ImmediateOrder;
import pl.edu.mimuw.order.Order;
import pl.edu.mimuw.order.purchase.Purchase;
import pl.edu.mimuw.order.sale.Sale;

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

    private void tryCreateTransaction() {
        if (purchases.peek() instanceof BinaryOrder || sales.peek() instanceof BinaryOrder) {
            tryCreateBinaryTransaction();
        } else {
            createTransaction();
        }
    }

    private void createTransaction() {
        Purchase purchase = purchases.peek();
        Sale sale = sales.peek();

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

        setLastPriceOfShare(price);
        Logger.log("Transaction was completed. Transferred " + sharesCount + " shares of " + name + " for " + price + " each.");
    }

    private void tryCreateBinaryTransaction() {
//        BinaryTransactionsResolver resolver = new BinaryTransactionsResolver();
//        resolver.tryResolve();
    }

    private int discussPrice(Purchase purchase, Sale sale) {
        return (purchase.getOrderNumber() < sale.getOrderNumber()) ? purchase.getPriceLimit() : sale.getPriceLimit();
    }

//    private class BinaryTransactionsResolver {
//        private void tryResolve() {
//            Purchase firstPurchase = purchases.peek();
//            Sale firstSale = sales.peek();
//
//            if (!(firstPurchase instanceof BinaryOrder) && !(firstSale instanceof BinaryOrder)) {
//                throw new IllegalStateException("Tried to resolve binary orders, but neither of them is binary.");
//            }
//
//            List<OrderPair<Purchase>> purchaseList;
//            List<OrderPair<Sale>> saleList;
//            if (firstPurchase instanceof BinaryOrder && firstSale instanceof BinaryOrder) {
//
//            } else if (firstPurchase instanceof BinaryOrder) {
//                int balance = firstSale.getSharesCount() - firstPurchase.getSharesCount();
//                if (balance >= 0) {
//                    // to się da
//                }
//
//                purchaseList = new ArrayList<>();
//                saleList = new ArrayList<>();
//                purchaseList.add(new OrderPair<>(firstPurchase, true));
//                saleList.add(new OrderPair<>(firstSale, true));
//
//
//                tryResolve(purchaseList, saleList, balance);
//            } else {
//                int balance = firstSale.getSharesCount() - firstPurchase.getSharesCount();
//                if (balance <= 0) {
//                    // to się da
//                }
//            }
//        }
//
//        private boolean tryResolve(List<OrderPair<Purchase>> purchaseList, List<OrderPair<Sale>> saleList, int balance) {
//            if (balance == 0) {
//                return true;
//            }
//            if (balance > 0) { // more to sell than to buy
//
//            }
//        }
//
//        private void createBinaryTransaction(List<OrderPair<Purchase>> purchaseList, List<OrderPair<Sale>> saleList) {
//            while (!purchaseList.isEmpty() && !purchaseList.isEmpty()) { // both non-empty
//                if (purchaseList.getFirst().order != purchases.peek() || saleList.getFirst().order != sales.peek()) {
//                    throw new IllegalStateException("Invalid binary purchase or sale list.");
//                }
//                if (!purchaseList.getFirst().isPossible) {
//                    purchaseList.removeFirst();
//                    purchases.poll();
//                    continue;
//                }
//                if (!saleList.getFirst().isPossible) {
//                    saleList.removeFirst();
//                    sales.poll();
//                    continue;
//                }
//
//            }
//        }
//    }
//
//    private class OrderPair<T extends Order> {
//        private T order;
//        private boolean isPossible;
//
//        private OrderPair(T order, boolean isPossible) {
//            this.order = order;
//            this.isPossible = isPossible;
//        }
//
//        private OrderPair(T order) {
//            this.order = order;
//            isPossible = !(order instanceof BinaryOrder);
//        }
//    }
}
