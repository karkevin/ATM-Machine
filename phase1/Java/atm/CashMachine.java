package atm;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;


public class CashMachine implements java.io.Serializable{
    private int amountOfCash;
    private static HashMap<Integer, Integer> typeOfCash;

    public CashMachine() {
        typeOfCash = new HashMap<>();
        typeOfCash.put(5, 2000);
        typeOfCash.put(10, 1000);
        typeOfCash.put(20, 5000);
        typeOfCash.put(50, 2000);
        this.amountOfCash = 0;
        for (Integer i : typeOfCash.keySet()) {
            this.amountOfCash += i * typeOfCash.get(i);
        }
    }

    private static HashMap<Integer, Integer> obtainBills(double amount)
    {
        double k = amount;
        HashMap<Integer, Integer> bills = new HashMap<>();
        for (Integer denomination: typeOfCash.keySet())
        {
            while (k >= denomination && typeOfCash.get(denomination) > 0)
            {
                bills.put(denomination, bills.get(denomination) + 1);
                k -= denomination;
            }
        }

        if (k == 0)
            return bills;
        return null;
    }

    public static boolean withdraw(CashTransaction transaction)throws IOException {
        if (transaction.amount % 5 != 0)
        {
            System.out.println("Invalid input");
            return false;
        }

        HashMap<Integer, Integer> billsRetrieved = obtainBills(transaction.amount);
        if (billsRetrieved == null)
        {
            checkAmount();
            return false;
        }
        if (transaction.getFrom().parseTransaction(transaction))
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

    private static void checkAmount() throws IOException {
        //System.out.println("The total amount in the cash machine is: " + amountOfCash);
        for (int i : typeOfCash.keySet()) {
            Integer r = typeOfCash.get(i);
            if (r < 20) {
                //this.sendAlert();
                sendAlert();
                System.out.println("$" + i + "bills are out of stock");
            }
        }
    }

    private static void sendAlert() throws IOException{
        // Open the file for writing and write to it.
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Alert")))) {
            out.println("Alert!!!");
            out.println("Alert is on, please restock the cash machine");
            System.out.println("Alert has been sent out");
        }
    }

    public static void addCash(Integer type, Integer amount) {
        typeOfCash.put(type, amount);
    }
}
