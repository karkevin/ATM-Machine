package atm;
public abstract class AssetAccount extends Account{
    public AssetAccount(double balance){
        super(balance);
    }
    @Override
    public boolean parseTransaction(Transaction transaction){
        if(!super.parseTransaction(transaction)) {
            if (transaction.getType().equals("CASH") && transaction.getFrom() == this) {
                if (transaction.getAmount() < 0) {
                    return this.transferOut(Math.abs(transaction.getAmount()));
                } else {
                    balance += transaction.getAmount();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void resetRecentTransaction(){
        super.resetRecentTransaction();
        if(this.recentTransaction != null && this.recentTransaction.getType().equals("CASH")){
            if(this.recentTransaction.getAmount() < 0){
               balance += this.getRecentTransaction().getAmount();
            } else {
                transferOut(this.recentTransaction.getAmount());
            }
        }
    }


}
