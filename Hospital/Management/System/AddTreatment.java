// Filename: AddTreatment.java
package Hospital.Management.System;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddTreatment extends JFrame implements ActionListener {

    JTextField patientID, patientName, doctorID, doctorName, diagnosis;
    JTextArea sideEffects, outcome, doctorNotes, medications;
    JComboBox<String> treatmentType, treatmentStatus;
    JDateChooser startDate, endDate;
    JButton submitButton, clearButton, exitButton;

    public AddTreatment() {
        setTitle("Treatment Panel");
        getContentPane().setBackground(new Color(214, 245, 240));
        setLayout(null);

        JLabel heading = new JLabel("TREATMENT DETAILS FORM");
        heading.setBounds(280, 30, 500, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        addLabel("Patient ID:", 60, 100);
        patientID = createTextField(200, 100);
        add(patientID);

        addLabel("Doctor ID:", 530, 100);
        doctorID = createTextField(650, 100);
        add(doctorID);

        addLabel("Patient Name:", 60, 150);
        patientName = createTextField(200, 150);
        patientName.setEditable(false);
        add(patientName);

        addLabel("Doctor Name:", 460, 150);
        doctorName = createTextField(610, 150);
        doctorName.setEditable(false);
        add(doctorName);

        addLabel("Diagnosis:", 60, 200);
        diagnosis = createTextField(200, 200);
        add(diagnosis);

        addLabel("Treatment Type:", 460, 200);
        treatmentType = createComboBox(new String[]{"Surgery", "Chemotherapy", "Physiotherapy", "Radiation", "Emergency"}, 610, 200);
        add(treatmentType);

        addLabel("Start Date:", 60, 250);
        startDate = new JDateChooser();
        startDate.setBounds(200, 250, 150, 30);
        startDate.setBackground(new Color(240, 233, 215));
        add(startDate);

        addLabel("End Date:", 460, 250);
        endDate = new JDateChooser();
        endDate.setBounds(610, 250, 150, 30);
        endDate.setBackground(new Color(240, 233, 215));
        add(endDate);

        addLabel("Medications:", 50, 300);
        medications = new JTextArea();
        JScrollPane medicationsScroll = new JScrollPane(medications);
        medicationsScroll.setBounds(200, 300, 560, 60);
        add(medicationsScroll);

        addLabel("Treatment Status:", 50, 390);
        treatmentStatus = createComboBox(new String[]{"Ongoing", "Completed", "Discharged"}, 200, 390);
        add(treatmentStatus);

        addLabel("Side Effects:", 460, 380);
        sideEffects = new JTextArea();
        JScrollPane sideEffectScroll = new JScrollPane(sideEffects);
        sideEffectScroll.setBounds(610, 380, 150, 60);
        add(sideEffectScroll);

        addLabel("Doctor Notes:", 50, 450);
        doctorNotes = new JTextArea();
        JScrollPane doctorNotesScroll = new JScrollPane(doctorNotes);
        doctorNotesScroll.setBounds(200, 450, 560, 60);
        add(doctorNotesScroll);

        addLabel("Outcome:", 50, 530);
        outcome = new JTextArea();
        JScrollPane outcomeScroll = new JScrollPane(outcome);
        outcomeScroll.setBounds(200, 530, 560, 60);
        add(outcomeScroll);

        submitButton = new JButton("SUBMIT");
        submitButton.setBounds(150, 610, 150, 35);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.cyan);
        submitButton.addActionListener(this);
        add(submitButton);

        clearButton = new JButton("CLEAR");
        clearButton.setBounds(350, 610, 150, 35);
        clearButton.setBackground(Color.BLACK);
        clearButton.setForeground(Color.cyan);
        clearButton.addActionListener(this);
        add(clearButton);

        exitButton = new JButton("BACK");
        exitButton.setBounds(550, 610, 150, 35);
        exitButton.setBackground(Color.BLACK);
        exitButton.setForeground(Color.cyan);
        exitButton.addActionListener(this);
        add(exitButton);

        setSize(1000, 700);
        setLocation(250, 100);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Add Enter key listeners for auto-fill functionality
        patientID.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    fetchPatientName();
                }
            }
        });

        doctorID.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    fetchDoctorName();
                }
            }
        });
    }

    private void addLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 150, 30);
        label.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(label);
    }

    private JTextField createTextField(int x, int y) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, 150, 30);
        tf.setBackground(new Color(240, 233, 215));
        return tf;
    }

    private JComboBox<String> createComboBox(String[] items, int x, int y) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setBounds(x, y, 150, 30);
        cb.setBackground(new Color(240, 233, 215));
        return cb;
    }

    private void fetchPatientName() {
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.statement.executeQuery("SELECT full_name FROM patients WHERE patient_id = '" + patientID.getText() + "'");
            if (rs.next()) {
                patientName.setText(rs.getString("full_name"));
            } else {
                JOptionPane.showMessageDialog(this, "Patient not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void fetchDoctorName() {
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.statement.executeQuery("SELECT name FROM doctors WHERE doc_id = '" + doctorID.getText() + "'");
            if (rs.next()) {
                doctorName.setText(rs.getString("name"));
            } else {
                JOptionPane.showMessageDialog(this, "Doctor not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            try {
                Conn conn = new Conn();
                String sql = "INSERT INTO treatment (patient_id, doctor_id, diagnosis, treatment_type, medications, start_date, end_date, notes, side_effects, outcome) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.connection.prepareStatement(sql);

                pstmt.setInt(1, Integer.parseInt(patientID.getText()));
                pstmt.setString(2, doctorID.getText());
                pstmt.setString(3, diagnosis.getText());
                pstmt.setString(4, (String)treatmentType.getSelectedItem());
                pstmt.setString(5, medications.getText());

                java.util.Date sDate = startDate.getDate();
                java.util.Date eDate = endDate.getDate();
                pstmt.setDate(6, sDate != null ? new java.sql.Date(sDate.getTime()) : null);
                pstmt.setDate(7, eDate != null ? new java.sql.Date(eDate.getTime()) : null);

                pstmt.setString(8, doctorNotes.getText());
                pstmt.setString(9, sideEffects.getText());
                pstmt.setString(10, outcome.getText());

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Treatment record saved successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error while saving data");
            }

        } else if (e.getSource() == clearButton) {
            patientID.setText("");
            patientName.setText("");
            doctorID.setText("");
            doctorName.setText("");
            diagnosis.setText("");
            medications.setText("");
            doctorNotes.setText("");
            sideEffects.setText("");
            outcome.setText("");
            treatmentType.setSelectedIndex(0);
            treatmentStatus.setSelectedIndex(0);
            startDate.setDate(null);
            endDate.setDate(null);

        } else if (e.getSource() == exitButton) {
            this.setVisible(false);
            new ViewTreatment();
        }
    }

    public static void main(String[] args) {
        new AddTreatment();
    }
}
