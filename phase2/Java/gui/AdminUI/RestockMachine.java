package gui.AdminUI;
import atm.BankDriver;
import UserTypes.BankManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RestockMachine {
    private JPanel panel1;
    private JTextField amountTextField;
    private JButton submitButton;
    private JComboBox comboBox1;
    private JButton mainMenuButton;
    private JFrame mainframe;
    private String selectedOption = "[Select One]";

    public RestockMachine(JFrame mainframe, BankManager manager) {
        this.mainframe = mainframe;
        mainframe.setContentPane(panel1);
        mainframe.setSize(500, 500);
        mainframe.setVisible(true);

        amountTextField.setText("Amount...");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String amount = amountTextField.getText();
                    int amt = Integer.parseInt(amount);
                    int type = Integer.parseInt(selectedOption);
                    BankDriver.bank.getCashMachine().addCash(type, amt);
                    //manager.increaseCashInMachine(type, amt);
                    amountTextField.setText("");
                }
                catch (NumberFormatException ex){
                    amountTextField.setText("Invalid amount. Please try again.");
                }
            }
        });

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                selectedOption = (String)cb.getSelectedItem();
            }
        });

        amountTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                amountTextField.setText("");
            }
        });

        mainMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new AdministratorGUI(mainframe, manager);
            }
        });
    }
}
