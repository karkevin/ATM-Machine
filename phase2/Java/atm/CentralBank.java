package atm;
import UserTypes.BankManager;
import UserTypes.User;
import accounts.*;
import transactions.*;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Scanner;


public class CentralBank implements Serializable {

    // The User HashMap in this CentralBank.
    private HashMap<String, User> users;

    // The cashMachines in this CentralBank.
    private CashMachine cashMachine;

    // The BankManager in this CentralBank.
    private BankManager manager;

    // The date of the CentralBank.
    public Calendar date;

    // The TransactionFactory in this CentralBank.
    public TransactionFactory tf;

    /**
     * Create a new CentralBank..
     */
    public CentralBank() throws ClassNotFoundException, IOException {
        String userFilePath = "users";
        String cashMachineFilepath = "cashMachine";
        date = Calendar.getInstance();
        tf = new TransactionFactory();

        HashMap<String, User> temp = null;
        File test1 = new File(userFilePath);
        if (test1.exists()) {
            temp = readUsersFromFile(userFilePath);
        }

        if(temp == null){
            users = new HashMap<>();
        } else {
            users = temp;
        }

        CashMachine temp2 = null;
        File test2 = new File(cashMachineFilepath);
        if (test2.exists()) {
            temp2 = readCashMachineFromFile(cashMachineFilepath);
        }

        if(temp2 == null){
            cashMachine = new CashMachine();
        } else {
            cashMachine = temp2;
        }

        BankManager temp3 = null;
        File test3 = new File("manager");
        if(test3.exists()){
            temp3 = readBankManagerFromFile("manager");
        }

        if(temp3 != null){
           manager = temp3;
        }


    }

    /**
     * Getter for date in this CentralBank
     * @return the date in this CentralBank.
     */
    public Calendar getTime() {
        return date;
    }

    /**
     * Setter for the date in this CentralBank.
     */
    public void setTime(Calendar cal){
        date = cal;
    }

    /**
     * Updates the time
     * @param delta amount of milliseconds
     */
    public void updateTime(int delta){
        date.add(Calendar.MILLISECOND,delta);
    }

    /**
     * Setter for the BankManager in this CentralBank.
     */
    public void setManager(BankManager bm) {
        manager = bm;
        save();
    }

    /**
     * Getter for the BankManager in this CentralBank
     * @return the BankManager in this CentralBank.
     */
    public BankManager getManager() {
        return manager;
    }

    /**
     * Update the monthlyFee and MonthlyTransactions in all the MinimumChecking account stored in this CentralBank.
     */
    public void updateMinimumChecking() {
        for (User user : users.values()) {
            for (Account acc : user.getAccounts()) {
                if (acc instanceof MinimumCheckingAccount) {
                    ((MinimumCheckingAccount) acc).updateMonthlyFee();
                    ((MinimumCheckingAccount) acc).updateMonthlyTransactions();
                }
            }
        }
    }

    /**
     * Update the interest in all the Savings account stored in this CentralBank.
     */
    public void updateInterest() {
        for (User user : users.values()) {
            for (Account acc : user.getAccounts()) {
                if (acc instanceof SavingsAccount && !acc.isSuspended()) {
                    ((SavingsAccount) acc).updateInterest();
                }
            }
        }
    }

    /**
     * Saves users, cashMachine, and manager into a serialized filed before the system shuts down every night.
     */
    public void save(){
        try {
            saveToFile("users", this.users);
            saveToFile("cashMachine", cashMachine);
            saveToFile("manager", manager);

        } catch (IOException e) {

        }
    }

    /**
     * this CentralBank system shuts down every night.
     */
    public void shutDown() {
        if (this.getTime().get(Calendar.DAY_OF_MONTH) == 1) {
            this.updateInterest();
            this.updateMinimumChecking();
        }
        save();
        System.exit(0);
    }

    /**
     * Read from deposits.txt and transfer in money to the account in this CentralBank.
     * Or giving a error message said "Failed to read deposits."
     */
    public void readDeposits() {
        try {
            readTextFile("deposits.txt", this);
            save();
        } catch (AccountNotFoundException e) {
            System.out.println("Failed to read deposits.");
        }
    }

    /**
     * Add User to the User HashMap in this CentralBank.
     */
    public void addUser(User user) {

        users.put(user.getLogin(), user);
        save();
    }

    /**
     * Getter for the users hashMap in this CentralBank system.
     * @return the users hashMap in this CentralBank system.
     */
    public HashMap<String, User> getUsers() {
        return users;
    }

    /**
     * Getter for the CashMachine in this CentralBank system.
     * @return the CashMachine in this CentralBank system.
     */
    public CashMachine getCashMachine() {
        return cashMachine;
    }

    /**
     * Transfer money from fromAccount to toAccount.
     * @return true if the transfer succeeds, false otherwise.
     */
    public boolean transferMoney(double amount, Account fromAccount, Account toAccount) {
        Transaction transaction = tf.getTransaction("ACCOUNT", amount, fromAccount);

        ((AccountTransaction) transaction).setToAccount(toAccount);
        return transaction.parse();
    }

    /**
     * Withdrawal money from the cash machine.
     * @return true if the withdrawal succeeds, false otherwise.
     */
    public boolean cashMoney(double amount, Account fromAccount) {
        Transaction transaction = tf.getTransaction("CASH", amount, fromAccount);
        try {
            return cashMachine.withdraw((CashTransaction) transaction);
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Failed to withdraw due to IOException");
            return false;
        }

    }

    /**
     * Pay bill,
     * @return true if the bill succeeds, false otherwise.
     */
    public boolean payBill(double amount, Account fromAccount) {
        Transaction transaction = tf.getTransaction("BILL", amount, fromAccount);
        return transaction.parse();
    }


    /**
     * Updates deposits in the text file
     * @param path the path to the deposit file
     * @param bank Bank to update
     * @throws AccountNotFoundException
     */
    private void readTextFile(String path, CentralBank bank) throws AccountNotFoundException {
        try {
            Scanner scanner = new Scanner(new FileInputStream(path));
            String[] deposit;

            while (scanner.hasNextLine()) {
                deposit = scanner.nextLine().split(",");
                int amount = 0;

                amount += 5 * Integer.parseInt(deposit[0]);
                bank.getCashMachine().addCash(5, Integer.parseInt(deposit[0]));
                amount += 10 * Integer.parseInt(deposit[1]);
                bank.getCashMachine().addCash(10, Integer.parseInt(deposit[1]));
                amount += 20 * Integer.parseInt(deposit[2]);
                bank.getCashMachine().addCash(20, Integer.parseInt(deposit[2]));
                amount += 50 * Integer.parseInt(deposit[3]);
                bank.getCashMachine().addCash(50, Integer.parseInt(deposit[3]));

                String login = deposit[4];
                String accountId;
                User user;
                Transaction transaction;
                for (String str : bank.getUsers().keySet()) {
                    if (login.equals(str)) {
                        user = bank.getUsers().get(str);
                        if (deposit.length == 5) {
                            transaction = tf.getTransaction("CASH", amount, user.getPrimaryChecking());
                            transaction.parse();
                        } else if (deposit.length == 6) {
                            accountId = deposit[5];
                            Account accountDep = user.getAccountFromId(accountId);
                            if (accountDep != null) {
                                transaction = tf.getTransaction("CASH", amount, accountDep);
                                transaction.parse();
                            } else {
                                throw new AccountNotFoundException();
                            }
                        }
                    }
                }
            }
            // Empties the file
            new PrintWriter(path).close();
            scanner.close();
        } catch (IOException e) {
            System.out.println("Failed to read from input");
        }
    }

    /**
     * After serializing, read users from the serialized file.
     * @param filePath
     * @return user login mapping to the actual user instance
     * @throws ClassNotFoundException
     */
    private static HashMap<String, User> readUsersFromFile(String filePath) throws ClassNotFoundException {
        HashMap<String, User> map;
        try {
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            map = (HashMap<String,User>) input.readObject();
            input.close();
            return map;
        } catch (IOException ex) {
            System.out.println("Cannot read from input.");
        }
        return null;
    }

    /**
     * Serialize information currently registered in the bank.
     * @param filePath
     * @param obj information that needs to be serialized.
     * @throws IOException
     */
    private void saveToFile(String filePath, Object obj) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(obj);
        output.close();
        file.close();
    }

    /**
     * After serializing, read cash machine information from serialized files.
     * @param filePath
     * @return CashMachine
     * @throws ClassNotFoundException
     */
    private static CashMachine readCashMachineFromFile(String filePath) throws ClassNotFoundException{
        CashMachine machines;
        try {
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            machines = (CashMachine) input.readObject();
            input.close();
            return machines;
        } catch (IOException ex) {
            System.out.println("Cannot read from input.");
        }
        return null;
    }

    /**
     * Reads bank manager after serializing
     * @param filePath path to the bank manager
     * @return the bank manager
     * @throws ClassNotFoundException
     */
    private BankManager readBankManagerFromFile(String filePath) throws ClassNotFoundException{
        BankManager bm;
        try {
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            bm = (BankManager) input.readObject();
            input.close();
            return bm;
        } catch (IOException ex) {
            System.out.println("Cannot read from input.");
        }
        return null;
    }
}