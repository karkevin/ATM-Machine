package accounts;

public class AccountNotFoundException extends Exception{
    public AccountNotFoundException() {
        super("Account not found");
    }
}
