package Hospital.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RemovePatient extends JFrame implements ActionListener {

    JButton removeBtn, backBtn;
    JTextField patientIDField;
    JLabel patientIDLabel, patientNameLabel, patientDetailsLabel;

    public RemovePatient() {
        setTitle("Remove Patient");
        getContentPane().setBackground(new Color(214, 245, 240));
        setLayout(null);

        JLabel heading = new JLabel("REMOVE PATIENT");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        JLabel idLabel = new JLabel("Patient ID: ");
        idLabel.setBounds(50, 100, 150, 30);
        idLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(idLabel);

        patientIDField = new JTextField();
        patientIDField.setBounds(200, 100, 150, 30);
        patientIDField.setBackground(new Color(240, 233, 215));
        add(patientIDField);

        patientIDField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchPatient();
                }
            }
        });

        patientIDLabel = new JLabel("Patient ID: ");
        patientIDLabel.setBounds(50, 150, 300, 30);
        patientIDLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(patientIDLabel);

        patientNameLabel = new JLabel("Name: ");
        patientNameLabel.setBounds(50, 200, 300, 30);
        patientNameLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(patientNameLabel);

        patientDetailsLabel = new JLabel("Details: ");
        patientDetailsLabel.setBounds(50, 250, 500, 30);
        patientDetailsLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(patientDetailsLabel);

        removeBtn = new JButton("Remove");
        removeBtn.setBounds(250, 300, 150, 35);
        removeBtn.setBackground(Color.BLACK);
        removeBtn.setForeground(Color.cyan);
        removeBtn.addActionListener(this);
        removeBtn.setEnabled(false);
        add(removeBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(450, 300, 150, 35);
        backBtn.setBackground(Color.BLACK);
        backBtn.setForeground(Color.cyan);
        backBtn.addActionListener(this);
        add(backBtn);

        setSize(900, 400);
        setLocation(300, 50);
        setVisible(true);
    }

    private void searchPatient() {
        String patientID = patientIDField.getText().trim();
        if (patientID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Patient ID.");
            return;
        }
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM patients WHERE patient_id = ?";
            PreparedStatement pst = c.connection.prepareStatement(query);
            pst.setString(1, patientID);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                patientIDLabel.setText("Patient ID: " + rs.getString("patient_id"));
                patientNameLabel.setText("Name: " + rs.getString("full_name"));
                patientDetailsLabel.setText("Details: " + rs.getString("category"));
                removeBtn.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Patient not found.");
                patientIDLabel.setText("Patient ID: ");
                patientNameLabel.setText("Name: ");
                patientDetailsLabel.setText("Details: ");
                removeBtn.setEnabled(false);
            }

            rs.close();
            pst.close();
            c.statement.close();
            c.connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String patientID = patientIDField.getText().trim();

        if (e.getSource() == removeBtn) {
            try {
                Conn c = new Conn();

                String[] relatedTables = {
                        "treatment", "medical_records", "medications", "billing",
                        "emergency_details", "appointments"
                };

                for (String table : relatedTables) {
                    PreparedStatement pst = c.connection.prepareStatement("DELETE FROM " + table + " WHERE patient_id = ?");
                    pst.setString(1, patientID);
                    pst.executeUpdate();
                    pst.close();
                }

                PreparedStatement pst = c.connection.prepareStatement("DELETE FROM patients WHERE patient_id = ?");
                pst.setString(1, patientID);
                int affected = pst.executeUpdate();
                pst.close();

                c.statement.close();
                c.connection.close();

                if (affected > 0) {
                    JOptionPane.showMessageDialog(this, "Patient removed successfully.");
                    setVisible(false);
                    new ViewPatient();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to remove patient.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error while removing patient.");
            }
        } else if (e.getSource() == backBtn) {
            setVisible(false);
            new ViewPatient();
        }
    }

    public static void main(String[] args) {
        new RemovePatient();
    }
}
