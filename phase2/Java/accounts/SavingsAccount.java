package accounts;

public class SavingsAccount extends AssetAccount {

    // The interestRate of this SavingsAccount.
    private double interestRate;

    public SavingsAccount(double balance, String id){
        super(balance, id);
        interestRate = 0.1;//default for now
    }

    /**
     * Checks whether it is allowed to transfer out a certain amount of money.
     * @param amount the amount of money being transferred out
     * @return whether or not a transfer out is allowed.
     */
    public boolean checkTransferOut(double amount){ return super.checkTransferOut(amount) && amount <= balance; }

    /**
     * Update function called on the beginning of a month to calculate the new balance after applying interest
     */
    public void updateInterest(){
        if (!isSuspended()){
            balance += balance * interestRate * 0.01;
        }
    }

    /**
     * Suspends an account if the balance is less than zero.
     */
    @Override
    public void checkAllowed(){
        if (balance < 0){
            setSuspension(true);
        } else {
            setSuspension(false);
        }
    }
}
