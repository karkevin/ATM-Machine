package tests;


import accounts.AccountFactory;
import accounts.CheckingAccount;
import accounts.MinimumCheckingAccount;
import accounts.Account;
import UserTypes.User;
import org.junit.Before;
import org.junit.Test;
import transactions.BillTransaction;
import transactions.CashTransaction;
import transactions.Transaction;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MinimumCheckingAccountTest {
    private MinimumCheckingAccount acc;
    private Account t;
    private User u;

    @Before
    public void setUp() throws Exception {
        acc = new MinimumCheckingAccount(500, "id");
        u = new User("User_mc","User_mc");
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
    public void updateMonthlyFeeTest(){
        boolean test = false;
        acc.updateMonthlyFee();
        if (acc.getBalance() == 495) {
            test = true;
        }
        assertTrue(test);
    }

//    @Test
//    public void increaseTransactionTest(){
//        boolean test = false;
//        acc.increaseTransactions();
//        if (acc.getCurrentTransactions() == 1){
//            test = true;
//        }
//        assertTrue(test);
//    }

    @Test
    public void checkTransferOutTest(){
        boolean test1 = false;
        boolean test2 = false;
        boolean test3 = false;

        if (acc.checkTransferOut(500) && acc.checkTransferOut(500)){
            test1 = true;
        }
        if (acc.getBalance() >= 500 && acc.checkTransferOut(500)){
            test2 = true;
        }
        for (int i = 0; i < 20; i++){
            acc.increaseTransactions();
        }
        if (!acc.checkTransferOut(500) && acc.getBalance() >= 500){
            test3 = true;
        }
        assertTrue(test1);
        assertTrue(test2);
        assertTrue(test3);
    }

    @Test
    public void updateMonthlyTransactionsTest(){
        for (int i = 0; i < 20; i++){
            acc.increaseTransactions();
        }
        assertFalse(acc.increaseTransactions());
        acc.updateMonthlyTransactions();
        assertTrue(acc.increaseTransactions());
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