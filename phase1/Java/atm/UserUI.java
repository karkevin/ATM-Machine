package atm;

import java.util.Scanner;

public abstract class UserUI {
    protected CentralBank bank;
    protected static Scanner inputs = new Scanner(System.in);
    protected boolean loggedIn = true;

    public UserUI(CentralBank b){
        bank = b;
    }
    protected abstract void runUI();

    protected Account userInputToAccount(User u){
        String choice;
        System.out.println(u.getAccounts().keySet().size());
        for(Long k : u.getAccounts().keySet()){
            System.out.println(k);
        }
        Object[] ids = u.getAccounts().keySet().toArray();
        System.out.println("Choose account: ");
        for(int i = 0; i < ids.length; i++)
            System.out.println(i + ")" + (Long)ids[i]);
       System.out.println("Please choose between" + 0 + " and " + (ids.length - 1));
        while(true) {
            choice = inputs.nextLine();
            try {
                return u.getAccounts().get((Long) ids[Integer.parseInt(choice)]);
            } catch (NumberFormatException e) {
                System.out.println("Please choose a numerical value");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please choose a value within " + 0 + " and " + (ids.length - 1));
            }
        }
    }
}
