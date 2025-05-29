package Hospital.Management.System;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.awt.print.*;

public class BillingPanel extends JFrame implements ActionListener, Printable {

    private static int billingIdCounter = 14280001; // initial default

    JLabel billingID;  // Billing ID shown as red label
    JTextField patientID, amount, discountField, insuranceCoverage;
    JComboBox<String> paymentMethod, billingStatus;
    JDateChooser billingDate, paymentDate;
    JButton submitButton, cancelButton, printButton, searchPatientBtn, viewAllBillsBtn;
    JLabel finalAmountLabel, outstandingAmountLabel;

    private double finalAmountValue = 0;
    private double outstandingAmountValue = 0;

    public BillingPanel() {
        setTitle("Billing Panel");
        setSize(900, 600);
        setLocation(300, 50);
        setLayout(null);
        getContentPane().setBackground(new Color(214, 245, 240));

        // Header
        JLabel headerLabel = new JLabel("BILLING INFORMATION");
        headerLabel.setBounds(250, 20, 400, 40);
        headerLabel.setFont(new Font("Raleway", Font.BOLD, 25));
        headerLabel.setForeground(new Color(6, 44, 63));
        add(headerLabel);

        int labelX = 50, labelWidth = 160, fieldX = 220, fieldWidth = 250, rowHeight = 30;
        int startY = 80, gapY = 45;

        // Billing ID label and value
        JLabel billingIDLabel = new JLabel("Billing ID:");
        billingIDLabel.setBounds(labelX, startY, labelWidth, rowHeight);
        billingIDLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        add(billingIDLabel);

        billingID = new JLabel();
        billingID.setBounds(fieldX, startY, fieldWidth, rowHeight);
        billingID.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        billingID.setForeground(new Color(147, 37, 42));
        add(billingID);

        // Patient ID label and field
        JLabel patientIDLabel = new JLabel("Patient ID:");
        patientIDLabel.setBounds(labelX, startY + gapY, labelWidth, rowHeight);
        add(patientIDLabel);

        patientID = new JTextField();
        patientID.setBounds(fieldX, startY + gapY, fieldWidth - 100, rowHeight);
        patientID.setEditable(false);
        add(patientID);

        searchPatientBtn = new JButton("Search");
        searchPatientBtn.setBounds(fieldX + fieldWidth - 90, startY + gapY, 90, rowHeight);
        searchPatientBtn.setBackground(new Color(6, 44, 63));
        searchPatientBtn.setForeground(Color.WHITE);
        searchPatientBtn.addActionListener(e -> openPatientSearchDialog());
        add(searchPatientBtn);

        // Amount label and field
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(labelX, startY + 2 * gapY, labelWidth, rowHeight);
        add(amountLabel);

        amount = new JTextField();
        amount.setBounds(fieldX, startY + 2 * gapY, fieldWidth, rowHeight);
        add(amount);

        // Discount label and field
        JLabel discountLabel = new JLabel("Discount (%):");
        discountLabel.setBounds(labelX, startY + 3 * gapY, labelWidth, rowHeight);
        add(discountLabel);

        discountField = new JTextField();
        discountField.setBounds(fieldX, startY + 3 * gapY, fieldWidth, rowHeight);
        add(discountField);

        // Insurance Coverage label and field
        JLabel insuranceLabel = new JLabel("Insurance Coverage:");
        insuranceLabel.setBounds(labelX, startY + 4 * gapY, labelWidth, rowHeight);
        add(insuranceLabel);

        insuranceCoverage = new JTextField();
        insuranceCoverage.setBounds(fieldX, startY + 4 * gapY, fieldWidth, rowHeight);
        add(insuranceCoverage);

        // Add document listeners for live calculation on amount, discount, and insurance coverage
        DocumentListener calcListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateAmounts(); }
            public void removeUpdate(DocumentEvent e) { updateAmounts(); }
            public void changedUpdate(DocumentEvent e) { updateAmounts(); }
        };

        amount.getDocument().addDocumentListener(calcListener);
        discountField.getDocument().addDocumentListener(calcListener);
        insuranceCoverage.getDocument().addDocumentListener(calcListener);

        // Billing Date label and chooser
        JLabel billingDateLabel = new JLabel("Billing Date:");
        billingDateLabel.setBounds(480, startY, labelWidth, rowHeight);
        add(billingDateLabel);

        billingDate = new JDateChooser();
        billingDate.setBounds(650, startY, fieldWidth - 100, rowHeight);
        add(billingDate);

        // Payment Date label and chooser
        JLabel paymentDateLabel = new JLabel("Payment Date:");
        paymentDateLabel.setBounds(480, startY + gapY, labelWidth, rowHeight);
        add(paymentDateLabel);

        paymentDate = new JDateChooser();
        paymentDate.setBounds(650, startY + gapY, fieldWidth - 100, rowHeight);
        add(paymentDate);

        // Payment Method label and combo
        JLabel paymentMethodLabel = new JLabel("Payment Method:");
        paymentMethodLabel.setBounds(480, startY + 2 * gapY, labelWidth, rowHeight);
        add(paymentMethodLabel);

        paymentMethod = new JComboBox<>(new String[]{"Cash", "Card", "Insurance", "Other"});
        paymentMethod.setBounds(650, startY + 2 * gapY, fieldWidth - 100, rowHeight);
        add(paymentMethod);

        // Billing Status label and combo
        JLabel billingStatusLabel = new JLabel("Billing Status:");
        billingStatusLabel.setBounds(480, startY + 3 * gapY, labelWidth, rowHeight);
        add(billingStatusLabel);

        billingStatus = new JComboBox<>(new String[]{"Paid", "Unpaid", "Pending"});
        billingStatus.setBounds(650, startY + 3 * gapY, fieldWidth - 100, rowHeight);
        add(billingStatus);

        // Final Amount and Outstanding Amount labels
        finalAmountLabel = new JLabel("Final Amount: 0.00");
        finalAmountLabel.setBounds(labelX, startY + 6 * gapY, 200, rowHeight);
        finalAmountLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(finalAmountLabel);

        outstandingAmountLabel = new JLabel("Outstanding Amount: 0.00");
        outstandingAmountLabel.setBounds(labelX + 250, startY + 6 * gapY, 250, rowHeight);
        outstandingAmountLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(outstandingAmountLabel);

        // Buttons: Submit, Cancel, Print
        JButton backButton = new JButton("BACK");
        backButton.setBounds(50, startY + 8 * gapY, 120, 40);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.CYAN);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            dispose();         // Close current window
            new Dashboard();   // Go back to Dashboard
        });
        add(backButton);

        submitButton = new JButton("Submit");
        submitButton.setBounds(200, startY + 8 * gapY, 120, 40);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.CYAN);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(this);
        add(submitButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(350, startY + 8 * gapY, 120, 40);
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setForeground(Color.CYAN);
        cancelButton.setFont(new Font("Raleway", Font.BOLD, 16));
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(this);
        add(cancelButton);

        printButton = new JButton("Print");
        printButton.setBounds(500, startY + 8 * gapY, 120, 40);
        printButton.setBackground(Color.BLACK);
        printButton.setForeground(Color.CYAN);
        printButton.setFont(new Font("Raleway", Font.BOLD, 16));
        printButton.setFocusPainted(false);
        printButton.addActionListener(this);
        add(printButton);

        viewAllBillsBtn = new JButton("View All Bills");
        viewAllBillsBtn.setBounds(650, startY + 8 * gapY, 120, 40);
        viewAllBillsBtn.setBackground(Color.BLACK);
        viewAllBillsBtn.setForeground(Color.CYAN);
        viewAllBillsBtn.setFont(new Font("Raleway", Font.BOLD, 16));
        viewAllBillsBtn.setFocusPainted(false);
        viewAllBillsBtn.addActionListener(e -> {
            this.setVisible(false);
            new BillingSummaryPanel(this);
        });
        add(viewAllBillsBtn);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeBillingIdCounter();

        generateBillingID();

        setVisible(true);
    }

    private void initializeBillingIdCounter() {
        Conn c = null;
        java.sql.ResultSet rs = null;
        try {
            c = new Conn();
            String query = "SELECT MAX(billing_id) AS maxId FROM billing";
            rs = c.statement.executeQuery(query);
            if (rs.next()) {
                int maxId = rs.getInt("maxId");
                if (maxId > 0) {
                    billingIdCounter = maxId + 1;
                }
            }
        } catch (Exception e) {
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

    private void updateAmounts() {
        try {
            double total = amount.getText().isEmpty() ? 0 : Double.parseDouble(amount.getText());
            double discountPercent = discountField.getText().isEmpty() ? 0 : Double.parseDouble(discountField.getText());
            double insuranceCov = insuranceCoverage.getText().isEmpty() ? 0 : Double.parseDouble(insuranceCoverage.getText());

            double discountAmount = total * discountPercent / 100.0;
            finalAmountValue = total - discountAmount - insuranceCov;
            if (finalAmountValue < 0) finalAmountValue = 0;  // Prevent negative values
            outstandingAmountValue = finalAmountValue;

            finalAmountLabel.setText(String.format("Final Amount: %.2f", finalAmountValue));
            outstandingAmountLabel.setText(String.format("Outstanding Amount: %.2f", outstandingAmountValue));
        } catch (NumberFormatException e) {
            finalAmountLabel.setText("Final Amount: 0.00");
            outstandingAmountLabel.setText("Outstanding Amount: 0.00");
        }
    }

    private void generateBillingID() {
        billingID.setText(String.valueOf(billingIdCounter));
    }

    private void openPatientSearchDialog() {
        JDialog dialog = new JDialog(this, "Search Patient", true);
        dialog.setSize(600, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new FlowLayout());
        JLabel searchLabel = new JLabel("Enter Patient Name or ID:");
        JTextField searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");

        panel.add(searchLabel);
        panel.add(searchField);
        panel.add(searchBtn);

        dialog.add(panel, BorderLayout.NORTH);

        String[] columns = {"Patient ID", "Full Name", "Category"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        dialog.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton okBtn = new JButton("OK");
        JButton cancelBtn = new JButton("Cancel");
        bottomPanel.add(okBtn);
        bottomPanel.add(cancelBtn);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> {
            model.setRowCount(0);
            String keyword = searchField.getText().trim();
            if (keyword.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter a search keyword.");
                return;
            }

            Conn c = null;
            java.sql.ResultSet rs = null;

            try {
                c = new Conn();
                String query = "SELECT patient_id, full_name, category FROM patients WHERE full_name LIKE '%" + keyword + "%' OR patient_id LIKE '%" + keyword + "%'";
                rs = c.statement.executeQuery(query);
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    model.addRow(new Object[]{
                            rs.getInt("patient_id"),
                            rs.getString("full_name"),
                            rs.getString("category")
                    });
                }
                if (!found) {
                    JOptionPane.showMessageDialog(dialog, "No data available.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
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
        });

        okBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Object pid = model.getValueAt(row, 0);
                patientID.setText(String.valueOf(pid));
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Please select a patient first.");
            }
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            try {
                updateAmounts();

                Conn c = new Conn();

                String sql = "INSERT INTO billing (billing_id, patient_id, amount, discount, insurance_coverage, billing_date, payment_date, payment_method, billing_status, final_amount, outstanding_amount) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                java.sql.PreparedStatement ps = c.connection.prepareStatement(sql);

                ps.setInt(1, billingIdCounter);
                ps.setInt(2, Integer.parseInt(patientID.getText()));
                ps.setDouble(3, Double.parseDouble(amount.getText()));
                ps.setDouble(4, discountField.getText().isEmpty() ? 0 : Double.parseDouble(discountField.getText()));
                ps.setString(5, insuranceCoverage.getText());

                java.sql.Date billDate = billingDate.getDate() != null ? new java.sql.Date(billingDate.getDate().getTime()) : null;
                java.sql.Date payDate = paymentDate.getDate() != null ? new java.sql.Date(paymentDate.getDate().getTime()) : null;

                ps.setDate(6, billDate);
                ps.setDate(7, payDate);
                ps.setString(8, (String) paymentMethod.getSelectedItem());
                ps.setString(9, (String) billingStatus.getSelectedItem());
                ps.setDouble(10, finalAmountValue);
                ps.setDouble(11, outstandingAmountValue);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Billing submitted with ID: " + billingID.getText());

                billingIdCounter++;
                generateBillingID();
                resetFields();

                ps.close();
                c.statement.close();
                c.connection.close();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving billing: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else if (e.getSource() == cancelButton) {
            resetFields();
        } else if (e.getSource() == printButton) {
            printBillingPaper();
        }
    }

    private void resetFields() {
        patientID.setText("");
        amount.setText("");
        discountField.setText("");
        insuranceCoverage.setText("");
        billingDate.setDate(null);
        paymentDate.setDate(null);
        paymentMethod.setSelectedIndex(0);
        billingStatus.setSelectedIndex(0);
        finalAmountLabel.setText("Final Amount: 0.00");
        outstandingAmountLabel.setText("Outstanding Amount: 0.00");
        generateBillingID();
    }

    private void printBillingPaper() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Print Billing Paper");

        job.setPrintable(this);

        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "Print Error: " + ex.getMessage());
            }
        }
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        if (page > 0) return NO_SUCH_PAGE;

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));
        int y = 20;
        int lineHeight = 15;

        g2d.drawString("=== Hospital Billing Receipt ===", 100, y);
        y += lineHeight * 2;

        g2d.drawString("Billing ID: " + billingID.getText(), 10, y);
        y += lineHeight;

        g2d.drawString("Patient ID: " + patientID.getText(), 10, y);
        y += lineHeight;

        g2d.drawString("Billing Date: " + (billingDate.getDate() != null ? billingDate.getDate().toString() : "N/A"), 10, y);
        y += lineHeight;

        g2d.drawString("Payment Date: " + (paymentDate.getDate() != null ? paymentDate.getDate().toString() : "N/A"), 10, y);
        y += lineHeight;

        g2d.drawString("Payment Method: " + paymentMethod.getSelectedItem(), 10, y);
        y += lineHeight;

        g2d.drawString("Amount: " + amount.getText(), 10, y);
        y += lineHeight;

        g2d.drawString("Discount (%): " + discountField.getText(), 10, y);
        y += lineHeight;

        g2d.drawString("Insurance Coverage: " + insuranceCoverage.getText(), 10, y);
        y += lineHeight;

        g2d.drawString("Billing Status: " + billingStatus.getSelectedItem(), 10, y);
        y += lineHeight;

        g2d.drawString(String.format("Final Amount: %.2f", finalAmountValue), 10, y);
        y += lineHeight;

        g2d.drawString(String.format("Outstanding Amount: %.2f", outstandingAmountValue), 10, y);
        y += lineHeight;

        g2d.drawString("=== Thank you for your payment! ===", 100, y);

        return PAGE_EXISTS;
    }

    public static void main(String[] args) {
        new BillingPanel();
    }
}
