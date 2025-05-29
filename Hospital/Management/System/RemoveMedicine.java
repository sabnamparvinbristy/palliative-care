package Hospital.Management.System;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RemoveMedicine extends JFrame implements ActionListener {

    JComboBox<String> medicineID;
    JButton removeButton, cancelButton, exitButton;
    JLabel nameLabel, manufacturerLabel, typeLabel;

    public RemoveMedicine() {
        setTitle("Remove Medicine Panel");
        getContentPane().setBackground(new Color(214, 245, 240));
        setLayout(null);

        JLabel heading = new JLabel("REMOVE MEDICINE FORM");
        heading.setBounds(300, 30, 500, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        // Row 1 - Medicine ID ComboBox
        addLabel("Medicine ID:", 50, 100);
        medicineID = new JComboBox<>();
        medicineID.setBounds(250, 100, 300, 30);
        medicineID.setBackground(new Color(240, 233, 215));
        add(medicineID);

        // Info Labels
        nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(250, 150, 400, 30);
        nameLabel.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        add(nameLabel);

        manufacturerLabel = new JLabel("Manufacturer: ");
        manufacturerLabel.setBounds(250, 180, 400, 30);
        manufacturerLabel.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        add(manufacturerLabel);

        typeLabel = new JLabel("Type: ");
        typeLabel.setBounds(250, 210, 400, 30);
        typeLabel.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        add(typeLabel);

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

        populateMedicineIDs();

        // Add action listener to combo box to fetch and show medicine info
        medicineID.addActionListener(e -> displayMedicineInfo());

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

    private void populateMedicineIDs() {
        try {
            Conn c = new Conn();
            String query = "SELECT medicine_id FROM medicine";
            PreparedStatement stmt = c.connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("medicine_id");
                medicineID.addItem(String.valueOf(id));
            }
            rs.close();
            stmt.close();
            c.connection.close();

            // Automatically display info for the first item after loading the combo box
            if (medicineID.getItemCount() > 0) {
                medicineID.setSelectedIndex(0); // Select the first item
                displayMedicineInfo(); // Show its info
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching medicine data.");
        }
    }

    private void displayMedicineInfo() {
        String selected = (String) medicineID.getSelectedItem();

        // Check if selected item is valid
        if (selected != null && !selected.isEmpty()) {
            try {
                Conn c = new Conn();
                String query = "SELECT medi_name, manufacturer, medicine_type FROM medicine WHERE medicine_id = ?";
                PreparedStatement stmt = c.connection.prepareStatement(query);
                stmt.setInt(1, Integer.parseInt(selected));  // Converting the selected item to an integer for the query

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    nameLabel.setText("Name: " + rs.getString("medi_name"));
                    manufacturerLabel.setText("Manufacturer: " + rs.getString("manufacturer"));
                    typeLabel.setText("Type: " + rs.getString("medicine_type"));
                    removeButton.setEnabled(true);
                } else {
                    clearInfoLabels(); // No result found, clear the labels
                }
                rs.close();
                stmt.close();
                c.connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                clearInfoLabels();  // In case of error, clear the labels
                JOptionPane.showMessageDialog(this, "Error fetching medicine information.");
            }
        } else {
            clearInfoLabels();  // In case selected item is null or empty
        }
    }


    private void clearInfoLabels() {
        nameLabel.setText("Name: ");
        manufacturerLabel.setText("Manufacturer: ");
        typeLabel.setText("Type: ");
        removeButton.setEnabled(false);
    }

    private void removeFromDatabase() {
        try {
            Conn c = new Conn();
            String selected = (String) medicineID.getSelectedItem();
            if (selected != null && !selected.isEmpty()) {
                String query = "DELETE FROM medicine WHERE medicine_id = ?";
                PreparedStatement stmt = c.connection.prepareStatement(query);
                stmt.setInt(1, Integer.parseInt(selected));

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Medicine removed successfully!");
                    medicineID.removeItem(selected); // remove from combo box
                    clearInfoLabels(); // reset info labels
                } else {
                    JOptionPane.showMessageDialog(this, "No medicine found with the given ID.");
                }
                stmt.close();
                c.connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error removing medicine from database.");
        }
    }

    private void resetFields() {
        medicineID.setSelectedIndex(0);
        clearInfoLabels();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == removeButton) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this medicine?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                removeFromDatabase();
            }
        } else if (e.getSource() == cancelButton) {
            resetFields();
        } else if (e.getSource() == exitButton) {
            setVisible(false);
            new PharmacyPanel(); // Replace with your main panel class
        }
    }

    public static void main(String[] args) {
        new RemoveMedicine();
    }
}