package accounts;

public abstract class DebtAccount extends Account {

    // the max negative amount of this debt account.
    private final int MAX_AMOUNT = -1000;

    DebtAccount(double balance, String id){
        super(balance, id);
    }

    @Override
    public boolean checkTransferOut(double amount){
        return balance - amount >= MAX_AMOUNT;
    }

    /**
     * Returns the balance of a debt account with the right sign for display purposes
     * @return the balance of the account multiplied by -1
     */
    @Override
    public double viewBalance(){
        return -1 * balance;
    }

    /**
     * Suspends an account if the maximum amount of debt is exceeded.
     */
    @Override
    public void checkAllowed(){
        if (balance < MAX_AMOUNT){
            setSuspension(true);
        } else {
            setSuspension(false);
        }
    }
}