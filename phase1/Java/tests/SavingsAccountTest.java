package tests;

import atm.SavingsAccount;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SavingsAccountTest {
    private SavingsAccount acc;

    @Before
    public void setUp() throws Exception {
        acc = new SavingsAccount(500);
    }

    @Test
    public void updateInterestTest() {
        acc.updateInterest();
        assertEquals(500 + 500 * 0.1 * 0.01, acc.getBalance(), 0.01);
    }
}