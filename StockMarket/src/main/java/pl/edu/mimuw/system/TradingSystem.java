package pl.edu.mimuw.system;

import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.investor.RandomInvestor;
import pl.edu.mimuw.investor.SimpleMovingAverageInvestor;
import pl.edu.mimuw.company.Share;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.shuffle;

public class TradingSystem {
    private static int currentRound;
    private final int roundsCount;
    private List<Investor> investors;

    public TradingSystem(String fileName, int roundsCount) {
        this.roundsCount = roundsCount;
        readInput(fileName);
    }

    public static int getCurrentRound() {
        return currentRound;
    }

    public void simulateStockMarket() {
        for (currentRound = 1; currentRound <= roundsCount; currentRound++) {
            simulateRound();
        }
    }

    private void simulateRound() {
        askInvestors();
        realizeTransactions();
    }

    private void askInvestors() {
        shuffle(investors);
        for (Investor investor : investors) {
            //...
        }

    }

    private void realizeTransactions() {

    }

    private void readInput(String fileName) {
            List<RandomInvestor> rInvestors = new ArrayList<>();
            List<SimpleMovingAverageInvestor> sInvestors = new ArrayList<>();
            Map<String, Share> shares = new HashMap<>();
            int randomInvestorsCount = 0;
            int simpleMovingAverageInvestorsCount = 0;
            int cash = 0;
            String[] tokens;

            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("#")) {
                        continue;
                    }
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    // First line - Investors
                    if (line.matches("^(R|S)( (R|S))*$")) {
                        tokens = line.split(" ");
                        for (String token : tokens) {
                            if (token.equals("R")) {
                                randomInvestorsCount++;
                            } else if (token.equals("S")) {
                                simpleMovingAverageInvestorsCount++;
                            }
                        }
                    }

                    // Second line - Shares
                    else if (line.matches("^\\w+:\\d+( \\w+:\\d+)*$")) {
                        tokens = line.split(" ");
                        for (String token : tokens) {
                            String[] parts = token.split(":");
                            String name = parts[0];
                            int price = Integer.parseInt(parts[1]);
                            shares.put(name, new Share(name, price));
                        }
                    }

                    // Third line - Initial cash and shares for each investor
                    else if (line.matches("^\\d+( \\w+:\\d+)*$")) {
                        tokens = line.split(" ");
                        cash = Integer.parseInt(tokens[0]);

                        for (int i = 0; i < randomInvestorsCount; i++) {
                            rInvestors.add(new RandomInvestor(cash));
                        }
                        for (int i = 0; i < simpleMovingAverageInvestorsCount; i++) {
                            sInvestors.add(new SimpleMovingAverageInvestor(cash));
                        }

                        for (int i = 1; i < tokens.length; i++) {
                            String[] parts = tokens[i].split(":");
                            String name = parts[0];
                            int quantity = Integer.parseInt(parts[1]);
                            Share share = shares.get(name);
                            for (RandomInvestor investor : rInvestors) {
                                investor.addShare(share, quantity);
                            }
                            for (SimpleMovingAverageInvestor investor : sInvestors) {
                                investor.addShare(share, quantity);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
