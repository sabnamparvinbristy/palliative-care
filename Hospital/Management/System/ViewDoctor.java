package Hospital.Management.System;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewDoctor extends JFrame implements ActionListener {

    JTable table;
    JTextField docIdField;
    JButton searchbtn, print, update, remove, back;

    public ViewDoctor() {
        getContentPane().setBackground(new Color(214, 245, 240));
        setLayout(null);

        JLabel search_doc = new JLabel("Enter Doctor ID:");
        search_doc.setBounds(20, 20, 150, 20);
        add(search_doc);

        docIdField = new JTextField();
        docIdField.setBounds(180, 20, 150, 20);
        add(docIdField);

        docIdField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performSearch();
                }
            }
        });

        table = new JTable();
        loadDoctorTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 100, 1300, 550);
        add(scrollPane);

        // Mouse click listener to show doctor details
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    showDoctorDetailsDialog(row);
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

    private void loadDoctorTable() {
        try {
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM doctors");
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            setColumnWidths();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setColumnWidths() {
        int[] widths = {80, 150, 120, 130, 80, 50, 80, 120, 100, 100, 150, 100, 80, 150};
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < widths.length && i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(widths[i]);
        }
    }

    // Show row details dialog (uses table data, not DB)
    private void showDoctorDetailsDialog(int row) {
        String[] columnNames = {
                "Doctor ID", "Name", "Joining Date", "Education", "Specialization",
                "Age", "Gender", "Email", "Department", "Contact No.", "Address",
                "NID", "Salary", "Shift"
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
        scrollPane.setPreferredSize(new Dimension(500, 350));

        JOptionPane.showMessageDialog(this, scrollPane, "Doctor Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void performSearch() {
        String enteredID = docIdField.getText().trim();
        if (enteredID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Doctor ID.");
            return;
        }
        String query = "SELECT * FROM doctors WHERE doc_id = '" + enteredID + "'";
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

    private void removeDoctor() {
        String doctorID = docIdField.getText().trim();
        if (doctorID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Doctor ID to remove.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete Doctor ID: " + doctorID + "?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Conn c = new Conn();
                String query = "DELETE FROM doctors WHERE doc_id = ?";
                PreparedStatement pst = c.connection.prepareStatement(query);
                pst.setString(1, doctorID);

                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Doctor record deleted successfully.");
                    loadDoctorTable(); // Refresh table
                } else {
                    JOptionPane.showMessageDialog(this, "Doctor ID not found.");
                }

                pst.close();
                c.connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting doctor.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
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
            String enteredID = docIdField.getText().trim();
            if (enteredID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Doctor ID.");
            } else {
                setVisible(false);
                new UpdateDoctor(enteredID);
            }
        } else if (e.getSource() == remove) {
            removeDoctor();
        } else if (e.getSource() == back) {
            setVisible(false);
            new DoctorManagement();
        }
    }

    public static void main(String[] args) {
        new ViewDoctor();
    }
}
