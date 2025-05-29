package Hospital.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TreatmentPanel extends JFrame {

    TreatmentPanel() {
        setTitle("Treatment Panel");

        // Load and set background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/treat.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        img.setBounds(0, 0, 1120, 630);
        add(img);

        // Heading
        JLabel heading = new JLabel("Treatment Panel");
        heading.setBounds(580, 135, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        img.add(heading);

        // ASSIGN TREATMENT button
        JButton add = new JButton("ASSIGN TREATMENT");
        add.setBounds(485, 270, 180, 40);
        add.setForeground(Color.WHITE);
        add.setBackground(new Color(13, 51, 52));
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddTreatment();
                setVisible(false);
            }
        });
        img.add(add);

        // VIEW TREATMENT button
        JButton view = new JButton("VIEW TREATMENT");
        view.setBounds(715, 270, 150, 40);
        view.setForeground(Color.WHITE);
        view.setBackground(new Color(13, 51, 52));
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewTreatment();
                setVisible(false);
            }
        });
        img.add(view);

        // REMOVE TREATMENT button
        JButton rem = new JButton("REMOVE TREATMENT");
        rem.setBounds(600, 370, 180, 40);
        rem.setForeground(Color.WHITE);
        rem.setBackground(new Color(13, 51, 52));
        rem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RemoveTreatment();
            }
        });
        img.add(rem);


        JButton backButton = new JButton("BACK");
        backButton.setBounds(825, 500, 180, 40); // just below remove
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(36, 84, 120));
        backButton.setFont(new Font("Tahoma", Font.BOLD, 13));
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();         // Close this panel
                new Dashboard();   // Open Dashboard
            }
        });
        img.add(backButton);

        // Frame settings
        setSize(1120, 630);
        setLocation(250, 100);
        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TreatmentPanel();
    }
}
