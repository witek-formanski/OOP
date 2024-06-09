package pl.edu.mimuw.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;
import pl.edu.mimuw.iostream.Logger;
import pl.edu.mimuw.order.*;
import pl.edu.mimuw.order.purchase.Purchase;
import pl.edu.mimuw.order.sale.Sale;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CompanyTest {

    private Company company;
    private final String companyName = "TestCompany";
    private final int initialSharePrice = 100;

    @BeforeEach
    public void setUp() {
        company = new Company(companyName, initialSharePrice);
    }

    @Test
    public void testGetLastPriceOfShare() {
        assertEquals(initialSharePrice, company.getLastPriceOfShare());
    }

    @Test
    public void testSetLastPriceOfShare() {
        int newPrice = 200;
        company.setLastPriceOfShare(newPrice);
        assertEquals(newPrice, company.getLastPriceOfShare());
    }

    @Test
    public void testAddOrder_Sale() {
        Sale sale = mock(Sale.class);
        company.addOrder(sale);
        assertTrue(company.getSales().contains(sale));
    }

    @Test
    public void testAddOrder_Purchase() {
        Purchase purchase = mock(Purchase.class);
        company.addOrder(purchase);
        assertTrue(company.getPurchases().contains(purchase));
    }

    @Test
    public void testAddOrder_InvalidOrder() {
        Order order = mock(Order.class);
        assertThrows(IllegalArgumentException.class, () -> company.addOrder(order));
    }

    @Test
    public void testRealizeTransactions_NoTransactions() {
        company.realizeTransactions();
        assertTrue(company.getPurchases().isEmpty());
        assertTrue(company.getSales().isEmpty());
    }

    @Test
    public void testRealizeTransactions_CompleteTransaction() {
        Purchase purchase = mock(Purchase.class);
        Sale sale = mock(Sale.class);

        when(purchase.getPriceLimit()).thenReturn(100);
        when(sale.getPriceLimit()).thenReturn(100);
        when(purchase.getSharesCount()).thenReturn(10);
        when(sale.getSharesCount()).thenReturn(10);

        company.addOrder(purchase);
        company.addOrder(sale);

        company.realizeTransactions();

        verify(purchase).buyShare(10, 100);
        verify(sale).sellShare(10, 100);

        assertTrue(company.getPurchases().isEmpty());
        assertTrue(company.getSales().isEmpty());

        ArgumentCaptor<String> logCaptor = ArgumentCaptor.forClass(String.class);
        verifyStatic(Logger.class);
        Logger.log(logCaptor.capture());
        assertEquals("Transaction was completed. Transferred 10 shares of TestCompany for 100 each.", logCaptor.getValue());
    }

    @Test
    public void testRealizeTransactions_PartialTransaction() {
        Purchase purchase = mock(Purchase.class);
        Sale sale = mock(Sale.class);

        when(purchase.getPriceLimit()).thenReturn(100);
        when(sale.getPriceLimit()).thenReturn(100);
        when(purchase.getSharesCount()).thenReturn(10);
        when(sale.getSharesCount()).thenReturn(5);

        company.addOrder(purchase);
        company.addOrder(sale);

        company.realizeTransactions();

        verify(purchase).buyShare(5, 100);
        verify(sale).sellShare(5, 100);

        assertFalse(company.getPurchases().isEmpty());
        assertTrue(company.getSales().isEmpty());

        ArgumentCaptor<String> logCaptor = ArgumentCaptor.forClass(String.class);
        verifyStatic(Logger.class);
        Logger.log(logCaptor.capture());
        assertEquals("Transaction was completed. Transferred 5 shares of TestCompany for 100 each.", logCaptor.getValue());
    }

    @Test
    public void testCleanupAwaitingOrders() {
        Purchase purchase = mock(Purchase.class, withSettings().extraInterfaces(BinaryOrder.class));
        Sale sale = mock(Sale.class, withSettings().extraInterfaces(BinaryOrder.class));

        company.addOrder(purchase);
        company.addOrder(sale);

        company.realizeTransactions();

        assertTrue(company.getPurchases().isEmpty());
        assertTrue(company.getSales().isEmpty());
    }

    @Test
    public void testCancelBinaryOrder() {
        BinaryOrder purchase = mock(BinaryOrder.class);
        BinaryOrder sale = mock(BinaryOrder.class);

        company.addOrder(purchase);
        company.addOrder(sale);

        company.realizeTransactions();

        ArgumentCaptor<String> logCaptor = ArgumentCaptor.forClass(String.class);
        verifyStatic(Logger.class);
        Logger.log(logCaptor.capture());
        assertTrue(logCaptor.getValue().contains("Could not realize binary order: "));
    }

    @Test
    public void testDiscussPrice() {
        Purchase purchase = mock(Purchase.class);
        Sale sale = mock(Sale.class);

        when(purchase.getOrderNumber()).thenReturn(1);
        when(sale.getOrderNumber()).thenReturn(2);
        when(purchase.getPriceLimit()).thenReturn(150);
        when(sale.getPriceLimit()).thenReturn(100);

        int price = company.discussPrice(purchase, sale);
        assertEquals(150, price);
    }
}

