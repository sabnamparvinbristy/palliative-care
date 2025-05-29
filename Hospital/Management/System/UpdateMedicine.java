package Hospital.Management.System;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateMedicine extends JFrame implements ActionListener {

    JTextField medicineIDField, medicineStrength, medicinePrice, medicineStock;
    JButton searchBtn, saveBtn, backBtn;

    public UpdateMedicine() {
        setTitle("Update Medicine Details");
        getContentPane().setBackground(new Color(214, 245, 240));
        setLayout(null);

        JLabel heading = new JLabel("UPDATE MEDICINE DETAILS");
        heading.setBounds(280, 30, 500, 50);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        JLabel idLabel = new JLabel("Enter Medicine ID:");
        idLabel.setBounds(50, 100, 200, 30);
        idLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(idLabel);

        medicineIDField = new JTextField();
        medicineIDField.setBounds(250, 100, 150, 30);
        medicineIDField.setBackground(new Color(240, 233, 215));
        add(medicineIDField);

        searchBtn = new JButton("Search");
        searchBtn.setBounds(420, 100, 100, 30);
        searchBtn.setBackground(Color.BLACK);
        searchBtn.setForeground(Color.cyan);
        searchBtn.addActionListener(this);
        add(searchBtn);

        JLabel strengthLabel = new JLabel("Strength:");
        strengthLabel.setBounds(50, 160, 200, 30);
        strengthLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(strengthLabel);

        medicineStrength = new JTextField();
        medicineStrength.setBounds(250, 160, 150, 30);
        medicineStrength.setBackground(new Color(240, 233, 215));
        add(medicineStrength);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(50, 210, 200, 30);
        priceLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(priceLabel);

        medicinePrice = new JTextField();
        medicinePrice.setBounds(250, 210, 150, 30);
        medicinePrice.setBackground(new Color(240, 233, 215));
        add(medicinePrice);

        JLabel stockLabel = new JLabel("Stock Quantity:");
        stockLabel.setBounds(50, 260, 200, 30);
        stockLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(stockLabel);

        medicineStock = new JTextField();
        medicineStock.setBounds(250, 260, 150, 30);
        medicineStock.setBackground(new Color(240, 233, 215));
        add(medicineStock);

        saveBtn = new JButton("Save");
        saveBtn.setBounds(250, 330, 150, 35);
        saveBtn.setBackground(Color.BLACK);
        saveBtn.setForeground(Color.cyan);
        saveBtn.addActionListener(this);
        add(saveBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(420, 330, 150, 35);
        backBtn.setBackground(Color.BLACK);
        backBtn.setForeground(Color.cyan);
        backBtn.addActionListener(this);
        add(backBtn);

        setSize(800, 450);
        setLocation(300, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBtn) {
            String idText = medicineIDField.getText().trim();
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter Medicine ID");
                return;
            }

            try {
                Conn c = new Conn();
                String query = "SELECT * FROM medicine WHERE medicine_id = ?";
                PreparedStatement ps = c.connection.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(idText));
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    medicineStrength.setText(rs.getString("strength"));
                    medicinePrice.setText(rs.getString("price"));
                    medicineStock.setText(rs.getString("quantity_in_stock"));
                } else {
                    JOptionPane.showMessageDialog(null, "Medicine not found with ID: " + idText);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error retrieving medicine.");
            }
        }

        else if (e.getSource() == saveBtn) {
            String idText = medicineIDField.getText().trim();
            String strength = medicineStrength.getText().trim();
            String priceText = medicinePrice.getText().trim();
            String stockText = medicineStock.getText().trim();

            if (strength.isEmpty() || priceText.isEmpty() || stockText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields");
                return;
            }

            try {
                Conn c = new Conn();
                String query = "UPDATE medicine SET strength = ?, price = ?, quantity_in_stock = ? WHERE medicine_id = ?";
                PreparedStatement ps = c.connection.prepareStatement(query);
                ps.setString(1, strength);
                ps.setDouble(2, Double.parseDouble(priceText));
                ps.setInt(3, Integer.parseInt(stockText));
                ps.setInt(4, Integer.parseInt(idText));

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Medicine details updated successfully!");
                    setVisible(false);
                    new ViewMedicine(); // Refresh medicine view
                } else {
                    JOptionPane.showMessageDialog(null, "No update made. Check the ID.");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating medicine.");
            }
        }

        else if (e.getSource() == backBtn) {
            setVisible(false);
            new ViewMedicine();
        }
    }

    public static void main(String[] args) {
        new UpdateMedicine();
    }
}