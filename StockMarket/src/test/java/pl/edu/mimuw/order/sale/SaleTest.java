package pl.edu.mimuw.order.sale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.mimuw.investor.Investor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SaleTest {
    private Sale sale;
    private Investor investor;

    @BeforeEach
    public void setUp() {
        investor = mock(Investor.class);
        sale = new Sale("Share1", 10, 100, investor) {};
    }

    @Test
    public void testCompareTo() {
        Sale other1 = new Sale("Share2", 10, 200, investor) {};
        Sale other2 = new Sale("Share2", 10, 100, investor) {};
        Sale other3 = new Sale("Share2", 10, 50, investor) {};

        assertEquals(-1, sale.compareTo(other1));
        assertEquals(-1, sale.compareTo(other2));
        assertEquals(1, sale.compareTo(other3));
    }

    @Test
    public void testSellShare() {
        doNothing().when(investor).sellShare(anyString(), anyInt(), anyInt());
        assertDoesNotThrow(() -> sale.sellShare(5, 50));
        verify(investor, times(1)).sellShare(anyString(), anyInt(), anyInt());
    }

    @Test
    public void testSellShareMoreThanOrdered() {
        doNothing().when(investor).sellShare(anyString(), anyInt(), anyInt());
        assertThrows(IllegalStateException.class, () -> sale.sellShare(15, 150));
    }

    @Test
    public void testIsPossible() {
        when(investor.hasShares(anyString(), anyInt())).thenReturn(true);
        assertTrue(sale.isPossible(5));
        verify(investor, times(1)).hasShares(anyString(), anyInt());
    }
}
