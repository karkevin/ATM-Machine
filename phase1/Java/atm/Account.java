package atm;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public abstract class Account implements java.io.Serializable{
    private Calendar dateCreated;
    protected double balance;
    protected Transaction recentTransaction;


    /**
     * @param balance initial balance of the account.
     */
    public Account(double balance) {
        this.dateCreated = Calendar.getInstance();
        this.balance = balance;
        this.recentTransaction = null;
    }

    public Calendar getDateCreated(){ return dateCreated; }
    public double getBalance(){ return balance; }
    public Transaction getRecentTransaction() { return this.recentTransaction; }


    /**
     * Transfers in money from another account
     * @param transaction the transaction data
     */
    public void transferIn(Transaction transaction) {
        this.balance += transaction.getAmount();
        this.recentTransaction = transaction;

    }
    /**
     * Takes a transaction from the main class and applies the transaction to this account.
     * @param transaction created transaction to parse and get information from
     * @return whether or not transaction successfully parsed or not, false for violating the requirements of a class.
     */
    public boolean parseTransaction(Transaction transaction){
        this.recentTransaction = transaction;
        if(transaction.getType().equals("BILL") && transaction.getFrom() == this) {
            try {
                writeToOutgoing(transaction);
            }
            catch (IOException e)
            {
                System.out.println("Write to outgoing.txt failed.");
            }
            return this.transferOut(transaction.getAmount());
        } else if(transaction.getType().equals("ACCOUNT")){
            AccountTransaction accTransaction = (AccountTransaction)transaction;
            if(accTransaction.getFrom() == this){
                return this.transferOut(accTransaction.getAmount());
            } else if(accTransaction.getTo() == this){
                balance += accTransaction.getAmount();
                return true;
            }
        }
        //invalid type
        return false;
    }

    /**
     * Transfers out money to another account
     * @param amount amount of money in transfer
     * @return whether or not the transfer out was successful
     */
    protected abstract boolean transferOut(double amount);

    /**
     * Function for the bank manager to reset the last transaction
     */
    public void resetRecentTransaction() {
        if(this.recentTransaction !=  null && this.recentTransaction instanceof AccountTransaction) {
            AccountTransaction accTransaction = (AccountTransaction) this.recentTransaction;
            if(this == accTransaction.getFrom() || this == accTransaction.getTo()) {
                Account otherAccount;
                double recentAmount = accTransaction.getAmount();
                if (this == accTransaction.getFrom()) {
                    otherAccount = accTransaction.getTo();
                } else {
                    otherAccount = accTransaction.getFrom();
                }

                if (otherAccount.checkUndo(accTransaction, recentAmount) &&
                        this.checkUndo(accTransaction, recentAmount)) {
                    this.updateUndo(accTransaction, recentAmount);
                    otherAccount.updateUndo(accTransaction, recentAmount);
                }
            }
        }
    }

    /**
     * Checks whether or not we can undo the last account transaction.
     * @param accTransaction the transaction in question
     * @param recentAmount the amount we need to transfer
     * @return true by default
     */
    protected boolean checkUndo(AccountTransaction accTransaction, double recentAmount) {
        return true;
    }

    /**
     * Undoes the transaction. Assumes that current account is one of the accounts in the accTransaction
     * @param accTransaction transaction to undo for checking its correct
     * @param recentAmount amount of money to remove
     */
    private void updateUndo(AccountTransaction accTransaction, double recentAmount) {
        if (this == accTransaction.getFrom()) { balance += recentAmount; }
        else {
            this.transferOut(recentAmount);
        }
        this.recentTransaction = null;
    }

    private void writeToOutgoing(Transaction transaction) throws IOException
    {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Outgoing")))) {
            out.println(transaction.getAmount() + "," + transaction.getFrom());
        }
    }
}
