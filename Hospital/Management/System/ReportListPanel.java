package Hospital.Management.System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ReportListPanel extends JFrame {

    JTable reportTable;
    DefaultTableModel model;
    JButton btnBack;

    public ReportListPanel() {
        setTitle("View All Reports");
        getContentPane().setBackground(new Color(203, 241, 194));
        setLayout(null);
        setSize(900, 600);
        setLocation(300, 50);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel heading = new JLabel("All Report Entries");
        heading.setBounds(280, 20, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 28));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        String[] columns = {
                "Report ID", "Patient ID", "Patient Name", "Doctor ID", "Doctor Name",
                "Report Title", "Test Date", "Test Result", "Report Type", "Follow-up", "Description"
        };
        model = new DefaultTableModel(columns, 0);
        reportTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(reportTable);
        scrollPane.setBounds(20, 80, 850, 400);
        add(scrollPane);

        loadReports();

        reportTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = reportTable.getSelectedRow();
                if (row != -1) {
                    int patientId = (int) model.getValueAt(row, 1);
                    int reportId = (int) model.getValueAt(row, 0);
                    showDetails(patientId, reportId);
                }
            }
        });

        btnBack = new JButton("Back");
        btnBack.setBounds(350, 500, 120, 40);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.CYAN);
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> {
            dispose();
            new Report();
        });
        add(btnBack);

        setVisible(true);
    }

    private void loadReports() {
        try {
            Conn c = new Conn();
            String query = "SELECT r.report_id, r.patient_id, p.full_name AS patient_name, " +
                    "r.doctor_id, d.name AS doctor_name, r.report_title, r.test_date, " +
                    "r.test_result, r.report_type, r.follow_up_required, r.report_description " +
                    "FROM reports r " +
                    "LEFT JOIN patients p ON r.patient_id = p.patient_id " +
                    "LEFT JOIN doctors d ON r.doctor_id = d.doc_id";

            ResultSet rs = c.connection.createStatement().executeQuery(query);

            while (rs.next()) {
                Object[] row = new Object[]{
                        rs.getInt("report_id"),
                        rs.getInt("patient_id"),
                        rs.getString("patient_name"),
                        rs.getString("doctor_id"),
                        rs.getString("doctor_name"),
                        rs.getString("report_title"),
                        rs.getDate("test_date"),
                        rs.getString("test_result"),
                        rs.getString("report_type"),
                        rs.getString("follow_up_required"),
                        rs.getString("report_description")
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading reports: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showDetails(int patientId, int reportId) {
        try {
            Conn c = new Conn();

            String patientQuery = "SELECT * FROM patients WHERE patient_id = " + patientId;
            ResultSet prs = c.connection.createStatement().executeQuery(patientQuery);

            String reportQuery = "SELECT * FROM reports WHERE report_id = " + reportId;
            ResultSet rrs = c.connection.createStatement().executeQuery(reportQuery);

            Patient patient = null;
            ReportData report = null;

            if (prs.next()) {
                patient = new Patient(
                        prs.getInt("patient_id"),
                        prs.getString("full_name"),
                        prs.getDate("dob"),
                        prs.getString("gender"),
                        prs.getString("contact"),
                        prs.getString("address"),
                        prs.getString("category"),
                        prs.getString("emergency_contact"),
                        prs.getInt("age")
                );
            }
            if (rrs.next()) {
                report = new ReportData(
                        rrs.getInt("report_id"),
                        rrs.getString("report_title"),
                        rrs.getDate("test_date"),
                        rrs.getString("test_result"),
                        rrs.getString("report_type"),
                        rrs.getString("follow_up_required"),
                        rrs.getString("report_description")
                );
            }

            if (patient == null && report == null) {
                JOptionPane.showMessageDialog(this, "Details not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DetailsDialog dialog = new DetailsDialog(this, patient, report);
            dialog.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching details: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private static class Patient {
        int id;
        String name;
        java.sql.Date dob;
        String gender, contact, address, category, emergencyContact;
        int age;

        Patient(int id, String name, java.sql.Date dob, String gender, String contact, String address,
                String category, String emergencyContact, int age) {
            this.id = id;
            this.name = name;
            this.dob = dob;
            this.gender = gender;
            this.contact = contact;
            this.address = address;
            this.category = category;
            this.emergencyContact = emergencyContact;
            this.age = age;
        }
    }

    private static class ReportData {
        int reportId;
        String title;
        java.sql.Date testDate;
        String result, type, followUp, description;

        ReportData(int reportId, String title, java.sql.Date testDate, String result,
                   String type, String followUp, String description) {
            this.reportId = reportId;
            this.title = title;
            this.testDate = testDate;
            this.result = result;
            this.type = type;
            this.followUp = followUp;
            this.description = description;
        }
    }

    private static class DetailsDialog extends JDialog {
        public DetailsDialog(JFrame parent, Patient patient, ReportData report) {
            super(parent, "Patient & Report Details", true);
            setSize(720, 480);
            setLocationRelativeTo(parent);
            setLayout(new BorderLayout());
            getContentPane().setBackground(new Color(203, 241, 194));

            JLabel heading = new JLabel("Patient & Report Details");
            heading.setFont(new Font("Raleway", Font.BOLD, 22));
            heading.setForeground(new Color(6, 44, 63));
            heading.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            heading.setHorizontalAlignment(SwingConstants.CENTER);
            add(heading, BorderLayout.NORTH);

            JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            mainPanel.setBackground(new Color(203, 241, 194));

            JPanel patientPanel = new JPanel();
            patientPanel.setLayout(new BoxLayout(patientPanel, BoxLayout.Y_AXIS));
            patientPanel.setBackground(Color.WHITE);
            patientPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(6, 44, 63), 2),
                    "Patient Details",
                    javax.swing.border.TitledBorder.LEFT,
                    javax.swing.border.TitledBorder.TOP,
                    new Font("Raleway", Font.BOLD, 16),
                    new Color(6, 44, 63)
            ));

            addDetailLabel(patientPanel, "Patient ID: " + (patient != null ? patient.id : "N/A"));
            addDetailLabel(patientPanel, "Full Name: " + (patient != null ? patient.name : "N/A"));
            addDetailLabel(patientPanel, "Date of Birth: " + (patient != null ? patient.dob : "N/A"));
            addDetailLabel(patientPanel, "Gender: " + (patient != null ? patient.gender : "N/A"));
            addDetailLabel(patientPanel, "Contact: " + (patient != null ? patient.contact : "N/A"));
            addDetailLabel(patientPanel, "Address: " + (patient != null ? patient.address : "N/A"));
            addDetailLabel(patientPanel, "Category: " + (patient != null ? patient.category : "N/A"));
            addDetailLabel(patientPanel, "Emergency Contact: " + (patient != null ? patient.emergencyContact : "N/A"));
            addDetailLabel(patientPanel, "Age: " + (patient != null ? patient.age : "N/A"));

            JPanel reportPanel = new JPanel();
            reportPanel.setLayout(new BoxLayout(reportPanel, BoxLayout.Y_AXIS));
            reportPanel.setBackground(Color.WHITE);
            reportPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(6, 44, 63), 2),
                    "Report Details",
                    javax.swing.border.TitledBorder.LEFT,
                    javax.swing.border.TitledBorder.TOP,
                    new Font("Raleway", Font.BOLD, 16),
                    new Color(6, 44, 63)
            ));

            addDetailLabel(reportPanel, "Report ID: " + (report != null ? report.reportId : "N/A"));
            addDetailLabel(reportPanel, "Title: " + (report != null ? report.title : "N/A"));
            addDetailLabel(reportPanel, "Test Date: " + (report != null ? report.testDate : "N/A"));
            addDetailLabel(reportPanel, "Test Result: " + (report != null ? report.result : "N/A"));
            addDetailLabel(reportPanel, "Report Type: " + (report != null ? report.type : "N/A"));
            addDetailLabel(reportPanel, "Follow-up Required: " + (report != null ? report.followUp : "N/A"));

            JTextArea descArea = new JTextArea(report != null ? report.description : "N/A");
            descArea.setLineWrap(true);
            descArea.setWrapStyleWord(true);
            descArea.setEditable(false);
            descArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
            descArea.setBackground(Color.WHITE);
            descArea.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(6, 44, 63), 1),
                    "Description",
                    javax.swing.border.TitledBorder.LEFT,
                    javax.swing.border.TitledBorder.TOP,
                    new Font("Raleway", Font.BOLD, 14),
                    new Color(6, 44, 63)
            ));
            descArea.setPreferredSize(new Dimension(300, 120));
            JScrollPane descScroll = new JScrollPane(descArea);
            reportPanel.add(descScroll);

            mainPanel.add(patientPanel);
            mainPanel.add(reportPanel);

            add(mainPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(new Color(203, 241, 194));
            JButton closeBtn = new JButton("Close");
            closeBtn.setBackground(new Color(6, 44, 63));
            closeBtn.setForeground(Color.CYAN);
            closeBtn.setFont(new Font("Raleway", Font.BOLD, 16));
            closeBtn.setFocusPainted(false);
            closeBtn.addActionListener(e -> dispose());
            buttonPanel.add(closeBtn);

            add(buttonPanel, BorderLayout.SOUTH);
        }

        private static void addDetailLabel(JPanel panel, String text) {
            JLabel label = new JLabel(text);
            label.setFont(new Font("SansSerif", Font.PLAIN, 14));
            label.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
            panel.add(label);
        }
    }

    public static void main(String[] args) {
        new ReportListPanel();
    }
}
