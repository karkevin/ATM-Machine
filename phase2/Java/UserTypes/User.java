package UserTypes;

import accounts.Account;
import accounts.AccountFactory;
import accounts.CheckingAccount;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class User extends CredentialedEntity {

    // The primary checking account ID for this User.
    private String primaryCheckingID;

    // The accounts ArrayList of this User.
    private final ArrayList<Account> accounts = new ArrayList<>();

    // The accountFactory of this User.
    private AccountFactory af;

    public User(String login, String password) {
        super(login, password);
        af = new AccountFactory();
        Account primaryChecking = af.getAccount("CA", 0.0);
        addAccount(primaryChecking);
        primaryCheckingID = primaryChecking.getId();
    }


    public AccountFactory getAf(){
        return af;
    }

    public ArrayList<Account> getAccounts(){
        return accounts;
    }


    /**
     * This User's net total in all accounts.
     */
    public double netTotal() {
        double ret = 0.0;
        for (Account acc: accounts) {
            ret += acc.getBalance();
        }
        return ret;
    }

    /**
     * Add a new account to the accounts ArrayList.
     */
    public void addAccount(Account acc) {
        accounts.add(acc);
    }

    /**
     Returns an ArrayList of account ids.
     */
    public ArrayList<String> getIDs() {
        ArrayList<String> IDs = new ArrayList<>();
        for (Account acc : accounts) {
            IDs.add(acc.getId());
        }
        return IDs;
    }

    /**
     * Get this User's account by given account id.
     @return the account by given account id, or null if the account id is not in the accounts id ArrayList.
     */
    public Account getAccountFromId(String id) {
        for (int i = 0; i < accounts.size();i++)
        {
            if (accounts.get(i).getId().equals(id))
                return accounts.get(i);
        }
        return null;
    }

    /**
     * Send the request to BankManager to create a new account by given account type.
     */
    public void sendAccountRequest(String accType) throws IOException {
        // Open the file for writing and write to it.
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("requests.txt", true)))) {
            out.println("1 " + this.toString() + " " + accType);
        }
    }

    /**
     * Send the request to BankManager to create a new account by given account type and the username.
     */
    public void sendAccountRequest(String username, String accType) throws IOException {
        // Open the file for writing and write to it.
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("requests.txt", true)))) {
            out.println("3 " + this.toString() + " " + username + " " + accType);
        }
    }

    public CheckingAccount getPrimaryChecking() {
        return (CheckingAccount) getAccountFromId(primaryCheckingID);
    }

    /**
     * Creates a request to undo the numTransactions transactions of an account.
     * @param accId the account we want to undo the request of
     */
    public void undoRequest(String accId, int numTransactions) throws IOException{
        // Open the file for writing and write to it.
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("requests.txt", true)))) {
            out.println("2 " + this.toString() + " " + accId + " " + numTransactions);
        }
    }

    /**
     * Checks if an account is suspended
     * @return suspended or not
     */
    public boolean hasSuspended(){
        for (Account acc: accounts) {
            if (acc.isSuspended()){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Account> getSuspended() {
        ArrayList<Account> sus = new ArrayList<>();
        for (Account acc : accounts) {
            if (acc.isSuspended()) {
                sus.add(acc);
            }
        }
        return sus;
    }
}
