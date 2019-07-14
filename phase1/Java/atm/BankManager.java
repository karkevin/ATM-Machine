package atm;

import java.util.ArrayList;
import java.util.Calendar;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class BankManager extends CredentialedEntity {

    public BankManager(String login, String password) {
        super(login, password);
    }

    public void createUser(String login, String password, CentralBank bank){
        User user = new User(login, password);
        bank.addUser(user);
    }
    public void createAccount(User user, String accountType) {
        ArrayList<String> at1 = new ArrayList<>() ;
        at1.add("CreditCardAccount");
        at1.add("SavingsAccount");
        at1.add("CheckingAccount");
        at1.add("LineOfCreditAccount");
        if (at1.contains(accountType)){
            if(accountType.equals("CreditCardAccount")){
                Account cca = new CreditCardAccount(0.0);
                user.addAccount(cca);
            }
            else if(accountType.equals("SavingsAccount")){
                Account sa = new SavingsAccount(0.0);
                user.addAccount(sa);
            }
            else if(accountType.equals("CheckingAccount")){
                Account ca = new CheckingAccount(0.0);
                user.addAccount(ca);
            }
            else if(accountType.equals("LineOfCreditAccount")){
                Account line = new LineOfCreditAccount(0.0);
                user.addAccount(line);
            }
        }
    }

    public void increaseCashInMachine(int type, int amount) {
        CashMachine.addCash(type, amount);
    }

    public void undoTransaction(Account acc) {
        acc.resetRecentTransaction();
    }

    public void setTime() {
        Calendar date = Calendar.getInstance();
        System.out.println("You've succeeded to setting the time: " + date.toString());
    }

    public void checkAlerts() throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("Alert"))) {

            // Print the lines from f prefaced with the line number,
            // starting at 1.
            String line = fileReader.readLine();
            int i = 1;
            while (line != null) {
                System.out.println("Line " + i + ": " + line);
                line = fileReader.readLine();
                i++;
            }
        }
    }

    public void checkRequest() throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("Request"))) {

            // Print the lines from f prefaced with the line number,
            // starting at 1.
            String line = fileReader.readLine();
            int i = 1;
            while (line != null) {
                System.out.println("Line " + i + ": " + line);
                line = fileReader.readLine();
                i++;
            }
        }
    }
}


