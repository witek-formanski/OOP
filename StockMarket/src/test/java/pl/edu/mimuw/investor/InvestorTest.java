package pl.edu.mimuw.investor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.mimuw.system.TradingSystem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class InvestorTest {
    private Investor investor;
    private TradingSystem system;

    @BeforeEach
    public void setUp() {
        system = mock(TradingSystem.class);
        investor = new Investor(1000) {
            @Override
            public void playRound(TradingSystem system) {
                // Mock implementation
            }

            @Override
            public String getInvestorShortName() {
                return "Mock";
            }
        };
    }

    @Test
    public void testUpdateShares() {
        assertDoesNotThrow(() -> investor.updateShares("Share1", 5));
        assertThrows(IllegalStateException.class, () -> investor.updateShares("Share1", -10));
    }

    @Test
    public void testUpdateMoney() {
        assertDoesNotThrow(() -> investor.updateMoney(500));
        assertDoesNotThrow(() -> investor.updateMoney(-1500));
        assertThrows(IllegalStateException.class, () -> investor.updateMoney(-1));
    }

    @Test
    public void testGetShareOfIndex() {
        investor.updateShares("Share1", 5);
        assertEquals("Share1", investor.getShareOfIndex(0));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> investor.getShareOfIndex(1));
    }

    @Test
    public void testBuyShare() {
        assertDoesNotThrow(() -> investor.buyShare("Share1", 5, 100));
        assertEquals(500, investor.money);
        assertEquals(5, investor.shares.get("Share1"));
    }

    @Test
    public void testSellShare() {
        investor.updateShares("Share1", 5);
        assertDoesNotThrow(() -> investor.sellShare("Share1", 5, 100));
        assertEquals(1500, investor.money);
        assertEquals(0, investor.shares.get("Share1"));
    }

    @Test
    public void testHasFunds() {
        assertTrue(investor.hasFunds(500));
        assertFalse(investor.hasFunds(1500));
    }

    @Test
    public void testHasShares() {
        investor.updateShares("Share1", 5);
        assertTrue(investor.hasShares("Share1", 5));
        assertFalse(investor.hasShares("Share1", 10));
    }
}
