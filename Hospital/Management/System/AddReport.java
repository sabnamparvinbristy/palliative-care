package Hospital.Management.System;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class AddReport extends JFrame implements ActionListener {
    private static int reportIdCounter = 10001; // starting value for Report ID

    JLabel reportID, patientNameLabel, doctorNameLabel;
    JTextField patientID, doctorID;
    JComboBox<String> reportTitle, testResult, reportType, followUpRequired;
    JTextArea reportDescription;
    JDateChooser testDate;
    JButton btnSave, btnClear, btnExit, btnBack, searchPatientBtn, searchDoctorBtn;

    public AddReport() {
        setTitle("Patient Report Panel");
        getContentPane().setBackground(new Color(214, 245, 240));
        setLayout(null);

        JLabel heading = new JLabel("Report Entry Information");
        heading.setBounds(250, 30, 500, 50);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        // VIEW button added here
        JButton btnView = new JButton("View");
        btnView.setBounds(720, 40, 100, 30);
        btnView.setBackground(new Color(6, 44, 63));
        btnView.setForeground(Color.WHITE);
        btnView.addActionListener(e -> {
            new ReportListPanel(); // Open report list window
        });
        add(btnView);

        JLabel lblReportID = new JLabel("Report ID: ");
        lblReportID.setBounds(50, 100, 150, 30);
        lblReportID.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(lblReportID);

        reportID = new JLabel();
        reportID.setBounds(200, 100, 150, 30);
        reportID.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        reportID.setForeground(new Color(147, 37, 42));
        add(reportID);

        generateReportID();

        JLabel lblPatientID = new JLabel("Patient ID: ");
        lblPatientID.setBounds(50, 150, 150, 30);
        lblPatientID.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(lblPatientID);

        patientID = new JTextField();
        patientID.setBounds(200, 150, 150, 30);
        patientID.setBackground(new Color(240, 233, 215));
        add(patientID);

        searchPatientBtn = new JButton("Search");
        searchPatientBtn.setBounds(360, 150, 90, 30);
        searchPatientBtn.setBackground(new Color(6, 44, 63));
        searchPatientBtn.setForeground(Color.WHITE);
        searchPatientBtn.addActionListener(e -> searchPatient());
        add(searchPatientBtn);

        patientNameLabel = new JLabel();
        patientNameLabel.setBounds(50, 180, 400, 25);
        patientNameLabel.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        patientNameLabel.setForeground(Color.DARK_GRAY);
        add(patientNameLabel);

        JLabel lblDoctorID = new JLabel("Doctor ID: ");
        lblDoctorID.setBounds(460, 150, 150, 30);
        lblDoctorID.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(lblDoctorID);

        doctorID = new JTextField();
        doctorID.setBounds(610, 150, 150, 30);
        doctorID.setBackground(new Color(240, 233, 215));
        add(doctorID);

        searchDoctorBtn = new JButton("Search");
        searchDoctorBtn.setBounds(770, 150, 90, 30);
        searchDoctorBtn.setBackground(new Color(6, 44, 63));
        searchDoctorBtn.setForeground(Color.WHITE);
        searchDoctorBtn.addActionListener(e -> searchDoctor());
        add(searchDoctorBtn);

        doctorNameLabel = new JLabel();
        doctorNameLabel.setBounds(460, 180, 400, 25);
        doctorNameLabel.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        doctorNameLabel.setForeground(Color.DARK_GRAY);
        add(doctorNameLabel);

        JLabel lblReportTitle = new JLabel("Report Title: ");
        lblReportTitle.setBounds(50, 220, 150, 30);
        lblReportTitle.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(lblReportTitle);

        reportTitle = new JComboBox<>(new String[]{"Routine Checkup", "Detailed Diagnosis", "Emergency Test", "Annual Physical"});
        reportTitle.setBounds(200, 220, 250, 30);
        reportTitle.setBackground(new Color(240, 233, 215));
        add(reportTitle);

        JLabel lblTestDate = new JLabel("Test Date: ");
        lblTestDate.setBounds(460, 220, 150, 30);
        lblTestDate.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(lblTestDate);

        testDate = new JDateChooser();
        testDate.setBounds(610, 220, 150, 30);
        testDate.setDate(new Date());
        testDate.setBackground(new Color(240, 233, 215));
        add(testDate);

        JLabel lblTestResult = new JLabel("Test Result: ");
        lblTestResult.setBounds(50, 270, 150, 30);
        lblTestResult.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(lblTestResult);

        testResult = new JComboBox<>(new String[]{"Positive", "Negative", "Moderate"});
        testResult.setBounds(200, 270, 250, 30);
        testResult.setBackground(new Color(240, 233, 215));
        add(testResult);

        JLabel lblReportType = new JLabel("Report Type: ");
        lblReportType.setBounds(460, 270, 150, 30);
        lblReportType.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(lblReportType);

        reportType = new JComboBox<>(new String[]{"Blood Test", "MRI", "X-Ray", "ECG"});
        reportType.setBounds(610, 270, 150, 30);
        reportType.setBackground(new Color(240, 233, 215));
        add(reportType);

        JLabel lblFollowUp = new JLabel("Follow-up Required: ");
        lblFollowUp.setBounds(50, 320, 200, 30);
        lblFollowUp.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(lblFollowUp);

        followUpRequired = new JComboBox<>(new String[]{"Yes", "No"});
        followUpRequired.setBounds(250, 320, 200, 30);
        followUpRequired.setBackground(new Color(240, 233, 215));
        add(followUpRequired);

        JLabel lblDescription = new JLabel("Report Description: ");
        lblDescription.setBounds(50, 370, 200, 30);
        lblDescription.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(lblDescription);

        reportDescription = new JTextArea();
        reportDescription.setLineWrap(true);
        reportDescription.setWrapStyleWord(true);
        reportDescription.setBackground(new Color(240, 233, 215));
        JScrollPane descScroll = new JScrollPane(reportDescription);
        descScroll.setBounds(225, 370, 560, 100);
        add(descScroll);

        btnBack = new JButton("Back");
        btnBack.setBounds(50, 490, 120, 35);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.CYAN);
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> {
            dispose();
            new Report();
        });
        add(btnBack);

        btnSave = new JButton("Save");
        btnSave.setBounds(200, 490, 150, 35);
        btnSave.setBackground(Color.BLACK);
        btnSave.setForeground(Color.CYAN);
        btnSave.addActionListener(this);
        add(btnSave);

        btnClear = new JButton("Clear");
        btnClear.setBounds(400, 490, 150, 35);
        btnClear.setBackground(Color.BLACK);
        btnClear.setForeground(Color.CYAN);
        btnClear.addActionListener(e -> clearFields());
        add(btnClear);

        btnExit = new JButton("Exit");
        btnExit.setBounds(600, 490, 150, 35);
        btnExit.setBackground(Color.BLACK);
        btnExit.setForeground(Color.CYAN);
        btnExit.addActionListener(e -> System.exit(0));
        add(btnExit);

        setSize(900, 600);
        setLocation(300, 50);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void searchPatient() {
        try {
            Conn c = new Conn();
            String id = patientID.getText().trim();
            ResultSet rs = c.connection.createStatement().executeQuery("SELECT full_name FROM patients WHERE patient_id=" + id);
            if (rs.next()) {
                patientNameLabel.setText("Name: " + rs.getString("full_name"));
            } else {
                patientNameLabel.setText("Patient not found");
            }
        } catch (Exception e) {
            patientNameLabel.setText("Error fetching patient");
            e.printStackTrace();
        }
    }

    private void searchDoctor() {
        try {
            Conn c = new Conn();
            String id = doctorID.getText().trim();
            ResultSet rs = c.connection.createStatement().executeQuery("SELECT name FROM doctors WHERE doc_id='" + id + "'");
            if (rs.next()) {
                doctorNameLabel.setText("Name: " + rs.getString("name"));
            } else {
                doctorNameLabel.setText("Doctor not found");
            }
        } catch (Exception e) {
            doctorNameLabel.setText("Error fetching doctor");
            e.printStackTrace();
        }
    }

    private void generateReportID() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.connection.createStatement().executeQuery("SELECT MAX(report_id) FROM reports");
            if (rs.next()) {
                int maxId = rs.getInt(1);
                if (maxId > 0) {
                    reportIdCounter = maxId + 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        reportID.setText(String.valueOf(reportIdCounter));
    }

    private void clearFields() {
        patientID.setText("");
        doctorID.setText("");
        patientNameLabel.setText("");
        doctorNameLabel.setText("");
        reportTitle.setSelectedIndex(0);
        testDate.setDate(new Date());
        testResult.setSelectedIndex(0);
        reportType.setSelectedIndex(0);
        followUpRequired.setSelectedIndex(0);
        reportDescription.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Save report data to database
        try {
            Conn c = new Conn();
            String query = "INSERT INTO reports(report_id, patient_id, doctor_id, report_title, test_date, test_result, report_type, follow_up_required, report_description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = c.connection.prepareStatement(query);
            pst.setInt(1, reportIdCounter);
            pst.setInt(2, Integer.parseInt(patientID.getText().trim()));
            pst.setString(3, doctorID.getText().trim());
            pst.setString(4, (String) reportTitle.getSelectedItem());

            // Format date for SQL
            java.util.Date date = testDate.getDate();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            pst.setDate(5, sqlDate);

            pst.setString(6, (String) testResult.getSelectedItem());
            pst.setString(7, (String) reportType.getSelectedItem());
            pst.setString(8, (String) followUpRequired.getSelectedItem());
            pst.setString(9, reportDescription.getText().trim());

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Report Saved Successfully");
            reportIdCounter++;
            generateReportID();
            clearFields();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving report: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Report();
    }
}
