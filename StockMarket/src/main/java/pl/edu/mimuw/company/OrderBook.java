package pl.edu.mimuw.company;

import pl.edu.mimuw.order.Order;

import java.util.PriorityQueue;

public class OrderBook {
    private PriorityQueue<Order> purchases;
    private PriorityQueue<Order> sales;
}
