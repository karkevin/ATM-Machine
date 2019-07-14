package transactions;

import accounts.Account;
import accounts.AssetAccount;

import java.util.ArrayList;

public class CashTransaction extends Transaction {

    // Representation Invariant:
    //  if amount > 0, the transaction is a deposit
    // if amount < 0, the transaction is a withdrawal

    public CashTransaction(double amount, Account fromAccount, long id) {
        super(amount, fromAccount, id);
    }

    /**
     * Returns a string representation of the object, including
     * date, the account created the transaction and the amount of this CashTransaction..
     * @return the string representation of the object.
     */
    public String toString() {
        return "Date: " + getDate()+ "\n From: " + fromAccount.toString() +
                "\n Amount: $" + amount;
    }

    public String getType() {
        return "CASH";
    }

    /**
     * Do the deposits, as long as the amount is not negative and return true.
     * Do the withdrawal, as long as the amount is not over the balance of fromAccount and return true
     * otherwise, return false.
     * @return true iff the transaction is success.
     */
    public boolean parse() {
        if (amount >= 0) {
            fromAccount.transferIn(amount);
            addObserver(fromAccount);
            setChanged();
            notifyObservers("add");
            parsed = true;
            return true;
        } else {
            if (fromAccount instanceof AssetAccount) {
                if (fromAccount.checkTransferOut(Math.abs(amount))) {
                    fromAccount.transferOut(Math.abs(amount));
                    addObserver(fromAccount);
                    setChanged();
                    notifyObservers("add");
                    parsed = true;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * undo this CashTransaction, as long as this CashTransaction is successfully parsed.
     */
    public void undo(){
        if(parsed) {
            if (amount >= 0) {
                fromAccount.transferOut(Math.abs(amount));
            } else {
                //From above, assuming that the account is an AssetAccount as we can only add it if it is an asset account
                fromAccount.transferIn(Math.abs(amount));
            }
            setChanged();
            notifyObservers("undo");
        }
    }

}
