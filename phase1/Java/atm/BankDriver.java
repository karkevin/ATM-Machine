package atm;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Scanner;

public class BankDriver {

    private static UserUI currentUser;
    private static Scanner inputs = new Scanner(System.in);
    private static UIFactory UIGetter;
    private static CentralBank bank;// = new CentralBank("temp");

    private static void initManager(){
        String login;
        String password;

        System.out.println("Setting manager login information...");
        System.out.print("Login: ");
        login = inputs.nextLine();
        System.out.print("Password: ");
        password = inputs.nextLine();

        BankManager bm = new BankManager(login, password);
        bank.setManager(bm);
    }

    public static void requestUserAccount(String name) throws IOException {
        // Open the file for writing and write to it.
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Request")))) {
            out.println(name + ", requesting account creation");
            System.out.println("Your request for account creation has been sent");
        }
    }

    private static void logIn(){
        System.out.println("Log in, or request an account? \n" +
                "1) Log in \n" +
                "2) Request an account");
        String choice = inputs.nextLine();
        if (choice.equals("2")){
            System.out.print("Requesting account creation. \n" +
                    "Please type your name: ");
            String name = inputs.nextLine();
            try {
                requestUserAccount(name);
            } catch (IOException e) {}
        } else if (choice.equals("1")) {
            System.out.println("Please log in.");
            System.out.print("Login: ");
            String login = inputs.nextLine();
            System.out.print("Password: ");
            String password = inputs.nextLine();
            if (bank.getManager().verifyLogin(login, password)) {
                currentUser = UIGetter.getUI(bank.getManager());
            } else {
                for (CredentialedEntity user : bank.getUsers().values()) {
                    if (user.verifyLogin(login, password)) {
                        currentUser = UIGetter.getUI(user);
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        try{
            bank = new CentralBank();
        } catch (ClassNotFoundException e1){

        } catch (IOException e){

        }
        Runnable r = new Runnable(){
            public void run() {
                Calendar calendar = Calendar.getInstance();
                boolean checked = false;
                while (calendar.get(Calendar.MINUTE) != 59 && calendar.get(Calendar.HOUR) != 23) {
                    calendar = Calendar.getInstance();
                    if(calendar.get(Calendar.MINUTE) % 5 == 0 && !checked){
                        bank.readDeposits();
                        checked = true;
                    } else if(calendar.get(Calendar.MINUTE)%5 != 0){
                        checked = false;
                    }
                }
                bank.shutDown();
            }
        };
        new Thread(r).start();

        UIGetter = new UIFactory(bank);
        initManager();
        while (true){
            while (currentUser == null) {
                logIn();
                if (currentUser == null) {
                    System.out.println("Incorrect login information. Please try again.");
                }
            }
            currentUser.runUI();
            currentUser = null;
        }
    }
}
