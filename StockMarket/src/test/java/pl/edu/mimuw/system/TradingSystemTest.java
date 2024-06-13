package pl.edu.mimuw.system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.mimuw.company.Company;
import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.iostream.InputReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TradingSystemTest {
    private TradingSystem tradingSystem;
    private Map<String, Company> companies;
    private List<Investor> investors;
    private InputReader inputReader;

    @BeforeEach
    public void setUp() {
        companies = new HashMap<>();
        investors = new ArrayList<>();
        inputReader = mock(InputReader.class);
        when(inputReader.getCompanies()).thenReturn(companies);
        when(inputReader.getInvestors()).thenReturn(investors);
        tradingSystem = new TradingSystem(inputReader, "testFile", 10);
        tradingSystem.setCompanies(companies);
    }

    @Test
    public void testGetMaximalPriceChange() {
        assertEquals(10, tradingSystem.getMaximalPriceChange());
    }

    @Test
    public void testGetCompaniesCount() {
        companies.put("TestCompany", mock(Company.class));
        assertEquals(1, tradingSystem.getCompaniesCount());
    }

    @Test
    public void testGetCurrentRound() {
        assertEquals(0, tradingSystem.getCurrentRound());
    }

    @Test
    public void testGetCompany() {
        Company company = mock(Company.class);
        companies.put("TestCompany", company);
        assertEquals(company, tradingSystem.getCompany("TestCompany"));
    }

    @Test
    public void testGetCompanyUnknown() {
        assertThrows(IllegalArgumentException.class, () -> tradingSystem.getCompany("UnknownCompany"));
    }

    @Test
    public void testGetCompanyOfIndex() {
        Company company = mock(Company.class);
        companies.put("TestCompany", company);
        assertEquals(company, tradingSystem.getCompanyOfIndex(0));
    }

    @Test
    public void testGetCompanyOfIndexOutOfBounds() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> tradingSystem.getCompanyOfIndex(1));
    }
}
