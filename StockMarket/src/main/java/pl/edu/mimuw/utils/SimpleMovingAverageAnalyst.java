package pl.edu.mimuw.utils;

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

        //ToDo: shouldBuy, shouldSell

        double sum = 0;
        for (int i = smaLowLength; i < smaHighLength; i++) {
            sum += companyData.prices.get(i);
        }
        companyData.lowAverage = sum / smaLowLength;

        for (int i = 0; i < smaLowLength; i++) {
            sum += companyData.prices.get(i);
        }
        companyData.highAverage = sum / smaHighLength;
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

    public Purchase createPurchase(String companyName, int money) {
        if (!shouldBuy(companyName)) {
            throw new IllegalStateException("Cannot create a purchase for shares that shouldn't been bought.");
        }

        //ToDo
    }

    public Sale createSale(String companyName, int sharesCount) {
        if (!shouldSell(companyName)) {
            throw new IllegalStateException("Cannot create a sale for shares that shouldn't been sold.");
        }

        //ToDo
    }

    private class CompanyData {
        private final List<Integer> prices;
        private double lowAverage;
        private double highAverage;
        private boolean shouldBuy;
        private boolean shouldSell;

        private CompanyData() {
            prices = new ArrayList<>();
            lowAverage = 0;
            highAverage = 0;
            shouldBuy = false;
            shouldSell = false;
        }
    }
}
