package tests;

import accounts.Account;
import accounts.AccountFactory;
import atm.CashMachine;
import org.junit.Before;
import org.junit.Test;
import transactions.CashTransaction;
import transactions.TransactionFactory;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CashMachineTest {

    private CashMachine cm;
    private TransactionFactory tf;
    private AccountFactory af;

    @Before
    public void setUp()
    {
        cm = new CashMachine(); //fives: 2000, tens: 1000, twenties: 5000, fifties: 2000
        tf = new TransactionFactory();
        af = new AccountFactory();
    }

    @Test
    public void addCashTest()
    {
        cm.addCash(20, 15);
        assertTrue(cm.getTypeOfCash(20) == 115);
    }

    @Test
    public void withdrawTest()
    {
        Account acc = af.getAccount("CA", 100.0);
        CashTransaction tran = (CashTransaction) tf.getTransaction("CASH", -45, acc);
        try
        {
            cm.withdraw(tran);
            assertTrue(acc.getBalance() == 55.0);
            assertTrue(cm.getTypeOfCash(50) == 100);
            assertTrue(cm.getTypeOfCash(20) == 98);
            assertTrue(cm.getTypeOfCash(10) == 100);
            assertTrue(cm.getTypeOfCash(5) == 99);
        }
        catch (IOException e)
        {
            assertFalse(true);
        }
    }

    @Test
    public void failWithdrawTest()
    {
        Account acc = af.getAccount("CA", 100.0);
        cm.addCash(5, -100);
        cm.addCash(10, -100);
        cm.addCash(20, -100);
        cm.addCash(50, -100);
        CashTransaction tran = (CashTransaction) tf.getTransaction("CASH", -45, acc);
        try
        {
            assertFalse(cm.withdraw(tran));
            assertTrue(acc.getBalance() == 100);
        }
        catch (IOException e)
        {
            assertFalse(true);
        }
    }

    @Test
    public void limitedWithdrawTest()
    {
        Account acc = af.getAccount("CA", 100.0);
        cm.addCash(10, -100);
        cm.addCash(20, -98);
        cm.addCash(50, -99);
        CashTransaction tran = (CashTransaction) tf.getTransaction("CASH", -105, acc);
        try
        {
            assertTrue(cm.withdraw(tran));
            assertTrue(acc.getBalance() == -5);
            assertTrue(cm.getTypeOfCash(50) == 0);
            assertTrue(cm.getTypeOfCash(20) == 0);
            assertTrue(cm.getTypeOfCash(10) == 0);
            assertTrue(cm.getTypeOfCash(5) == 97);

        }
        catch (IOException e)
        {
            assertFalse(true);
        }
    }

    @Test
    public void limitedFailWithdrawTest()
    {
        Account acc = af.getAccount("CA", 100.0);
        cm.addCash(5, -1996);
        cm.addCash(10, -1000);
        cm.addCash(20, -4999);
        cm.addCash(50, -1999);
        CashTransaction tran = (CashTransaction) tf.getTransaction("CASH", 45, acc);
        try
        {
            assertFalse(cm.withdraw(tran));
            assertTrue(acc.getBalance() == 100);

        }
        catch (IOException e)
        {
            assertFalse(true);
        }
    }
}
