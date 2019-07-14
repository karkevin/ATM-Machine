package gui.AdminUI;

import UserTypes.BankEmployee;
import gui.CustomerUI.CustomerGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeGUI {
    private JPanel panel1;
    private JButton customerButton;
    private JButton administratorButton;

    public EmployeeGUI(JFrame mainframe, BankEmployee employee) {
        mainframe.setContentPane(panel1);
        mainframe.setSize(500,500);
        mainframe.setVisible(true);
        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerGUI(mainframe, employee);
            }
        });
        administratorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdministratorGUI(mainframe, employee);
            }
        });
    }
}
