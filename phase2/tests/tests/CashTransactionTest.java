package tests;

import transactions.CashTransaction;
import accounts.CheckingAccount;
import org.junit.Before;
import org.junit.Test;
import transactions.Transaction;


import static org.junit.Assert.assertTrue;

public class CashTransactionTest {

    private CashTransaction trans1;
    private CashTransaction trans2;
    private CheckingAccount acc1;

    @Before
    public void setUp() throws Exception {
        acc1 = new CheckingAccount(100, "id");
        trans1 = new CashTransaction(40, acc1, 1);
        trans2 = new CashTransaction(-40, acc1, 1);
    }

    @Test
    public void parseDepositTest() {
        boolean test = false;
        assertTrue(trans1.parse());
        if (acc1.getBalance() == 140){
            test = true;
        }
        assertTrue(test);
    }

    @Test
    public void parseWithdrawTest() {
        boolean test = false;
        assertTrue(trans2.parse());
        if (acc1.getBalance() == 60){
            test = true;
        }
        assertTrue(test);
    }

    @Test
    public void undoDepositTest() {
        boolean test = false;
        if(trans1.parse()){
            trans1.undo();
        }
        if(acc1.getBalance() == 100) {
            test = true;
        }
        assertTrue(test);
    }

    @Test
    public void undoWithdrawTest() {
        boolean test = false;
        if(trans2.parse()){
            trans2.undo();
        }
        if(acc1.getBalance() == 100) {
            test = true;
        }
        assertTrue(test);
    }

    @Test
    public void getMostRecentDepositTest(){
        boolean test = false;
        if (trans1.parse()) {
            Transaction t = acc1.getRecentTransaction();
            if (trans1.equals(t)){
                test = true;
            }
        }
        assertTrue(test);
    }

    @Test
    public void getMostRecentWithdrawalTest(){
        boolean test = false;
        if (trans2.parse()) {
            Transaction t = acc1.getRecentTransaction();
            if (trans2.equals(t)){
                test = true;
            }
        }
        assertTrue(test);
    }

    @Test
    public void getMostRecentDepositsAfterUndoTest(){
        boolean test = false;
        if (trans1.parse()) {
            trans1.undo();
            Transaction t = acc1.getRecentTransaction();
            if (t == null){
                test = true;
            }
        }
        assertTrue(test);
    }

    @Test
    public void getMostRecentWithdrawalAfterUndoTest(){
        boolean test = false;
        if (trans2.parse()) {
            trans2.undo();
            Transaction t = acc1.getRecentTransaction();
            if (t == null){
                test = true;
            }
        }
        assertTrue(test);
    }


}
