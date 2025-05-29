package Hospital.Management.System;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddMedicine extends JFrame implements ActionListener {

    JTextField medicineID, name, manufacturer, strength, price, quantityInStock, batchNumber;
    JComboBox<String> medicineType, dosageForm, drugCategory;
    JCheckBox prescriptionRequired;
    JTextArea sideEffects, storageConditions;
    JButton submitButton, cancelButton, exitButton;

    public AddMedicine() {
        setTitle("Add Medicine Panel");
        getContentPane().setBackground(new Color(214, 245, 240));
        setLayout(null);

        JLabel heading = new JLabel("ADD MEDICINE FORM");
        heading.setBounds(300, 30, 500, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        // Row 1
        addLabel("Medicine ID:", 50, 100);
        medicineID = createTextField(250, 100);
        add(medicineID);

        addLabel("Medicine Name:", 470, 100);
        name = createTextField(640, 100);
        add(name);

        // Row 2
        addLabel("Manufacturer:", 50, 150);
        manufacturer = createTextField(250, 150);
        add(manufacturer);

        addLabel("Strength:", 470, 150);
        strength = createTextField(640, 150);
        add(strength);

        // Row 3
        addLabel("Price:", 50, 200);
        price = createTextField(250, 200);
        add(price);

        addLabel("Quantity in Stock:", 470, 200);
        quantityInStock = createTextField(640, 200);
        add(quantityInStock);

        // Row 4
        addLabel("Batch Number:", 50, 250);
        batchNumber = createTextField(250, 250);
        add(batchNumber);

        addLabel("Medicine Type:", 470, 250);
        medicineType = createComboBox(new String[]{"Tablet", "Syrup", "Injection", "Ointment"}, 640, 250);
        add(medicineType);

        // Row 5
        addLabel("Dosage Form:", 50, 300);
        dosageForm = createComboBox(new String[]{"Oral", "Topical", "Injectable"}, 250, 300);
        add(dosageForm);

        addLabel("Drug Category:", 470, 300);
        drugCategory = createComboBox(new String[]{"Painkiller", "Antibiotic", "Antipyretic", "Other"}, 640, 300);
        add(drugCategory);

        // Row 6
        addLabel("Side Effects:", 50, 350);
        sideEffects = new JTextArea();
        JScrollPane sideEffectsScroll = new JScrollPane(sideEffects);
        sideEffectsScroll.setBounds(250, 350, 540, 50);
        add(sideEffectsScroll);

        // Row 7
        addLabel("Storage Conditions:", 50, 420);
        storageConditions = new JTextArea();
        JScrollPane storageScroll = new JScrollPane(storageConditions);
        storageScroll.setBounds(250, 420, 540, 50);
        add(storageScroll);

        // Row 8
        prescriptionRequired = new JCheckBox("Prescription Required");
        prescriptionRequired.setBounds(250, 490, 200, 30);
        prescriptionRequired.setBackground(new Color(203, 241, 194));
        add(prescriptionRequired);

        // Buttons
        submitButton = createButton("SUBMIT", 180, 550);
        cancelButton = createButton("CLEAR", 360, 550);
        exitButton = createButton("BACK", 540, 550);
        add(submitButton);
        add(cancelButton);
        add(exitButton);

        submitButton.addActionListener(this);
        cancelButton.addActionListener(this);
        exitButton.addActionListener(this);

        setSize(900, 700);
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

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 150, 35);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.cyan);
        button.setFocusPainted(false);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            saveToDatabase();
        } else if (e.getSource() == cancelButton) {
            resetFields();
        } else if (e.getSource() == exitButton) {
            //System.exit(0);
            setVisible(false);
            new PharmacyPanel();
        }
    }

    private void saveToDatabase() {
        try {
            Conn c = new Conn();
            String query = "INSERT INTO medicine (medi_name, manufacturer, strength, price, quantity_in_stock, batch_number, medicine_type, dosage_form, drug_category, side_effects, storage_conditions, prescription_required) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Request auto-generated keys from the DB
            java.sql.PreparedStatement stmt = c.connection.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, name.getText());
            stmt.setString(2, manufacturer.getText());
            stmt.setString(3, strength.getText());
            stmt.setBigDecimal(4, new java.math.BigDecimal(price.getText()));
            stmt.setInt(5, Integer.parseInt(quantityInStock.getText()));
            stmt.setString(6, batchNumber.getText());
            stmt.setString(7, (String) medicineType.getSelectedItem());
            stmt.setString(8, (String) dosageForm.getSelectedItem());
            stmt.setString(9, (String) drugCategory.getSelectedItem());
            stmt.setString(10, sideEffects.getText());
            stmt.setString(11, storageConditions.getText());
            stmt.setBoolean(12, prescriptionRequired.isSelected());

            stmt.executeUpdate();

            // Retrieve the generated key (medicine_id)
            java.sql.ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                JOptionPane.showMessageDialog(this, "Medicine added successfully! Generated ID: " + generatedId);
            } else {
                JOptionPane.showMessageDialog(this, "Medicine added, but no ID was returned.");
            }

            rs.close();
            stmt.close();
            c.connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving medicine to database.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for price and quantity.");
        }
    }




    private void resetFields() {
        medicineID.setText("");
        name.setText("");
        manufacturer.setText("");
        strength.setText("");
        price.setText("");
        quantityInStock.setText("");
        batchNumber.setText("");
        sideEffects.setText("");
        storageConditions.setText("");
        medicineType.setSelectedIndex(0);
        dosageForm.setSelectedIndex(0);
        drugCategory.setSelectedIndex(0);
        prescriptionRequired.setSelected(false);
    }

    public static void main(String[] args) {
        new AddMedicine();
    }
}