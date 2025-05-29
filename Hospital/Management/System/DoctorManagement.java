package Hospital.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoctorManagement extends JFrame {

    DoctorManagement() {
        setTitle("Doctor Management Panel");

        // Background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/docman.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        img.setBounds(0, 0, 1120, 630);
        add(img);

        // Heading label
        JLabel heading = new JLabel("Doctor Management Panel");
        heading.setBounds(520, 135, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        img.add(heading);

        // ADD DOCTOR button
        JButton add = new JButton("ADD DOCTOR");
        add.setBounds(485, 270, 150, 40);
        add.setForeground(Color.WHITE);
        add.setBackground(new Color(13, 51, 52));
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddDoctor();
                setVisible(false);
            }
        });
        img.add(add);

        // VIEW DOCTOR button
        JButton view = new JButton("VIEW DOCTOR");
        view.setBounds(715, 270, 150, 40);
        view.setForeground(Color.WHITE);
        view.setBackground(new Color(13, 51, 52));
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewDoctor();
                setVisible(false);
            }
        });
        img.add(view);

        // REMOVE DOCTOR button
        JButton rem = new JButton("REMOVE DOCTOR");
        rem.setBounds(600, 370, 150, 40);
        rem.setForeground(Color.WHITE);
        rem.setBackground(new Color(13, 51, 52));
        rem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RemoveDoctor();
            }
        });
        img.add(rem);

        // BACK button (below REMOVE DOCTOR)
        JButton back = new JButton("BACK");
        back.setBounds(600, 430, 150, 40); // Just below "REMOVE DOCTOR"
        back.setForeground(Color.WHITE);
        back.setBackground(new Color(13, 51, 52));
        back.setFont(new Font("Tahoma", Font.BOLD, 13));
        back.setFocusPainted(false);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();         // Close this panel
                new Dashboard();   // Go back to dashboard
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
        new DoctorManagement();
    }
}
