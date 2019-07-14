package gui.AdminUI;

import UserTypes.BankManager;
import gui.Login;

import atm.BankDriver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerCreation {
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton submitButton;

    public ManagerCreation(JFrame mainframe) {
        mainframe.setContentPane(panel1);
        mainframe.setSize(500,500);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BankDriver.bank.setManager(new BankManager(
                        textField1.getText(),
                        new String(passwordField1.getPassword())));
                new Login(mainframe);
            }
        });
    }
}
