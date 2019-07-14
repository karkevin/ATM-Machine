package transactions;

import accounts.Account;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class BillTransaction extends Transaction {

    public BillTransaction(double amount, Account fromAccount, long id) {
        super(amount, fromAccount, id);
    }

    /**
     * Returns a string representation of the object, including
     * date, the account created the transaction and the amount of this BillTransaction..
     * @return the string representation of the object.
     */
    public String toString() {
        return "Date: " + getDate() + "\n From: " + fromAccount.toString() +
                 "\n Amount: $" + amount;
    }

    public String getType() {
        return "BILL";
    }

    private void writeToOutgoing(Transaction transaction) throws IOException
    {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("outgoing.txt",true)))) {
            out.println(transaction.getAmount() + "," + transaction.getFrom());
        }
    }
    /**
     * Do the transaction, as long as the amount is not over the balance of fromAccount, return true and
     * write to outgoing.txt.
     * otherwise, return false and show message said "Write to outgoing.txt failed."
     * @return true iff the transaction is success.
     */
    @Override
    public boolean parse() {
        if (fromAccount.checkTransferOut(amount)) {
            try {
                writeToOutgoing(this);
                fromAccount.transferOut(amount);
                addObserver(fromAccount);
                setChanged();
                notifyObservers("add");
                parsed = true;
                return true;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Write to outgoing.txt failed.");
                return false;
            }
        }
        return false;
    }

    /**
     * undo this BillTransaction, which is not available for the BillTransaction.
     */
    public void undo(){}
}
