package atm;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InputOutput {
    /**
     * Reads in and instantiates all of the transactions described in the deposits text file.
     * @param path path to deposits
     * @param CentralBank The bank containing all of the users
     * @throws AccountNotFoundException
     */
    public static void readTextFile(String path, CentralBank CentralBank) throws AccountNotFoundException {
        try {
            Scanner scanner = new Scanner(new FileInputStream(path));
            String[] deposit;

            while (scanner.hasNextLine()) {
                deposit = scanner.nextLine().split(",");
                int amount = Integer.parseInt(deposit[0]);
                String login = deposit[1];
                long accountTime;
                User user;
                CashTransaction transaction;
                for (String str : CentralBank.getUsers().keySet()) {
                    if (login.equals(str)) {
                        user = CentralBank.getUsers().get(str);
                        if (deposit.length == 2) {
                            transaction = new CashTransaction(amount, user.getPrimaryChecking());
                            user.getPrimaryChecking().parseTransaction(transaction);
                        } else if (deposit.length == 3) {
                            accountTime = Long.parseLong(deposit[2]);
                            Account accountDep = findAccount(user, accountTime);
                            if (accountDep != null) {
                                transaction = new CashTransaction(amount, accountDep);
                                accountDep.parseTransaction(transaction);
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
    @SuppressWarnings("unchecked")
    private static Account findAccount(User user, Long accountTime) throws AccountNotFoundException{
        for (Long time : user.getAccounts().keySet()) {
            if (accountTime.equals(time)) {
                return user.getAccounts().get(time);
            }
        }
        throw new AccountNotFoundException();
    }

    public static HashMap<String, User> readUsersFromFile(String filePath) throws ClassNotFoundException {
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

    public static void saveMapToFile(String filePath, HashMap<String, User> obj) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(obj);
        output.close();
        //file.close();
    }
    public static void saveArrayListToFile(String filePath, ArrayList<CashMachine> obj) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(obj);
        output.close();
        //file.close();
    }

    public static ArrayList<CashMachine> readCashMachinesFromFile(String filePath) throws ClassNotFoundException{
        ArrayList<CashMachine> machines;
        try {
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            machines = (ArrayList<CashMachine>) input.readObject();
            input.close();
            return machines;
        } catch (IOException ex) {
            System.out.println("Cannot read from input.");
        }
        return null;
    }

}