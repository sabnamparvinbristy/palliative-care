package Hospital.Management.System;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewPatient extends JFrame implements ActionListener {

    JTable table;
    JTextField patientIDField;
    JButton searchbtn, print, update, remove, back;

    public ViewPatient() {
        getContentPane().setBackground(new Color(214, 245, 240));
        setLayout(null);

        JLabel searchLabel = new JLabel("Enter Patient ID:");
        searchLabel.setBounds(20, 20, 150, 20);
        add(searchLabel);

        patientIDField = new JTextField();
        patientIDField.setBounds(180, 20, 150, 20);
        add(patientIDField);

        patientIDField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performSearch();
                }
            }
        });

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 100, 1300, 550);
        add(scrollPane);

        // Mouse click listener
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    showPatientDetailsDialog(row);
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

        loadPatientTable();
    }

    public ViewPatient(String highlightPatientId) {
        this();
        if (highlightPatientId != null && !highlightPatientId.isEmpty()) {
            patientIDField.setText(highlightPatientId);
            highlightRowByPatientId(highlightPatientId);
        }
    }

    private void loadPatientTable() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery(
                    "SELECT patient_id, full_name, dob, gender, contact, address, category, emergency_contact, age FROM patients"
            );
            table.setModel(DbUtils.resultSetToTableModel(rs));
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            setColumnWidths();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setColumnWidths() {
        int[] widths = {100, 150, 100, 70, 120, 200, 100, 120, 50};
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < widths.length && i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(widths[i]);
        }
    }

    private void highlightRowByPatientId(String patientId) {
        for (int i = 0; i < table.getRowCount(); i++) {
            String currentId = table.getValueAt(i, 0).toString();
            if (currentId.equals(patientId)) {
                table.setRowSelectionInterval(i, i);
                table.scrollRectToVisible(table.getCellRect(i, 0, true));
                break;
            }
        }
    }

    private void performSearch() {
        String enteredID = patientIDField.getText().trim();
        if (enteredID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Patient ID.");
            return;
        }
        String query = "SELECT patient_id, full_name, dob, gender, contact, address, category, emergency_contact, age FROM patients WHERE patient_id = '" + enteredID + "'";
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

    // New method using table row, not DB
    private void showPatientDetailsDialog(int row) {
        String[] columnNames = {
                "Patient ID", "Full Name", "Date of Birth", "Gender",
                "Contact", "Address", "Category", "Emergency Contact", "Age"
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

        JOptionPane.showMessageDialog(this, scrollPane, "Patient Details", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String enteredID = patientIDField.getText().trim();

        if (e.getSource() == searchbtn) {
            performSearch();
        } else if (e.getSource() == print) {
            try {
                table.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == update) {
            if (enteredID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Patient ID.");
            } else {
                setVisible(false);
                new UpdatePatient(enteredID);
            }
        } else if (e.getSource() == remove) {
            setVisible(false);
            new RemovePatient();
        } else if (e.getSource() == back) {
            setVisible(false);
            new PatientManagement();
        }
    }

    public static void main(String[] args) {
        new ViewPatient();
    }
}
