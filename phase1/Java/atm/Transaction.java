package atm;
import java.util.Calendar;

public abstract class Transaction implements java.io.Serializable{
    protected double amount;
    protected Calendar date;
    protected Account fromAccount;

    public Transaction(double amount, Account fromAccount) {
        this.amount = amount;
        this.date = Calendar.getInstance();
        this.fromAccount = fromAccount;
    }

    public abstract String getType();

    public Calendar time() {return date;}
    public double getAmount() {return amount;}
    public abstract String toString();

    public Account getFrom() {
        return this.fromAccount;
    }
}
