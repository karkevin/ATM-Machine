package tests;

import accounts.CreditCardAccount;
import org.junit.Before;
import org.junit.Test;
import transactions.CashTransaction;

import static org.junit.Assert.assertTrue;

public class CreditCardAccountTest {
    private CreditCardAccount acc;

    @Before
    public void setUp() throws Exception {
        acc = new CreditCardAccount(500, "id");
    }

    @Test
    public void balanceTest() {
        assertTrue(acc.viewBalance() < 0);
        if (acc.checkTransferOut(1000)) {
            acc.transferOut(1000);
            assertTrue(acc.viewBalance() > 0);
        }
    }

    @Test
    public void transferTest() {
        boolean ti = false;
        boolean to = false;

        if (!acc.checkTransferOut(500)) {
            to = true;
        }
        acc.transferIn(500);
        if (acc.viewBalance() == -1000) {
            ti = true;
        }
        assertTrue(to);
        assertTrue(ti);
    }

    @Test
    public void maximumDebtTest() {
        CreditCardAccount cca = new CreditCardAccount(-1001, "bad");
        cca.checkAllowed();
        assertTrue(cca.isSuspended());
    }

    @Test
    public void getRecentTransactionTest(){
        boolean test = false;
        if (acc.getRecentTransaction() == null)
            test = true;
        assertTrue(test);

        test = false;
        CashTransaction tran = new CashTransaction(10, acc, 1);
        tran.parse();
        if (acc.getRecentTransaction() == tran)
            test = true;
        assertTrue(test);
    }

    @Test
    public void undoTransactionsTest(){
        boolean test = false;
        CashTransaction tran1 = new CashTransaction(50, acc, 0);
        CashTransaction tran2 = new CashTransaction(200, acc, 0);
        CashTransaction tran3 = new CashTransaction(-50, acc, 0);
        tran1.parse();
        tran2.parse();
        tran3.parse();
        acc.undoTransactions(1);
        if (acc.getBalance() == 550)
            test = true;
        assertTrue(test);
    }
}