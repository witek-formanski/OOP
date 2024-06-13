package pl.edu.mimuw.investor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.mimuw.company.Company;
import pl.edu.mimuw.system.TradingSystem;
import pl.edu.mimuw.utils.SimpleMovingAverageAnalyst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SimpleMovingAverageInvestorTest {
    private SimpleMovingAverageInvestor investor;
    private TradingSystem system;
    private SimpleMovingAverageAnalyst smaAnalyst;

    @BeforeEach
    public void setUp() {
        investor = new SimpleMovingAverageInvestor(1000);
        system = mock(TradingSystem.class);
        smaAnalyst = mock(SimpleMovingAverageAnalyst.class);
    }

    @Test
    public void testCreation() {
        assertNotNull(investor);
    }

    @Test
    public void testPlayRoundBeforeSmaHighLength() {
        when(system.getCurrentRound()).thenReturn(3);
        when(smaAnalyst.getSmaHighLength()).thenReturn(10);

        List<Company> companies = new ArrayList<>();
        Company mockCompany = mock(Company.class);
        when(mockCompany.getName()).thenReturn("MockCompany");
        when(mockCompany.getLastPriceOfShare()).thenReturn(100);
        companies.add(mockCompany);
        when(system.getAvailableCompaniesList()).thenReturn(companies);

        investor.playRound(system);

        verify(system, times(0)).submitOrder(any());
    }

    @Test
    public void testPlayRoundNoAction() {
        when(system.getCurrentRound()).thenReturn(11);
        when(smaAnalyst.getSmaHighLength()).thenReturn(10);

        List<Company> companies = new ArrayList<>();
        Company mockCompany = mock(Company.class);
        when(mockCompany.getName()).thenReturn("MockCompany");
        when(mockCompany.getLastPriceOfShare()).thenReturn(100);
        companies.add(mockCompany);
        when(system.getAvailableCompaniesList()).thenReturn(companies);

        when(smaAnalyst.shouldBuy("MockCompany")).thenReturn(false);
        when(smaAnalyst.shouldSell("MockCompany")).thenReturn(false);

        investor.playRound(system);

        verify(system, times(0)).submitOrder(any());
    }

        @Test
        public void testPlayRound() {
            Company company1 = mock(Company.class);
            Company company2 = mock(Company.class);
            when(system.getAvailableCompaniesList()).thenReturn(Arrays.asList(company1, company2));
            when(company1.getName()).thenReturn("Company1");
            when(company2.getName()).thenReturn("Company2");
            when(company1.getLastPriceOfShare()).thenReturn(100);
            when(company2.getLastPriceOfShare()).thenReturn(200);
            when(system.getLastPriceOf("Company1")).thenReturn(100);
            when(system.getLastPriceOf("Company2")).thenReturn(200);
            when(system.getMaximalPriceChange()).thenReturn(10);

            assertDoesNotThrow(() -> investor.playRound(system));
            verify(system, times(1)).getAvailableCompaniesList();
            verify(company1, times(1)).getName();
            verify(company2, times(1)).getName();
            verify(company1, times(1)).getLastPriceOfShare();
            verify(company2, times(1)).getLastPriceOfShare();
        }

        @Test
        public void testGetInvestorShortName() {
            assertEquals("SMA", investor.getInvestorShortName());
        }
}
