package Hospital.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Report extends JFrame {

    Report() {
        setTitle("Report Panel");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/treat.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        img.setBounds(0, 0, 1120, 630);
        add(img);

        JLabel heading = new JLabel("Report Panel");
        heading.setBounds(580, 135, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        img.add(heading);

        JButton add = new JButton("ADD REPORT");
        add.setBounds(485, 270, 150, 40);
        add.setForeground(Color.WHITE);
        add.setBackground(new Color(13, 51, 52));
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddReport();
                setVisible(false);
            }
        });
        img.add(add);

        JButton view = new JButton("VIEW REPORT");
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

        JButton back = new JButton("BACK");
        back.setBounds(600, 370, 180, 40);
        back.setForeground(Color.WHITE);
        back.setBackground(new Color(13, 51, 52));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Define what happens when Back is clicked
                // For example, go back to main dashboard or previous screen
                dispose(); // close current window
                new Dashboard(); // or any other class for main menu
            }
        });
        img.add(back);

        setSize(1120, 630);
        setLocation(250, 100);
        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Report();
    }
}
