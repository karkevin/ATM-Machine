package gui;

import UserTypes.CredentialedEntity;
import UserTypes.User;
import atm.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Login {
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton submitButton;
    private JButton createAccountButton;
    private JButton jointUserAccountButton;
    private JLabel issue;
    private CredentialedEntity currentUser;

    public Login(JFrame mainframe) {
        CentralBank bank = BankDriver.bank;
        UIFactory uiGetter = new UIFactory(bank, mainframe);
        mainframe.setContentPane(panel1);
        mainframe.setSize(500,500);
        mainframe.setVisible(true);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                boolean loggedIn = false;
                if (bank.getManager().verifyLogin(
                        textField1.getText(),
                        new String(passwordField1.getPassword())
                    )
                ) {
                    currentUser = bank.getManager();
                    loggedIn = true;
                } else {
                    User user = bank.getUsers().get(textField1.getText());
                    if (user != null)
                    {
                        if (user.verifyLogin(textField1.getText(), new String(passwordField1.getPassword()))) {
                            currentUser = user;
                            loggedIn = true;
                        }
                    }
                }

                if(!loggedIn){
                    JOptionPane.showMessageDialog(null, "Incorrect username or password.");
                    textField1.setText("");
                    passwordField1.setText("");
                }
                uiGetter.getUI(currentUser);
            }
        });
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = textField1.getText();
                if(bank.getManager().getLogin().equals(name) || BankDriver.bank.getUsers().containsKey(name)) {
                    JOptionPane.showMessageDialog(null, "Username taken.");
                } else{
                    try {
                        BankDriver.requestUserAccount(name);
                        JOptionPane.showMessageDialog(null, "Request Sent!");
                    }
                    catch (IOException e)
                    {
                        JOptionPane.showMessageDialog(null, "IOException");
                    }
                }

            }
        });
    }
}
