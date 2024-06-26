package pl.edu.mimuw.company;

import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.iostream.Logger;
import pl.edu.mimuw.order.BinaryOrder;
import pl.edu.mimuw.order.DefiniteOrder;
import pl.edu.mimuw.order.ImmediateOrder;
import pl.edu.mimuw.order.Order;
import pl.edu.mimuw.order.purchase.Purchase;
import pl.edu.mimuw.order.sale.Sale;

import java.util.*;

public class Company {
    private final SortedSet<Purchase> purchases;
    private final SortedSet<Sale> sales;
    private int lastPriceOfShare;
    private final String name;

    public Company(String name, int lastPriceOfShare) {
        this.name = name;
        this.lastPriceOfShare = lastPriceOfShare;
        purchases = new TreeSet<>();
        sales = new TreeSet<>();
    }

    public int getLastPriceOfShare() {
        return lastPriceOfShare;
    }

    public void setLastPriceOfShare(int price) {
        lastPriceOfShare = price;
        Logger.log("Last price of " + getName() + " is: " + price + ".");
    }

    public void addOrder(Order order) {
        if (order instanceof Sale) {
            sales.add((Sale) order);
        } else if (order instanceof Purchase) {
            purchases.add((Purchase) order);
        } else {
            throw new IllegalArgumentException("The order type " + order.getClass().getSimpleName() + " is not supported.");
        }
    }

    public String getName() {
        return name;
    }

    public void realizeTransactions() {
        while (!purchases.isEmpty() && !sales.isEmpty()) {
            if (purchases.first().getPriceLimit() < sales.first().getPriceLimit()) {
                return;
            }
            tryCreateTransaction();
        }
        cleanupAwaitingOrders();
    }

    private void cleanupAwaitingOrders() {
        cleanupAwaitingOrders(purchases);
        cleanupAwaitingOrders(sales);
    }

    private void cleanupAwaitingOrders(SortedSet<? extends Order> orders) {
        orders.removeIf(order -> order instanceof BinaryOrder
                || order instanceof ImmediateOrder
                || (order instanceof DefiniteOrder && !((DefiniteOrder) order).isValid())
        );
    }

    private void tryCreateTransaction() {
        if (purchases.first() instanceof BinaryOrder || sales.first() instanceof BinaryOrder) {
            tryCreateBinaryTransaction();
        } else {
            createTransaction();
        }
    }

    private void createTransaction() {
        Purchase purchase = purchases.first();
        Sale sale = sales.first();

        int sharesCount = Math.min(purchase.getSharesCount(), sale.getSharesCount());
        int price = discussPrice(purchase, sale);

        if (!purchase.isPossible(sharesCount * price)) {
            Logger.log("Purchase order was cancelled due to not enough money on investor's account: " + purchase);
            purchases.removeFirst();
            return;
        }

        if (!sale.isPossible(sharesCount)) {
            Logger.log("Sale order was cancelled due to not enough shares possessed by investor: " + sale);
            sales.removeFirst();
            return;
        }

        purchase.buyShare(sharesCount, price);
        sale.sellShare(sharesCount, price);
        if (purchase.getSharesCount() == 0) {
            Logger.log("Purchase was finalized: " + purchase);
            purchases.removeFirst();
        }
        if (sale.getSharesCount() == 0) {
            Logger.log("Sale was finalized: " + sale);
            sales.removeFirst();
        }

        setLastPriceOfShare(price);
        Logger.log("Transaction was completed. Transferred " + sharesCount + " shares of " + name + " for " + price + " each.");
    }

    private void tryCreateBinaryTransaction() {
        if (checkIfBinaryTransactionIsPossible()) {
            createTransaction();
        } else {
            cancelBinaryOrder();
        }
    }

    private void cancelBinaryOrder() {
        if (purchases.first() instanceof BinaryOrder && sales.first() instanceof BinaryOrder) {
            cancelBinaryOrder(purchases.first().getOrderNumber() < sales.first().getOrderNumber() ? purchases : sales);
        } else if (purchases.first() instanceof BinaryOrder) {
            cancelBinaryOrder(purchases);
        } else if (sales.first() instanceof BinaryOrder) {
            cancelBinaryOrder(sales);
        } else {
            throw new IllegalStateException("Tried to cancel one of binary orders, but neither of them is binary.");
        }
    }

    private void cancelBinaryOrder(SortedSet<? extends Order> queue) {
        Logger.log("Could not realize binary order: " + queue.removeFirst());
    }

    private boolean checkIfBinaryTransactionIsPossible() {
        Iterator<Purchase> purchaseIterator = purchases.iterator();
        Iterator<Sale> saleIterator = sales.iterator();
        Purchase firstPurchase = purchaseIterator.next();
        Sale firstSale = saleIterator.next();
        int balance = firstSale.getSharesCount() - firstPurchase.getSharesCount();
        Map<Investor, Integer> fundsNeeded = new HashMap<>();
        Map<Investor, Map<String, Integer>> sharesNeeded = new HashMap<>();

        if (firstPurchase instanceof BinaryOrder && firstSale instanceof BinaryOrder) {
            return checkIfBinaryTransactionIsPossible(purchaseIterator, saleIterator, balance, firstSale.getOrderNumber() < firstPurchase.getOrderNumber(), fundsNeeded, sharesNeeded);
        } else if (firstPurchase instanceof BinaryOrder) {
            return checkIfBinaryTransactionIsPossible(purchaseIterator, saleIterator, balance, true, fundsNeeded, sharesNeeded);
        } else if (firstSale instanceof BinaryOrder) {
            return checkIfBinaryTransactionIsPossible(purchaseIterator, saleIterator, balance, false, fundsNeeded, sharesNeeded);
        } else {
            throw new IllegalStateException("Tried to resolve binary orders, but neither of them is binary.");
        }
    }

    private boolean checkIfBinaryTransactionIsPossible(Iterator<Purchase> purchaseIterator, Iterator<Sale> saleIterator, int saleMinusPurchase, boolean saleShouldBeBiggerThanPurchase, Map<Investor, Integer> fundsNeeded, Map<Investor, Map<String, Integer>> sharesNeeded) {
        if ((saleShouldBeBiggerThanPurchase && saleMinusPurchase >= 0) || (!saleShouldBeBiggerThanPurchase && saleMinusPurchase <= 0)) {
            return true;
        }

        if (saleShouldBeBiggerThanPurchase) {
            if (!purchaseIterator.hasNext()) {
                return false;
            }
            Purchase purchase = purchaseIterator.next();
            Investor investor = purchase.getInvestor();
            fundsNeeded.computeIfAbsent(investor, k -> 0);
            fundsNeeded.put(investor, fundsNeeded.get(investor) + purchase.getTotalPriceLimit());
            if (!purchase.isPossible(fundsNeeded.get(investor))) {
                return false;
            }
            saleMinusPurchase -= purchase.getSharesCount();
            return checkIfBinaryTransactionIsPossible(purchaseIterator, saleIterator, saleMinusPurchase,
                    !(purchase instanceof BinaryOrder) || saleMinusPurchase < 0, fundsNeeded, sharesNeeded);
        } else {
            if (!saleIterator.hasNext()) {
                return false;
            }
            Sale sale = saleIterator.next();
            Investor investor = sale.getInvestor();
            sharesNeeded.computeIfAbsent(investor, k -> new HashMap<>());
            Map<String, Integer> shares = sharesNeeded.get(investor);
            String shareName = sale.getShareName();
            shares.computeIfAbsent(shareName, k -> 0);
            shares.put(shareName, shares.get(shareName) + sale.getSharesCount());
            if (!sale.isPossible(shares.get(shareName))) {
                return false;
            }
            saleMinusPurchase += sale.getSharesCount();
            return checkIfBinaryTransactionIsPossible(purchaseIterator, saleIterator, saleMinusPurchase,
                    sale instanceof BinaryOrder && saleMinusPurchase < 0, fundsNeeded, sharesNeeded);
        }
    }

    private int discussPrice(Purchase purchase, Sale sale) {
        return (purchase.getOrderNumber() < sale.getOrderNumber()) ? purchase.getPriceLimit() : sale.getPriceLimit();
    }
}
