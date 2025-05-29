package Hospital.Management.System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class BillingSummaryPanel extends JFrame {

    JTable billingTable;
    JLabel totalCountLabel;
    BillingPanel billingPanelRef;

    public BillingSummaryPanel(BillingPanel billingPanel) {
        this.billingPanelRef = billingPanel;

        setTitle("Billing Summary");
        setSize(900, 550);
        setLocation(300, 100);
        setLayout(new BorderLayout());

        // Main background color
        getContentPane().setBackground(new Color(214, 245, 240));

        String[] columns = {
                "Billing ID", "Patient ID", "Patient Name", "Amount", "Discount (%)", "Insurance Coverage",
                "Final Amount", "Outstanding Amount", "Billing Date", "Payment Date", "Payment Method", "Billing Status"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        billingTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(billingTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(214, 245, 240)); // Set panel color

        totalCountLabel = new JLabel("Total Billed Patients: 0");
        totalCountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalCountLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(totalCountLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(214, 245, 240)); // Set panel color

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setFont(new Font("Arial", Font.BOLD, 14));
        deleteBtn.addActionListener(e -> deleteSelectedBilling(model));
        buttonPanel.add(deleteBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setPreferredSize(new Dimension(100, 40));
        backBtn.addActionListener(e -> {
            this.dispose();
            billingPanelRef.setVisible(true);
        });
        buttonPanel.add(backBtn);

        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        loadBillingData(model);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void loadBillingData(DefaultTableModel model) {
        Conn c = null;
        ResultSet rs = null;
        try {
            c = new Conn();
            String query = "SELECT b.billing_id, b.patient_id, p.full_name, b.amount, b.discount, b.insurance_coverage, " +
                    "b.final_amount, b.outstanding_amount, b.billing_date, b.payment_date, b.payment_method, b.billing_status " +
                    "FROM billing b JOIN patients p ON b.patient_id = p.patient_id ORDER BY b.billing_id";
            rs = c.statement.executeQuery(query);

            int count = 0;
            model.setRowCount(0);
            while (rs.next()) {
                count++;
                model.addRow(new Object[]{
                        rs.getInt("billing_id"),
                        rs.getInt("patient_id"),
                        rs.getString("full_name"),
                        rs.getDouble("amount"),
                        rs.getDouble("discount"),
                        rs.getString("insurance_coverage"),
                        rs.getDouble("final_amount"),
                        rs.getDouble("outstanding_amount"),
                        rs.getDate("billing_date"),
                        rs.getDate("payment_date"),
                        rs.getString("payment_method"),
                        rs.getString("billing_status")
                });
            }
            totalCountLabel.setText("Total Billed Patients: " + count);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading billing data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (c != null) {
                    if (c.statement != null) c.statement.close();
                    if (c.connection != null) c.connection.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void deleteSelectedBilling(DefaultTableModel model) {
        int row = billingTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a billing record to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete the selected billing record?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int billingId = (int) model.getValueAt(row, 0);

        Conn c = null;
        try {
            c = new Conn();
            String sql = "DELETE FROM billing WHERE billing_id = ?";
            PreparedStatement ps = c.connection.prepareStatement(sql);
            ps.setInt(1, billingId);

            int deleted = ps.executeUpdate();
            if (deleted > 0) {
                JOptionPane.showMessageDialog(this, "Billing record deleted successfully.");
                loadBillingData(model);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete billing record.");
            }

            ps.close();
            c.statement.close();
            c.connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting billing record: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BillingSummaryPanel(null); // Pass actual BillingPanel reference when integrating
    }
}
