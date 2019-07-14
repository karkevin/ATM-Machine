package atm;

import gui.AdminUI.ManagerCreation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;


import javax.swing.*;

public class BankDriver {

    public static CentralBank bank;

    /**
     * Writes a request for the creation of a user to Request.txt
     * @param name the text entered into the username box when a request is made.
     * @throws IOException exception that occurs if the file writing fails.
     */
    public static void requestUserAccount(String name) throws IOException {
        // Open the file for writing and write to it.
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("requests.txt", true)))) {
            out.println("0 " + name + " requesting user creation");
            System.out.println("Your request for account creation has been sent");
        }
    }

    public static void main(String[] args){
        try{
            bank = new CentralBank();
        } catch (ClassNotFoundException e1){
            JOptionPane.showMessageDialog(null, "Object could not be read from serialized file");
            return;
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "File reading error");
            return;
        }
        Runnable r = new Runnable(){
            public void run() {

                boolean checked = false;
                long initial = System.currentTimeMillis();
                while (true) {
                        if(bank.getTime().get(Calendar.MINUTE) == 59 && bank.getTime().get(Calendar.HOUR_OF_DAY) == 23){
                            break;
                        }
                        if (bank.getTime().get(Calendar.MINUTE) % 5 == 0 && !checked) {
                            bank.readDeposits();
                            checked = true;
                        } else if (bank.getTime().get(Calendar.MINUTE) % 5 != 0) {
                            checked = false;
                        }
                        long newTime = System.currentTimeMillis();
                        if(newTime - initial >= 1) {
                            bank.updateTime((int) (System.currentTimeMillis() - initial));
                            //assume atomic
                            initial = newTime;
                        }
                }
                bank.shutDown();
            }
        };
        new Thread(r).start();
        JFrame frame = new JFrame("ATM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        if(bank.getManager() != null){
            new gui.Login(frame);
        } else {
            new ManagerCreation(frame);
        }
    }
}
