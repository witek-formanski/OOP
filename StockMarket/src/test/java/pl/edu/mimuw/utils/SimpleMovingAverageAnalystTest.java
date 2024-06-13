package pl.edu.mimuw.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.order.purchase.Purchase;
import pl.edu.mimuw.order.sale.Sale;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleMovingAverageAnalystTest {
    private SimpleMovingAverageAnalyst analyst;
    private Investor mockInvestor;

    @BeforeEach
    public void setUp() {
        analyst = new SimpleMovingAverageAnalyst(5, 10);
        mockInvestor = Mockito.mock(Investor.class);
    }

    @Test
    public void testAddPrice() {
        for (int i = 0; i < 100; i++) {
            assertDoesNotThrow(() -> analyst.addPrice("Test Share", 200));
        }
    }

    @Test
    public void testShouldBuyAndSell() {
        String shareName = "Test Share";
        analyst.addPrice(shareName, 200);

        assertFalse(analyst.shouldBuy(shareName));
        assertFalse(analyst.shouldSell(shareName));

        for (int i = 100; i < 110; i++) {
            analyst.addPrice(shareName, i);
        }
        assertTrue(analyst.shouldBuy(shareName));
        assertFalse(analyst.shouldSell(shareName));
    }

    @Test
    public void testCreatePurchaseAndSale() {
        String shareName = "Test Share";
        int money = 1000;
        int maximalPriceChange = 50;
        int availableSharesCount = 100;

        for (int i = 0; i < 10; i++) {
            analyst.addPrice(shareName, i);
        }

        if (analyst.shouldBuy(shareName)) {
            Purchase purchase = analyst.createPurchase(shareName, money, maximalPriceChange, mockInvestor);
            assertNotNull(purchase);
        } else {
            assertThrows(IllegalStateException.class, () -> analyst.createPurchase(shareName, money, maximalPriceChange, mockInvestor));
        }

        if (analyst.shouldSell(shareName)) {
            Sale sale = analyst.createSale(shareName, availableSharesCount, maximalPriceChange, mockInvestor);
            assertNotNull(sale);
        } else {
            assertThrows(IllegalStateException.class, () -> analyst.createSale(shareName, availableSharesCount, maximalPriceChange, mockInvestor));
        }
    }
}
