package atm;

public class AccountTransaction extends Transaction {

    private Account toAccount;

    public AccountTransaction(double amount, Account fromAccount) {
        super(amount, fromAccount);
    }

    public void setToAccount(Account to){
        this.toAccount = to;
    }

    public Account getTo(){
        return this.toAccount;
    }

    public String toString() {
        return "Date: " + date.toString() + "\n From: " + fromAccount.toString() + "\n To: " + toAccount.toString()
                + "\n Amount: " + amount;
    }

    public String getType() {
        return "ACCOUNT";
    }
}
