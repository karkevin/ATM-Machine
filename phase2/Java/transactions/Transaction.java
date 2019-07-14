package transactions;

import accounts.Account;

import java.io.*;
import java.util.Calendar;
import java.util.Observable;

public abstract class Transaction extends Observable implements Serializable {

    // The amount of this Transaction.
    protected double amount;

    // The date of this Transaction.
    private Calendar date;

    // The account created this Transaction.
    Account fromAccount;

    // The id of this Transaction.
    private long id;

    // The label that shows whether the transaction is success or not.
    boolean parsed;

    public Transaction(double amount, Account fromAccount, long id) {
        this.amount = amount;
        this.date = Calendar.getInstance();
        this.fromAccount = fromAccount;
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public abstract String getType();

    public double getAmount() {return amount;}

    /**
     * Undoes this particular transaction
     */
    public abstract void undo();

    /**
     * Returns a string representation of this Transaction.
     * @return the string representation of the Type of this Transaction.
     */
    public abstract String toString();

    /**
     * Returns a string representation of the Date.
     * @return the string representation of the Date of the Transaction.
     */
    public String getDate(){
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        return month + "/" + day + "/" + year;
    }

    public Account getFrom() {
        return this.fromAccount;
    }

    /**
     * Parses a transaction
     * @return whether or not the parse was successful
     */
    public abstract boolean parse();



    /**
     * Compare two ids of the Transaction, and return true as long as they have the same id.
     * otherwise, return false.
     * @return true iff two Transactions have the same id.
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Transaction){
            return ((Transaction)o).getId() == id;
        }
        return false;
    }
}
