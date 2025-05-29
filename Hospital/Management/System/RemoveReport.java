package Hospital.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RemoveReport extends JFrame implements ActionListener {

    JComboBox<String> reportID;
    JButton removeButton, cancelButton, exitButton;
    JLabel patientIDLabel, reportDateLabel, diagnosisLabel;

    public RemoveReport() {
        setTitle("Remove Report Panel");
        getContentPane().setBackground(new Color(203, 241, 194));
        setLayout(null);

        JLabel heading = new JLabel("REMOVE REPORT FORM");
        heading.setBounds(300, 30, 500, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        // Row 1 - Report ID ComboBox
        addLabel("Report ID:", 50, 100);
        reportID = new JComboBox<>();
        reportID.setBounds(250, 100, 300, 30);
        reportID.setBackground(new Color(240, 233, 215));
        add(reportID);

        // Info Labels
        patientIDLabel = new JLabel("Patient ID: ");
        patientIDLabel.setBounds(250, 150, 400, 30);
        patientIDLabel.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        add(patientIDLabel);

        reportDateLabel = new JLabel("Report Date: ");
        reportDateLabel.setBounds(250, 180, 400, 30);
        reportDateLabel.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        add(reportDateLabel);

        diagnosisLabel = new JLabel("Diagnosis: ");
        diagnosisLabel.setBounds(250, 210, 400, 30);
        diagnosisLabel.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        add(diagnosisLabel);

        // Buttons
        removeButton = createButton("REMOVE", 180, 270);
        cancelButton = createButton("CLEAR", 360, 270);
        exitButton = createButton("BACK", 540, 270);
        removeButton.setEnabled(false); // Initially disabled

        add(removeButton);
        add(cancelButton);
        add(exitButton);

        removeButton.addActionListener(this);
        cancelButton.addActionListener(this);
        exitButton.addActionListener(this);

        populateReportIDs();

        // Show report info when selection changes
        reportID.addActionListener(e -> displayReportInfo());

        setSize(900, 400);
        setLocation(300, 50);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void addLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 180, 30);
        label.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        add(label);
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 150, 35);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.cyan);
        button.setFocusPainted(false);
        return button;
    }

    private void populateReportIDs() {
        try {
            Conn c = new Conn();
            String query = "SELECT report_id FROM report";
            PreparedStatement stmt = c.connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("report_id");
                reportID.addItem(String.valueOf(id));
            }
            rs.close();
            stmt.close();
            c.connection.close();

            if (reportID.getItemCount() > 0) {
                reportID.setSelectedIndex(0);
                displayReportInfo();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching report data.");
        }
    }

    private void displayReportInfo() {
        String selected = (String) reportID.getSelectedItem();

        if (selected != null && !selected.isEmpty()) {
            try {
                Conn c = new Conn();
                String query = "SELECT patient_id, test_date, diagnosis FROM report WHERE report_id = ?";
                PreparedStatement stmt = c.connection.prepareStatement(query);
                stmt.setInt(1, Integer.parseInt(selected));

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    patientIDLabel.setText("Patient ID: " + rs.getString("patient_id"));
                    reportDateLabel.setText("Test Date: " + rs.getDate("test_date"));
                    diagnosisLabel.setText("Diagnosis: " + rs.getString("diagnosis"));
                    removeButton.setEnabled(true);
                } else {
                    clearInfoLabels();
                }
                rs.close();
                stmt.close();
                c.connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                clearInfoLabels();
                JOptionPane.showMessageDialog(this, "Error fetching report information.");
            }
        } else {
            clearInfoLabels();
        }
    }

    private void clearInfoLabels() {
        patientIDLabel.setText("Patient ID: ");
        reportDateLabel.setText("Report Date: ");
        diagnosisLabel.setText("Diagnosis: ");
        removeButton.setEnabled(false);
    }

    private void removeFromDatabase() {
        try {
            Conn c = new Conn();
            String selected = (String) reportID.getSelectedItem();
            if (selected != null && !selected.isEmpty()) {
                String query = "DELETE FROM report WHERE report_id = ?";
                PreparedStatement stmt = c.connection.prepareStatement(query);
                stmt.setInt(1, Integer.parseInt(selected));

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Report removed successfully!");
                    reportID.removeItem(selected);
                    clearInfoLabels();
                } else {
                    JOptionPane.showMessageDialog(this, "No report found with the given ID.");
                }
                stmt.close();
                c.connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error removing report from database.");
        }
    }

    private void resetFields() {
        if (reportID.getItemCount() > 0) {
            reportID.setSelectedIndex(0);
        }
        clearInfoLabels();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == removeButton) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this report?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                removeFromDatabase();
            }
        } else if (e.getSource() == cancelButton) {
            resetFields();
        } else if (e.getSource() == exitButton) {
            setVisible(false);
            new Report();
        }
    }

    public static void main(String[] args) {
        new RemoveReport();
    }
}