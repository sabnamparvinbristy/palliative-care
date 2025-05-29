package Hospital.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateDoctor extends JFrame implements ActionListener {

    JTextField doc_name, doc_gender, doc_age, doc_education, doc_specialization, doc_email, doc_phn, doc_address, doc_salary, shift, doc_department;
    JLabel doc_id, doc_NID, JOINING_DATE;
    JButton ADD, BACK;
    String number;

    UpdateDoctor(String number) {
        this.number = number;
        setLayout(null);
        getContentPane().setBackground(new Color(203, 241, 194));

        JLabel heading = new JLabel("UPDATE DOCTOR DETAILS");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        JLabel id = new JLabel("Doc ID:");
        id.setBounds(50, 100, 150, 30);
        id.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(id);

        doc_id = new JLabel();
        doc_id.setBounds(200, 100, 150, 30);
        doc_id.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        doc_id.setForeground(new Color(147, 37, 42));
        add(doc_id);

        JLabel name = new JLabel("Name:");
        name.setBounds(50, 150, 150, 30);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(name);

        doc_name = new JTextField();
        doc_name.setBounds(200, 150, 150, 30);
        doc_name.setBackground(new Color(240, 233, 215));
        add(doc_name);

        JLabel joining = new JLabel("Joining Date:");
        joining.setBounds(460, 150, 150, 30);
        joining.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(joining);

        JOINING_DATE = new JLabel();
        JOINING_DATE.setBounds(610, 150, 150, 30);
        JOINING_DATE.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(JOINING_DATE);

        JLabel EDU = new JLabel("Education:");
        EDU.setBounds(50, 200, 150, 30);
        EDU.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(EDU);

        doc_education = new JTextField();
        doc_education.setBounds(200, 200, 150, 30);
        doc_education.setBackground(new Color(240, 233, 215));
        add(doc_education);

        JLabel specialization = new JLabel("Specialization:");
        specialization.setBounds(460, 200, 150, 30);
        specialization.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(specialization);

        doc_specialization = new JTextField();
        doc_specialization.setBounds(610, 200, 150, 30);
        doc_specialization.setBackground(new Color(240, 233, 215));
        add(doc_specialization);

        JLabel age = new JLabel("Age:");
        age.setBounds(50, 250, 150, 30);
        age.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(age);

        doc_age = new JTextField();
        doc_age.setBounds(200, 250, 150, 30);
        doc_age.setBackground(new Color(240, 233, 215));
        add(doc_age);

        JLabel gender = new JLabel("Gender:");
        gender.setBounds(460, 250, 150, 30);
        gender.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(gender);

        doc_gender = new JTextField();
        doc_gender.setBounds(610, 250, 150, 30);
        doc_gender.setBackground(new Color(240, 233, 215));
        add(doc_gender);

        JLabel email = new JLabel("Email:");
        email.setBounds(50, 300, 150, 30);
        email.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(email);

        doc_email = new JTextField();
        doc_email.setBounds(200, 300, 150, 30);
        doc_email.setBackground(new Color(240, 233, 215));
        add(doc_email);

        JLabel dept = new JLabel("Department:");
        dept.setBounds(460, 300, 150, 30);
        dept.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(dept);

        doc_department = new JTextField();
        doc_department.setBounds(610, 300, 150, 30);
        doc_department.setBackground(new Color(240, 233, 215));
        add(doc_department);

        JLabel phn = new JLabel("Contact No:");
        phn.setBounds(50, 350, 150, 30);
        phn.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(phn);

        doc_phn = new JTextField();
        doc_phn.setBounds(200, 350, 150, 30);
        doc_phn.setBackground(new Color(240, 233, 215));
        add(doc_phn);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(460, 350, 150, 30);
        addressLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(addressLabel);

        doc_address = new JTextField();
        doc_address.setBounds(610, 350, 150, 30);
        doc_address.setBackground(new Color(240, 233, 215));
        add(doc_address);

        JLabel nid = new JLabel("NID:");
        nid.setBounds(50, 400, 150, 30);
        nid.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(nid);

        doc_NID = new JLabel();
        doc_NID.setBounds(200, 400, 150, 30);
        doc_NID.setForeground(new Color(147, 37, 42));
        add(doc_NID);

        JLabel sal = new JLabel("Salary:");
        sal.setBounds(460, 400, 150, 30);
        sal.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(sal);

        doc_salary = new JTextField();
        doc_salary.setBounds(610, 400, 150, 30);
        doc_salary.setBackground(new Color(240, 233, 215));
        add(doc_salary);

        JLabel available = new JLabel("DOCTOR AVAILABILITY -");
        available.setBounds(50, 460, 500, 50);
        available.setFont(new Font("Raleway", Font.BOLD, 20));
        available.setForeground(new Color(6, 44, 63));
        add(available);

        JLabel HOURS = new JLabel("Shift Hours:");
        HOURS.setBounds(50, 530, 150, 30);
        HOURS.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(HOURS);

        shift = new JTextField();
        shift.setBounds(200, 530, 170, 30);
        shift.setBackground(new Color(240, 233, 215));
        add(shift);

        try {
            Conn c = new Conn();
            PreparedStatement pst = c.connection.prepareStatement("SELECT * FROM doctors WHERE doc_id = ?");
            pst.setString(1, number);
            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                doc_id.setText(resultSet.getString("doc_id"));
                doc_name.setText(resultSet.getString("name"));
                JOINING_DATE.setText(resultSet.getString("joining_date"));
                doc_education.setText(resultSet.getString("education"));
                doc_specialization.setText(resultSet.getString("specialization"));
                doc_age.setText(resultSet.getString("age"));
                doc_gender.setText(resultSet.getString("gender"));
                doc_email.setText(resultSet.getString("email"));
                doc_department.setText(resultSet.getString("department"));
                doc_phn.setText(resultSet.getString("contact_no"));
                doc_address.setText(resultSet.getString("address"));
                doc_NID.setText(resultSet.getString("nid"));
                doc_salary.setText(resultSet.getString("salary"));
                shift.setText(resultSet.getString("shift"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ADD = new JButton("UPDATE");
        ADD.setBounds(250, 630, 150, 35);
        ADD.setBackground(Color.BLACK);
        ADD.setForeground(Color.cyan);
        ADD.addActionListener(this);
        add(ADD);

        BACK = new JButton("BACK");
        BACK.setBounds(450, 630, 150, 35);
        BACK.setBackground(Color.BLACK);
        BACK.setForeground(Color.cyan);
        BACK.addActionListener(this);
        add(BACK);

        setSize(900, 750);
        setLocation(300, 50);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ADD) {
            try {
                Conn c = new Conn();
                PreparedStatement pst = c.connection.prepareStatement(
                        "UPDATE doctors SET name=?, education=?, specialization=?, age=?, gender=?, email=?, department=?, contact_no=?, address=?, salary=?, shift=? WHERE doc_id=?"
                );
                pst.setString(1, doc_name.getText());
                pst.setString(2, doc_education.getText());
                pst.setString(3, doc_specialization.getText());
                pst.setString(4, doc_age.getText());
                pst.setString(5, doc_gender.getText());
                pst.setString(6, doc_email.getText());
                pst.setString(7, doc_department.getText());
                pst.setString(8, doc_phn.getText());
                pst.setString(9, doc_address.getText());
                pst.setString(10, doc_salary.getText());
                pst.setString(11, shift.getText());
                pst.setString(12, number);

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Details Updated Successfully");
                setVisible(false);
                new DoctorManagement().setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            setVisible(false);
            new ViewDoctor().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UpdateDoctor(""));
    }
}
