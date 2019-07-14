package atm;

public class LineOfCreditAccount extends DebtAccount{

    public LineOfCreditAccount(double balance){
        super(balance);
    }

    protected boolean transferOut(double amount) {
        balance -= amount;
        return true;
    }
}
