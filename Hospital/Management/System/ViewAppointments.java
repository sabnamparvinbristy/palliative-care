package Hospital.Management.System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewAppointments extends JFrame implements ActionListener {

    JTextField searchField;
    JButton searchBtn, backBtn;
    JTable appointmentTable;
    JScrollPane scrollPane;

    public ViewAppointments() {
        setTitle("View All Appointments");
        getContentPane().setBackground(new Color(214, 245, 240));
        setLayout(null);

        JLabel heading = new JLabel("All Appointment Records");
        heading.setBounds(300, 30, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        JLabel searchLabel = new JLabel("Search by Patient ID:");
        searchLabel.setBounds(50, 100, 180, 30);
        searchLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(230, 100, 150, 30);
        add(searchField);

        // Trigger search on pressing Enter key
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String search = searchField.getText().trim();
                    fetchAppointments(search);
                }
            }
        });

        searchBtn = new JButton("Search");
        searchBtn.setBounds(400, 100, 100, 30);
        searchBtn.setBackground(Color.BLACK);
        searchBtn.setForeground(Color.CYAN);
        searchBtn.addActionListener(this);
        add(searchBtn);

        String[] columns = {"Appointment ID", "Patient ID", "Doctor ID", "Date", "Time", "Status", "Purpose", "Notes"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        appointmentTable = new JTable(model);
        scrollPane = new JScrollPane(appointmentTable);
        scrollPane.setBounds(50, 160, 800, 250);
        add(scrollPane);

        // Show details on row click
        appointmentTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = appointmentTable.getSelectedRow();
                if (row >= 0) {
                    DefaultTableModel model = (DefaultTableModel) appointmentTable.getModel();
                    String details = "Appointment ID: " + model.getValueAt(row, 0)
                            + "\nPatient ID: " + model.getValueAt(row, 1)
                            + "\nDoctor ID: " + model.getValueAt(row, 2)
                            + "\nDate: " + model.getValueAt(row, 3)
                            + "\nTime: " + model.getValueAt(row, 4)
                            + "\nStatus: " + model.getValueAt(row, 5)
                            + "\nPurpose: " + model.getValueAt(row, 6)
                            + "\nNotes: " + model.getValueAt(row, 7);
                    JOptionPane.showMessageDialog(null, details, "Appointment Details", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        backBtn = new JButton("Back");
        backBtn.setBounds(400, 430, 100, 35);
        backBtn.setBackground(Color.BLACK);
        backBtn.setForeground(Color.CYAN);
        backBtn.addActionListener(e -> {
            dispose();
            new AppointmentPanel();
        });
        add(backBtn);

        setSize(900, 550);
        setLocation(300, 50);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        fetchAppointments(""); // Load all on startup
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBtn) {
            String search = searchField.getText().trim();
            fetchAppointments(search);
        }
    }

    private void fetchAppointments(String patientID) {
        Conn c = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            c = new Conn();
            String query = "SELECT appointment_id, patient_id, doctor_id, appointment_date, appointment_time, status, purpose, notes FROM appointments";
            if (!patientID.isEmpty()) {
                query += " WHERE CAST(patient_id AS CHAR) LIKE ?";
                pst = c.connection.prepareStatement(query);
                pst.setString(1, "%" + patientID + "%");
            } else {
                pst = c.connection.prepareStatement(query);
            }
            rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) appointmentTable.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("appointment_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_time"),
                        rs.getString("status"),
                        rs.getString("purpose"),
                        rs.getString("notes")
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching appointments: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (c != null) {
                    if (c.statement != null) c.statement.close();
                    if (c.connection != null) c.connection.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ViewAppointments();
    }
}
