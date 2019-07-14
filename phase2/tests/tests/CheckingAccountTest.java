package tests;


import accounts.AccountFactory;
import accounts.CheckingAccount;
import accounts.Account;
import UserTypes.User;
import org.junit.Before;
import org.junit.Test;
import transactions.BillTransaction;
import transactions.CashTransaction;
import transactions.Transaction;

import static org.junit.Assert.assertTrue;

public class CheckingAccountTest {
    private CheckingAccount acc;
    private Account t;
    private User u;

    @Before
    public void setUp() throws Exception {
        acc = new CheckingAccount(500, "id");
        u = new User("User_c","User_c");
    }

    @Test
    public void depositDestinationTest() {
        boolean test = false;
        CheckingAccount ca = u.getPrimaryChecking();
        ca.transferIn(500);
        if (ca.getBalance() == 500){
            test = true;
        }
        assertTrue(test);

    }

    @Test
    public void withdrawalTest(){
        boolean test = false;
        if(acc.checkTransferOut(500)) {
            acc.transferOut(500);
        }
        if(acc.checkTransferOut(100)) {
            acc.transferOut(100);
        }
        if(acc.checkTransferOut(500)) {
            acc.transferOut(500);
        }
        if (acc.getBalance() == 0) {
            test = true;
        }
        assertTrue(test);
    }

    @Test
    public void withdrawalLimitTest(){
        boolean test = false;
        if(acc.checkTransferOut(499)) {
            acc.transferOut(499);
        }
        if(acc.checkTransferOut(100)) {
            acc.transferOut(100);
        }
        if(acc.checkTransferOut(500)) {
            acc.transferOut(500);
        }
        if (acc.getBalance() == -99) {
            test = true;
        }
        assertTrue(test);
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
        BillTransaction tran2 = new BillTransaction(625, acc, 1);
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
