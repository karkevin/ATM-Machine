package gui.AdminUI;

import atm.BankDriver;
import UserTypes.BankManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SetTime {
    private JSpinner Time;
    private JButton button1;
    private JPanel panel1;
    private JButton mainMenuButton;


    public SetTime(JFrame mainframe, BankManager manager) {
        BankManager bm = BankDriver.bank.getManager();
        mainframe.setContentPane(panel1);
        mainframe.setSize(500,500);
        mainframe.setVisible(true);
        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH mm");

        SpinnerDateModel yearModel = new SpinnerDateModel();
        Time.setModel(yearModel);
        Time.setValue(BankDriver.bank.getTime().getTime());

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                bm.setTime(format.format(Time.getValue()));
                Time.setValue(BankDriver.bank.getTime().getTime());
                new AdministratorGUI(mainframe, manager);
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
