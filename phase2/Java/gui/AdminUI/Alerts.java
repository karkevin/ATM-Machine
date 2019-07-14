package gui.AdminUI;

import UserTypes.Administrator;
import UserTypes.BankManager;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Alerts {
    private JPanel panel1;
    private JComboBox comboBox1;
    private JButton mainMenuButton;
    private JTextArea textArea1;
    private JScrollPane scrollPane;
    private JButton clearButton;

    public Alerts(JFrame mainframe, Administrator admin) {
        mainframe.setContentPane(panel1);
        mainframe.setSize(500,500);
        mainframe.setVisible(true);
        try {
            for (String al: admin.checkAlerts()) {
                if (al != null) {
                    textArea1.append(al + "\n");
                }
            }
        } catch (IOException e){}

        mainMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new AdministratorGUI(mainframe, admin);
            }
        });

        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                admin.clearAlerts();
                textArea1.setText("");
            }
        });
    }
}
