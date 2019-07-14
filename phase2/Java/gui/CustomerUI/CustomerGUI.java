package gui.CustomerUI;

import UserTypes.User;
import accounts.Account;
import gui.ChangePassword;
import gui.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomerGUI {
    private JComboBox comboBox1;
    private JButton selectButton;
    private JButton logoutButton;
    private JPanel panel1;
    private JLabel welcome;
    private JFrame mainframe;
    private String selectedOption = "[Select One]";
    private User user;


    private void optionSelect(User user){
        switch (selectedOption){
            case "Get Summary": new GetSummary(mainframe, user);
                break;
            case "Net Total":
                JOptionPane.showMessageDialog(null, "Your net total is: " + user.netTotal());
                break;
            case "Request to Open an Account":
                if (user.hasSuspended()){
                    JOptionPane.showMessageDialog(null,
                            "Cannot request while an account is suspended. \n" +
                            "Please try again once the issue has been resolved.");
                    return;
                }
                new RequestAccount(mainframe, user);
                break;
            case "Make a Transaction": new RequestTransaction(mainframe, user);
                break;
            case "Request Transaction Undo": new RequestUndo(mainframe, user);
                break;
            case "Change Password": new ChangePassword(mainframe, user);
                break;
        }
    }


    public CustomerGUI(JFrame mainframe, User user) {
        this.mainframe = mainframe;
        this.user = user;
        mainframe.setContentPane(panel1);
        mainframe.setSize(500, 500);
        mainframe.setVisible(true);

        if(user.hasSuspended()){
            ArrayList<Account> sus = user.getSuspended();
            String welcomeText = "Welcome. The accounts: " + sus.get(0);
            for(int i = 1; i < sus.size(); i++){
                if(i == sus.size() - 1){
                    welcomeText += ", and ";
                } else {
                    welcomeText += ", ";
                }
                welcomeText += sus.get(i);
            }
            welcomeText += " are suspended";
            welcome.setText(welcomeText);

        }

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(null, selectedOption);
                optionSelect(user);
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                selectedOption = (String)cb.getSelectedItem();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login(mainframe);
            }
        });
    }
}
