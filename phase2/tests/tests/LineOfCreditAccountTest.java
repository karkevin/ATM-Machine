package tests;

import accounts.LineOfCreditAccount;
import org.junit.Before;
import org.junit.Test;
import transactions.BillTransaction;
import transactions.CashTransaction;
import transactions.Transaction;

import static org.junit.Assert.*;

public class LineOfCreditAccountTest {
    private LineOfCreditAccount acc;

    @Before
    public void setUp() throws Exception {
        acc = new LineOfCreditAccount(500, "id");
    }

    @Test
    public void balanceTest() {
        assertTrue(acc.viewBalance() < 0);
        if (acc.checkTransferOut(1000)){
            acc.transferOut(1000);
            assertTrue(acc.viewBalance()> 0);
        }
    }

    @Test
    public void transferTest(){
        boolean test = true;
        assertTrue(acc.checkTransferOut(500));
        acc.transferOut(500);
        assertFalse(acc.checkTransferOut(1001));
        acc.transferIn(500);
        if (acc.viewBalance() != -500) {
            test = false;
        }
        assertTrue(test);
    }

    @Test
    public void maximumDebtTest() {
        LineOfCreditAccount cca1 = new LineOfCreditAccount(-1001, "bad");
        LineOfCreditAccount cca2 = new LineOfCreditAccount(-1000, "good");
        cca1.checkAllowed();
        cca2.checkAllowed();
        assertTrue(cca1.isSuspended());
        assertFalse(cca2.isSuspended());
    }

    @Test
    public void getRecentTransactionTest(){
        boolean test = false;
        if (acc.getRecentTransaction() == null)
            test = true;
        assertTrue(test);

        test = false;
        BillTransaction tran = new BillTransaction(10, acc, 1);
        tran.parse();
        if (acc.getRecentTransaction() == tran)
            test = true;
        assertTrue(test);
    }

    @Test
    public void getMostRecentTransactionAfterUndoTest(){
        boolean test = false;
        Transaction trans = new CashTransaction(40, acc, 0);
        if (trans.parse()) {
            trans.undo();
            Transaction t = acc.getRecentTransaction();
            if (t == null){
                test = true;
            }
        }
        assertTrue(test);
    }


    @Test
    public void undoTransactionsTest(){
        boolean test = false;
        CashTransaction tran1 = new CashTransaction(50, acc, 0); // 1,2,4 won't be undone
        BillTransaction tran2 = new BillTransaction(100, acc, 1);
        CashTransaction tran3 = new CashTransaction(200, acc, 0); //3,5 will be undone
        BillTransaction tran4 = new BillTransaction(500, acc, 1);
        CashTransaction tran5 = new CashTransaction(-50, acc, 0);
        tran1.parse();
        tran2.parse();
        tran3.parse();
        tran4.parse();
        tran5.parse();
        acc.undoTransactions(3);
        if (acc.getBalance() == -50)
            test = true;
        assertTrue(test);
    }

    @Test
    public void suspensionTest(){
        boolean test = false;
        CashTransaction tran1 = new CashTransaction(50, acc, 0);
        BillTransaction tran2 = new BillTransaction(1525, acc, 1);
        tran1.parse();
        tran2.parse();
        acc.undoTransactions(2);
        if (acc.isSuspended())
            test = true;
        assertTrue(test);
        test = false;
        tran1.parse();
        if (!acc.isSuspended())
            test = true;
        assertTrue(test);
    }
}
