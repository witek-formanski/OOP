package pl.edu.mimuw.utils;

import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.iostream.Logger;
import pl.edu.mimuw.order.purchase.*;
import pl.edu.mimuw.order.sale.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleMovingAverageAnalyst {
    private final int smaLowLength;
    private final int smaHighLength;
    private final Map<String, CompanyData> data;

    public SimpleMovingAverageAnalyst(int smaLowLength, int smaHighLength) {
        this.smaLowLength = smaLowLength;
        this.smaHighLength = smaHighLength;
        data = new HashMap<>();
    }

    public int getSmaHighLength() {
        return smaHighLength;
    }

    public void addPrice(String shareName, int price) {
        List<Integer> priceList = data.computeIfAbsent(shareName, k -> new CompanyData()).prices;
        priceList.add(price);
        if (priceList.size() == smaHighLength + 1) {
            priceList.removeFirst();
            calculateAverages(shareName);
        } else if (priceList.size() > smaHighLength + 1) {
            throw new IllegalStateException("Exceeded expected length of price list. Expected less than or equal to " + (smaHighLength + 1) + ", but was: " + priceList.size() + ".");
        }
    }

    private void calculateAverages(String shareName) {
        CompanyData companyData = getCompanyData(shareName);
        if (companyData == null || companyData.prices.size() != smaHighLength) {
            throw new IllegalStateException("Invalid price list for share: " + shareName + ".");
        }

        double previousLow = companyData.lowAverage;
        double previousHigh = companyData.highAverage;

        double sum = 0;
        for (int i = smaLowLength; i < smaHighLength; i++) {
            sum += companyData.prices.get(i);
        }
        companyData.lowAverage = sum / smaLowLength;

        for (int i = 0; i < smaLowLength; i++) {
            sum += companyData.prices.get(i);
        }
        companyData.highAverage = sum / smaHighLength;

        decide(companyData, previousLow, previousHigh);
    }

    private void decide(CompanyData companyData, double previousLow, double previousHigh) {
        companyData.clearDecisions();

        if (previousLow >= previousHigh && companyData.lowAverage < companyData.highAverage) {
            companyData.shouldSell = true;
            companyData.decisionCoefficient = companyData.highAverage - companyData.lowAverage;
            companyData.normalizeDecisionCoefficient();
        } else if (previousLow <= previousHigh && companyData.lowAverage > companyData.highAverage) {
            companyData.shouldBuy = true;
            companyData.decisionCoefficient = companyData.lowAverage - companyData.highAverage;
            companyData.normalizeDecisionCoefficient();
        }
    }

    private CompanyData getCompanyData(String companyName) {
        if (!data.containsKey(companyName)) {
            throw new IllegalArgumentException("Company " + companyName + " not present in data.");
        }
        return data.get(companyName);
    }

    public boolean shouldBuy(String companyName) {
        return getCompanyData(companyName).shouldBuy;
    }

    public boolean shouldSell(String companyName) {
        return getCompanyData(companyName).shouldSell;
    }

    public Purchase createPurchase(String companyName, int money, int maximalPriceChange, Investor investor) {
        if (!shouldBuy(companyName)) {
            throw new IllegalStateException("Cannot create a purchase for shares that shouldn't been bought.");
        }

        CompanyData companyData = getCompanyData(companyName);
        double coefficient = companyData.decisionCoefficient;
        int pricePerShare = companyData.prices.getLast() + (int) ((coefficient - 0.5) * maximalPriceChange);
        int sharesCount = Math.min(money / pricePerShare, 1 + (int) (10 * coefficient));

        Purchase purchase;
        if (coefficient < 0.25) {
            purchase = new ImmediatePurchase(companyName, sharesCount, pricePerShare, investor);
        } else if (coefficient < 0.5) {
            purchase = new BinaryPurchase(companyName, sharesCount, pricePerShare, investor);
        } else if (coefficient < 0.75) {
            purchase = new DefinitePurchase(companyName, sharesCount, pricePerShare, investor, (int) (6 * coefficient));
        } else {
            purchase = new IndefinitePurchase(companyName, sharesCount, pricePerShare, investor);
        }

        Logger.log("A SimpleMovingAverageAnalyst decided to create " + purchase);
        return purchase;
    }

    public Sale createSale(String companyName, int availableSharesCount, int maximalPriceChange, Investor investor) {
        if (!shouldSell(companyName)) {
            throw new IllegalStateException("Cannot create a sale for shares that shouldn't been sold.");
        }

        CompanyData companyData = getCompanyData(companyName);
        double coefficient = companyData.decisionCoefficient;
        int pricePerShare = companyData.prices.getLast() - (int) ((coefficient - 0.5) * maximalPriceChange);
        int sharesCount = Math.min(availableSharesCount, 1 + (int) (10 * coefficient));

        Sale sale;
        if (coefficient < 0.25) {
            sale = new ImmediateSale(companyName, sharesCount, pricePerShare, investor);
        } else if (coefficient < 0.5) {
            sale = new BinarySale(companyName, sharesCount, pricePerShare, investor);
        } else if (coefficient < 0.75) {
            sale = new DefiniteSale(companyName, sharesCount, pricePerShare, investor, (int) (6 * coefficient));
        } else {
            sale = new IndefiniteSale(companyName, sharesCount, pricePerShare, investor);
        }

        Logger.log("A SimpleMovingAverageAnalyst decided to create " + sale);
        return sale;
    }

    private static class CompanyData {
        private final List<Integer> prices;
        private double lowAverage;
        private double highAverage;
        private boolean shouldBuy;
        private boolean shouldSell;
        // The decisionCoefficient should be between 0 and 1.
        // The bigger the coefficient, the stronger the decision should be.
        private double decisionCoefficient;

        private CompanyData() {
            prices = new ArrayList<>();
            lowAverage = 0;
            highAverage = 0;
            clearDecisions();
        }

        private void clearDecisions() {
            shouldBuy = false;
            shouldSell = false;
        }

        private void normalizeDecisionCoefficient() {
            decisionCoefficient = Math.min(1, decisionCoefficient);
            decisionCoefficient = Math.max(0, decisionCoefficient);
        }
    }
}
