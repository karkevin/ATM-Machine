package gui.CustomerUI;

import atm.BankDriver;
import UserTypes.User;
import atm.CentralBank;
import gui.UIFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RequestAccount {
    private JComboBox comboBox1;
    private JPanel panel1;
    private JLabel label1;
    private JButton mainMenuButton;
    private JButton requestButton;
    private JButton requestJointAccountButton;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton button1;
    private String selectedItem = "[Select One]";
    private String accountType = null;


    public RequestAccount(JFrame mainframe, User user) {
        CentralBank bank = BankDriver.bank;
        mainframe.setContentPane(panel1);
        mainframe.setSize(500, 500);
        textField1.setVisible(false);
        passwordField1.setVisible(false);
        button1.setVisible(false);

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                selectedItem = (String)cb.getSelectedItem();
            }
        });


        requestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAccountType();
                if (accountType != null) {
                    try{
                        user.sendAccountRequest(accountType);
                        JOptionPane.showMessageDialog(null, "Request Successful!");
                        new CustomerGUI(mainframe, user);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Request cancelled. Please try again.");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please select an account type.");

                }
            }
        });
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerGUI(mainframe, user);
            }
        });
        requestJointAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField1.setVisible(true);
                textField1.setVisible(true);
                button1.setVisible(true);
                passwordLabel.setText("Password: ");
                usernameLabel.setText("Username: ");
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = new String(passwordField1.getPassword());
                String username = textField1.getText();

                if (bank.getManager().verifyLogin(username, password)) {
                    JOptionPane.showMessageDialog(null, "Please input a user.");
                } else if(textField1.getText().equals(user.getLogin())){
                    JOptionPane.showMessageDialog(null,
                            "Cannot request a joint account with yourself.");
                    textField1.setText("");
                    passwordField1.setText("");
                }
                else {
                    User user2 = bank.getUsers().get(username);
                    if (user2 != null) {
                        if (user2.verifyLogin(username, password)) {
                            if (user2.hasSuspended()){
                                JOptionPane.showMessageDialog(null,
                                        "Target user has a suspended account. \n" +
                                                "Please try again once the issue has been resolved.");
                                textField1.setText("");
                                passwordField1.setText("");
                                return;
                            }
                            setAccountType();
                            if (accountType != null) {
                                try{
                                    user.sendAccountRequest(user2.toString(), accountType);
                                    JOptionPane.showMessageDialog(null, "Request Successful!");
                                    new CustomerGUI(mainframe, user);
                                } catch (IOException ex) {
                                    JOptionPane.showMessageDialog(null, "Request cancelled. Please try again.");
                                }
                            }
                            else {
                                JOptionPane.showMessageDialog(null, "Invalid Request. Please try again.");
                            }

                        }
                        else {
                            JOptionPane.showMessageDialog(null, "User does not exist.");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Please input a user.");

                    }
                }

            }
        });
    }
    private void setAccountType() {
        switch (selectedItem){
            case "Checking Account": accountType = "CA"; break;
            case "Credit Card Account": accountType = "CCA"; break;
            case "Line of Credit Account": accountType = "LINE"; break;
            case "Savings Account": accountType = "SA"; break;
            case "Minimum Checking Account": accountType = "MCA";
        }
    }
}
