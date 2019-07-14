package tests;

import accounts.Account;
import accounts.AccountFactory;
import accounts.CheckingAccount;
import UserTypes.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class UserTest {

    private User user;
    private AccountFactory af;

    @Before
    public void setUp() {

        user = new User("mike", "ross999");
        af = new AccountFactory();

    }

    @Test
    public void verifyLoginTest()
    {
        assertFalse(user.verifyLogin("wrong", "wrong"));
        assertFalse(user.verifyLogin("mike", "wrong"));
        assertFalse(user.verifyLogin("wrong", "ross999"));
        assertTrue(user.verifyLogin("mike", "ross999"));
    }

    @Test
    public void changePasswordTest()
    {
        user.changePassword("harv");
        assertFalse(user.verifyLogin("mike", "ross999"));
        assertTrue(user.verifyLogin("mike", "harv"));
    }

    @Test
    public void netTotalTest()
    {
        assertTrue(user.netTotal() == 0.0);
        user.addAccount(af.getAccount("SA", 12));
        assertTrue(user.netTotal() == 12);
        user.addAccount(af.getAccount("CCA", -4));
        assertTrue(user.netTotal() == 8);
    }

    @Test
    public void getAccountFromIdTest()
    {
        Account acc2 = af.getAccount("SA", 0.0);
        user.addAccount(acc2);
        assertTrue(acc2 == user.getAccountFromId("SA1"));
        assertTrue(user.getAccountFromId("N") == null);
    }

    @Test
    public void addAccountTest()
    {
        Account acc = af.getAccount("LINE", 0.0);
        user.addAccount(acc);
        assertTrue(user.getAccounts().contains(acc));
    }

    @Test
    public void getPrimaryCheckingTest()
    {
        CheckingAccount acc = user.getPrimaryChecking();
        assertTrue(acc.getId().equals("CA1"));
    }
}
