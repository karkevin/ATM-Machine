package gui.AdminUI;

import UserTypes.Administrator;
import atm.BankDriver;
import UserTypes.BankManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CreateUser {
    private JButton Create;
    private JTextField username;
    private JPasswordField password;
    private JPanel panel1;
    private JLabel taken;
    private JButton mainMenuButton;
    private JLabel label1;

    public CreateUser(JFrame mainframe, Administrator admin, String req) {
        mainframe.setContentPane(panel1);
        mainframe.setSize(500,500);
        mainframe.setVisible(true);
        if(req.equals("Employee")) {
            label1.setText("Create new Bank Employee.");
        }
        else {
            label1.setText("Create new Bank User.");
        }
        Create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = username.getText();
                if(BankDriver.bank.getManager().getLogin().equals(name) || BankDriver.bank.getUsers().containsKey(name)) {
                    taken.setText("name taken!");
                } else if(req.equals("Employee")){
                    ((BankManager) admin).createUser(
                            username.getText(),
                            new String(password.getPassword()),
                            true
                    );
                    JOptionPane.showMessageDialog(null, "Employee Added!");
                    new AdministratorGUI(mainframe, admin);
                } else{
                    admin.createUser(
                            username.getText(),
                            new String(password.getPassword())
                    );
                    JOptionPane.showMessageDialog(null, "User Created!");
                    admin.removeLine(req);
                    new AdministratorGUI(mainframe, admin);
                }
            }
        });
        mainMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new AdministratorGUI(mainframe, admin);
            }
        });
    }
}
