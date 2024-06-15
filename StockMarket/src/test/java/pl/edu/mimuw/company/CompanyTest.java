package pl.edu.mimuw.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.mimuw.investor.Investor;
import pl.edu.mimuw.order.Order;
import pl.edu.mimuw.order.purchase.*;
import pl.edu.mimuw.order.sale.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        Order purchase = new Purchase("Share1", 10, 100, investor) {
        };
        Order sale = new Sale("Share1", 10, 100, investor) {
        };
        assertDoesNotThrow(() -> company.addOrder(purchase));
        assertDoesNotThrow(() -> company.addOrder(sale));
        assertThrows(IllegalArgumentException.class, () -> company.addOrder(order));
    }

    @Test
    public void testGetName() {
        assertEquals("Company1", company.getName());
    }

    @Test
    public void testRealizeTransactions() {
        Investor investor = mock(Investor.class);
        when(investor.hasFunds(anyInt())).thenReturn(true);
        when(investor.hasShares(anyString(), anyInt())).thenReturn(true);

        Purchase purchase1 = new IndefinitePurchase("Share1", 10, 100, investor);
        Purchase purchase2 = new DefinitePurchase("Share2", 20, 300, investor, 2);
        Purchase purchase3 = new ImmediatePurchase("Share3", 5, 400, investor);
        Purchase purchase4 = new BinaryPurchase("Share4", 10, 350, investor);

        Sale sale1 = new IndefiniteSale("Share1", 10, 100, investor);
        Sale sale2 = new DefiniteSale("Share2", 20, 300, investor, 2);
        Sale sale3 = new ImmediateSale("Share3", 5, 400, investor);
        Sale sale4 = new BinarySale("Share4", 10, 350, investor);

        company.addOrder(purchase1);
        company.addOrder(purchase2);
        company.addOrder(purchase3);
        company.addOrder(purchase4);
        company.addOrder(sale1);
        company.addOrder(sale2);
        company.addOrder(sale3);
        company.addOrder(sale4);

        company.realizeTransactions();

        assertEquals(purchase1.getPriceLimit(), 100);
        assertEquals(purchase1.getSharesCount(), 10);
        assertEquals(purchase2.getPriceLimit(), 300);
        assertEquals(purchase2.getSharesCount(), 0);
        assertEquals(purchase3.getPriceLimit(), 400);
        assertEquals(purchase3.getSharesCount(), 0);
        assertEquals(purchase4.getPriceLimit(), 350);
        assertEquals(purchase4.getSharesCount(), 10);

        assertEquals(sale1.getPriceLimit(), 100);
        assertEquals(sale1.getSharesCount(), 0);
        assertEquals(sale2.getPriceLimit(), 300);
        assertEquals(sale2.getSharesCount(), 5);
        assertEquals(sale3.getPriceLimit(), 400);
        assertEquals(sale3.getSharesCount(), 5);
        assertEquals(sale4.getPriceLimit(), 350);
        assertEquals(sale4.getSharesCount(), 10);
    }

    @Test
    public void testDoesNotRealizeTransactions() {
        Investor investor = mock(Investor.class);
        when(investor.hasFunds(anyInt())).thenReturn(false);
        when(investor.hasShares(anyString(), anyInt())).thenReturn(false);

        Purchase purchase1 = new IndefinitePurchase("Share1", 10, 100, investor);
        Purchase purchase2 = new DefinitePurchase("Share2", 20, 300, investor, 2);
        Purchase purchase3 = new ImmediatePurchase("Share3", 5, 400, investor);
        Purchase purchase4 = new BinaryPurchase("Share4", 10, 350, investor);

        Sale sale1 = new IndefiniteSale("Share1", 10, 100, investor);
        Sale sale2 = new DefiniteSale("Share2", 20, 300, investor, 2);
        Sale sale3 = new ImmediateSale("Share3", 5, 400, investor);
        Sale sale4 = new BinarySale("Share4", 10, 350, investor);

        company.addOrder(purchase1);
        company.addOrder(purchase2);
        company.addOrder(purchase3);
        company.addOrder(purchase4);
        company.addOrder(sale1);
        company.addOrder(sale2);
        company.addOrder(sale3);
        company.addOrder(sale4);

        company.realizeTransactions();

        assertEquals(purchase1.getPriceLimit(), 100);
        assertEquals(purchase1.getSharesCount(), 10);
        assertEquals(purchase2.getPriceLimit(), 300);
        assertEquals(purchase2.getSharesCount(), 20);
        assertEquals(purchase3.getPriceLimit(), 400);
        assertEquals(purchase3.getSharesCount(), 5);
        assertEquals(purchase4.getPriceLimit(), 350);
        assertEquals(purchase4.getSharesCount(), 10);

        assertEquals(sale1.getPriceLimit(), 100);
        assertEquals(sale1.getSharesCount(), 10);
        assertEquals(sale2.getPriceLimit(), 300);
        assertEquals(sale2.getSharesCount(), 20);
        assertEquals(sale3.getPriceLimit(), 400);
        assertEquals(sale3.getSharesCount(), 5);
        assertEquals(sale4.getPriceLimit(), 350);
        assertEquals(sale4.getSharesCount(), 10);
    }

    @Test
    public void testRealizeTransactionsOfTheSameShare() {
        Investor investor = mock(Investor.class);
        when(investor.hasFunds(anyInt())).thenReturn(true);
        when(investor.hasShares(anyString(), anyInt())).thenReturn(true);

        Purchase purchase1 = new IndefinitePurchase("Share1", 10, 500, investor);
        Purchase purchase2 = new DefinitePurchase("Share1", 20, 300, investor, 2);
        Purchase purchase3 = new ImmediatePurchase("Share1", 5, 400, investor);
        Purchase purchase4 = new BinaryPurchase("Share1", 10, 350, investor);

        Sale sale1 = new IndefiniteSale("Share1", 10, 100, investor);
        Sale sale2 = new DefiniteSale("Share1", 20, 200, investor, 2);
        Sale sale3 = new ImmediateSale("Share1", 5, 250, investor);
        Sale sale4 = new BinarySale("Share1", 10, 250, investor);

        company.addOrder(purchase1);
        company.addOrder(purchase2);
        company.addOrder(purchase3);
        company.addOrder(purchase4);
        company.addOrder(sale1);
        company.addOrder(sale2);
        company.addOrder(sale3);
        company.addOrder(sale4);

        company.realizeTransactions();

        assertEquals(purchase1.getPriceLimit(), 500);
        assertEquals(purchase1.getSharesCount(), 0);
        assertEquals(purchase2.getPriceLimit(), 300);
        assertEquals(purchase2.getSharesCount(), 0);
        assertEquals(purchase3.getPriceLimit(), 400);
        assertEquals(purchase3.getSharesCount(), 0);
        assertEquals(purchase4.getPriceLimit(), 350);
        assertEquals(purchase4.getSharesCount(), 0);

        assertEquals(sale1.getPriceLimit(), 100);
        assertEquals(sale1.getSharesCount(), 0);
        assertEquals(sale2.getPriceLimit(), 200);
        assertEquals(sale2.getSharesCount(), 0);
        assertEquals(sale3.getPriceLimit(), 250);
        assertEquals(sale3.getSharesCount(), 0);
        assertEquals(sale4.getPriceLimit(), 250);
        assertEquals(sale4.getSharesCount(), 0);
    }
}
