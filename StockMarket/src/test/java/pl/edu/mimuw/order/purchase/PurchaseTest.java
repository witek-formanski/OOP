package pl.edu.mimuw.order.purchase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.mimuw.investor.Investor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PurchaseTest {
    private Purchase purchase;
    private Investor investor;

    @BeforeEach
    public void setUp() {
        investor = mock(Investor.class);
        purchase = new Purchase("Share1", 10, 100, investor) {};
    }

    @Test
    public void testCompareTo() {
        Purchase other1 = new Purchase("Share2", 10, 200, investor) {};
        Purchase other2 = new Purchase("Share2", 10, 100, investor) {};
        Purchase other3 = new Purchase("Share2", 10, 50, investor) {};

        assertEquals(1, purchase.compareTo(other1));
        assertEquals(-1, purchase.compareTo(other2));
        assertEquals(-1, purchase.compareTo(other3));
    }

    @Test
    public void testBuyShare() {
        doNothing().when(investor).buyShare(anyString(), anyInt(), anyInt());
        assertDoesNotThrow(() -> purchase.buyShare(5, 50));
        verify(investor, times(1)).buyShare(anyString(), anyInt(), anyInt());
    }

    @Test
    public void testBuyShareMoreThanOrdered() {
        doNothing().when(investor).buyShare(anyString(), anyInt(), anyInt());
        assertThrows(IllegalStateException.class, () -> purchase.buyShare(15, 150));
    }

    @Test
    public void testIsPossible() {
        when(investor.hasFunds(anyInt())).thenReturn(true);
        assertTrue(purchase.isPossible(1000));
        verify(investor, times(1)).hasFunds(anyInt());
    }
}
