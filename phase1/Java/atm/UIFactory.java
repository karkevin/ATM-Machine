package atm;

public class UIFactory {

    private CentralBank bank;

    public UIFactory(CentralBank bank){
        this.bank = bank;
    }

    public UserUI getUI(CredentialedEntity user){
        if (user instanceof BankManager){
            return new ManagerUI((BankManager) user, bank);
        } else if (user instanceof User){
            return new CustomerUI((User) user, bank);
        } else return null;
    }

}
