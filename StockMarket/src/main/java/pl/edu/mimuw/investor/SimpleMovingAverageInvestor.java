package pl.edu.mimuw.investor;

import pl.edu.mimuw.company.Company;
import pl.edu.mimuw.order.purchase.Purchase;
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
            return;
        }

        for (Company company : companies) {
            String companyName = company.getName();
            if (smaAnalyst.shouldBuy(companyName)) {
                system.submitOrder(smaAnalyst.createPurchase(companyName, money));
                return;
            }
            if (smaAnalyst.shouldSell(companyName) && shares.containsKey(companyName)) {
                system.submitOrder(smaAnalyst.createSale(companyName, shares.get(companyName)));
                return;
            }
        }
    }
}
