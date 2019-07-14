package gui.AdminUI;

import UserTypes.Administrator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class CheckRequests {
    private JComboBox comboBox1;
    private JPanel panel1;
    private JButton button1;
    private JButton mainMenuButton;
    private JButton clearButton;
    private String selectedItem = "[Select One]";
    private Administrator admin;

    private void reReadCombo(){
        comboBox1.removeAllItems();

        try {
            comboBox1.addItem("[Select One]");
            for (String req: admin.checkRequest()) {
                comboBox1.addItem(req);
            }
        } catch (IOException e){}

    }

    public CheckRequests(JFrame mainframe, Administrator admin){
        this.admin = admin;
        mainframe.setContentPane(panel1);
        mainframe.setSize(500,500);
        reReadCombo();

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                selectedItem = (String)cb.getSelectedItem();

            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedItem.equals("[Select One]")){
                    JOptionPane.showMessageDialog(null, "Please select a request.");
                } else if(selectedItem.substring(0, 1).equals("0")){
                    new CreateUser(mainframe, admin, selectedItem);
                } else {
                    admin.parseRequest(selectedItem);
                    reReadCombo();
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
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!selectedItem.equals("[Select One]")){
                    admin.removeLine(selectedItem);
                    reReadCombo();
                }
            }
        });
    }


}
