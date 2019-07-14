package gui;


import UserTypes.*;
import atm.CentralBank;
import gui.AdminUI.EmployeeGUI;
import gui.CustomerUI.CustomerGUI;
import gui.AdminUI.AdministratorGUI;

import javax.swing.*;

public class UIFactory {

    private CentralBank bank;
    private JFrame mainframe;

    public UIFactory(CentralBank bank, JFrame mainframe){
        this.bank = bank;
        this.mainframe = mainframe;
    }

    public void getUI(CredentialedEntity user){
        if (user instanceof Administrator){
            ((Administrator) user).setBank(bank);
        }

        if (user instanceof BankManager) {
            new AdministratorGUI(mainframe, (BankManager) user);
        } else if(user instanceof BankEmployee){
            new EmployeeGUI(mainframe, (BankEmployee) user);
        } else if (user instanceof User){
            new CustomerGUI(mainframe, (User) user);
        }
    }

}
