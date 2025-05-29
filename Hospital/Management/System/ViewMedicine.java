package Hospital.Management.System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class ViewMedicine extends JFrame {

    JTable table;
    DefaultTableModel model;
    JButton exitButton, addMedicineButton, removeMedicineButton, updateMedicineButton;

    public ViewMedicine() {
        setTitle("View Medicines");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(214, 245, 240));

        JLabel heading = new JLabel("VIEW MEDICINE LIST");
        heading.setBounds(500, 30, 500, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        model = new DefaultTableModel();
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        String[] columns = {
                "ID", "Name", "Manufacturer", "Strength", "Price", "Stock",
                "Batch No", "Type", "Dosage Form", "Category",
                "Side Effects", "Storage", "Prescription"
        };

        for (String col : columns) {
            model.addColumn(col);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 100, 1200, 350);
        add(scrollPane);

        // Add row click listener to show details
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    StringBuilder details = new StringBuilder();
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        String columnName = table.getColumnName(i);
                        Object value = table.getValueAt(row, i);
                        details.append(columnName).append(": ").append(value).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, details.toString(), "Medicine Details", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        loadDataFromDatabase();

        addMedicineButton = createButton("ADD MEDICINE", 200, 480);
        removeMedicineButton = createButton("REMOVE MEDICINE", 400, 480);
        updateMedicineButton = createButton("UPDATE MEDICINE", 600, 480);
        exitButton = createButton("BACK", 800, 480);

        add(addMedicineButton);
        add(removeMedicineButton);
        add(updateMedicineButton);
        add(exitButton);

        addMedicineButton.addActionListener(e -> openAddMedicine());
        removeMedicineButton.addActionListener(e -> openRemoveMedicine());
        updateMedicineButton.addActionListener(e -> openUpdateMedicine());
        exitButton.addActionListener(e -> {
            dispose();
            new PharmacyPanel();
        });

        setVisible(true);
    }

    private void loadDataFromDatabase() {
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM medicine";
            PreparedStatement stmt = c.connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            model.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("medicine_id"),
                        rs.getString("medi_name"),
                        rs.getString("manufacturer"),
                        rs.getString("strength"),
                        rs.getBigDecimal("price"),
                        rs.getInt("quantity_in_stock"),
                        rs.getString("batch_number"),
                        rs.getString("medicine_type"),
                        rs.getString("dosage_form"),
                        rs.getString("drug_category"),
                        rs.getString("side_effects"),
                        rs.getString("storage_conditions"),
                        rs.getBoolean("prescription_required") ? "Yes" : "No"
                };
                model.addRow(row);
            }

            rs.close();
            stmt.close();
            c.connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load medicines from database.");
        }
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 150, 35);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.cyan);
        button.setFocusPainted(false);
        return button;
    }

    private void openAddMedicine() {
        dispose();
        new AddMedicine();
    }

    private void openRemoveMedicine() {
        dispose();
        new RemoveMedicine();
    }

    private void openUpdateMedicine() {
        dispose();
        new UpdateMedicine();
    }

    public static void main(String[] args) {
        new ViewMedicine();
    }
}
