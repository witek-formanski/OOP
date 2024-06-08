package pl.edu.mimuw.company;

import pl.edu.mimuw.order.Order;
import pl.edu.mimuw.order.purchase.Purchase;
import pl.edu.mimuw.order.sale.Sale;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Company {
    private PriorityQueue<Purchase> purchases;
    private PriorityQueue<Sale> sales;
    private ArrayList<Transaction> transactions;
    private int lastPriceOfShare;
    private String name;

    public Company(String name, int lastPriceOfShare) {
        this.name = name;
        this.lastPriceOfShare = lastPriceOfShare;
    }

    public int getLastPriceOfShare() {
        return lastPriceOfShare;
    }

    public void setLastPriceOfShare(int price) {
        lastPriceOfShare = price;
    }

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
}
