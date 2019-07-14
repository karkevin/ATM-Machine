package atm;

public class AccountNotFoundException extends Exception{
    public AccountNotFoundException() {
        super("Account not found");
    }
}
