package Hospital.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat; // Import SimpleDateFormat
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;

public class AddPatient extends JFrame implements ActionListener {

    // Declare components for the Add Patient form
    JTextField patientName, patientContact, emergencyContact;
    JDateChooser patientDOB;
    JComboBox<String> patientGender, patientCategory;
    JButton saveBtn, backBtn;
    JLabel patientIDLabel, ageLabel;

    // Constructor for AddPatient class
    public AddPatient() {
        setTitle("Add Patient");

        getContentPane().setBackground(new Color(214, 245, 240));


        // Heading
        JLabel heading = new JLabel("ADD NEW PATIENT");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        // Patient ID (Auto-generated)
        JLabel idLabel = new JLabel("Patient ID: ");
        idLabel.setBounds(50, 100, 150, 30);
        idLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(idLabel);

        patientIDLabel = new JLabel(""); // To display auto-generated patient ID
        patientIDLabel.setBounds(200, 100, 150, 30);
        patientIDLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        patientIDLabel.setForeground(new Color(147, 37, 42));
        add(patientIDLabel);

        // Patient Name
        JLabel nameLabel = new JLabel("Patient Name: ");
        nameLabel.setBounds(50, 150, 150, 30);
        nameLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(nameLabel);

        patientName = new JTextField();
        patientName.setBounds(200, 150, 150, 30);
        patientName.setBackground(new Color(240, 233, 215));
        add(patientName);

        // Date of Birth
        JLabel dobLabel = new JLabel("Date of Birth: ");
        dobLabel.setBounds(50, 200, 150, 30);
        dobLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(dobLabel);

        patientDOB = new JDateChooser();
        patientDOB.setBounds(200, 200, 150, 30);
        patientDOB.setBackground(new Color(240, 233, 215));
        add(patientDOB);

        // Patient Gender
        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setBounds(50, 250, 150, 30);
        genderLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(genderLabel);

        String genderOptions[] = { "Male", "Female", "Other" };
        patientGender = new JComboBox<>(genderOptions);
        patientGender.setBackground(new Color(240, 233, 215));
        patientGender.setBounds(200, 250, 150, 30);
        add(patientGender);

        // Patient Contact
        JLabel contactLabel = new JLabel("Contact Info: ");
        contactLabel.setBounds(50, 300, 150, 30);
        contactLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(contactLabel);

        patientContact = new JTextField();
        patientContact.setBounds(200, 300, 150, 30);
        patientContact.setBackground(new Color(240, 233, 215));
        add(patientContact);

        // Emergency Contact
        JLabel emergencyContactLabel = new JLabel("Emergency Contact: ");
        emergencyContactLabel.setBounds(50, 350, 150, 30);
        emergencyContactLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(emergencyContactLabel);

        emergencyContact = new JTextField();
        emergencyContact.setBounds(200, 350, 150, 30);
        emergencyContact.setBackground(new Color(240, 233, 215));
        add(emergencyContact);

        // Patient Category - Drop-down (Critical, Hospice, Long-Term)
        JLabel categoryLabel = new JLabel("Category: ");
        categoryLabel.setBounds(50, 400, 150, 30);
        categoryLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(categoryLabel);

        String categories[] = { "Critical", "Hospice", "Long-Term" };
        patientCategory = new JComboBox<>(categories);
        patientCategory.setBackground(new Color(240, 233, 215));
        patientCategory.setBounds(200, 400, 150, 30);
        add(patientCategory);

        // Age - Automatically calculated from Date of Birth
        JLabel ageTitleLabel = new JLabel("Age: ");
        ageTitleLabel.setBounds(50, 450, 150, 30);
        ageTitleLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(ageTitleLabel);

        ageLabel = new JLabel("");
        ageLabel.setBounds(200, 450, 150, 30);
        ageLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        ageLabel.setForeground(new Color(147, 37, 42));
        add(ageLabel);

        // Save Button
        saveBtn = new JButton("Save");
        saveBtn.setBounds(250, 510, 150, 35);
        saveBtn.setBackground(Color.BLACK);
        saveBtn.setForeground(Color.cyan);
        saveBtn.addActionListener(this);
        add(saveBtn);

        // Back Button
        backBtn = new JButton("Back");
        backBtn.setBounds(450, 510, 150, 35);
        backBtn.setBackground(Color.BLACK);
        backBtn.setForeground(Color.cyan);
        backBtn.addActionListener(this);
        add(backBtn);

        setSize(900, 600); // height & width of this frame
        setLayout(null); // Default layout
        setLocation(300, 50); // Position on screen
        setVisible(true); // Make visible

        // Fetch the next available patient_id
        getNextPatientId();

        // Add a listener for the DOB field to update age automatically
        patientDOB.getDateEditor().addPropertyChangeListener(evt -> {
            if (patientDOB.getDate() != null) {
                ageLabel.setText(String.valueOf(calculateAge(patientDOB.getDate()))); // Automatically update age
            }
        });
    }

    // Method to get the next patient_id
    public void getNextPatientId() {
        try {
            Conn c = new Conn();
            String query = "SELECT patient_id FROM patients ORDER BY patient_id DESC LIMIT 1"; // Get the last inserted patient ID
            ResultSet rs = c.statement.executeQuery(query);

            if (rs.next()) {
                int lastPatientId = rs.getInt("patient_id");
                int newPatientId = lastPatientId + 1; // Increment by 1
                patientIDLabel.setText(String.valueOf(newPatientId)); // Set the new patient_id
            } else {
                // If no patients are in the database, start from 30001
                patientIDLabel.setText("30001");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to calculate the patient's age based on their date of birth (DOB)
    public int calculateAge(java.util.Date dob) {
        LocalDate birthDate = new java.sql.Date(dob.getTime()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBtn) {
            String name = patientName.getText();
            String gender = (String) patientGender.getSelectedItem();
            String dob = new SimpleDateFormat("yyyy-MM-dd").format(patientDOB.getDate());
            String contact = patientContact.getText();
            String emergency = emergencyContact.getText();
            String category = (String) patientCategory.getSelectedItem();

            // Calculate age based on DOB
            int age = calculateAge(patientDOB.getDate());

            // Validate the inputs
            if (name.isEmpty() || contact.isEmpty() || emergency.isEmpty() || category.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all mandatory fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insert into the database
            try {
                Conn c = new Conn();
                String query = "INSERT INTO patients (patient_id, full_name, dob, gender, contact, emergency_contact, category, age) " +
                        "VALUES ('" + patientIDLabel.getText() + "', '" + name + "', '" + dob + "', '" + gender + "', '" + contact + "', '" + emergency + "', '" + category + "', '" + age + "')";
                c.statement.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Patient added successfully!");
                setVisible(false);
                new ViewPatient(); // Go back to the Patient Management Panel
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding patient.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == backBtn) {
            setVisible(false);
            new ViewPatient(); // Go back to the Patient Management Panel
        }
    }


    public static void main(String[] args) {
        new AddPatient(); // Create the AddPatient screen
    }
}
