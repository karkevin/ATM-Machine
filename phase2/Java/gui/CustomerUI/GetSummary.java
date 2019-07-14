package gui.CustomerUI;

import javax.swing.*;

import UserTypes.User;
import accounts.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GetSummary {
    private JPanel panel1;
    private JButton selectButton;
    private JButton mainMenuButton;
    private JComboBox comboBox1;
    private JLabel label1;
    private JFrame mainframe;
    private String selectedItem = "[Select One]";

    public GetSummary(JFrame mainframe, User user) {
        this.mainframe = mainframe;
        mainframe.setContentPane(panel1);
        mainframe.setSize(500, 500);
        mainframe.setVisible(true);

        comboBox1.addItem(selectedItem);
        for (String id : user.getIDs()) {
            comboBox1.addItem(id);
        }
        label1.setText("Select an Account...");
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
                    Account acc = user.getAccountFromId(selectedItem);
                    String lastTransaction = "" + acc.getRecentTransaction();
                    if (lastTransaction.equals("null")){
                        lastTransaction = "N/A";
                    }
                    String lastUndoneTransaction = "" + acc.getUndoneTransaction();
                    if(lastUndoneTransaction.equals("null")){
                        lastUndoneTransaction = "N/A";
                    }
                    String message = "Balance: $" + acc.viewBalance() + "\n" +
                            "Date of Creation: " + acc.getDateCreated() + "\n" +
                            "Most Recent Transaction: " + lastTransaction + "\n" +
                            "Most Recent Undone Transaction: " + lastUndoneTransaction;
                    JOptionPane.showMessageDialog(null, message);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please Select an Account.");
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
