package tests;

import accounts.Account;
import accounts.AccountFactory;
import accounts.CheckingAccount;
import accounts.LineOfCreditAccount;
import UserTypes.BankManager;
import atm.CentralBank;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BankManagerTest {

    private BankManager bm;
    private CentralBank bank;

    @Before
    public void setUp() throws ClassNotFoundException, IOException
    {
        bm = new BankManager("bank", "manager");
        bank = new CentralBank();
        bm.setBank(bank);
    }

    @Test
    public void verifyLoginTest()
    {
        assertFalse(bm.verifyLogin("wrong", "wrong"));
        assertFalse(bm.verifyLogin("bank", "wrong"));
        assertFalse(bm.verifyLogin("wrong", "manager"));
        assertTrue(bm.verifyLogin("bank", "manager"));
    }

    @Test
    public void changePasswordTest()
    {
        bm.changePassword("teller");
        assertFalse(bm.verifyLogin("bank", "manager"));
        assertTrue(bm.verifyLogin("bank", "teller"));
    }

    @Test
    public void createUserTest()
    {
        String login = "test";
        String password = "pass";
        bm.createUser(login, password);
        assertTrue(bank.getUsers().get(login).verifyLogin(login, password));
    }

    @Test
    public void createAccountTest()
    {
        bm.createUser("name", "pass");
        bm.createAccount("name", "CA");
        assertTrue(bank.getUsers().get("name").getIDs().contains("CA2"));
        bm.createAccount("name", "LINE");
        assertTrue(bank.getUsers().get("name").getIDs().contains("LINE3"));
    }



}
