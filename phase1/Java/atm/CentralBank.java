package atm;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Calendar;
//import java.io.File;
//import java.io.FileNotFoundException;


public class CentralBank implements Serializable {
    private HashMap<String, User> users;
    private ArrayList<CashMachine> cashMachines;
    private BankManager manager;
    public Calendar date;

    public CentralBank() throws ClassNotFoundException, IOException {
        String userFilePath = "users";
        String cashMachineFilepath = "cashMachines";
        File test1 = new File(userFilePath);
        if (test1.exists()) {
            users = InputOutput.readUsersFromFile(userFilePath);
        } else {
            users = new HashMap<>();
        }

        File test2 = new File(cashMachineFilepath);
        if (test2.exists()) {
            cashMachines = InputOutput.readCashMachinesFromFile(cashMachineFilepath);
        } else {
            cashMachines = new ArrayList<>();
            cashMachines.add(new CashMachine());
        }


    }

    public Calendar getTime() {
        return Calendar.getInstance();
    }

    public void setManager(BankManager bm) {
        manager = bm;
    }

    public BankManager getManager() {
        return manager;
    }

    public void updateInterest() {
        for (User user : users.values()) {
            for (Account acc : user.getAccounts().values()) {
                if (acc instanceof SavingsAccount) {
                    ((SavingsAccount) acc).updateInterest();
                }
            }
        }
    }

    public void save(){
        try {
            InputOutput.saveMapToFile("users", this.users);
            InputOutput.saveArrayListToFile("cashMachines", this.cashMachines);
        } catch (IOException e) {

        }
    }

    public void shutDown() {
        if (this.getTime().get(Calendar.DAY_OF_MONTH) == 1) {
            this.updateInterest();
        }
        save();
        System.exit(0);
    }

    public void readDeposits() {
        try {
            InputOutput.readTextFile("deposits.txt", this);
        } catch (AccountNotFoundException e) {
            System.out.println("Failed to read deposits.");
        }
    }

    public void addUser(User user) {

        users.put(user.getLogin(), user);
        try {
            InputOutput.saveMapToFile("users", users);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void addCashMachine(CashMachine cashMachine) {
        cashMachines.add(cashMachine);
    }

    public ArrayList<CashMachine> getCashMachine() {
        return cashMachines;
    }
}