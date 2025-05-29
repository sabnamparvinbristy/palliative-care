package Hospital.Management.System;

import javax.swing.*; // JFRAME COMES FROM JAVA SWING PACKAGE
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {
    JTextField textField ;  //GLOBALLY DECLARE OTHERWISE CAN'T ACCESS THIS ON ACTION PERFORMED
    JPasswordField jPassword ;
    JButton b1, b2 ;
    Login(){  //constructor MAKING

        setTitle("Login Panel");

        JLabel namelabel = new JLabel("Username");//Jlabel used to make something visible on frame
        namelabel.setBounds(40,50,100, 30);
        namelabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        namelabel.setForeground(Color.DARK_GRAY);
        add(namelabel) ;

        JLabel password = new JLabel("Password");//Jlabel used to make something visible on frame
        password.setBounds(40,100,100, 30);
        password.setFont(new Font("Tahoma", Font.BOLD, 16));
        password.setForeground(Color.DARK_GRAY);
        add(password) ;

        textField = new JTextField() ;
        textField.setBounds(150, 50, 150, 30);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textField.setBackground(new Color(231, 231, 233));
        add(textField) ;

        jPassword = new JPasswordField() ;
        jPassword.setBounds(150,100, 150, 30);
        jPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
        jPassword.setBackground(new Color(231, 231, 233)) ;
        add(jPassword) ;

         ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("icons/login.png")) ; // get image
         Image i1 = img.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)  ;  // scale the image according to the frame
         ImageIcon  img1 = new ImageIcon(i1) ;
         JLabel label = new JLabel(img1) ;
         label.setBounds(300, -30, 400, 300);
         add(label) ;

         b1 = new JButton("LOGIN") ;
         b1.setBounds(40,170,120, 30);
         b1.setFont(new Font("serif", Font.BOLD, 15));
         b1.setBackground(new Color(255, 213, 118));
         b1.setForeground(new Color(37, 57, 120)); ;
         b1.addActionListener(this);
         add(b1) ;

        b2 = new JButton("CANCEL") ;
        b2.setBounds(180,170,120, 30);
        b2.setFont(new Font("serif", Font.BOLD, 15));
        b2.setBackground(new Color(255, 213, 118));
        b2.addActionListener(this);
        b2.setForeground(new Color(37, 57, 120)); ;

        add(b2) ;


        getContentPane().setBackground(new Color(139, 134, 250));
        setSize(750, 300);
        setLocation(400, 270);  //left to right shift 400, top to bottom 270 te frame khulbe
        setLayout(null) ;  //we are designing frame size our own so have to disable the default layout

        setVisible(true);  //jframes visibility is by default hidden

    }
    public static void main(String[] args) {
        new Login() ; //call the constructor inside the main function
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b1){
            try{
                Conn c = new Conn() ;
                String user = textField.getText() ;
                String pass = jPassword.getText() ;

                String q = "select * from users where username = '"+user+"' and password = '"+pass+"' " ;
                ResultSet resultSet = c.statement.executeQuery(q) ;

                if(resultSet.next()){
                    new Dashboard() ;
                    setVisible(false);
                }else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                }
            }catch (Exception E){
                E.printStackTrace();
            }
        }else {
            System.exit(10);
        }
    }
}
