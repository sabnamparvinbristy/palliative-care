package Hospital.Management.System;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class AppointmentPanel extends JFrame implements ActionListener {

    JTextField patientID, doctorID;
    JLabel patientNameLabel, doctorNameLabel;
    JComboBox<String> purposeBox, appointmentTime, appointmentStatus;
    JTextArea notes;
    JDateChooser appointmentDate;
    JButton submitButton, cancelButton, viewAppointmentsBtn, backButton;

    public AppointmentPanel() {
        setTitle("Appointment Booking Panel");
        getContentPane().setBackground(new Color(214, 245, 240));
        setLayout(null);

        JLabel heading = new JLabel("Appointment Booking Information");
        heading.setBounds(250, 30, 500, 50);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        JLabel lblPatientID = new JLabel("Patient ID:");
        lblPatientID.setBounds(50, 100, 150, 30);
        lblPatientID.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(lblPatientID);

        patientID = new JTextField();
        patientID.setBounds(200, 100, 150, 30);
        patientID.setBackground(new Color(240, 233, 215));
        add(patientID);

        patientNameLabel = new JLabel("Name: ");
        patientNameLabel.setBounds(360, 100, 250, 30);
        patientNameLabel.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        add(patientNameLabel);

        JLabel lblDoctorID = new JLabel("Doctor ID:");
        lblDoctorID.setBounds(50, 150, 150, 30);
        lblDoctorID.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(lblDoctorID);

        doctorID = new JTextField();
        doctorID.setBounds(200, 150, 150, 30);
        doctorID.setBackground(new Color(240, 233, 215));
        add(doctorID);

        doctorNameLabel = new JLabel("Name: ");
        doctorNameLabel.setBounds(360, 150, 250, 30);
        doctorNameLabel.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        add(doctorNameLabel);

        JLabel lblPurpose = new JLabel("Purpose:");
        lblPurpose.setBounds(50, 200, 150, 30);
        lblPurpose.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(lblPurpose);

        purposeBox = new JComboBox<>(new String[]{"General Checkup", "Consultation", "Follow-up", "Emergency"});
        purposeBox.setBounds(200, 200, 150, 30);
        purposeBox.setBackground(new Color(240, 233, 215));
        add(purposeBox);

        JLabel lblAppointmentDate = new JLabel("Appointment Date:");
        lblAppointmentDate.setBounds(460, 200, 170, 30);
        lblAppointmentDate.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(lblAppointmentDate);

        appointmentDate = new JDateChooser();
        appointmentDate.setBounds(630, 200, 130, 30);
        appointmentDate.setBackground(new Color(240, 233, 215));
        add(appointmentDate);

        JLabel lblTime = new JLabel("Appointment Time:");
        lblTime.setBounds(50, 250, 170, 30);
        lblTime.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(lblTime);

        appointmentTime = new JComboBox<>(new String[]{"09:00 AM", "10:00 AM", "11:00 AM", "01:00 PM", "02:00 PM", "03:00 PM"});
        appointmentTime.setBounds(200, 250, 150, 30);
        appointmentTime.setBackground(new Color(240, 233, 215));
        add(appointmentTime);

        JLabel lblStatus = new JLabel("Appointment Status:");
        lblStatus.setBounds(460, 250, 170, 30);
        lblStatus.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(lblStatus);

        appointmentStatus = new JComboBox<>(new String[]{"Pending", "Confirmed", "Cancelled"});
        appointmentStatus.setBounds(630, 250, 130, 30);
        appointmentStatus.setBackground(new Color(240, 233, 215));
        add(appointmentStatus);

        JLabel lblNotes = new JLabel("Notes:");
        lblNotes.setBounds(50, 300, 150, 30);
        lblNotes.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(lblNotes);

        notes = new JTextArea();
        notes.setLineWrap(true);
        notes.setWrapStyleWord(true);
        notes.setBackground(new Color(240, 233, 215));
        JScrollPane noteScroll = new JScrollPane(notes);
        noteScroll.setBounds(200, 300, 560, 80);
        add(noteScroll);

        submitButton = new JButton("Submit");
        submitButton.setBounds(200, 400, 150, 35);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.CYAN);
        submitButton.addActionListener(this);
        add(submitButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(400, 400, 150, 35);
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setForeground(Color.CYAN);
        cancelButton.addActionListener(evt -> {
            patientID.setText("");
            doctorID.setText("");
            purposeBox.setSelectedIndex(0);
            notes.setText("");
            appointmentDate.setDate(null);
            appointmentTime.setSelectedIndex(0);
            appointmentStatus.setSelectedIndex(0);
            patientNameLabel.setText("Name: ");
            doctorNameLabel.setText("Name: ");
        });
        add(cancelButton);

        viewAppointmentsBtn = new JButton("View All Appointments");
        viewAppointmentsBtn.setBounds(600, 400, 200, 35);
        viewAppointmentsBtn.setBackground(Color.BLACK);
        viewAppointmentsBtn.setForeground(Color.CYAN);
        viewAppointmentsBtn.addActionListener(evt -> {
            dispose();
            new ViewAppointments();
        });
        add(viewAppointmentsBtn);

        backButton = new JButton("Back");
        backButton.setBounds(50, 400, 120, 35);
        backButton.setBackground(Color.DARK_GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(evt -> {
            dispose();
            new Dashboard();
        });
        add(backButton);

        patientID.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        Conn c = new Conn();
                        PreparedStatement pst = c.connection.prepareStatement("SELECT full_name FROM patients WHERE patient_id = ?");
                        pst.setInt(1, Integer.parseInt(patientID.getText().trim()));
                        ResultSet rs = pst.executeQuery();
                        if (rs.next()) {
                            patientNameLabel.setText("Name: " + rs.getString("full_name"));
                        } else {
                            patientNameLabel.setText("Name: Not found");
                        }
                        pst.close(); rs.close(); c.connection.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        doctorID.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        Conn c = new Conn();
                        PreparedStatement pst = c.connection.prepareStatement("SELECT name FROM doctors WHERE doc_id = ?");
                        pst.setString(1, doctorID.getText().trim());
                        ResultSet rs = pst.executeQuery();
                        if (rs.next()) {
                            doctorNameLabel.setText("Name: " + rs.getString("name"));
                        } else {
                            doctorNameLabel.setText("Name: Not found");
                        }
                        pst.close(); rs.close(); c.connection.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        setSize(900, 500);
        setLocation(300, 50);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String pid = patientID.getText().trim();
            String did = doctorID.getText().trim();
            String purpose = (String) purposeBox.getSelectedItem();
            String note = notes.getText();
            String time = (String) appointmentTime.getSelectedItem();
            String status = (String) appointmentStatus.getSelectedItem();

            if (pid.isEmpty() || did.isEmpty() || appointmentDate.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
                return;
            }

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = sdf.format(appointmentDate.getDate());
                Conn c = new Conn();
                PreparedStatement pst = c.connection.prepareStatement("INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, purpose, notes) VALUES (?, ?, ?, ?, ?, ?, ?)");
                pst.setInt(1, Integer.parseInt(pid));
                pst.setInt(2, Integer.parseInt(did));
                pst.setString(3, date);
                pst.setString(4, time);
                pst.setString(5, status);
                pst.setString(6, purpose);
                pst.setString(7, note);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Appointment Added Successfully");
                pst.close(); c.connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new AppointmentPanel();
    }
}
