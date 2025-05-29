package Hospital.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame implements ActionListener {
    JButton patientBtn,doctorBtn, appointmentBtn, treatmentBtn, pharmacyBtn, bedRoomBtn, billingBtn, reportsBtn, criticalBtn, logoutBtn;


    Dashboard(){
        setTitle("Dashboard");

        JPanel panel = new JPanel() ;
        panel.setLayout(null);  //border layout null korlam
        panel.setBounds(5,160,1525, 670);
        panel.setBackground(new Color(247, 236, 244));
        add(panel) ;

        JPanel panel1 = new JPanel() ;
        panel1.setLayout(null);  //border layout null korlam
        panel1.setBounds(5,5,1525, 150);
        panel1.setBackground(new Color(245, 245, 245));
        add(panel1) ;

        JLabel title1 = new JLabel("Username");//Jlabel used to make something visible on frame
        title1.setBounds(40,20,100, 30);
        title1.setFont(new Font("Tahoma", Font.BOLD, 16));
        title1.setForeground(Color.DARK_GRAY);
        add(title1) ;

        JLabel text = new JLabel("PALLIATIVE CARE HOSPITAL - DASHBOARD");
        text.setBounds(550, 60, 700, 35); // Adjust X/Y to position next to image
        text.setForeground(Color.BLACK);
        text.setFont(new Font("System", Font.BOLD, 22)); // Increased font size for visibility
        panel1.add(text); //  add to panel1


        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/p2.png")) ;
        Image image = i1.getImage().getScaledInstance(155, 155, Image.SCALE_SMOOTH);
        ImageIcon i2 = new ImageIcon(image);
        JLabel label = new JLabel(i2);
        label.setBounds(390, 0, 155, 155);
        panel1.add(label) ;

        // Initialize buttons
        patientBtn = new JButton("Patient Management");
        patientBtn.setBounds(350, 100, 280, 40);
        patientBtn.setBackground(new Color(167, 119, 241));
        patientBtn.setForeground(Color.WHITE);
        patientBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        patientBtn.addActionListener(this);
        panel.add(patientBtn);

        doctorBtn = new JButton("Doctor Management");
        doctorBtn.setBounds(350, 240, 280, 40);
        doctorBtn.setBackground(new Color(167, 119, 241));
        doctorBtn.setForeground(Color.WHITE);
        doctorBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        doctorBtn.addActionListener(this);
        panel.add(doctorBtn);

        appointmentBtn = new JButton("Appointments");
        appointmentBtn.setBounds(910, 100, 280, 40);
        appointmentBtn.setBackground(new Color(167, 119, 241));
        appointmentBtn.setForeground(Color.WHITE);
        appointmentBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        appointmentBtn.addActionListener(this);
        panel.add(appointmentBtn);

        treatmentBtn = new JButton("Treatment & Medications");
        treatmentBtn.setBounds(350, 170, 280, 40);
        treatmentBtn.setBackground(new Color(167, 119, 241));
        treatmentBtn.setForeground(Color.WHITE);
        treatmentBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        treatmentBtn.addActionListener(this);
        panel.add(treatmentBtn);

        pharmacyBtn = new JButton("Pharmacy");
        pharmacyBtn.setBounds(910, 170, 280, 40);
        pharmacyBtn.setBackground(new Color(167, 119, 241));
        pharmacyBtn.setForeground(Color.WHITE);
        pharmacyBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        pharmacyBtn.addActionListener(this);
        panel.add(pharmacyBtn);

      /*  bedRoomBtn = new JButton("Bed & Room Management");
        bedRoomBtn.setBounds(350, 240, 280, 40);
        bedRoomBtn.setBackground(new Color(167, 119, 241));
        bedRoomBtn.setForeground(Color.WHITE);
        bedRoomBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        bedRoomBtn.addActionListener(this);
        panel.add(bedRoomBtn); */

        billingBtn = new JButton("Billing & Insurance");
        billingBtn.setBounds(910, 240, 280, 40);
        billingBtn.setBackground(new Color(167, 119, 241));
        billingBtn.setForeground(Color.WHITE);
        billingBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        billingBtn.addActionListener(this);
        panel.add(billingBtn);

        reportsBtn = new JButton("Reports & Analytics");
        reportsBtn.setBounds(350, 310, 280, 40);
        reportsBtn.setBackground(new Color(167, 119, 241));
        reportsBtn.setForeground(Color.WHITE);
        reportsBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        reportsBtn.addActionListener(this);
        panel.add(reportsBtn);

        criticalBtn = new JButton("Critical Patients Dashboard");
        criticalBtn.setBounds(910, 310, 280, 40);
        criticalBtn.setBackground(new Color(167, 119, 241)); // LAVENDER CLR
        criticalBtn.setForeground(Color.WHITE);
        criticalBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        criticalBtn.addActionListener(this);
        panel.add(criticalBtn);

        logoutBtn = new JButton("LOGOUT");
        logoutBtn.setBounds(630, 450, 280, 40);
        logoutBtn.setBackground(new Color(16, 22, 46));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        logoutBtn.addActionListener(this);
        panel.add(logoutBtn);




        setSize(1950,1090);
        getContentPane().setBackground(Color.WHITE);  //set background color white
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutBtn) {
            setVisible(false);
            new Login();
        } else if (e.getSource() == patientBtn) {
              new PatientManagement();
            setVisible(false);
        }
        else if (e.getSource() == treatmentBtn) {
            new TreatmentPanel();
            setVisible(false);
        }
        else if(e.getSource() == doctorBtn) {
            new DoctorManagement() ;
            setVisible(false);
        }
        else if (e.getSource() == reportsBtn) {
            new Report();
            setVisible(false);

        } else if (e.getSource() == appointmentBtn) {
            new AppointmentPanel() ;
            setVisible(false);
        }
        else if (e.getSource() == pharmacyBtn) {

            new PharmacyPanel();
            setVisible(false);
        }else if(e.getSource() == criticalBtn){
            new CriticalPatient();
            setVisible(false);

        }
        else if(e.getSource() == billingBtn){
            new BillingPanel();
            setVisible(false);

        }
        else if (e.getSource() == treatmentBtn) {
            new TreatmentPanel();
            setVisible(false);



        } else if (e.getSource() == pharmacyBtn) {

            JOptionPane.showMessageDialog(this, "Pharmacy Panel Clicked");
        } else if (e.getSource() == bedRoomBtn) {

            JOptionPane.showMessageDialog(this, "Bed & Room Management Panel Clicked");
        } else if (e.getSource() == billingBtn) {

            JOptionPane.showMessageDialog(this, "Billing & Insurance Panel Clicked");
        } else if (e.getSource() == reportsBtn) {

            JOptionPane.showMessageDialog(this, "Reports & Analytics Panel Clicked");
        } else if (e.getSource() == criticalBtn) {

            JOptionPane.showMessageDialog(this, "Critical Patients Dashboard Clicked");
        }
    }
    public static void main(String[] args) {
        new Dashboard() ;
    }
}