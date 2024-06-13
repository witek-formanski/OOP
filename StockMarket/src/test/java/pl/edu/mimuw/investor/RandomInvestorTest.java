package pl.edu.mimuw.investor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pl.edu.mimuw.company.Company;
import pl.edu.mimuw.iostream.Logger;
import pl.edu.mimuw.order.sale.BinarySale;
import pl.edu.mimuw.system.TradingSystem;
import pl.edu.mimuw.utils.RandomNumberGenerator;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class RandomInvestorTest {
    private TradingSystem mockSystem;
    private Company mockCompany;
    private RandomInvestor investor;

    @BeforeEach
    public void setUp() {
        mockSystem = mock(TradingSystem.class);
        mockCompany = mock(Company.class);
        investor = new RandomInvestor(1000);

        when(mockSystem.getCompaniesCount()).thenReturn(1);
        when(mockSystem.getCompanyOfIndex(0)).thenReturn(mockCompany);
        when(mockSystem.getCompany(anyString())).thenReturn(mockCompany);
        when(mockCompany.getLastPriceOfShare()).thenReturn(100);
        when(mockSystem.getMaximalPriceChange()).thenReturn(10);
    }

    @Test
    public void testInitialMoneyLogged() {
        try (MockedStatic<Logger> mockedLogger = mockStatic(Logger.class)) {
            new RandomInvestor(1000);
            mockedLogger.verify(() -> Logger.log("A RND investor with initial money 1000 was created."));
        }
    }

    @Test
    public void testPlayRoundDoesNothingWhenCoinFlipsTrue() {
        try (MockedStatic<RandomNumberGenerator> mockedRNG = mockStatic(RandomNumberGenerator.class)) {
            mockedRNG.when(RandomNumberGenerator::flipCoin).thenReturn(true);

            investor.playRound(mockSystem);

            verify(mockSystem, never()).submitOrder(any());
        }
    }

    @Test
    public void testMakeSaleSubmitsOrder() {
        try (MockedStatic<RandomNumberGenerator> mockedRNG = mockStatic(RandomNumberGenerator.class)) {
            Map<String, Integer> shares = new HashMap<>();
            shares.put("TestCompany", 10);
            investor.shares = shares;

            mockedRNG.when(RandomNumberGenerator::flipCoin).thenReturn(false);
            mockedRNG.when(() -> RandomNumberGenerator.getRandom(0, shares.size() - 1)).thenReturn(0);
            mockedRNG.when(() -> RandomNumberGenerator.getRandom(0, 10)).thenReturn(1);
            mockedRNG.when(() -> RandomNumberGenerator.getRandom(-10, 10)).thenReturn(0);
            mockedRNG.when(() -> RandomNumberGenerator.getRandom(0, 10)).thenReturn(1);
            mockedRNG.when(() -> RandomNumberGenerator.getRandom(0, 3)).thenReturn(0);

            investor.playRound(mockSystem);

            verify(mockSystem).submitOrder(any(BinarySale.class));
        }
    }

    @Test
    public void testMakePurchaseWithNoMoney() {
        investor = new RandomInvestor(0);

        try (MockedStatic<RandomNumberGenerator> mockedRNG = mockStatic(RandomNumberGenerator.class)) {
            mockedRNG.when(RandomNumberGenerator::flipCoin).thenReturn(false);
            mockedRNG.when(() -> RandomNumberGenerator.getRandom(0, 0)).thenReturn(0);
            mockedRNG.when(() -> RandomNumberGenerator.getRandom(-10, 10)).thenReturn(0);
            mockedRNG.when(() -> RandomNumberGenerator.getRandom(0, 10)).thenReturn(0);

            investor.playRound(mockSystem);

            verify(mockSystem, never()).submitOrder(any());
        }
    }

    @Test
    public void testMakeSaleWithNoShares() {
        try (MockedStatic<RandomNumberGenerator> mockedRNG = mockStatic(RandomNumberGenerator.class)) {
            mockedRNG.when(RandomNumberGenerator::flipCoin).thenReturn(false);

            investor.playRound(mockSystem);

            verify(mockSystem, never()).submitOrder(any());
        }
    }
}
