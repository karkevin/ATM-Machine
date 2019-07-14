package tests;

import accounts.CheckingAccount;
import org.junit.Before;
import org.junit.Test;
import transactions.*;

import static org.junit.Assert.assertTrue;

public class TransactionFactoryTest {

    private TransactionFactory tf;
    private CheckingAccount acc1;
    private CheckingAccount acc2;

    @Before
    public void setUp()
    {
        tf = new TransactionFactory();
        acc1 = new CheckingAccount(100.0, "id1");
        acc2 = new CheckingAccount(50.0, "id2");
    }

    @Test
    public void testGetTransaction()
    {
        Transaction tran1 = tf.getTransaction("ACCOUNT", 50, acc1);
        assertTrue(tran1 instanceof AccountTransaction);

        Transaction tran2 = tf.getTransaction("BILL", 50, acc1);
        assertTrue(tran2 instanceof BillTransaction);

        Transaction tran3 = tf.getTransaction("CASH", 50, acc1);
        assertTrue(tran3 instanceof CashTransaction);
    }
}
