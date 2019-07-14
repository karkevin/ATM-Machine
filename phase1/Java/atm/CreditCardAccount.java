package atm;
public class CreditCardAccount extends DebtAccount{

    public CreditCardAccount(double balance){
        super(balance);
    }

    public boolean transferOut(double amount) {
        return false;
    }
}
