package Hospital.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.toedter.calendar.JDateChooser;

public class MedicationPanel extends JFrame {

    // Declare components for the medication form
    JTextField medicationID, name, manufacturer, strength, price, quantityInStock, batchNumber;
    JComboBox<String> medicationType, dosageForm, drugCategory;
    JCheckBox prescriptionRequired;
    JDateChooser expirationDate;
    JTextArea sideEffects, storageConditions;
    JButton submitButton, cancelButton;

    // Constructor for the MedicationPanel
    public MedicationPanel() {
        setTitle("Medication Panel");
        setSize(850, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window on the screen
        setLayout(new BorderLayout());

        // Background color for the frame
        getContentPane().setBackground(new Color(214, 245, 240));


        // Header Panel (title of the form)
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(28, 40, 51));  // Dark blue header
        JLabel headerLabel = new JLabel("Medication Information Form");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Form Fields Panel (center)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(14, 2, 15, 15)); // grid layout with gaps
        formPanel.setBackground(new Color(242, 246, 249));

        // Initialize components (text fields, combo boxes, text areas)
        medicationID = new JTextField(20);
        name = new JTextField(20);
        manufacturer = new JTextField(20);
        strength = new JTextField(20);
        price = new JTextField(10);
        quantityInStock = new JTextField(10);
        batchNumber = new JTextField(20);

        medicationType = new JComboBox<>(new String[]{"Tablet", "Syrup", "Injection", "Ointment"});
        dosageForm = new JComboBox<>(new String[]{"Oral", "Topical", "Injectable"});
        drugCategory = new JComboBox<>(new String[]{"Painkiller", "Antibiotic", "Antipyretic", "Other"});

        prescriptionRequired = new JCheckBox("Prescription Required");

        expirationDate = new JDateChooser();

        sideEffects = new JTextArea(5, 20);
        storageConditions = new JTextArea(5, 20);

        // Add components to formPanel
        formPanel.add(new JLabel("Medication ID:"));
        formPanel.add(medicationID);
        formPanel.add(new JLabel("Medication Name:"));
        formPanel.add(name);
        formPanel.add(new JLabel("Manufacturer:"));
        formPanel.add(manufacturer);
        formPanel.add(new JLabel("Strength:"));
        formPanel.add(strength);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(price);
        formPanel.add(new JLabel("Quantity in Stock:"));
        formPanel.add(quantityInStock);
        formPanel.add(new JLabel("Batch Number:"));
        formPanel.add(batchNumber);
        formPanel.add(new JLabel("Medication Type:"));
        formPanel.add(medicationType);
        formPanel.add(new JLabel("Dosage Form:"));
        formPanel.add(dosageForm);
        formPanel.add(new JLabel("Drug Category:"));
        formPanel.add(drugCategory);
        formPanel.add(new JLabel("Expiration Date:"));
        formPanel.add(expirationDate);
        formPanel.add(new JLabel("Side Effects:"));
        formPanel.add(new JScrollPane(sideEffects));
        formPanel.add(new JLabel("Storage Conditions:"));
        formPanel.add(new JScrollPane(storageConditions));
        formPanel.add(prescriptionRequired);

        // Add formPanel to the center of the frame
        add(formPanel, BorderLayout.CENTER);

        // Footer panel with buttons
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(13, 51, 52));  // Dark blue footer
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        // Submit Button with styling
        submitButton = new JButton("Submit");
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(new Color(13, 51, 52));
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setPreferredSize(new Dimension(120, 40));
        submitButton.setFocusPainted(false);  // removes the focus border
        submitButton.setBorder(BorderFactory.createLineBorder(new Color(13, 51, 52)));
        footerPanel.add(submitButton);

        // Cancel Button with styling
        cancelButton = new JButton("Cancel");
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(13, 51, 52));
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createLineBorder(new Color(13, 51, 52)));
        footerPanel.add(cancelButton);

        // Add footerPanel to the bottom of the frame
        add(footerPanel, BorderLayout.SOUTH);

        // ActionListener for Submit Button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle form submission (collect data)
                System.out.println("Medication Details:");
                System.out.println("Medication ID: " + medicationID.getText());
                System.out.println("Name: " + name.getText());
                System.out.println("Manufacturer: " + manufacturer.getText());
                System.out.println("Strength: " + strength.getText());
                System.out.println("Price: " + price.getText());
                System.out.println("Quantity in Stock: " + quantityInStock.getText());
                System.out.println("Batch Number: " + batchNumber.getText());
                System.out.println("Expiration Date: " + expirationDate.getDate());
                System.out.println("Prescription Required: " + prescriptionRequired.isSelected());
                System.out.println("Side Effects: " + sideEffects.getText());
                System.out.println("Storage Conditions: " + storageConditions.getText());
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
                expirationDate.setDate(null); // Reset date
                prescriptionRequired.setSelected(false); // Reset checkbox
            }
        });
    }

    // Main method to run the Medication Panel
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MedicationPanel().setVisible(true);
            }
        });
    }
}
