package Hospital.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;

public class RemoveDoctor extends JFrame implements ActionListener {

    JTextField docIdField;
    JLabel txt_name, txt_phn, txt_email;
    JButton delete, back;

    RemoveDoctor() {

        setTitle("Remove Doctor Panel");

        JLabel label = new JLabel("Doctor ID: ");
        label.setBounds(50, 50, 100, 30);
        label.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(label);

        docIdField = new JTextField();
        docIdField.setBounds(200, 50, 150, 30);
        add(docIdField);

        docIdField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    fetchDoctorDetails(docIdField.getText().trim());
                }
            }
        });

        JLabel name = new JLabel("Name");
        name.setBounds(50, 100, 100, 30);
        name.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(name);

        txt_name = new JLabel();
        txt_name.setBounds(200, 100, 200, 30);
        add(txt_name);

        JLabel phn = new JLabel("Phone");
        phn.setBounds(50, 150, 100, 30);
        phn.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(phn);

        txt_phn = new JLabel();
        txt_phn.setBounds(200, 150, 200, 30);
        add(txt_phn);

        JLabel mail = new JLabel("E-mail");
        mail.setBounds(50, 200, 100, 30);
        mail.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(mail);

        txt_email = new JLabel();
        txt_email.setBounds(200, 200, 200, 30);
        add(txt_email);

        delete = new JButton("Delete");
        delete.setBounds(80, 300, 100, 30);
        delete.setBackground(Color.BLACK);
        delete.setForeground(Color.WHITE);
        delete.addActionListener(this);
        add(delete);

        back = new JButton("Back");
        back.setBounds(220, 300, 100, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.white);
        back.addActionListener(this);
        add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/rem.png"));
        Image i2 = i1.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        img.setBounds(700, 80, 200, 200);
        add(img);

        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("icons/rembg.jpg"));
        Image i22 = i11.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i33 = new ImageIcon(i22);
        JLabel img1 = new JLabel(i33);
        img1.setBounds(0, 0, 1120, 630);
        add(img1);

        setSize(1000, 400);
        setLocation(300, 150);
        setLayout(null);
        setVisible(true);
    }

    private void fetchDoctorDetails(String docId) {
        try {
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("select * from doctors where doc_id = '" + docId + "'");
            if (resultSet.next()) {
                txt_name.setText(resultSet.getString("name"));
                txt_email.setText(resultSet.getString("email"));
                txt_phn.setText(resultSet.getString("contact_no"));
            } else {
                txt_name.setText("");
                txt_email.setText("");
                txt_phn.setText("");
                JOptionPane.showMessageDialog(null, "No doctor found with ID: " + docId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delete) {
            try {
                Conn c = new Conn();
                String docId = docIdField.getText().trim();
                String query = "delete from doctors where doc_id = '" + docId + "'";
                int result = c.statement.executeUpdate(query);
                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Doctor Record Deleted Successfully!");
                    setVisible(false);
                    new Dashboard();
                } else {
                    JOptionPane.showMessageDialog(null, "No doctor found to delete with ID: " + docId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == back) {
            setVisible(false);
            new DoctorManagement();
        }
    }

    public static void main(String[] args) {
        new RemoveDoctor();
    }
}
