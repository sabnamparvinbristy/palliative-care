package Hospital.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PharmacyPanel extends JFrame {

    PharmacyPanel() {
        setTitle("Pharmacy Panel");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/medibg.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        img.setBounds(0, 0, 1120, 630);
        add(img);

        JLabel heading = new JLabel("Pharmacy Panel");
        heading.setBounds(600, 135, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        img.add(heading);

        // ADD MEDICINE button
        JButton add = new JButton("ADD MEDICINE");
        add.setBounds(485, 270, 150, 40);
        add.setForeground(Color.WHITE);
        add.setBackground(new Color(13, 51, 52));
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddMedicine();
                setVisible(false);
            }
        });
        img.add(add);

        // VIEW MEDICINE button
        JButton view = new JButton("VIEW MEDICINE");
        view.setBounds(715, 270, 150, 40);
        view.setForeground(Color.WHITE);
        view.setBackground(new Color(13, 51, 52));
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewMedicine();
                setVisible(false);
            }
        });
        img.add(view);

        // REMOVE MEDICINE button
        JButton rem = new JButton("REMOVE MEDICINE");
        rem.setBounds(600, 370, 150, 40);
        rem.setForeground(Color.WHITE);
        rem.setBackground(new Color(13, 51, 52));
        rem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RemoveMedicine();
            }
        });
        img.add(rem);

        // BACK button
        JButton back = new JButton("BACK");
        back.setBounds(600, 430, 150, 40); // Below REMOVE MEDICINE
        back.setForeground(Color.WHITE);
        back.setBackground(new Color(13, 51, 52));
        back.setFocusPainted(false);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();         // Close this panel
                new Dashboard();   // Open dashboard
            }
        });
        img.add(back);

        setSize(1120, 630);
        setLocation(250, 100);
        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PharmacyPanel();
    }
}
