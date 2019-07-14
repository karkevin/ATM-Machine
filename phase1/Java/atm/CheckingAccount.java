package atm;

public class CheckingAccount extends AssetAccount{
    public CheckingAccount(double balance){
        super(balance);
    }

    protected boolean transferOut(double amount){
        if(balance >0 && balance - amount >= -100){
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    protected boolean checkUndo(AccountTransaction accTransaction, double recentAmount) {
        if (this == accTransaction.getFrom()) { return true; }
        else {
            return balance > 0 && balance - recentAmount >= -100;
        }
    }

}
