import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends JFrame implements ActionListener {

    JFrame frame;
    JTextField email;
    JLabel username_label, password_label, vivek_nimbolkar;
    JPasswordField password;
    JButton login_btn;

    Login(){

        frame = new JFrame("Welcome Admin");

        username_label = new JLabel("Email address");
        username_label.setBounds(100,50,100,40);

        password_label = new JLabel("Password");
        password_label.setBounds(100,100,100,40);

        email = new JTextField();
        email.setBounds(200,50,200,40);

        password = new JPasswordField();
        password.setBounds(200,100,200,40);

        login_btn = new JButton("Login");
        login_btn.setBounds(200,150,100,40);
        login_btn.addActionListener(this);

        frame.add(username_label);
        frame.add(email);
        frame.add(password_label);
        frame.add(password);
        frame.add(login_btn);

        frame.setSize(500,300);
        frame.setLocation(400,200);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Login login = new Login();
        String me = "vivek nimbolkar";
    }

    //perform authentication on database
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String useremail = email.getText();
        String userpassword = password.getText();

        if (useremail.isEmpty() || userpassword.isEmpty()){
            JOptionPane.showMessageDialog(this,"Username or password should not be empty!");
            return;
        }
        //database connection operations
        try {
            //get the connection from the DBConnection class
            Connection conn = DBConnection.getDBConnection();
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM `admin` WHERE email_address='"+useremail+"' AND " +
                    "password='"+userpassword+"'");

                //check for the useremail and password
                if (resultSet.next()){
                    //if email and password match
                    //System.out.println("email found "+resultSet.getString(2));
                    new Home();
                    frame.dispose();
                }else {
                    JOptionPane.showMessageDialog(frame,"Incorrect email or password!");
                    email.setText("");
                    password.setText("");
                }
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
