package accounts;

import java.io.Serializable;

public class AccountFactory implements Serializable {

    private long idCounter;

    /**
     * Using the factory design pattern to create an account
     * @param type   type of account to create
     * @param amount initial balance
     * @return Account
     */
    public Account getAccount(String type, double amount) {

        if (type == null) {
            return null;
        }

        switch (type) {
            case "CCA":
                idCounter++;
                return new CreditCardAccount(amount, "CCA" + idCounter);
            case "LINE":
                idCounter++;
                return new LineOfCreditAccount(amount, "LINE" + idCounter);
            case "SA":
                idCounter++;
                return new SavingsAccount(amount, "SA" + idCounter);
            case "CA":
                idCounter++;
                return new CheckingAccount(amount, "CA" + idCounter);
            case "MCA":
                idCounter++;
                return new MinimumCheckingAccount(amount, "MCA" + idCounter);
            default:
                return null;
        }
    }

    public void setIdCounter(long idCounter) {
        this.idCounter = idCounter;
    }
}