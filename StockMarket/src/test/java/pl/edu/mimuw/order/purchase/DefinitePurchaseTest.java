package pl.edu.mimuw.order.purchase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.mimuw.investor.Investor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class DefinitePurchaseTest {
    private DefinitePurchase definitePurchase;
    private Investor investor;

    @BeforeEach
    public void setUp() {
        investor = mock(Investor.class);
        definitePurchase = new DefinitePurchase("Share1", 10, 100, investor, 50);
    }

    @Test
    public void testIsValidThrowsException() {
        for (int i = 0; i < 49; i++) {
            assertTrue(definitePurchase.isValid());
        }
        assertFalse(definitePurchase.isValid());
        assertThrows(IllegalStateException.class, () -> definitePurchase.isValid());
    }
}
