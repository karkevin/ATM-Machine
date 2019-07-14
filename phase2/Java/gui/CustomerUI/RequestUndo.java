package gui.CustomerUI;

import UserTypes.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RequestUndo {
    private JSpinner spinner1;
    private JPanel panel1;
    private JComboBox comboBox1;
    private JButton requestButton;
    private JButton mainMenuButton;
    private String selectedItem = "[Select One]";

    public RequestUndo(JFrame mainframe, User user) {
        mainframe.setContentPane(panel1);
        mainframe.setSize(500, 500);
        mainframe.setVisible(true);
        spinner1.setModel(new SpinnerNumberModel());
        for (String id : user.getIDs()) {
            comboBox1.addItem(id);
        }
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                selectedItem = (String)cb.getSelectedItem();
            }
        });
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerGUI(mainframe, user);
            }
        });
        requestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((int)spinner1.getValue() < 1){
                    JOptionPane.showMessageDialog(null, "Please enter a valid amount.");
                } else if(!selectedItem.equals("[Select One]")){
                    try {
                        user.undoRequest(selectedItem, (int)spinner1.getValue());
                        new CustomerGUI(mainframe, user);
                        JOptionPane.showMessageDialog(null,
                                "Request successfully sent.");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null,
                                "Failed to send request. Please try again.");
                    }
                }
            }
        });
    }
}
