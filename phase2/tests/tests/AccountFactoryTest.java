package tests;

import accounts.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountFactoryTest {

    private AccountFactory af;

    @Before
    public void setUp()
    {
        af = new AccountFactory();
    }

    @Test
    public void testGetAccount() {
        Account acc1 = af.getAccount("CA", 0.0);
        assertTrue(acc1 instanceof CheckingAccount);
        assertTrue(acc1.getId().equals("CA1"));
        assertTrue(acc1.getBalance() == 0.0);

        Account acc2 = af.getAccount("CCA", -110.0);
        assertTrue(acc2 instanceof CreditCardAccount);
        assertTrue(acc2.getId().equals("CCA2"));
        assertTrue(acc2.getBalance() == -110.0);

        Account acc3 = af.getAccount("LINE", -5.5);
        assertTrue(acc3 instanceof LineOfCreditAccount);
        assertTrue(acc3.getId().equals("LINE3"));
        assertTrue(acc3.getBalance() == -5.5);

        Account acc4 = af.getAccount("MCA", 1.2);
        assertTrue(acc4 instanceof MinimumCheckingAccount);
        assertTrue(acc4.getId().equals("MCA4"));
        assertTrue(acc4.getBalance() == 1.2);

        Account acc5 = af.getAccount("SA", 4.94);
        assertTrue(acc5 instanceof SavingsAccount);
        assertTrue(acc5.getId().equals("SA5"));
        assertTrue(acc5.getBalance() == 4.94);
    }
}
