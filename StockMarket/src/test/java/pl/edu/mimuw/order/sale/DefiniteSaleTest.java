package pl.edu.mimuw.order.sale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.mimuw.investor.Investor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class DefiniteSaleTest {
    private DefiniteSale definiteSale;
    private Investor investor;

    @BeforeEach
    public void setUp() {
        investor = mock(Investor.class);
        definiteSale = new DefiniteSale("Share1", 10, 100, investor, 5);
    }

    @Test
    public void testIsValidThrowsException() {
        for (int i = 0; i < 4; i++) {
            assertTrue(definiteSale.isValid());
        }
        assertFalse(definiteSale.isValid());
        assertThrows(IllegalStateException.class, () -> definiteSale.isValid());
    }
}
