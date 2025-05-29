package Hospital.Management.System;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewTreatment extends JFrame implements ActionListener {

    JTable table;
    JTextField treatmentIdField;
    JButton searchbtn, print, update, remove, back;

    public ViewTreatment() {
        getContentPane().setBackground(new Color(214, 245, 240));
        setLayout(null);

        JLabel search_treatment = new JLabel("Enter Treatment ID:");
        search_treatment.setBounds(20, 20, 150, 20);
        add(search_treatment);

        treatmentIdField = new JTextField();
        treatmentIdField.setBounds(180, 20, 150, 20);
        add(treatmentIdField);

        treatmentIdField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performSearch();
                }
            }
        });

        table = new JTable();
        loadTreatmentTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 100, 1300, 550);
        add(scrollPane);

        // 🖱️ Add mouse click listener for row details
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    showTreatmentDetailsDialog(row);
                }
            }
        });

        searchbtn = new JButton("Search");
        searchbtn.setBounds(20, 70, 80, 20);
        searchbtn.addActionListener(this);
        add(searchbtn);

        print = new JButton("Print");
        print.setBounds(120, 70, 80, 20);
        print.addActionListener(this);
        add(print);

        update = new JButton("Update");
        update.setBounds(220, 70, 80, 20);
        update.addActionListener(this);
        add(update);

        remove = new JButton("Remove");
        remove.setBounds(320, 70, 90, 20);
        remove.addActionListener(this);
        add(remove);

        back = new JButton("Back");
        back.setBounds(430, 70, 80, 20);
        back.addActionListener(this);
        add(back);

        setSize(1350, 700);
        setLocation(100, 50);
        setVisible(true);
    }

    private void loadTreatmentTable() {
        try {
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM treatment");
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            setColumnWidths();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setColumnWidths() {
        int[] widths = {80, 100, 100, 200, 150, 150, 120, 120, 200, 150, 150};
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < widths.length && i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(widths[i]);
        }
    }

    private void performSearch() {
        String enteredID = treatmentIdField.getText().trim();
        if (enteredID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Treatment ID.");
            return;
        }
        String query = "SELECT * FROM treatment WHERE treatment_id = '" + enteredID + "'";
        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(rs));
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            setColumnWidths();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void removeTreatment() {
        String treatmentID = treatmentIdField.getText().trim();
        if (treatmentID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Treatment ID to remove.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete Treatment ID: " + treatmentID + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Conn c = new Conn();
                String query = "DELETE FROM treatment WHERE treatment_id = '" + treatmentID + "'";
                int rowsAffected = c.statement.executeUpdate(query);
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Treatment record deleted successfully.");
                    loadTreatmentTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Treatment ID not found.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void showTreatmentDetailsDialog(int row) {
        String[] columnNames = {
                "Treatment ID", "Patient ID", "Doctor ID", "Diagnosis",
                "Treatment Type", "Medications", "Start Date", "End Date",
                "Notes", "Side Effects", "Outcome"
        };

        StringBuilder details = new StringBuilder();
        for (int i = 0; i < table.getColumnCount(); i++) {
            String column = (i < columnNames.length) ? columnNames[i] : "Column " + (i + 1);
            String value = String.valueOf(table.getValueAt(row, i));
            details.append(column).append(": ").append(value).append("\n");
        }

        JTextArea textArea = new JTextArea(details.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Treatment Details", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchbtn) {
            performSearch();
        } else if (e.getSource() == print) {
            try {
                table.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == update) {
            String enteredID = treatmentIdField.getText().trim();
            if (enteredID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Treatment ID.");
            } else {
                setVisible(false);
                new UpdateTreatment();
            }
        } else if (e.getSource() == remove) {
            removeTreatment();
        } else if (e.getSource() == back) {
            setVisible(false);
            new TreatmentPanel();
        }
    }

    public static void main(String[] args) {
        new ViewTreatment();
    }
}
