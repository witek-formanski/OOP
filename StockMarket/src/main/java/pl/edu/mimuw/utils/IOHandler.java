package pl.edu.mimuw.utils;

import pl.edu.mimuw.company.Share;
import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.investor.RandomInvestor;
import pl.edu.mimuw.investor.SimpleMovingAverageInvestor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IOHandler {
    public static List<Investor> readFromFile(String fileName) {
        List<Investor> Investors = new ArrayList<>();
        Map<String, Share> shares = new HashMap<>();
        int randomInvestorsCount = 0;
        int simpleMovingAverageInvestorsCount = 0;
        int cash;
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
                        Investors.add(new RandomInvestor(cash));
                    }
                    for (int i = 0; i < simpleMovingAverageInvestorsCount; i++) {
                        Investors.add(new SimpleMovingAverageInvestor(cash));
                    }

                    for (int i = 1; i < tokens.length; i++) {
                        String[] parts = tokens[i].split(":");
                        String name = parts[0];
                        int quantity = Integer.parseInt(parts[1]);
                        Share share = shares.get(name);
                        for (Investor investor : Investors) {
                            investor.addShare(share, quantity);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Investors;
    }
}
