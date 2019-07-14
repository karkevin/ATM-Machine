package atm;

import java.io.Serializable;

public class TransactionFactory implements Serializable {
    public Transaction getTransaction(String transactionType, double amount, Account fromAccount) {
        if (transactionType == null) {
            return null;
        }
        if(transactionType.equalsIgnoreCase("BILL")) {
            return new BillTransaction(amount, fromAccount);
        }
        else if(transactionType.equalsIgnoreCase("CASH")) {
            return new CashTransaction(amount, fromAccount);
        }
        else if(transactionType.equalsIgnoreCase("ACCOUNT")) {
            return new AccountTransaction(amount, fromAccount);
        }
        return null;
    }
}
