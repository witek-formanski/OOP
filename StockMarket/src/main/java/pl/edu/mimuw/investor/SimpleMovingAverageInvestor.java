package pl.edu.mimuw.investor;

import pl.edu.mimuw.company.Company;
import pl.edu.mimuw.iostream.Logger;
import pl.edu.mimuw.system.TradingSystem;
import pl.edu.mimuw.utils.SimpleMovingAverageAnalyst;

import java.util.List;

public class SimpleMovingAverageInvestor extends Investor {
    private final SimpleMovingAverageAnalyst smaAnalyst;

    public SimpleMovingAverageInvestor(int money) {
        super(money);
        smaAnalyst = new SimpleMovingAverageAnalyst(5, 10);
    }

    @Override
    public void playRound(TradingSystem system) {
        List<Company> companies = system.getAvailableCompaniesList();
        for (Company company : companies) {
            smaAnalyst.addPrice(company.getName(), company.getLastPriceOfShare());
        }

        if (system.getCurrentRound() < smaAnalyst.getSmaHighLength()) {
            Logger.log("A " + getInvestorShortName() + " investor will wait for more data to make decisions.");
            return;
        }

        for (Company company : companies) {
            String companyName = company.getName();
            if (smaAnalyst.shouldBuy(companyName) && money >= system.getLastPriceOf(companyName) + 10) {
                system.submitOrder(smaAnalyst.createPurchase(companyName, money, system.getMaximalPriceChange(), this));
                return;
            }
            if (smaAnalyst.shouldSell(companyName) && shares.containsKey(companyName) && shares.get(companyName) > 0) {
                system.submitOrder(smaAnalyst.createSale(companyName, shares.get(companyName), system.getMaximalPriceChange(), this));
                return;
            }
        }

        Logger.log("A " + getInvestorShortName() + " investor decided not to make orders in this round.");
    }

    @Override
    public String getInvestorShortName() {
        return "SMA";
    }
}
