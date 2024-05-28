package pl.edu.mimuw.investor;

public class SimpleMovingAverageInvestor extends Investor {
    private int sma5;
    private int sma10;
    public SimpleMovingAverageInvestor(int money) {
        super(money);
    }
}
