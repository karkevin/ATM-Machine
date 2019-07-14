package gui.CustomerUI;

import javax.swing.*;

import UserTypes.User;
import atm.*;
import accounts.Account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TransferMoney {
    private JPanel panel1;
    private JTextField textField1;
    private JButton selectButton;
    private JComboBox comboBox1;
    private JButton selectButton1;
    private JButton mainMenuButton;
    private JLabel l;
    private JFrame mainframe;
    private String selectedItem = "[Select One]";
    private User target;
    private Account toAccount;

    public TransferMoney(JFrame mainframe, int amount, Account fromAccount, User user) {
        this.mainframe = mainframe;
        mainframe.setContentPane(panel1);
        mainframe.setSize(500, 500);
        mainframe.setVisible(true);

        selectButton1.setVisible(false);
        comboBox1.setVisible(false);

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String targetUser = textField1.getText();
                if(BankDriver.bank.getUsers().containsKey(targetUser)){
                    comboBox1.removeAllItems();
                    comboBox1.addItem("[Select One]");
                    target = BankDriver.bank.getUsers().get(targetUser);
                    for (String id : target.getIDs()) {
                        comboBox1.addItem(id);
                    }
                    comboBox1.setVisible(true);
                    selectButton1.setVisible(true);
                } else {
                    textField1.setText("");
                    JOptionPane.showMessageDialog(null, "Invalid User");
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
        selectButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(selectedItem.equals("[Select One]"))) {
                    toAccount = target.getAccountFromId(selectedItem);
                    boolean check = BankDriver.bank.transferMoney(amount, fromAccount, toAccount);
                    if (!check) {
                        JOptionPane.showMessageDialog(null, "Invalid Request.");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Successful Transaction!");
                        new CustomerGUI(mainframe, user);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Select an Account");
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
    }
}
