package pl.edu.mimuw.investor;

import pl.edu.mimuw.company.Share;
import pl.edu.mimuw.system.TradingSystem;
import pl.edu.mimuw.utils.RandomNumberGenerator;

public class RandomInvestor extends Investor {
    public RandomInvestor(int money) {
        super(money);
    }

    @Override
    public void playRound(TradingSystem system) {
        int currentRound = system.getCurrentRound();
        int randomNumber = RandomNumberGenerator.getRandom(0, 100);

        int chosenShareIndex = currentRound * randomNumber % system.getSharesCount();

        Share share = shares.keySet().toArray(new Share[]{})[chosenShareIndex % shares.size()];
        if (RandomNumberGenerator.flipCoin()) {

            system.purchaseShare(chosenShareIndex);
        } else {
            system.saleShare();
        }
    }
}
