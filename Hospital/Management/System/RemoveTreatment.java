package Hospital.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RemoveTreatment extends JFrame implements ActionListener {

    JButton removeBtn, backBtn;
    JTextField treatmentIDField;
    JLabel treatmentIDLabel, patientIDLabel, outcomeLabel;

    public RemoveTreatment() {
        setTitle("Remove Treatment");
        getContentPane().setBackground(new Color(214, 245, 240));
        setLayout(null);

        JLabel heading = new JLabel("REMOVE TREATMENT");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(new Color(6, 44, 63));
        add(heading);

        JLabel idLabel = new JLabel("Treatment ID: ");
        idLabel.setBounds(50, 100, 150, 30);
        idLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(idLabel);

        treatmentIDField = new JTextField();
        treatmentIDField.setBounds(200, 100, 150, 30);
        treatmentIDField.setBackground(new Color(240, 233, 215));
        add(treatmentIDField);

        treatmentIDField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchTreatment();
                }
            }
        });

        treatmentIDLabel = new JLabel("Treatment ID: ");
        treatmentIDLabel.setBounds(50, 150, 400, 30);
        treatmentIDLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(treatmentIDLabel);

        patientIDLabel = new JLabel("Patient ID: ");
        patientIDLabel.setBounds(50, 200, 400, 30);
        patientIDLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(patientIDLabel);

        outcomeLabel = new JLabel("Outcome: ");
        outcomeLabel.setBounds(50, 250, 500, 30);
        outcomeLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        add(outcomeLabel);

        removeBtn = new JButton("Remove");
        removeBtn.setBounds(250, 300, 150, 35);
        removeBtn.setBackground(Color.BLACK);
        removeBtn.setForeground(Color.CYAN);
        removeBtn.setEnabled(false);
        removeBtn.addActionListener(this);
        add(removeBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(450, 300, 150, 35);
        backBtn.setBackground(Color.BLACK);
        backBtn.setForeground(Color.CYAN);
        backBtn.addActionListener(this);
        add(backBtn);

        setSize(900, 400);
        setLocation(300, 50);
        setVisible(true);
    }

    private void searchTreatment() {
        String treatmentID = treatmentIDField.getText().trim();
        if (treatmentID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Treatment ID.");
            return;
        }
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM treatment WHERE treatment_id = ?";
            PreparedStatement pst = c.connection.prepareStatement(query);
            pst.setString(1, treatmentID);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                treatmentIDLabel.setText("Treatment ID: " + rs.getString("treatment_id"));
                patientIDLabel.setText("Patient ID: " + rs.getString("patient_id"));
                outcomeLabel.setText("Outcome: " + rs.getString("outcome"));
                removeBtn.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Treatment not found.");
                clearLabels();
            }

            rs.close();
            pst.close();
            c.connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void clearLabels() {
        treatmentIDLabel.setText("Treatment ID: ");
        patientIDLabel.setText("Patient ID: ");
        outcomeLabel.setText("Outcome: ");
        removeBtn.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String treatmentID = treatmentIDField.getText().trim();

        if (e.getSource() == removeBtn) {
            try {
                Conn c = new Conn();
                PreparedStatement pst = c.connection.prepareStatement("DELETE FROM treatment WHERE treatment_id = ?");
                pst.setString(1, treatmentID);
                int affected = pst.executeUpdate();
                pst.close();
                c.connection.close();

                if (affected > 0) {
                    JOptionPane.showMessageDialog(this, "Treatment removed successfully.");
                    setVisible(false);
                    new ViewTreatment();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to remove treatment.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error while removing treatment.");
            }
        } else if (e.getSource() == backBtn) {
            setVisible(false);
            new ViewTreatment();
        }
    }

    public static void main(String[] args) {
        new RemoveTreatment();
    }
}
