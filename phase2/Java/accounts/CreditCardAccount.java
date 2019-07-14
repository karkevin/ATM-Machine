package accounts;

public class CreditCardAccount extends DebtAccount {

    public CreditCardAccount(double balance, String id){
        super(balance, id);
    }

    /**
     * Make sure the Transfer out from the CreditCardAccount is not available.
     * @return false since Transfer out from the CreditCardAccount is not available.
     */
    @Override
    public boolean checkTransferOut(double amount) {return false;}
}
