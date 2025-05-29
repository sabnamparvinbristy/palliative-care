package Hospital.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PatientManagement extends JFrame {

    PatientManagement() {
        setTitle("Patient Management Panel");

        // Background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/pats.png")); // Use your actual image file
        Image i2 = i1.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        img.setBounds(0, 0, 1120, 630);
        add(img);

        // Heading label
        JLabel heading = new JLabel("Patient Management Panel");
        heading.setBounds(520, 135, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        img.add(heading);

        // ADD PATIENT button
        JButton add = new JButton("ADD PATIENT");
        add.setBounds(485, 270, 150, 40);
        add.setForeground(Color.WHITE);
        add.setBackground(new Color(13, 51, 52));
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddPatient();
                setVisible(false);
            }
        });
        img.add(add);

        // VIEW PATIENT button
        JButton view = new JButton("VIEW PATIENT");
        view.setBounds(715, 270, 150, 40);
        view.setForeground(Color.WHITE);
        view.setBackground(new Color(13, 51, 52));
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewPatient();  // Create ViewPatient class to show patient list
                setVisible(false);
            }
        });
        img.add(view);

        // REMOVE PATIENT button
        JButton rem = new JButton("REMOVE PATIENT");
        rem.setBounds(600, 370, 150, 40);
        rem.setForeground(Color.WHITE);
        rem.setBackground(new Color(13, 51, 52));
        rem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RemovePatient();  // Create RemovePatient class with removal logic
            }
        });
        img.add(rem);

        // BACK button
        JButton back = new JButton("BACK");
        back.setBounds(825, 500, 180, 40); // just below remove
        back.setForeground(Color.WHITE);
        back.setBackground(new Color(36, 84, 120));
        back.setFont(new Font("Tahoma", Font.BOLD, 13));
        back.setFocusPainted(false);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Dashboard();
            }
        });
        img.add(back);

        // Frame settings
        setSize(1120, 630);
        setLocation(250, 100);
        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PatientManagement();
    }
}
