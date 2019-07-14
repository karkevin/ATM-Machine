package transactions;

import accounts.Account;

public class AccountTransaction extends Transaction {

    // The destination account of this AccountTransaction.
    private Account toAccount;

    public AccountTransaction(double amount, Account fromAccount, long id) {
        super(amount, fromAccount, id);
    }

    public void setToAccount(Account to){
        this.toAccount = to;
    }

    public Account getTo(){
        return this.toAccount;
    }

    /**
     * Returns a string representation of the object, including
     * date, the account created the transaction, the destination account and the amount of this AccountTransaction..
     * @return the string representation of the object.
     */
    public String toString() {
        return "Date: " + getDate() + "\n From: " + fromAccount.toString() + "\n To: " + toAccount.toString()
                + "\n Amount: $" + amount;
    }

    public String getType() {
        return "ACCOUNT";
    }

    /**
     * Do the transaction, as long as the amount is not over the balance of fromAccount and return true,
     * otherwise, return false.
     * @return true iff the transaction is success.
     */
    public boolean parse(){
        if(fromAccount.checkTransferOut(amount)){
            fromAccount.transferOut(amount);
            addObserver(fromAccount);
            toAccount.transferIn(amount);
            addObserver(toAccount);
            setChanged();
            notifyObservers("add");
            parsed = true;
            return true;
        }
        return false;

    }

    /**
     * undo this AccountTransaction, as long as this AccountTransaction is successfully parsed.
     */
    public void undo(){
        if(parsed) {
            toAccount.transferOut(amount);
            fromAccount.transferIn(amount);
            setChanged();
            notifyObservers("undo");
        }
    }
}
