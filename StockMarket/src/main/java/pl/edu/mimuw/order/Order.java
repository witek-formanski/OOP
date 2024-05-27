package pl.edu.mimuw.order;

public abstract class Order {
    private OrderType type;
    private String identificator;
    private int sharesCount;
    private int priceLimit;
    private static int ordersCount = 0;
    private int orderNumber;
    private int round;

    public Order() {
        ordersCount++;
        orderNumber = ordersCount;
    }

    public int compareTo(Order other) {

    }
}
