package gui.AdminUI;
import UserTypes.Administrator;
import UserTypes.BankManager;
import UserTypes.CredentialedEntity;
import gui.ChangePassword;
import gui.Login;

import javax.security.auth.login.CredentialNotFoundException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministratorGUI {
    private JComboBox comboBox1;
    public JPanel panel1;
    private JButton button1;
    private JButton logoutButton;
    private JFrame mainframe;
    private String selectedOption = "[Select One]";
    private Administrator admin;

    private void optionSelect(){
        switch (selectedOption){
            case "Check Requests": new CheckRequests(mainframe, admin);
                break;
            case "Check Alerts": new Alerts(mainframe, admin);
                break;
            case "Restock Machine": new RestockMachine(mainframe,(BankManager) admin);
                break;
            case "Add Employee": new CreateUser(mainframe, admin, "Employee");
                break;
            case "Set Time": new SetTime(mainframe,(BankManager) admin);
                break;
            case "Change Password": new ChangePassword(mainframe, (CredentialedEntity)admin);
                break;
        }
    }

    public AdministratorGUI(JFrame mainframe, Administrator admin) {
        this.admin = admin;
        this.mainframe = mainframe;
        mainframe.setContentPane(panel1);
        mainframe.setSize(500,500);
        mainframe.setVisible(true);

        if (admin instanceof BankManager){
            comboBox1.addItem("Restock Machine");
            comboBox1.addItem("Add Employee");
            comboBox1.addItem("Set Time");
        }

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionSelect();
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                selectedOption = (String)cb.getSelectedItem();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login(mainframe);
            }
        });
    }


}
