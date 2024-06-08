package pl.edu.mimuw.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleMovingAverageAnalyst {
    private final int smaLowLength;
    private final int smaHighLength;
    private final Map<String, List<Integer>> prices;
    private final Map<String, Double[]> averages;

    public SimpleMovingAverageAnalyst(int smaLowLength, int smaHighLength) {
        this.smaLowLength = smaLowLength;
        this.smaHighLength = smaHighLength;
        prices = new HashMap<>();
        averages = new HashMap<>();
    }

    public int getSmaLowLength() {
        return smaLowLength;
    }

    public int getSmaHighLength() {
        return smaHighLength;
    }

    public void addPrice(String shareName, int price) {
//        if (!prices.containsKey(shareName)) {
//            prices.put(shareName, new ArrayList<>());
//        }
//        List<Integer> priceList = prices.get(shareName);
        List<Integer> priceList = prices.computeIfAbsent(shareName, k -> new ArrayList<>());
        priceList.add(price);
        if (priceList.size() == smaHighLength + 1) {
            priceList.removeFirst();
            calculateAverages(shareName);
        } else if (priceList.size() > smaHighLength + 1) {
            throw new IllegalStateException("Exceeded expected length of price list. Expected less than or equal to " + (smaHighLength + 1) + ", but was: " + priceList.size() + ".");
        }
    }

    private void calculateAverages(String shareName) {
        List<Integer> priceList = prices.get(shareName);
        if (priceList == null || priceList.size() != smaHighLength) {
            throw new IllegalStateException("Invalid price list for share: " + shareName + ".");
        }

//        if (!averages.containsKey(shareName)) {
//            averages.put(shareName, new Double[2]);
//        }
//        Double[] smas = averages.get(shareName);
        Double[] smas = averages.computeIfAbsent(shareName, k -> new Double[2]);

        double sum = 0;
        for (int i = smaLowLength; i < smaHighLength; i++) {
            sum += priceList.get(i);
        }
        smas[0] = sum / smaLowLength;

        for (int i = 0; i < smaLowLength; i++) {
            sum += priceList.get(i);
        }
        smas[1] = sum / smaHighLength;
    }
}
