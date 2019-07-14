package atm;
import java.io.IOException;
import java.util.Scanner;

public class ManagerUI extends UserUI {

    private BankManager manager;

    public ManagerUI(BankManager manager, CentralBank bank){
        super(bank);
        this.manager = manager;
        this.bank = bank;
    }

    private void getAlerts(){
        System.out.println("Current Alerts: ");
        try {
            manager.checkAlerts();
        } catch (IOException e) {
            //Add something here later
        }
    }

    private void getRequests(){
        System.out.println("Current Requests: ");
        try {
            manager.checkRequest();
        } catch (IOException e) {
            //Add something here later
        }
    }

    private void createUser(){
        System.out.println("Creating a customer account...");
        boolean validUsername = false;
        String username = null;
        while (!validUsername){
            validUsername = true;
            System.out.print("Login: ");
            username = inputs.nextLine();
            if (username.equals(manager.getLogin())){
                System.out.println("Username already taken.");
                validUsername = false;
            } else {
                for (User user : bank.getUsers().values()) {
                    if (username.equals(user.getLogin())) {
                        System.out.println("Username already taken.");
                        validUsername = false;
                        break;
                    }
                }
            }
        }
        System.out.print("Password: ");
        String password = inputs.nextLine();
        bank.addUser(new User(username, password));
    }

    private void createAccount(){
        System.out.println("Creating an account...");
        System.out.println("Type of account: \n" +
                "1) Credit Card account \n" +
                "2) Line of Credit account \n" +
                "3) Checking account \n" +
                "4) Savings account \n" +
                "5) Cancel");
        String accountType = inputs.nextLine();
        String newAccount;
        switch (accountType){
            case "1": newAccount = "CreditCardAccount";
                break;
            case "2": newAccount = "LineOfCreditAccount";
                break;
            case "3": newAccount = "CheckingAccount";
                break;
            case "4": newAccount = "SavingsAccount";
                break;
            default: System.out.println("Canceling account creation...");
                return;
        }
        System.out.print("Owner of the account: ");
        String owner = inputs.nextLine();

        for (User user : bank.getUsers().values()){
            if (owner.equals(user.getLogin())){
                manager.createAccount(user, newAccount);
                break;
            }
        }
    }

    private void addCash(){
        System.out.println("Adding bills");
        System.out.println("Which denomination? \n" +
                "1) $5 \n" +
                "2) $10 \n" +
                "3) $20 \n" +
                "4) $50 \n" +
                "5) Cancel");
        String choice = inputs.nextLine();
        int billType;
        switch (choice){
            case "1": billType = 5;
                break;
            case "2": billType = 10;
                break;
            case "3": billType = 20;
                break;
            case "4": billType = 50;
                break;
            default: System.out.println("Canceling Restock...");
                return;
        }
        boolean validAmount = false;
        int amount = 0;
        while (!validAmount){
            System.out.println("$" + billType + " bills chosen. \n" +
                    "Amount: ");
            try {
                amount = Integer.parseInt(inputs.nextLine());
                validAmount = true;
            } catch (NumberFormatException e){
                System.out.println("Invalid amount. Please try again.");
            }
        }
        manager.increaseCashInMachine(billType, amount);
        System.out.println("Successfully added " + amount + " $" + billType + " bills");
    }

    private void undoTransaction(){
        System.out.println("Undoing a user's most recent transaction \n" +
                "User's login: ");
        String login = inputs.nextLine();
        Account selectedAccount = null;
        for (User user: bank.getUsers().values()) {
            if (user.getLogin().equals(login)) {
                selectedAccount = userInputToAccount(user);
                break;
            }
        }
        if(selectedAccount != null){
            manager.undoTransaction(selectedAccount);
        } else {
            System.out.println("Invalid user or account ID. Canceling transfer undo.");
        }
    }

    @Override
    protected void runUI(){
        while(loggedIn) {
            System.out.println("Logged in as Administrator. \n" +
                    "Options (select a number): \n" +
                    "1) Check Alerts \n" +
                    "2) Check Requests \n" +
                    "3) Create User \n" +
                    "4) Create Account \n" +
                    "5) Restock Machine \n" +
                    "6) Undo Transaction \n" +
                    "7) Set Time \n" +
                    "8) Log Out"
            );
            String choice = inputs.nextLine();
            switch (choice) {
                case "1": getAlerts();
                    break;
                case "2": getRequests();
                    break;
                case "3": createUser();
                    break;
                case "4": createAccount();
                    break;
                case "5": addCash();
                    break;
                case "6": undoTransaction();
                    break;
                case "7": manager.setTime();
                    break;
                case "8":
                    System.out.println("Logging Out...");
                    return;
            }
        }
    }

}
