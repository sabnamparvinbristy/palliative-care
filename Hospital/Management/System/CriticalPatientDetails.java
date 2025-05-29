package Hospital.Management.System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CriticalPatientDetails extends JFrame implements ActionListener {

    JTextField patientIDField;
    JButton updateBtn, backBtn, removeBtn;
    JLabel totalEmergencyLabel;
    JTable patientTable;
    JScrollPane scrollPane;

    public CriticalPatientDetails() {
        setTitle("View Critical Patient Details");
        getContentPane().setBackground(new Color(214, 245, 240));
        setLayout(null);

        JLabel heading = new JLabel("View Critical Patient Details");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        totalEmergencyLabel = new JLabel("Total Critical Patients: 0");
        totalEmergencyLabel.setBounds(50, 100, 300, 30);
        totalEmergencyLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(totalEmergencyLabel);

        JLabel idLabel = new JLabel("Enter Patient ID: ");
        idLabel.setBounds(50, 150, 150, 30);
        idLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(idLabel);

        patientIDField = new JTextField();
        patientIDField.setBounds(200, 150, 150, 30);
        patientIDField.setBackground(new Color(240, 233, 215));
        add(patientIDField);

        // Key listener for manual Enter-triggered search
        patientIDField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String patientIDText = patientIDField.getText().trim();
                    fetchCriticalPatientDetails(patientIDText);
                }
            }
        });

        // Removed search button
        // searchBtn = new JButton("Search");
        // searchBtn.setBounds(400, 150, 100, 30);
        // searchBtn.setBackground(Color.BLACK);
        // searchBtn.setForeground(Color.cyan);
        // searchBtn.addActionListener(this);
        // add(searchBtn);

        String[] columnNames = {"ID", "Patient Name", "Critical Status", "Ventilator Status", "Life Support Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        patientTable = new JTable(model);
        scrollPane = new JScrollPane(patientTable);
        scrollPane.setBounds(50, 200, 800, 200);
        add(scrollPane);

        updateBtn = new JButton("Update");
        updateBtn.setBounds(250, 420, 150, 35);
        updateBtn.setBackground(Color.BLACK);
        updateBtn.setForeground(Color.cyan);
        updateBtn.addActionListener(this);
        add(updateBtn);

        removeBtn = new JButton("Remove");
        removeBtn.setBounds(450, 420, 150, 35);
        removeBtn.setBackground(Color.BLACK);
        removeBtn.setForeground(Color.cyan);
        removeBtn.addActionListener(this);
        add(removeBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(650, 420, 150, 35);
        backBtn.setBackground(Color.BLACK);
        backBtn.setForeground(Color.cyan);
        backBtn.addActionListener(this);
        add(backBtn);

        setSize(900, 520);
        setLocation(300, 50);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        fetchCriticalPatientDetails("");
        countTotalCriticalPatients();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            setVisible(false);
            new CriticalPatient();
        } else if (e.getSource() == updateBtn) {
            updateSelectedPatient();
        } else if (e.getSource() == removeBtn) {
            removeSelectedPatient();
        }
    }

    private void updateSelectedPatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow != -1) {
            Object patientIdObj = patientTable.getValueAt(selectedRow, 0);
            if (patientIdObj != null) {
                new CriticalPatient(patientIdObj.toString());
                setVisible(false);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a patient from the table.");
        }
    }

    private void removeSelectedPatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow != -1) {
            Object patientIdObj = patientTable.getValueAt(selectedRow, 0);
            if (patientIdObj != null) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this patient?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        Conn c = new Conn();
                        String query = "DELETE FROM emergency_details WHERE patient_id = ?";
                        PreparedStatement pst = c.connection.prepareStatement(query);
                        pst.setString(1, patientIdObj.toString());
                        int rowsAffected = pst.executeUpdate();
                        pst.close();
                        c.statement.close();
                        c.connection.close();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Patient removed successfully.");
                            refreshData();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error removing patient: " + ex.getMessage());
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a patient to remove.");
        }
    }

    public void refreshData() {
        fetchCriticalPatientDetails(patientIDField.getText().trim());
        countTotalCriticalPatients();
    }

    private void fetchCriticalPatientDetails(String patientID) {
        try {
            Conn c = new Conn();
            String query = "SELECT ed.patient_id, p.full_name AS patient_name, ed.critical_status, ed.ventilator_status, ed.life_support_status FROM emergency_details ed LEFT JOIN patients p ON ed.patient_id = p.patient_id WHERE ed.patient_id LIKE ? ORDER BY ed.critical_status DESC";
            PreparedStatement pst = c.connection.prepareStatement(query);
            pst.setString(1, "%" + patientID + "%");
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) patientTable.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("patient_id"),
                        rs.getString("patient_name"),
                        rs.getString("critical_status"),
                        rs.getString("ventilator_status"),
                        rs.getString("life_support_status")
                });
            }

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "No critical patients found.");
            }

            rs.close();
            pst.close();
            c.statement.close();
            c.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage());
        }
    }

    private void countTotalCriticalPatients() {
        try {
            Conn c = new Conn();
            String query = "SELECT COUNT(*) FROM emergency_details";
            PreparedStatement pst = c.connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                totalEmergencyLabel.setText("Total Critical Patients: " + rs.getInt(1));
            }
            rs.close();
            pst.close();
            c.statement.close();
            c.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CriticalPatientDetails());
    }
}
