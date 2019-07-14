package transactions;

import accounts.Account;

import java.io.Serializable;

public class TransactionFactory implements Serializable {

    // The idCounter for this TransactionFactory.
    private long idCounter;

    /**
     * Returns the Transaction.
     * @param transactionType the type of the Transaction.
     * @param amount the amount of the Transaction.
     * @param fromAccount the account created the Transaction.
     * @return the transaction with given transactionType, otherwise null.
     */
    public Transaction getTransaction(String transactionType, double amount, Account fromAccount) {
        if (transactionType == null) {
            return null;
        }
        if(transactionType.equalsIgnoreCase("BILL")) {
            idCounter++;
            return new BillTransaction(amount, fromAccount, idCounter);
        }
        else if(transactionType.equalsIgnoreCase("CASH")) {
            idCounter++;
            return new CashTransaction(amount, fromAccount, idCounter);
        }
        else if(transactionType.equalsIgnoreCase("ACCOUNT")) {
            idCounter++;
            return new AccountTransaction(amount, fromAccount, idCounter);
        }
        return null;
    }
}
