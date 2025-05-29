package Hospital.Management.System;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddDoctor extends JFrame implements ActionListener {

    JTextField doc_name, doc_age, doc_edu, doc_specialization, doc_email,
            doc_phn, doc_address, doc_NID, doc_salary, doc_availability;
    JLabel doc_id;
    JDateChooser DOC_JOINING_DATE;

    JButton ADD, BACK;

    JComboBox doc_gender, doc_dept, shift_combo, days_combo;

    AddDoctor() {
        setTitle("Add Doctor Details");

        getContentPane().setBackground(new Color(214, 245, 240));

        JLabel heading = new JLabel("ADD DOCTOR DETAILS");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        JLabel id = new JLabel("Doc ID: ");
        id.setBounds(50, 100, 150, 30);
        id.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(id);

        doc_id = new JLabel(""); // Start with empty label for doc_id
        doc_id.setBounds(200, 100, 150, 30);
        doc_id.setFont(new Font("SAN_SARIF", Font.BOLD, 20));
        doc_id.setForeground(new Color(147, 37, 42));
        add(doc_id);

        JLabel name = new JLabel("Name: ");
        name.setBounds(50, 150, 150, 30);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(name);

        doc_name = new JTextField();
        doc_name.setBounds(200, 150, 150, 30);
        doc_name.setBackground(new Color(240, 233, 215));
        add(doc_name);

        JLabel joining = new JLabel("Joining Date: ");
        joining.setBounds(460, 150, 150, 30);
        joining.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(joining);

        DOC_JOINING_DATE = new JDateChooser();
        DOC_JOINING_DATE.setBounds(610, 150, 150, 30);
        DOC_JOINING_DATE.setBackground(new Color(240, 233, 215));
        add(DOC_JOINING_DATE);

        JLabel EDU = new JLabel("Education: ");
        EDU.setBounds(50, 200, 150, 30);
        EDU.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(EDU);

        doc_edu = new JTextField();
        doc_edu.setBounds(200, 200, 150, 30);
        doc_edu.setBackground(new Color(240, 233, 215));
        add(doc_edu);

        JLabel specialization = new JLabel("Specialization: ");
        specialization.setBounds(460, 200, 150, 30);
        specialization.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(specialization);

        doc_specialization = new JTextField();
        doc_specialization.setBounds(610, 200, 150, 30);
        doc_specialization.setBackground(new Color(240, 233, 215));
        add(doc_specialization);

        JLabel age = new JLabel("Age: ");
        age.setBounds(50, 250, 150, 30);
        age.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(age);

        doc_age = new JTextField();
        doc_age.setBounds(200, 250, 150, 30);
        doc_age.setBackground(new Color(240, 233, 215));
        add(doc_age);

        JLabel gender = new JLabel("Gender: ");
        gender.setBounds(460, 250, 150, 30);
        gender.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(gender);

        String items[] = { "MALE", "FEMALE", "OTHER" };
        doc_gender = new JComboBox(items);
        doc_gender.setBackground(new Color(240, 233, 215)); // to add custom color
        doc_gender.setBounds(610, 250, 150, 30);
        add(doc_gender);

        JLabel email = new JLabel("Email: ");
        email.setBounds(50, 300, 150, 30);
        email.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(email);

        doc_email = new JTextField();
        doc_email.setBounds(200, 300, 150, 30);
        doc_email.setBackground(new Color(240, 233, 215));
        add(doc_email);

        JLabel dept = new JLabel("Department: ");
        dept.setBounds(460, 300, 150, 30);
        dept.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(dept);

        String departments[] = { "Palliative Care", "Oncology", "Pain Management", "Psychiatry", "Rehabilitation", "Geriatrics", "Nursing", "Home Care" };
        doc_dept = new JComboBox(departments);
        doc_dept.setBackground(new Color(240, 233, 215)); // to add custom color
        doc_dept.setBounds(610, 300, 150, 30);
        add(doc_dept);

        JLabel phn = new JLabel("Contact No.");
        phn.setBounds(50, 350, 150, 30);
        phn.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(phn);

        doc_phn = new JTextField();
        doc_phn.setBounds(200, 350, 150, 30);
        doc_phn.setBackground(new Color(240, 233, 215));
        add(doc_phn);

        JLabel add = new JLabel("Address: ");
        add.setBounds(460, 350, 150, 30);
        add.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(add);

        doc_address = new JTextField();
        doc_address.setBounds(610, 350, 150, 30);
        doc_address.setBackground(new Color(240, 233, 215));
        add(doc_address);

        JLabel nid = new JLabel("NID.");
        nid.setBounds(50, 400, 150, 30);
        nid.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(nid);

        doc_NID = new JTextField();
        doc_NID.setBounds(200, 400, 150, 30);
        doc_NID.setBackground(new Color(240, 233, 215));
        add(doc_NID);

        JLabel sal = new JLabel("Salary: ");
        sal.setBounds(460, 400, 150, 30);
        sal.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(sal);

        doc_salary = new JTextField();
        doc_salary.setBounds(610, 400, 150, 30);
        doc_salary.setBackground(new Color(240, 233, 215));
        add(doc_salary);

        JLabel available = new JLabel("DOCTOR AVAILABILITY - ");
        available.setBounds(50, 460, 500, 50);
        available.setFont(new Font("Raleway", Font.BOLD, 20));
        available.setForeground(new Color(6, 44, 63));
        add(available);

        JLabel HOURS = new JLabel("Shift Hours: ");
        HOURS.setBounds(50, 530, 150, 30);
        HOURS.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(HOURS);

        String shift_hrs[] = { "Morning (8 AM - 12 PM)", "Afternoon (1 PM - 4 PM)", "Evening (5 PM - 9 PM)", "Night (10 PM - 6 AM)" };
        shift_combo = new JComboBox(shift_hrs);
        shift_combo.setBackground(new Color(240, 233, 215)); // to add custom color
        shift_combo.setBounds(200, 530, 170, 30);
        add(shift_combo);

        ADD = new JButton("ADD");
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

        setSize(900, 750); // height & width of this frame
        setLayout(null); // by default frame jedik thika open hoy ta null kore
        setLocation(300, 50); // kon location e open hobe tar size dilam
        setVisible(true); // by default false thake true korlam

        // Fetch the next available doc_id when the form is initialized
        getNextDoctorId();
    }

    public void getNextDoctorId() {
        try {
            Conn c = new Conn();
            String query = "SELECT doc_id FROM doctors ORDER BY doc_id DESC LIMIT 1"; // Get the last inserted doctor ID
            ResultSet rs = c.statement.executeQuery(query);

            if (rs.next()) {
                int lastDocId = rs.getInt("doc_id");
                int newDocId = lastDocId + 1; // Increment by 1
                doc_id.setText(String.valueOf(newDocId)); // Set the new doc_id on the label
            } else {
                // If no doctors are in the database, start from 20001
                doc_id.setText("20001");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ADD) {
            String docid = doc_id.getText();
            String name = doc_name.getText();
            String joined = new SimpleDateFormat("yyyy-MM-dd").format(DOC_JOINING_DATE.getDate());  // whatever date is entered will be stored in joined string
            String education = doc_edu.getText();
            String specialization = doc_specialization.getText();
            String age = doc_age.getText();
            String gender = (String) doc_gender.getSelectedItem(); // dropdown theke jei item select hobe ta getSelected item er maddhome store hbe
            String email = doc_email.getText();
            String department = (String) doc_dept.getSelectedItem();
            String contact_no = doc_phn.getText();
            String address = doc_address.getText();
            String nid = doc_NID.getText();
            String salary = doc_salary.getText();
            String shift_hrs = (String) shift_combo.getSelectedItem();

            try {
                Conn c = new Conn();
                String query = "INSERT INTO doctors (doc_id, name, joining_date, education, specialization, age, gender, email, department, contact_no, address, nid, salary, shift) " +
                        "VALUES ('" + docid + "', '" + name + "', '" + joined + "', '" + education + "', '" + specialization + "', '" + age + "', '" + gender + "', '" + email + "', '" + department + "', '" + contact_no + "', '" + address + "', '" + nid + "', '" + salary + "', '" + shift_hrs + "')";
                c.statement.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Details Added Successfully . . .");
                setVisible(false);
                new DoctorManagement();
            } catch (Exception E) {
                E.printStackTrace();
            }
        } else {
            setVisible(false);
            new DoctorManagement();
        }
    }

    public static void main(String[] args) {
        new AddDoctor(); // creating obj of the constructor inside the main function
    }
}
