package tests;

import transactions.BillTransaction;
import accounts.CheckingAccount;
import org.junit.Before;
import org.junit.Test;
import transactions.Transaction;

import static org.junit.Assert.assertTrue;

public class BillTransactionTest {
    private BillTransaction trans;
    private CheckingAccount acc1;

    @Before
    public void setUp() throws Exception {
        acc1 = new CheckingAccount(100, "id");
        trans = new BillTransaction(40, acc1, 1);
    }

    @Test
    public void parseTest() {
        boolean test = false;
        assertTrue(trans.parse());
        if (acc1.getBalance() == 60) {
            test = true;
        }
        assertTrue(test);
    }

    @Test
    public void undoTransactionTest() {
        boolean test = false;
        if (trans.parse()) {
            trans.undo();
        }
        if (acc1.getBalance() == 60) {
            test = true;
        }
        assertTrue(test);
    }

    @Test
    public void getMostRecentTransactionTest(){
        boolean test = false;
        if (trans.parse()) {
            Transaction t = acc1.getRecentTransaction();
            if (trans.equals(t)){
                test = true;
            }
        }
        assertTrue(test);
    }

    @Test
    public void getMostRecentTransactionAfterUndoTest(){
        boolean test = false;
        if (trans.parse()) {
            trans.undo();
            Transaction t = acc1.getRecentTransaction();
            if (trans.equals(t)){
                test = true;
            }
        }
        assertTrue(test);
    }
}
