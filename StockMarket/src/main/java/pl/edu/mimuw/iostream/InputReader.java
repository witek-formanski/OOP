package pl.edu.mimuw.iostream;

import pl.edu.mimuw.company.Company;
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

public class InputReader {
    private Map<String, Company> companies;
    private List<Investor> investors;

    public void readFromFile(String fileName) {
        List<Investor> investors = new ArrayList<>();
        Map<String, Company> companies = new HashMap<>();
        int randomInvestorsCount = 0;
        int simpleMovingAverageInvestorsCount = 0;
        int money;
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
                        companies.put(name, new Company(name, price));
                    }
                    this.companies = companies;
                }

                // Third line - Initial cash and shares for each investor
                else if (line.matches("^\\d+( \\w+:\\d+)*$")) {
                    tokens = line.split(" ");
                    money = Integer.parseInt(tokens[0]);

                    for (int i = 0; i < randomInvestorsCount; i++) {
                        investors.add(new RandomInvestor(money));
                    }
                    for (int i = 0; i < simpleMovingAverageInvestorsCount; i++) {
                        investors.add(new SimpleMovingAverageInvestor(money));
                    }

                    for (int i = 1; i < tokens.length; i++) {
                        String[] parts = tokens[i].split(":");
                        String name = parts[0];
                        int quantity = Integer.parseInt(parts[1]);
                        if (companies.get(name) == null) {
                            throw new IllegalArgumentException("The share of name " + name + " was not found in shares list.");
                        }
                        for (Investor investor : investors) {
                            investor.updateShares(name, quantity);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid input data provided.");
        }

        this.investors = investors;
    }

    public List<Investor> getInvestors() {
        return investors;
    }

    public Map<String, Company> getCompanies() {
        return companies;
    }
}
