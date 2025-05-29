package icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PharmacyPanel extends JFrame {

    // Declare components for the Pharmacy form
    JTextField medicationID, name, manufacturer, strength, price, quantityInStock, batchNumber;
    JComboBox<String> medicationType, dosageForm, drugCategory;
    JCheckBox prescriptionRequired;
    JTextArea sideEffects, storageConditions;
    JButton submitButton, cancelButton;

    // Database Connection details
    Connection conn;

    // Constructor for the PharmacyPanel
    public PharmacyPanel() {
        // Set up JFrame properties
        setTitle("Pharmacy Panel");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new FlowLayout());

        // Create the components (form fields)
        medicationID = new JTextField(20);
        name = new JTextField(20);
        manufacturer = new JTextField(20);
        strength = new JTextField(20);
        price = new JTextField(10);
        quantityInStock = new JTextField(10);
        batchNumber = new JTextField(20);

        // ComboBox for Medication Type, Dosage Form, and Drug Category
        medicationType = new JComboBox<>(new String[]{"Tablet", "Syrup", "Injection", "Ointment"});
        dosageForm = new JComboBox<>(new String[]{"Oral", "Topical", "Injectable"});
        drugCategory = new JComboBox<>(new String[]{"Painkiller", "Antibiotic", "Antipyretic", "Other"});

        // Checkbox for Prescription Required
        prescriptionRequired = new JCheckBox("Prescription Required");

        // TextArea for Side Effects and Storage Conditions
        sideEffects = new JTextArea(5, 20);
        storageConditions = new JTextArea(5, 20);

        // Buttons for Submit and Cancel
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");

        // Add components to the frame
        add(new JLabel("Medication ID:"));
        add(medicationID);
        add(new JLabel("Medication Name:"));
        add(name);
        add(new JLabel("Manufacturer:"));
        add(manufacturer);
        add(new JLabel("Strength:"));
        add(strength);
        add(new JLabel("Price:"));
        add(price);
        add(new JLabel("Quantity in Stock:"));
        add(quantityInStock);
        add(new JLabel("Batch Number:"));
        add(batchNumber);
        add(new JLabel("Medication Type:"));
        add(medicationType);
        add(new JLabel("Dosage Form:"));
        add(dosageForm);
        add(new JLabel("Drug Category:"));
        add(drugCategory);
        add(new JLabel("Side Effects:"));
        add(new JScrollPane(sideEffects));
        add(new JLabel("Storage Conditions:"));
        add(new JScrollPane(storageConditions));
        add(prescriptionRequired);

        // Add Submit and Cancel buttons
        add(submitButton);
        add(cancelButton);

        // ActionListener for Submit Button (save to database)
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Establish a connection to the database
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy", "root", "password");
                    String query = "INSERT INTO medications (medication_id, name, manufacturer, strength, price, quantity_in_stock, batch_number, medication_type, dosage_form, drug_category, side_effects, storage_conditions, prescription_required) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(query);

                    // Set values from form fields
                    stmt.setString(1, medicationID.getText());
                    stmt.setString(2, name.getText());
                    stmt.setString(3, manufacturer.getText());
                    stmt.setString(4, strength.getText());
                    stmt.setString(5, price.getText());
                    stmt.setString(6, quantityInStock.getText());
                    stmt.setString(7, batchNumber.getText());
                    stmt.setString(8, (String) medicationType.getSelectedItem());
                    stmt.setString(9, (String) dosageForm.getSelectedItem());
                    stmt.setString(10, (String) drugCategory.getSelectedItem());
                    stmt.setString(11, sideEffects.getText());
                    stmt.setString(12, storageConditions.getText());
                    stmt.setBoolean(13, prescriptionRequired.isSelected());

                    // Execute the query
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Medication Added Successfully");

                    // Close the connection
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error saving data");
                }
            }
        });

        // ActionListener for Cancel Button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reset all fields when cancel is pressed
                medicationID.setText("");
                name.setText("");
                manufacturer.setText("");
                strength.setText("");
                price.setText("");
                quantityInStock.setText("");
                batchNumber.setText("");
                sideEffects.setText("");
                storageConditions.setText("");
                medicationType.setSelectedIndex(0); // Reset combo box
                dosageForm.setSelectedIndex(0); // Reset combo box
                drugCategory.setSelectedIndex(0); // Reset combo box
                prescriptionRequired.setSelected(false); // Reset checkbox
            }
        });
    }

    // Main method to run the Pharmacy Panel
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PharmacyPanel().setVisible(true);
            }
        });
    }
}

