package tests;

import accounts.CreditCardAccount;
import transactions.AccountTransaction;
import accounts.CheckingAccount;
import transactions.Transaction;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class AccountTransactionTest {
    private AccountTransaction trans;
    private CheckingAccount acc1;
    private CreditCardAccount acc2;

    @Before
    public void setUp() throws Exception {
        acc1 = new CheckingAccount(100, "id");
        acc2 = new CreditCardAccount(0, "card");
        trans = new AccountTransaction(40, acc1, 1);
        trans.setToAccount(acc2);
    }

    @Test
    public void parseTest()
    {   boolean test = false;
        assertTrue(trans.parse());
        if (acc1.getBalance() == 60 && acc2.getBalance() == 40) {
            test = true;
        }
        assertTrue(test);
    }

    @Test
    public void undoTransactionTest() {
        boolean test = false;
        if(trans.parse()){
            trans.undo();
        }
        if (acc1.getBalance() == 100 && acc2.getBalance() == 0) {
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
            Transaction t_1 = acc1.getRecentTransaction();
            Transaction t_2 = acc2.getRecentTransaction();
            if (t_1 == null && t_2 == null){
                test = true;
            }
        }
        assertTrue(test);
    }
}
