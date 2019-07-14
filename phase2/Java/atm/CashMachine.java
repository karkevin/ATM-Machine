package atm;

import transactions.CashTransaction;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;


public class CashMachine implements java.io.Serializable{

    private HashMap<Integer, Integer> typeOfCash;

    public CashMachine() {
        typeOfCash = new HashMap<>();
        typeOfCash.put(5, 100);
        typeOfCash.put(10, 100);
        typeOfCash.put(20, 100);
        typeOfCash.put(50, 100);
    }

    public int getTypeOfCash(int denomination)
    {
        return typeOfCash.get(denomination);
    }

    /**
     * Returns the bills that are withdrawn from the cash machine for a given amount of money
     * @param amount the amount of money being withdraw
     * @return if there is a valid combination of bills that add up to the amount, a hashmap with bill
     * denominations as keys and the number of bills of that denomination being withdrawn as values,
     * otherwise, null
     */
    private HashMap<Integer, Integer> obtainBills(double amount)
    {
        HashMap<Integer, Integer> bills = new HashMap<>();
        bills.put(5, 0);
        bills.put(10, 0);
        bills.put(20, 0);
        bills.put(50, 0);
        return obtainBills(amount, bills);
    }

    /**
     * Returns the bills that are withdrawn from the cash machine for a given amount of money
     * @param amount the amount of money being withdraw
     * @return if there is a valid combination of bills that add up to the amount, a hashmap with bill
     * denominations as keys and the number of bills of that denomination being withdrawn as values,
     * otherwise, null
     */
    private HashMap<Integer, Integer> obtainBills(double amount, HashMap<Integer, Integer> bills)
    {
        if (amount == 0)
            return bills;
        HashMap<Integer, Integer> b;
        HashMap<Integer, Integer> k;
        if (amount >= 50 && typeOfCash.get(50) - bills.get(50) > 0)
        {
            k = new HashMap<>(bills);
            k.put(50, bills.get(50) + 1);
            b = obtainBills(amount - 50, k);
            if (b != null)
                return b;
        }
        if (amount >= 20 && typeOfCash.get(20) - bills.get(20) > 0)
        {
            k = new HashMap<>(bills);
            k.put(20, bills.get(20) + 1);
            b = obtainBills(amount - 20, k);
            if (b != null)
                return b;
        }
        if (amount >= 10 && typeOfCash.get(10) - bills.get(10) > 0)
        {
            k = new HashMap<>(bills);
            k.put(10, bills.get(10) + 1);
            b = obtainBills(amount - 10, k);
            if (b != null)
                return b;
        }
        if (amount >= 5 && typeOfCash.get(5) - bills.get(5) > 0)
        {
            k = new HashMap<>(bills);
            k.put(5, bills.get(5) + 1);
            b = obtainBills(amount - 5, k);
            if (b != null)
                return b;
        }
        return null;
    }

    /**
     * Withdraws money from account
     * @param transaction transaction containing details of amount of money
     * @return success
     * @throws IOException
     */
    public boolean withdraw(CashTransaction transaction)throws IOException {
        if (transaction.getAmount() % 5 != 0)
        {
            JOptionPane.showMessageDialog(null, "Invalid input");
            return false;
        }

        HashMap<Integer, Integer> billsRetrieved = obtainBills(transaction.getAmount()*-1);
        if (billsRetrieved == null)
        {
            checkAmount();
            return false;
        }
        if (transaction.parse())
        {
            for (int denomination: billsRetrieved.keySet())
            {
                typeOfCash.put(denomination, typeOfCash.get(denomination) - billsRetrieved.get(denomination));
            }
            checkAmount();
            return true;
        }
        return false;
    }

    /**
     * Checks if there is enough money in the machine, and sends an alert if it is running low
     * @throws IOException
     */
    private void checkAmount() throws IOException {
        //System.out.println("The total amount in the cash machine is: " + amountOfCash);
        for (int i : typeOfCash.keySet()) {
            Integer r = typeOfCash.get(i);
            if (r < 20) {
                sendAlert(i);
                JOptionPane.showMessageDialog(null, "$" + i + "bills are out of stock");
            }
        }
    }

    private void sendAlert(int billType) throws IOException{
        // Open the file for writing and write to it.
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("alerts.txt", true)))) {
            out.println("Please restock the cash machine with the following bill type: " + billType);
            System.out.println("Alert has been sent out");
        }
    }

    public void addCash(Integer type, Integer amount) {
        typeOfCash.put(type, amount + typeOfCash.get(type));
    }
}
