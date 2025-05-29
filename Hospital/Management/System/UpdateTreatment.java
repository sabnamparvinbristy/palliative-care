package Hospital.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateTreatment extends JFrame implements ActionListener {

    JTextField treatmentID, doctorID, medications, outcome;
    JButton searchBtn, saveBtn, backBtn;

    public UpdateTreatment() {
        setTitle("Update Treatment Details");
        getContentPane().setBackground(new Color(214, 245, 240));
        setLayout(null);

        JLabel heading = new JLabel("UPDATE TREATMENT DETAILS");
        heading.setBounds(280, 30, 500, 50);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        JLabel idLabel = new JLabel("Enter Treatment ID:");
        idLabel.setBounds(50, 100, 200, 30);
        idLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(idLabel);

        treatmentID = new JTextField();
        treatmentID.setBounds(250, 100, 150, 30);
        treatmentID.setBackground(new Color(240, 233, 215));
        add(treatmentID);

        searchBtn = new JButton("Search");
        searchBtn.setBounds(420, 100, 100, 30);
        searchBtn.setBackground(Color.BLACK);
        searchBtn.setForeground(Color.cyan);
        searchBtn.addActionListener(this);
        add(searchBtn);

        JLabel doctorLabel = new JLabel("Doctor ID:");
        doctorLabel.setBounds(50, 160, 200, 30);
        doctorLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(doctorLabel);

        doctorID = new JTextField();
        doctorID.setBounds(250, 160, 150, 30);
        doctorID.setBackground(new Color(240, 233, 215));
        add(doctorID);

        JLabel medicationsLabel = new JLabel("Medications:");
        medicationsLabel.setBounds(50, 210, 200, 30);
        medicationsLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(medicationsLabel);

        medications = new JTextField();
        medications.setBounds(250, 210, 150, 30);
        medications.setBackground(new Color(240, 233, 215));
        add(medications);

        JLabel outcomeLabel = new JLabel("Outcome:");
        outcomeLabel.setBounds(50, 260, 200, 30);
        outcomeLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(outcomeLabel);

        outcome = new JTextField();
        outcome.setBounds(250, 260, 150, 30);
        outcome.setBackground(new Color(240, 233, 215));
        add(outcome);

        saveBtn = new JButton("Save");
        saveBtn.setBounds(250, 330, 150, 35);
        saveBtn.setBackground(Color.BLACK);
        saveBtn.setForeground(Color.cyan);
        saveBtn.addActionListener(this);
        add(saveBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(420, 330, 150, 35);
        backBtn.setBackground(Color.BLACK);
        backBtn.setForeground(Color.cyan);
        backBtn.addActionListener(this);
        add(backBtn);

        setSize(800, 450);
        setLocation(300, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBtn) {
            String idText = treatmentID.getText().trim();
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter Treatment ID");
                return;
            }

            try {
                Conn c = new Conn();
                String query = "SELECT * FROM treatment WHERE treatment_id = ?";
                PreparedStatement ps = c.connection.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(idText));
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    doctorID.setText(rs.getString("doctor_id"));
                    medications.setText(rs.getString("medications"));
                    outcome.setText(rs.getString("outcome"));
                } else {
                    JOptionPane.showMessageDialog(null, "Treatment not found with ID: " + idText);
                }

                rs.close();
                ps.close();
                c.connection.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error retrieving treatment.");
            }
        } else if (e.getSource() == saveBtn) {
            String idText = treatmentID.getText().trim();
            String doctorText = doctorID.getText().trim();
            String medsText = medications.getText().trim();
            String outcomeText = outcome.getText().trim();

            if (doctorText.isEmpty() || medsText.isEmpty() || outcomeText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields");
                return;
            }

            try {
                Conn c = new Conn();
                String query = "UPDATE treatment SET doctor_id = ?, medications = ?, outcome = ? WHERE treatment_id = ?";
                PreparedStatement ps = c.connection.prepareStatement(query);
                ps.setString(1, doctorText);
                ps.setString(2, medsText);
                ps.setString(3, outcomeText);
                ps.setInt(4, Integer.parseInt(idText));

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Treatment details updated successfully!");
                    setVisible(false);
                    new ViewTreatment();
                } else {
                    JOptionPane.showMessageDialog(null, "No update made. Check the ID.");
                }

                ps.close();
                c.connection.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating treatment.");
            }
        } else if (e.getSource() == backBtn) {
            setVisible(false);
            new ViewTreatment();
        }
    }

    public static void main(String[] args) {
        new UpdateTreatment();
    }
}