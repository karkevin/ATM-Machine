package UserTypes;


import atm.CentralBank;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class BankManager extends CredentialedEntity implements Administrator, Serializable {

    // This BankManagers's CentralBank.
    public CentralBank bank;


    public BankManager(String login, String password) {
        super(login, password);
    }

    /**
     * Create a new CredentialedEntity.
     * @param login this CredentialedEntity's login
     * @param password this CredentialedEntity's password.
     * @param isEmployee true iff this BankManager is a BankEmployee.
     */
    public void createUser(String login, String password, boolean isEmployee){
        if (isEmployee){
            User user = new BankEmployee(login, password);
            getBank().addUser(user);
        } else {
            createUser(login, password);
        }
    }

    public void setTime(String time) {
        String[] data = time.split(" ");

        int[] times = new int[5];
        try{
            for(int i = 0; i < 5; i++){
                times[i] = Integer.parseInt(data[i]);
            }
        } catch(NumberFormatException e){
            return;
        }
        Calendar date = new GregorianCalendar();
        date.set(times[0], times[1], times[2], times[3], times[4]);
        bank.setTime(date);
    }

    /**
     * Getter for this BankManager's CentralBank.
     * @return this BankManager's CentralBank.
     */
    @Override
    public CentralBank getBank() {
        return bank;
    }

    /**
     * Setter for this BankManager's CentralBank.
     */
    @Override
        public void setBank(CentralBank cb){
        bank = cb;
    }
}


