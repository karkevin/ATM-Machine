package gui.CustomerUI;

import accounts.Account;
import atm.BankDriver;
import UserTypes.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class RequestTransaction {
    private JPanel panel1;
    private JTextField textField1;
    private JButton checkButton;
    private JComboBox comboBox1;
    private JButton selectButton;
    private JLabel label1;
    private JComboBox comboBox2;
    private JButton selectButton1;
    private JButton mainMenuButton;
    private JButton clearButton;
    private JFrame mainframe;
    private String selectedItem = "[Select One]";
    private String selectedItem1 = "[Select One]";
    Account fromAccount;
    int amount;

    public RequestTransaction(JFrame mainframe, User user) {
        this.mainframe = mainframe;
        mainframe.setContentPane(panel1);
        mainframe.setSize(500, 500);
        mainframe.setVisible(true);

        for (String id : user.getIDs()) {
            comboBox1.addItem(id);
        }
        comboBox2.setVisible(false);
        selectButton1.setVisible(false);
        comboBox1.setVisible(false);
        selectButton.setVisible(false);
        label1.setText("How much money would you like to move? ");

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    amount = Integer.parseInt(textField1.getText());
                    if(amount < 0 ){
                        textField1.setText("");
                        JOptionPane.showMessageDialog(null, "Please enter a positive number");
                    }
                } catch (NumberFormatException ex) {
                    textField1.setText("");
                    JOptionPane.showMessageDialog(null, "Invalid amount");
                }
                if (amount > 0) {
                    label1.setText("Please Select an Account.");
                    textField1.setEnabled(false);
                    checkButton.setVisible(false);
                    comboBox1.setVisible(true);
                    selectButton.setVisible(true);
                }
            }
        });

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                selectedItem = (String)cb.getSelectedItem();
            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(selectedItem.equals("[Select One]"))) {
                    fromAccount = user.getAccountFromId(selectedItem);
                    comboBox1.setEnabled(false);
                    selectButton.setVisible(false);
                    comboBox2.setVisible(true);
                    selectButton1.setVisible(true);
                    label1.setText("Please select a type of transaction");
                }
            }
        });
        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                selectedItem1 = (String)cb.getSelectedItem();
            }
        });
        selectButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = selectedItem1.substring(0,1);
                switch(type) {
                    case "[": break;
                    case "1":
                        new TransferMoney(mainframe, amount, fromAccount, user);
                        break;

                    case "2":
                            boolean check = BankDriver.bank.cashMoney(-1 * amount, fromAccount);
                            if (!check) {
                                JOptionPane.showMessageDialog(null, "Invalid Request.");
                            }
                            else {
                                label1.setText("Successful transaction!");
                            }
                            break;
                    case "3":
                        boolean check1 = BankDriver.bank.payBill(amount, fromAccount);
                        if (!check1) {
                            JOptionPane.showMessageDialog(null, "Invalid Request.");
                        }
                        else {
                            label1.setText("Successful transaction!");
                        }
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Unrecognized request");
                        break;
                }
            }
        });
        mainMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new CustomerGUI(mainframe, user);
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RequestTransaction(mainframe, user);
            }
        });
    }
}
