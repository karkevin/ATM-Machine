package atm;

public class BillTransaction extends Transaction {

    public BillTransaction(double amount, Account fromAccount) {
        super(amount, fromAccount);
    }

    public String toString() {
        return "Date: " + super.date.toString() + "\n From: " + fromAccount.toString() +
                 "\n Amount: " + amount;
    }
    public String getType() {
        return "BILL";
    }
}
