package pl.edu.mimuw.system;

import pl.edu.mimuw.company.Company;
import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.order.Order;
import pl.edu.mimuw.iostream.InputReader;

import java.util.List;
import java.util.Map;

import static java.util.Collections.shuffle;

public class TradingSystem {
    private Map<String, Company> companies;
    private int currentRound;
    private final int roundsCount;
    private final List<Investor> investors;
    private final int maximalPriceChange = 10;

    public TradingSystem(String fileName, int roundsCount) {
        this.roundsCount = roundsCount;
        investors = readInput(fileName);
    }

    public int getMaximalPriceChange() {
        return maximalPriceChange;
    }

    public int getCompaniesCount() {
        return companies.size();
    }

    public int getCurrentRound() {
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
            investor.playRound(this);
        }
    }

    private void realizeTransactions() {
        for (Company company : getAvailableCompaniesList()) {
            company.realizeTransactions();
        }
    }

    private List<Investor> readInput(String fileName) {
        return InputReader.readFromFile(fileName, this);
    }

    public void submitOrder(Order order) {
        Company company = getCompany(order.getShareName());
        company.addOrder(order);
    }

    public List<String> getAvailableCompaniesNamesList() {
        return companies.keySet().stream().toList();
    }

    public List<Company> getAvailableCompaniesList() {
        return companies.values().stream().toList();
    }

    public int getLastPriceOfShare(String companyName) {
        Company company = getCompany(companyName);
        return company.getLastPriceOfShare();
    }

    public Company getCompany(String companyName) {
        Company company = companies.get(companyName);
        if (company == null) {
            throw new IllegalArgumentException("Unknown company name " + companyName + ".");
        }
        return company;
    }

    public void setCompanies(Map<String, Company> companies) {
        this.companies = companies;
    }

    public Company getCompanyOfIndex(int index) {
        if (index >= getCompaniesCount()) {
            throw new ArrayIndexOutOfBoundsException("Tried to access company at index " + index + ", but the companies count is " + getCompaniesCount() + ".");
        }
        return companies.get(getAvailableCompaniesNamesList().get(index));
    }
}
