package gui;

import UserTypes.CredentialedEntity;
import atm.BankDriver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePassword {
    private JPasswordField firstPass;
    private JPanel panel1;
    private JPasswordField secondPass;
    private JButton updateButton;
    private JLabel Wrong;

    public ChangePassword(JFrame mainframe, CredentialedEntity u) {
        UIFactory uiGetter = new UIFactory(BankDriver.bank, mainframe);
        mainframe.setContentPane(panel1);
        mainframe.setSize(500,500);
        mainframe.setVisible(true);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String newPass = new String(firstPass.getPassword());
                String confirm = new String(secondPass.getPassword());
                if(newPass.equals(confirm)){
                    u.changePassword(newPass);
                    uiGetter.getUI(u);
                } else {
                    Wrong.setText("Please make the passwords match");
                }
            }
        });
    }
}
