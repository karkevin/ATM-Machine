package accounts;

import transactions.BillTransaction;
import transactions.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;


public abstract class Account implements java.io.Serializable, Observer {

    private Calendar dateCreated;
    protected double balance;
    protected ArrayList<Transaction> transactionHistory;
    private boolean suspended;
    protected ArrayList<Transaction> undoneTransactions;
    protected String id;

    public Account(double balance, String id) {
        this.dateCreated = Calendar.getInstance();
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
        this.undoneTransactions = new ArrayList<>();
        this.suspended = false;
        this.id = id;
    }

    /**
     * @return String representation of date created.
     */
    public String getDateCreated(){
        int year = dateCreated.get(Calendar.YEAR);
        int month = dateCreated.get(Calendar.MONTH) + 1;
        int day = dateCreated.get(Calendar.DAY_OF_MONTH);
        return month + "/" + day + "/" + year;
    }

    public String getId(){return id;}

    public String toString() {return id;}

    public double getBalance(){ return balance; }

    public Transaction getRecentTransaction() {
        if (this.transactionHistory.size() > 0)
            return this.transactionHistory.get(0);
        return null;
    }

    public Transaction getUndoneTransaction(){
        if(this.undoneTransactions.size() >0)
            return undoneTransactions.get(undoneTransactions.size() - 1);
        return null;
    }

    public void transferOut(double amount){
        balance -= amount;
    }

    /**
     * A method to check if we can take out this much money
     * @return Whether or not we can take out the money
     */
    public boolean checkTransferOut(double amount){
        return !isSuspended();
    }

    public void transferIn(double amount) {
        balance += amount;
        checkAllowed();
    }

    /**
     * Resets n transactions
     * @param n number of transactions to undo
     */
    public void undoTransactions(int n){
        int d = 0;
        int undoN = Math.min(n, transactionHistory.size());
        for(int i = 0; i < undoN; i++){
            //transactionHistory.get(d).undo();
            if (d < transactionHistory.size())
            {
                Transaction t = transactionHistory.get(d);
                if (t instanceof BillTransaction)
                    d++;
                else
                    t.undo();
            }
            else
            {
                break;
            }
        }

    }

    public boolean isSuspended(){
        return suspended;
    }

    public void setSuspension(boolean sus){
        suspended = sus;
    }

    /**
     * Checks if the conditions for suspension is valid, and then suspends the account if necessary
     */
    public abstract void checkAllowed();

    public double viewBalance(){
        return balance;
    }

    /**
     *  Update observer, assume only called when undo transaction
     * @param o Observer object (in this case the transaction
     * @param arg argument
     */
    public void update(Observable o, Object arg){
        String argument = (String)arg;
        Transaction t = (Transaction)o;
        if(argument.equalsIgnoreCase("UNDO")) {
            transactionHistory.remove(t);
            undoneTransactions.add(t);
            checkAllowed();
        } else if(argument.equalsIgnoreCase("add")){
            transactionHistory.add(0, t);
        }
    }

}
