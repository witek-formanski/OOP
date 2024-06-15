package pl.edu.mimuw.investor;

import pl.edu.mimuw.company.Company;
import pl.edu.mimuw.iostream.Logger;
import pl.edu.mimuw.order.purchase.*;
import pl.edu.mimuw.order.sale.*;
import pl.edu.mimuw.system.TradingSystem;
import pl.edu.mimuw.utils.RandomNumberGenerator;

public class RandomInvestor extends Investor {
    public RandomInvestor(int money) {
        super(money);
    }

    @Override
    public void playRound(TradingSystem system) {
        if (RandomNumberGenerator.flipCoin()) {
            Logger.log("A " + getInvestorShortName() + " investor decided not to make orders in this round.");
            return;
        }

        if (RandomNumberGenerator.flipCoin()) {
            Logger.log("A " + getInvestorShortName() + " investor decided to submit purchase order in this round.");
            makePurchase(system);
        } else {
            Logger.log("A " + getInvestorShortName() + " investor decided to submit sale order in this round.");
            makeSale(system);
        }
    }

    @Override
    public String getInvestorShortName() {
        return "RND";
    }

    private void makePurchase(TradingSystem system) {
        if (money == 0) {
            Logger.log("A " + getInvestorShortName() + " investor has not enough money to make a purchase.");
            return;
        }
        int index = RandomNumberGenerator.getRandom(0, system.getCompaniesCount() - 1);
        Company company = system.getCompanyOfIndex(index);

        int range = RandomNumberGenerator.getRandom(0, system.getMaximalPriceChange());
        int pricePerShare = company.getLastPriceOfShare() + RandomNumberGenerator.getRandom(-range, range);
        int sharesCount = RandomNumberGenerator.getRandom(0, money / pricePerShare);
        if (sharesCount == 0) {
            Logger.log("A " + getInvestorShortName() + " investor will not make a purchase this round.");
            return;
        }

        String shareName = company.getName();
        Purchase purchase;
        int purchaseType = RandomNumberGenerator.getRandom(0, 3);
        purchase = switch (purchaseType) {
            case 0 -> new BinaryPurchase(shareName, sharesCount, pricePerShare, this);
            case 1 ->
                    new DefinitePurchase(shareName, sharesCount, pricePerShare, this, RandomNumberGenerator.getRandom(1, 7));
            case 2 -> new ImmediatePurchase(shareName, sharesCount, pricePerShare, this);
            case 3 -> new IndefinitePurchase(shareName, sharesCount, pricePerShare, this);
            default -> throw new IllegalArgumentException("Exceeded available purchase types count.");
        };

        system.submitOrder(purchase);
    }

    private void makeSale(TradingSystem system) {
        if (shares.isEmpty()) {
            Logger.log("A " + getInvestorShortName() + " investor has not enough sales to make a sale.");
            return;
        }
        int index = RandomNumberGenerator.getRandom(0, shares.size() - 1);
        String shareName = getShareOfIndex(index);
        Company company = system.getCompany(shareName);

        int range = RandomNumberGenerator.getRandom(0, system.getMaximalPriceChange());
        int pricePerShare = company.getLastPriceOfShare() + RandomNumberGenerator.getRandom(-range, range);
        int sharesCount = RandomNumberGenerator.getRandom(0, shares.get(shareName));
        if (sharesCount == 0) {
            Logger.log("A " + getInvestorShortName() + " investor will not make a sale this round.");
            return;
        }

        Sale sale;
        int saleType = RandomNumberGenerator.getRandom(0, 3);
        sale = switch (saleType) {
            case 0 -> new BinarySale(shareName, sharesCount, pricePerShare, this);
            case 1 ->
                    new DefiniteSale(shareName, sharesCount, pricePerShare, this, RandomNumberGenerator.getRandom(1, 7));
            case 2 -> new ImmediateSale(shareName, sharesCount, pricePerShare, this);
            case 3 -> new IndefiniteSale(shareName, sharesCount, pricePerShare, this);
            default -> throw new IllegalArgumentException("Exceeded available sale types count.");
        };

        system.submitOrder(sale);
    }
}
