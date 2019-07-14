package atm;

public class CashTransaction extends Transaction {

    // Representation Invariant:
    //  if amount > 0, the transaction is a deposit
    // if amount < 0, the transaction is a withdrawal

    public CashTransaction(double amount, Account fromAccount) {
        super(amount, fromAccount);
    }

    public String toString() {
        return "Date: " + super.date.toString() + "\n From: " + fromAccount.toString() +
                "\n Amount: " + amount;
    }
    public String getType() {
        return "CASH";
    }
}
