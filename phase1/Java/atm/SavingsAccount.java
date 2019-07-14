package atm;

public class SavingsAccount extends AssetAccount{
    private double interestRate;
    public SavingsAccount(double balance){
        super(balance);
        interestRate = 0.1;//default for now
    }

    protected boolean transferOut(double amount){
        if(amount <= balance){
            balance -= amount;
        }
        return true;
    }

    /**
     * Update function called on the beginning of a month to calculate the new balance after applying interest
     */
    public void updateInterest(){
        balance += balance * interestRate * 0.01;
    }

    @Override
    protected boolean checkUndo(AccountTransaction accTransaction, double recentAmount) {
        if (this == accTransaction.getFrom()) { return true; }
        else {
            return recentAmount <= balance;
        }
    }
}
