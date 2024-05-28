package pl.edu.mimuw.company;

import pl.edu.mimuw.order.Order;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Company {
    private PriorityQueue<Order> purchases;
    private PriorityQueue<Order> sales;
    private ArrayList<Transaction> transactions;
}
