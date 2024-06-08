package pl.edu.mimuw.investor;

import pl.edu.mimuw.company.Company;
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
            return;
        }

        if (RandomNumberGenerator.flipCoin()) {
            makePurchase(system);
        } else {
            makeSale(system);
        }
    }

    private void makePurchase(TradingSystem system) {
        if (money == 0) {
            return;
        }
        int index = RandomNumberGenerator.getRandom(0, system.getCompaniesCount() - 1);
        Company company = system.getCompanyOfIndex(index);

        int range = RandomNumberGenerator.getRandom(0, 10);
        int pricePerShare = company.getLastPriceOfShare() + RandomNumberGenerator.getRandom(-range, range);;
        int sharesCount = RandomNumberGenerator.getRandom(0, money / pricePerShare);
        if (sharesCount == 0) {
            return;
        }

        String shareName = company.getName();
        Purchase purchase;
        int purchaseType = RandomNumberGenerator.getRandom(0, 3);
        purchase = switch (purchaseType) {
            case 0 -> new BinaryPurchase(shareName, sharesCount, pricePerShare);
            case 1 -> new DefinitePurchase(shareName, sharesCount, pricePerShare);
            case 2 -> new ImmediatePurchase(shareName, sharesCount, pricePerShare);
            case 3 -> new IndefinitePurchase(shareName, sharesCount, pricePerShare);
            default -> throw new IllegalArgumentException("Exceeded available purchase types count.");
        };

        system.submitOrder(purchase);
    }

    private void makeSale(TradingSystem system) {
        if (shares.isEmpty()) {
            return;
        }
        int index = RandomNumberGenerator.getRandom(0, shares.size() - 1);
        String shareName = getShareOfIndex(index);
        Company company = system.getCompany(shareName);

        int range = RandomNumberGenerator.getRandom(0, 10);
        int pricePerShare = company.getLastPriceOfShare() + RandomNumberGenerator.getRandom(-range, range);;
        int sharesCount = RandomNumberGenerator.getRandom(0, shares.get(shareName));
        if (sharesCount == 0) {
            return;
        }

        Sale sale;
        int saleType = RandomNumberGenerator.getRandom(0, 3);
        sale = switch (saleType) {
            case 0 -> new BinarySale(shareName, sharesCount, pricePerShare);
            case 1 -> new DefiniteSale(shareName, sharesCount, pricePerShare);
            case 2 -> new ImmediateSale(shareName, sharesCount, pricePerShare);
            case 3 -> new IndefiniteSale(shareName, sharesCount, pricePerShare);
            default -> throw new IllegalArgumentException("Exceeded available sale types count.");
        };

        system.submitOrder(sale);
    }
}
