package atm;
import java.io.IOException;
import java.util.Scanner;

public class CustomerUI extends UserUI {

    private User user;

    public CustomerUI(User user, CentralBank bank){
        super(bank);
        this.user = user;
    }

    private void makeTransaction() {
        Account fromAccount;
        Account toAccount = null;
        int amount;
        System.out.println("Requesting to make a transaction \n" +
                "How much money would you like to move? \n");
        String money;
        while(true) {
            money = inputs.nextLine();
            try {
                amount = Integer.parseInt(money);
                if(amount < 0 ){
                    System.out.println("Please enter a positive number");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number:");
            }
        }
        fromAccount = userInputToAccount(user);

        System.out.println("What type of transaction would you like to make? \n" +
                "1) transfer money to another account \n" +
                "2) withdrawal\n" +
                "3) Pay Bill \n" +
                "4) Cancel");
        String choice = inputs.nextLine();
        String transactionType = null;
        switch(choice) {
            case "1":
                transactionType = "TRANSFER";
                System.out.println("What is the user you want to transfer to?");
                String targetUser;
                while(true){
                    targetUser = inputs.nextLine();
                    if(bank.getUsers().containsKey(targetUser)){
                        break;
                    } else {
                        System.out.println("Please choose an actual user");
                    }
                }
                toAccount = userInputToAccount(bank.getUsers().get(targetUser));
                break;
            case "2": transactionType = "WITHDRAWAL"; break;
            case "3": transactionType = "BILL"; break;
            default:
                System.out.println("Canceling transaction request...");
        }
        if (transactionType != null && fromAccount != null){
            if (transactionType.equals("TRANSFER") && toAccount != null) {
                user.transferMoney(amount, fromAccount, toAccount);

            }else if (transactionType.equals("WITHDRAWAL")) {
                // negative amount for withdrawing money
                user.cashMoney(-1 * amount, fromAccount);
            }else {
                user.payBill(amount, fromAccount);
            }
        }
    }

    private void requestAccount(){
        String choice;
        System.out.println("Requesting to open an account. \n" +
                "Which type of account would you like to open? \n" +
                "1) Credit Card account \n" +
                "2) Line of Credit account \n" +
                "3) Checking account \n" +
                "4) Savings account \n" +
                "5) Cancel");
        choice = inputs.nextLine();
        String accountType = null;
        switch (choice){
            case "1": accountType = "CREDITCARD"; break;
            case "2": accountType = "LINEOFCREDIT"; break;
            case "3": accountType = "CHECKING"; break;
            case "4": accountType = "SAVINGS"; break;
            default:
                System.out.println("Canceling account request...");
        }
        if (accountType != null){
            try{
                user.sendRequest(accountType);
            } catch (IOException e) {
                //Add something here later
            }
        }
    }

    private void getAccountBalance(){
        Account acc = userInputToAccount(user);
        System.out.println(acc.getBalance());
    }

    private void getAccountRecentTransaction(){
        Account acc = userInputToAccount(user);
        if(acc.getRecentTransaction() == null){
            System.out.println("No recent transaction");
        } else {
            System.out.println(acc.getRecentTransaction());
        }
    }

    @Override
    protected void runUI(){
        String choice;
        while (loggedIn) {
            System.out.println("Welcome. \n" +
                    "Options (select a number): \n" +
                    "1) Get Summary \n" +
                    "2) See Net Total \n" +
                    "3) Request to Open an Account \n" +
                    "4) Request to Make a Transaction \n" +
                    "5) Show specific account balance\n" +
                    "6) Show recent transaction of an account\n"+
                    "7) Log Out"
            );
            choice = inputs.nextLine();
            switch (choice) {
                case "1":
                    System.out.println(user.getSummary());
                    break;
                case "2":
                    System.out.println("Net Total: " + user.netTotal());
                    break;
                case "3":
                    requestAccount();
                    break;
                case "4":
                    makeTransaction();
                case "5":
                    getAccountBalance();
                case"6":
                    getAccountRecentTransaction();
                case "7":
                    System.out.println("Logging Out...");
                    return;
            }
            System.out.println("Stay logged in? (Y/N)");
            choice = inputs.nextLine();
            switch (choice){
                case "Y": break;
                default: loggedIn = false;
            }
        }
        System.out.println("Logging Out...");
    }
}
