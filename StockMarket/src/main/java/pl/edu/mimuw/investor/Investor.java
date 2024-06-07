package pl.edu.mimuw.investor;

import pl.edu.mimuw.company.Share;
import pl.edu.mimuw.system.TradingSystem;

import java.util.HashMap;
import java.util.Map;

public abstract class Investor {
    protected Map<Share, Integer> shares;
    protected int money;

    public Investor(int money) {
        this.money = money;
        shares = new HashMap<>();
    }

    public void addShare(Share share, int quantity) {
        shares.put(share, quantity);
    }

    public abstract void playRound(TradingSystem system);
}
