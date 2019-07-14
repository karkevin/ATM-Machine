package accounts;

public class CheckingAccount extends AssetAccount {

    public CheckingAccount(double balance, String id){
        super(balance, id);
    }

    /**
     * Check the transfer out is allowed.
     * @return true iff the balance is positive and the difference between balance and the amount
     * is greater than or equal to -100.
     */
    public boolean checkTransferOut(double amount){
        return super.checkTransferOut(amount) && balance > 0 && balance - amount >= -100;
    }

    /**
     * Suspends an account if the balance is less than -100
     */
    @Override
    public void checkAllowed(){
        if (balance < -100){
            setSuspension(true);
        } else {
            setSuspension(false);
        }
    }
}
