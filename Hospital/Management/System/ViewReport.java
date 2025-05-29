package Hospital.Management.System;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewReport extends JFrame implements ActionListener {

    JTable table;
    JTextField reportIDField;
    JButton searchbtn, print, back;

    public ViewReport() {
        getContentPane().setBackground(new Color(214, 245, 240));
        setLayout(null);

        JLabel searchLabel = new JLabel("Enter Report ID:");
        searchLabel.setBounds(20, 20, 150, 20);
        add(searchLabel);

        reportIDField = new JTextField();
        reportIDField.setBounds(180, 20, 150, 20);
        add(reportIDField);

        reportIDField.addKeyListener(new KeyAdapter() {
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

        // Mouse click listener for showing report and patient details
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    showReportDetailsDialog(row);
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

        back = new JButton("Back");
        back.setBounds(220, 70, 80, 20);
        back.addActionListener(this);
        add(back);

        setSize(1350, 700);
        setLocation(100, 50);
        setVisible(true);

        loadReportTable();
    }

    private void loadReportTable() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery(
                    "SELECT report_id, patient_id, doctor_id, report_title, report_type, test_date, test_result, report_description, follow_up_required FROM reports"
            );
            table.setModel(DbUtils.resultSetToTableModel(rs));
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            setColumnWidths();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setColumnWidths() {
        int[] widths = {100, 100, 100, 150, 120, 100, 100, 200, 100};
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < widths.length && i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(widths[i]);
        }
    }

    private void performSearch() {
        String enteredID = reportIDField.getText().trim();
        if (enteredID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Report ID.");
            return;
        }
        String query = "SELECT report_id, patient_id, doctor_id, report_title, report_type, test_date, test_result, report_description, follow_up_required FROM reports WHERE report_id = '" + enteredID + "'";
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

    private void showReportDetailsDialog(int row) {
        String[] columnNames = {
                "Report ID", "Patient ID", "Doctor ID", "Title", "Type",
                "Test Date", "Result", "Description", "Follow-Up"
        };

        StringBuilder details = new StringBuilder();
        for (int i = 0; i < table.getColumnCount(); i++) {
            String column = (i < columnNames.length) ? columnNames[i] : "Column " + (i + 1);
            String value = String.valueOf(table.getValueAt(row, i));
            details.append(column).append(": ").append(value).append("\n");
        }

        // Fetch patient details from database
        try {
            String patientId = table.getValueAt(row, 1).toString();
            Conn c = new Conn();
            String query = "SELECT * FROM patients WHERE patient_id = '" + patientId + "'";
            ResultSet rs = c.statement.executeQuery(query);
            if (rs.next()) {
                details.append("\n--- Patient Details ---\n");
                details.append("Name: ").append(rs.getString("full_name")).append("\n");
                details.append("DOB: ").append(rs.getString("dob")).append("\n");
                details.append("Gender: ").append(rs.getString("gender")).append("\n");
                details.append("Contact: ").append(rs.getString("contact")).append("\n");
                details.append("Address: ").append(rs.getString("address")).append("\n");
            }
            rs.close();
            c.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JTextArea textArea = new JTextArea(details.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 350));

        JOptionPane.showMessageDialog(this, scrollPane, "Report & Patient Details", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String enteredID = reportIDField.getText().trim();

        if (e.getSource() == searchbtn) {
            performSearch();
        } else if (e.getSource() == print) {
            try {
                table.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == back) {
            setVisible(false);
            new Report();
        }
    }

    public static void main(String[] args) {
        new ViewReport();
    }
}
