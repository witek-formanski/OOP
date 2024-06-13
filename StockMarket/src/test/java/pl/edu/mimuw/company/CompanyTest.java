package pl.edu.mimuw.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.order.Order;
import pl.edu.mimuw.order.purchase.Purchase;
import pl.edu.mimuw.order.sale.Sale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CompanyTest {
    private Company company;

    @BeforeEach
    public void setUp() {
        company = new Company("Company1", 100);
    }

    @Test
    public void testGetLastPriceOfShare() {
        assertEquals(100, company.getLastPriceOfShare());
    }

    @Test
    public void testSetLastPriceOfShare() {
        company.setLastPriceOfShare(200);
        assertEquals(200, company.getLastPriceOfShare());
    }

    @Test
    public void testAddOrder() {
        Investor investor = mock(Investor.class);
        Order order = mock(Order.class);
        Order purchase = new Purchase("Share1", 10, 100, investor) {};
        Order sale = new Sale("Share1", 10, 100, investor) {};
        assertDoesNotThrow(() -> company.addOrder(purchase));
        assertDoesNotThrow(() -> company.addOrder(sale));
        assertThrows(IllegalArgumentException.class, () -> company.addOrder(order));
    }

    @Test
    public void testGetName() {
        assertEquals("Company1", company.getName());
    }
}
