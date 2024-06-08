package pl.edu.mimuw.investor;

import pl.edu.mimuw.system.TradingSystem;
import pl.edu.mimuw.utils.SimpleMovingAverageAnalyst;

public class SimpleMovingAverageInvestor extends Investor {
    private final static SimpleMovingAverageAnalyst smaAnalyst = new SimpleMovingAverageAnalyst(5, 10);

    public SimpleMovingAverageInvestor(int money) {
        super(money);
    }

    @Override
    public void playRound(TradingSystem system) {
        if (system.getCurrentRound() < smaAnalyst.getSmaHighLength()) {
            return;
        }


    }
}
