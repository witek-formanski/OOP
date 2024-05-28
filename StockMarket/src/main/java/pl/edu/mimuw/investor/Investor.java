package pl.edu.mimuw.investor;

import pl.edu.mimuw.company.Share;

import java.util.HashMap;
import java.util.Map;

public abstract class Investor {
    private Map<Share, Integer> shares;
    private int money;

    public Investor(int money) {
        this.money = money;
        shares = new HashMap<>();
    }

    public void addShare(Share share, int quantity) {
        shares.put(share, quantity);
    }
}
