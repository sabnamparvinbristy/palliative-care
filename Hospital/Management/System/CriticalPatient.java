package Hospital.Management.System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CriticalPatient extends JFrame implements ActionListener {

    JTextField patientID, patientName;
    JComboBox<String> criticalStatus, ventilatorStatus, lifeSupportStatus;
    JButton btnSave, btnClear, btnExit, btnViewEmergencyDetails;
    String patientIdToUpdate;

    private CriticalPatientDetails detailsWindow = null;

    public CriticalPatient() {
        setTitle("CRITICAL PATIENT FORM");
        getContentPane().setBackground(new Color(214, 245, 240));
        setLayout(null);

        JLabel heading = new JLabel("CRITICAL PATIENT FORM");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        JLabel patientIdLabel = new JLabel("Patient ID: ");
        patientIdLabel.setBounds(50, 100, 150, 30);
        patientIdLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(patientIdLabel);

        patientID = new JTextField();
        patientID.setBounds(200, 100, 150, 30);
        patientID.setBackground(new Color(240, 233, 215));
        patientID.setForeground(new Color(0, 0, 0));
        patientID.setEditable(true); // Make editable
        patientID.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(patientID);

        // Pressing ENTER will trigger data loading
        patientID.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String pid = patientID.getText().trim();
                    if (!pid.isEmpty()) {
                        loadCriticalDetails(pid);
                    }
                }
            }
        });

        JLabel patientNameLabel = new JLabel("Patient Name: ");
        patientNameLabel.setBounds(50, 150, 150, 30);
        patientNameLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(patientNameLabel);

        patientName = new JTextField();
        patientName.setBounds(200, 150, 300, 30);
        patientName.setBackground(new Color(240, 233, 215));
        patientName.setForeground(new Color(0, 0, 0));
        patientName.setEditable(false);
        patientName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(patientName);

        JLabel criticalStatusLabel = new JLabel("Critical Status: ");
        criticalStatusLabel.setBounds(50, 200, 150, 30);
        criticalStatusLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(criticalStatusLabel);

        criticalStatus = new JComboBox<>(new String[]{"Critical", "Stable", "Emergency"});
        criticalStatus.setBackground(new Color(240, 233, 215));
        criticalStatus.setBounds(200, 200, 150, 30);
        add(criticalStatus);

        JLabel ventilatorStatusLabel = new JLabel("Ventilator Status: ");
        ventilatorStatusLabel.setBounds(50, 250, 150, 30);
        ventilatorStatusLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(ventilatorStatusLabel);

        ventilatorStatus = new JComboBox<>(new String[]{"On", "Off", "In Use", "Not In Use", "Unavailable", "Not Applicable"});
        ventilatorStatus.setBackground(new Color(240, 233, 215));
        ventilatorStatus.setBounds(200, 250, 150, 30);
        add(ventilatorStatus);

        JLabel lifeSupportStatusLabel = new JLabel("Life Support Status: ");
        lifeSupportStatusLabel.setBounds(50, 300, 180, 30);
        lifeSupportStatusLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(lifeSupportStatusLabel);

        lifeSupportStatus = new JComboBox<>(new String[]{"On", "Off", "Required", "Not Required", "Unknown"});
        lifeSupportStatus.setBackground(new Color(240, 233, 215));
        lifeSupportStatus.setBounds(230, 300, 150, 30);
        add(lifeSupportStatus);

        JButton backButton = new JButton("BACK");
        backButton.setBounds(50, 450, 150, 35);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.CYAN);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            dispose();
            new Dashboard();
        });
        add(backButton);

        btnSave = new JButton("Save");
        btnSave.setBounds(250, 450, 150, 35);
        btnSave.setBackground(Color.BLACK);
        btnSave.setForeground(Color.cyan);
        btnSave.addActionListener(this);
        add(btnSave);

        btnClear = new JButton("Clear");
        btnClear.setBounds(450, 450, 150, 35);
        btnClear.setBackground(Color.BLACK);
        btnClear.setForeground(Color.cyan);
        btnClear.addActionListener(this);
        add(btnClear);

        btnExit = new JButton("Exit");
        btnExit.setBounds(650, 450, 150, 35);
        btnExit.setBackground(Color.BLACK);
        btnExit.setForeground(Color.cyan);
        btnExit.addActionListener(this);
        add(btnExit);

        btnViewEmergencyDetails = new JButton("View Critical Patient List");
        btnViewEmergencyDetails.setBounds(330, 510, 250, 35);
        btnViewEmergencyDetails.setBackground(Color.BLACK);
        btnViewEmergencyDetails.setForeground(Color.cyan);
        btnViewEmergencyDetails.addActionListener(this);
        add(btnViewEmergencyDetails);

        setSize(900, 600);
        setLocation(300, 50);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public CriticalPatient(String patientId) {
        this();
        if (patientId != null && !patientId.isEmpty()) {
            patientID.setText(patientId);
            loadCriticalDetails(patientId);
        }
    }

    private void loadCriticalDetails(String patientId) {
        Conn c = new Conn();
        try {
            String nameQuery = "SELECT full_name FROM patients WHERE patient_id = ?";
            PreparedStatement pstName = c.connection.prepareStatement(nameQuery);
            pstName.setString(1, patientId);
            ResultSet rsName = pstName.executeQuery();
            if (rsName.next()) {
                patientName.setText(rsName.getString("full_name"));
            }
            rsName.close();
            pstName.close();

            String query = "SELECT critical_status, ventilator_status, life_support_status FROM emergency_details WHERE patient_id = ?";
            PreparedStatement pst = c.connection.prepareStatement(query);
            pst.setString(1, patientId);
            ResultSet rs = pst.executeQuery();

            criticalStatus.setSelectedIndex(0);
            ventilatorStatus.setSelectedIndex(0);
            lifeSupportStatus.setSelectedIndex(0);

            if (rs.next()) {
                criticalStatus.setSelectedItem(rs.getString("critical_status"));
                ventilatorStatus.setSelectedItem(rs.getString("ventilator_status"));
                lifeSupportStatus.setSelectedItem(rs.getString("life_support_status"));
            }
            patientIdToUpdate = patientId;

            rs.close();
            pst.close();
            c.statement.close();
            c.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            if (patientIdToUpdate == null || patientIdToUpdate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a patient ID and press Enter.");
                return;
            }

            String criticalStatusVal = (String) criticalStatus.getSelectedItem();
            String ventilatorStatusVal = (String) ventilatorStatus.getSelectedItem();
            String lifeSupportStatusVal = (String) lifeSupportStatus.getSelectedItem();

            try {
                Conn c = new Conn();
                String query = "UPDATE emergency_details SET critical_status = ?, ventilator_status = ?, life_support_status = ? WHERE patient_id = ?";
                PreparedStatement pst = c.connection.prepareStatement(query);
                pst.setString(1, criticalStatusVal);
                pst.setString(2, ventilatorStatusVal);
                pst.setString(3, lifeSupportStatusVal);
                pst.setString(4, patientIdToUpdate);
                int updatedRows = pst.executeUpdate();

                if (updatedRows == 0) {
                    pst.close();
                    String insertQuery = "INSERT INTO emergency_details (patient_id, critical_status, ventilator_status, life_support_status, patient_name) VALUES (?, ?, ?, ?, ?)";
                    pst = c.connection.prepareStatement(insertQuery);
                    pst.setString(1, patientIdToUpdate);
                    pst.setString(2, criticalStatusVal);
                    pst.setString(3, ventilatorStatusVal);
                    pst.setString(4, lifeSupportStatusVal);
                    pst.setString(5, patientName.getText());
                    pst.executeUpdate();
                }

                JOptionPane.showMessageDialog(this, "Patient critical data saved successfully.");

                pst.close();
                c.statement.close();
                c.connection.close();

                if (detailsWindow != null && detailsWindow.isDisplayable()) {
                    detailsWindow.refreshData();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage());
            }
        } else if (e.getSource() == btnClear) {
            patientID.setText("");
            patientName.setText("");
            criticalStatus.setSelectedIndex(0);
            ventilatorStatus.setSelectedIndex(0);
            lifeSupportStatus.setSelectedIndex(0);
            patientIdToUpdate = null;
        } else if (e.getSource() == btnExit) {
            this.dispose();
        } else if (e.getSource() == btnViewEmergencyDetails) {
            if (detailsWindow == null || !detailsWindow.isDisplayable()) {
                detailsWindow = new CriticalPatientDetails();
            } else {
                detailsWindow.toFront();
            }
        }
    }

    public static void main(String[] args) {
        new CriticalPatient();
    }
}
