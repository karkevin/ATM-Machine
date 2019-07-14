package accounts;


public class MinimumCheckingAccount extends CheckingAccount{

    private double monthlyFee;

    // Allowed number of transactions per month.
    private int monthlyTransactions;

    // The current number of transactions.
    private int currentTransactions;

    public MinimumCheckingAccount(double balance, String id) {
        super(balance, id);
        monthlyFee = 5.00;
        monthlyTransactions = 20;
        currentTransactions = 0;
    }

    /**
     * Reduces account balance by the monthly fee.
     */
    public void updateMonthlyFee() {
      balance -= monthlyFee;
      checkAllowed();
    }

    /**
     * Resets the number of allowed monthly transactions back down to 0 at the beginning of each month.
     */
    public void updateMonthlyTransactions() {
        this.currentTransactions = 0;
    }

    @Override
    public boolean checkTransferOut(double amount) {
        if (super.checkTransferOut(amount)){
            return increaseTransactions();
        }
        return false;
    }

    /**
     * Increases transaction count after each transaction, and returns true if there are still transactions left.
     */
    public boolean increaseTransactions() {
        if (currentTransactions < monthlyTransactions) {
            currentTransactions += 1;
            return true;
        }
        else {
            return false;
        }
    }
}
