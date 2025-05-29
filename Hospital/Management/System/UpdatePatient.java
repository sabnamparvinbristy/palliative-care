package Hospital.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.sql.*;

public class UpdatePatient extends JFrame implements ActionListener {

    JTextField patientIDField, patientName, patientAge, patientContact, emergencyContact;
    JDateChooser patientDOB;
    JComboBox<String> patientGender, patientCategory;
    JButton searchBtn, saveBtn, backBtn;

    public UpdatePatient(String patientID) {
        setTitle("Update Patient");
        setLayout(null);
        getContentPane().setBackground(new Color(214, 245, 240));

        JLabel heading = new JLabel("UPDATE PATIENT");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        JLabel idLabel = new JLabel("Enter Patient ID: ");
        idLabel.setBounds(50, 100, 150, 30);
        idLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(idLabel);

        patientIDField = new JTextField();
        patientIDField.setBounds(200, 100, 150, 30);
        patientIDField.setBackground(new Color(240, 233, 215));
        add(patientIDField);

        searchBtn = new JButton("Search");
        searchBtn.setBounds(400, 100, 100, 30);
        searchBtn.setBackground(Color.BLACK);
        searchBtn.setForeground(Color.cyan);
        searchBtn.addActionListener(this);
        add(searchBtn);

        JLabel nameLabel = new JLabel("Patient Name: ");
        nameLabel.setBounds(50, 150, 150, 30);
        nameLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(nameLabel);

        patientName = new JTextField();
        patientName.setBounds(200, 150, 150, 30);
        patientName.setBackground(new Color(240, 233, 215));
        add(patientName);

        JLabel ageLabel = new JLabel("Age: ");
        ageLabel.setBounds(50, 200, 150, 30);
        ageLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(ageLabel);

        patientAge = new JTextField();
        patientAge.setBounds(200, 200, 150, 30);
        patientAge.setBackground(new Color(240, 233, 215));
        add(patientAge);

        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setBounds(50, 250, 150, 30);
        genderLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(genderLabel);

        String genderOptions[] = { "Male", "Female", "Other" };
        patientGender = new JComboBox<>(genderOptions);
        patientGender.setBackground(new Color(240, 233, 215));
        patientGender.setBounds(200, 250, 150, 30);
        add(patientGender);

        JLabel dobLabel = new JLabel("Date of Birth: ");
        dobLabel.setBounds(50, 300, 150, 30);
        dobLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(dobLabel);

        patientDOB = new JDateChooser();
        patientDOB.setBounds(200, 300, 150, 30);
        patientDOB.setBackground(new Color(240, 233, 215));
        add(patientDOB);

        JLabel contactLabel = new JLabel("Contact Info: ");
        contactLabel.setBounds(50, 350, 150, 30);
        contactLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(contactLabel);

        patientContact = new JTextField();
        patientContact.setBounds(200, 350, 150, 30);
        patientContact.setBackground(new Color(240, 233, 215));
        add(patientContact);

        JLabel emergencyContactLabel = new JLabel("Emergency Contact: ");
        emergencyContactLabel.setBounds(50, 400, 180, 30);
        emergencyContactLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(emergencyContactLabel);

        emergencyContact = new JTextField();
        emergencyContact.setBounds(230, 400, 150, 30);
        emergencyContact.setBackground(new Color(240, 233, 215));
        add(emergencyContact);

        JLabel categoryLabel = new JLabel("Category: ");
        categoryLabel.setBounds(50, 450, 150, 30);
        categoryLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(categoryLabel);

        String categories[] = { "Critical", "Hospice", "Long-Term" };
        patientCategory = new JComboBox<>(categories);
        patientCategory.setBackground(new Color(240, 233, 215));
        patientCategory.setBounds(200, 450, 150, 30);
        add(patientCategory);

        saveBtn = new JButton("Save");
        saveBtn.setBounds(250, 500, 150, 35);
        saveBtn.setBackground(Color.BLACK);
        saveBtn.setForeground(Color.cyan);
        saveBtn.addActionListener(this);
        add(saveBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(450, 500, 150, 35);
        backBtn.setBackground(Color.BLACK);
        backBtn.setForeground(Color.cyan);
        backBtn.addActionListener(this);
        add(backBtn);

        setSize(900, 600);
        setLocation(300, 50);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        // Auto-fetch if patient ID provided
        if (patientID != null && !patientID.trim().isEmpty()) {
            patientIDField.setText(patientID);
            fetchPatientDetails(patientID);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBtn) {
            String patientID = patientIDField.getText().trim();
            if (!patientID.isEmpty()) {
                fetchPatientDetails(patientID);
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a patient ID.");
            }
        } else if (e.getSource() == saveBtn) {
            String name = patientName.getText();
            String age = patientAge.getText();
            String gender = (String) patientGender.getSelectedItem();
            if (patientDOB.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Please select a date of birth.");
                return;
            }
            String dob = new SimpleDateFormat("yyyy-MM-dd").format(patientDOB.getDate());
            String contact = patientContact.getText();
            String emergency = emergencyContact.getText();
            String category = (String) patientCategory.getSelectedItem();

            if (name.isEmpty() || age.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all mandatory fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Conn c = new Conn();
                String query = "UPDATE patients SET full_name = ?, age = ?, gender = ?, dob = ?, contact = ?, emergency_contact = ?, category = ? WHERE patient_id = ?";
                PreparedStatement pst = c.connection.prepareStatement(query);
                pst.setString(1, name);
                pst.setString(2, age);
                pst.setString(3, gender);
                pst.setString(4, dob);
                pst.setString(5, contact);
                pst.setString(6, emergency);
                pst.setString(7, category);
                pst.setString(8, patientIDField.getText().trim());

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Patient details updated successfully!");
                setVisible(false);
                new ViewPatient(); // Redirect to ViewPatient instead of PatientManagement
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating patient.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == backBtn) {
            setVisible(false);
            new ViewPatient(); // Optional: You can change this to ViewPatient as well
        }
    }

    private void fetchPatientDetails(String patientID) {
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM patients WHERE patient_id = ?";
            PreparedStatement pst = c.connection.prepareStatement(query);
            pst.setString(1, patientID);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                patientIDField.setText(rs.getString("patient_id"));
                patientName.setText(rs.getString("full_name"));
                patientAge.setText(rs.getString("age"));
                patientGender.setSelectedItem(rs.getString("gender"));
                patientDOB.setDate(rs.getDate("dob"));
                patientContact.setText(rs.getString("contact"));
                emergencyContact.setText(rs.getString("emergency_contact"));
                patientCategory.setSelectedItem(rs.getString("category"));
            } else {
                JOptionPane.showMessageDialog(null, "No patient found with ID: " + patientID);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UpdatePatient("");
    }
}
