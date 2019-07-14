package atm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class User extends CredentialedEntity {
    private TransactionFactory tf;
    private CheckingAccount primaryChecking;
    private final HashMap<Long, Account> accounts = new HashMap<>();

    public User(String login, String password) {
        super(login, password);
        tf = new TransactionFactory();
        primaryChecking = new CheckingAccount(0);
    }

    public String getSummary()
    {
        String ret = "Account balances: \n";
        for (Map.Entry<Long, Account> e: accounts.entrySet())
            ret += "Account " + e.getKey() + ": " + e.getValue();
        return ret;
    }

    public double netTotal() {
        double ret = 0.0;
        for (Account acc: accounts.values()) {
            ret += acc.getBalance();
        }
        return ret;
    }

    public void addAccount(Account acc)
    {
        //accounts.add(acc);
        accounts.put(System.currentTimeMillis(), acc);
        if (acc instanceof CheckingAccount && primaryChecking == null)
            primaryChecking = (CheckingAccount) acc;
    }

    public HashMap<Long, Account> getAccounts() {
        return accounts;
    }

    public void sendRequest(String accType) throws IOException {
        // Open the file for writing and write to it.
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Request")))) {
            out.println(this.toString() + ", " + accType);
            System.out.println("Your request for a new account has been sent");
        }

    }
    public CheckingAccount getPrimaryChecking() {
        return primaryChecking;
    }

    public void transferMoney(double amount, Account fromAccount, Account toAccount) {
        Transaction transaction = tf.getTransaction("ACCOUNT", amount, fromAccount);
        ((AccountTransaction) transaction).setToAccount(toAccount);
        if(fromAccount.parseTransaction(transaction))
            toAccount.parseTransaction(transaction);
    }
    public void cashMoney(double amount, Account fromAccount) {
        Transaction transaction = tf.getTransaction("CASH", amount, fromAccount);
        try {
            CashMachine.withdraw((CashTransaction) transaction);
        }
        catch (IOException e)
        {
            System.out.println("Failed to withdraw due to IOException");
        }

    }
    public void payBill(double amount, Account fromAccount) {
        Transaction transaction = tf.getTransaction("BILL", amount, fromAccount);
        fromAccount.parseTransaction(transaction);
    }

    /**
     * Undoes the most recent request(if possible)
     * @param accId the account we want to undo the request of
     */
    public void undoRequest(long accId){
        accounts.get(accId).resetRecentTransaction();
    }


}
