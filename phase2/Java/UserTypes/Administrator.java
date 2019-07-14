package UserTypes;

import accounts.Account;
import atm.CentralBank;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public interface Administrator {

    CentralBank getBank();
    void setBank(CentralBank cb);

    default void createUser(String login, String password){
        User user = new User(login, password);
        getBank().addUser(user);
    }

    /**
     * Creates a single account
     * @param username user
     * @param accountType the account type to make
     */
    default void createAccount(String username, String accountType) {
        User u = getBank().getUsers().get(username);
        if(u != null){
            Account newAcc = u.getAf().getAccount(accountType, 0.0);
            u.addAccount(newAcc);
        }
    }

    /**
     * Creates a joint account
     * @param username1 first user
     * @param username2 second user
     * @param accountType the type of account to make
     */
    default void createAccount(String username1, String username2, String accountType) {
        User u1 = getBank().getUsers().get(username1);
        User u2 = getBank().getUsers().get(username2);
        if(u1 != null && u2 != null){
            Account newAcc;
            if (u1.getAccounts().size() >= u2.getAccounts().size()) {
                newAcc = u1.getAf().getAccount(accountType, 0.0);
                u2.getAf().setIdCounter(u1.getAccounts().size()+1);
            }
            else {
                newAcc = u2.getAf().getAccount(accountType, 0.0);
                u1.getAf().setIdCounter(u2.getAccounts().size()+1);
            }
            u1.addAccount(newAcc);
            u2.addAccount(newAcc);
        }
    }

    /**
     * Undoes n number of transactions
     * @param username the username of the user the account belongs to
     * @param accID the account that we want to undo transactions from
     * @param num The amount of transactions we want to undo
     */
    default void undoNTransactions(String username, String accID, String num){
        User u = getBank().getUsers().get(username);
        if(u == null) {
            JOptionPane.showMessageDialog(null, "Request format error!");
            return;
        }
        Account acc = u.getAccountFromId(accID);
        try {
            int n = Integer.parseInt(num);
            if(n < 0 || acc == null) {
                JOptionPane.showMessageDialog(null, "Request format error!");
                return;
            }
            acc.undoTransactions(n);
        } catch(NumberFormatException error){
            JOptionPane.showMessageDialog(null, "Request format error!");
            return;
        }
    }

    /**
     * Reads from request file
     * @return the requests
     */
    default ArrayList<String> checkRequest() throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("requests.txt"))) {

            // Print the lines from f prefaced with the line number,
            // starting at 1.
            ArrayList<String> currentRequests = new ArrayList<>();
            String line = fileReader.readLine();
            int i = 1;
            while (line != null) {
                currentRequests.add(line);
                System.out.println("Line " + i + ": " + line.substring(2));
                line = fileReader.readLine();
                i++;
            }
            return currentRequests;
        }
    }

    /**
     * Removes a line from the requests file
     * @param req the line to remove
     */
    default void removeLine(String req) {
        try {
            File inputFile = new File("requests.txt");
            File tempFile = new File("requests.tmp");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean removed = false;

            while((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                if(trimmedLine.equals(req) && !removed) {
                    removed = true;
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            Files.move(tempFile.toPath(), inputFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch(IOException error){
            JOptionPane.showMessageDialog(null, "Couldn't remove line, please try again");
            return;
        }
    }

    /**
     * Takes in a non user creation request string and grants it
     * @param req request to grant
     */
    default void parseRequest(String req){
        String type = req.substring(0,1);
        String[] data = req.split(" ");
        if(data.length >= 3) {
            switch (type) {
                case "1":
                    this.createAccount(data[1], data[2]);
                    JOptionPane.showMessageDialog(null, "Account Created.");
                    removeLine(req);
                    break;
                case "2":
                    this.undoNTransactions(data[1], data[2], data[3]);
                    JOptionPane.showMessageDialog(null, "Transactions undone.");
                    removeLine(req);
                    break;
                case "3":
                    this.createAccount(data[1], data[2], data[3]);
                    JOptionPane.showMessageDialog(null, "Joint Account Created.");
                    removeLine(req);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Unrecognized request");
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Wrong format error");
        }
    }

    /**
     * @return The alerts if any
     */
    default ArrayList<String> checkAlerts() throws IOException {
        ArrayList<String> alerts = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader("alerts.txt"))) {
            String line = fileReader.readLine();
            alerts.add(line);
            int i = 1;
            while (line != null) {
                System.out.println("Line " + i + ": " + line);
                line = fileReader.readLine();
                i++;
                alerts.add(line);
            }
        }
        return alerts;
    }

    default void clearAlerts() {
        try {
            new PrintWriter("alerts.txt").close();
        }
        catch (IOException e) {}
    }
}