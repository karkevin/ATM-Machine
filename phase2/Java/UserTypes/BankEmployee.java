package UserTypes;

import atm.CentralBank;

public class BankEmployee extends User implements Administrator {

    // This BankEmployee's CentralBank.
    public CentralBank bank;

    public BankEmployee(String login, String password) {
        super(login, password);
    }

    @Override
    public CentralBank getBank() {
        return bank;
    }

    @Override
    public void setBank(CentralBank cb){
        bank = cb;
    }
}
