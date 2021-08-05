import javax.swing.*;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Random;

public class Home extends JFrame {

    JFrame homeFrame;
    JButton addStudentBtn, removeStudentBtn, updateStudentBtn, findStudentBtn;

    Home(){

        homeFrame = new JFrame("Student Record System");
        addStudentBtn = new JButton("Add Student");
        addStudentBtn.setFont(new Font("Arial",Font.BOLD,20));
        addStudentBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new AddStudent();
                homeFrame.dispose();
            }
        });

        removeStudentBtn = new JButton("Remove Student");
        removeStudentBtn.setFont(new Font("Arial",Font.BOLD,20));

        //remove  student from database
        removeStudentBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new DeleteStudent();
                homeFrame.dispose();
            }
        });

        //update existing student
        updateStudentBtn = new JButton("Update Student");
        updateStudentBtn.setFont(new Font("Arial",Font.BOLD,20));

        //update the record of the database
        updateStudentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new UpdateStudent();
                homeFrame.dispose();
            }
        });

        //find the student
        findStudentBtn = new JButton("Find Student");
        findStudentBtn.setFont(new Font("Arial",Font.BOLD,20));

        findStudentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               new FindStudent();
               homeFrame.dispose();
            }
        });


        homeFrame.add(addStudentBtn);homeFrame.add(removeStudentBtn);
        homeFrame.add(updateStudentBtn);homeFrame.add(findStudentBtn);

        homeFrame.setSize(500,300);
        homeFrame.setLocation(500,200);
        homeFrame.setLayout(new GridLayout(2,2));
        homeFrame.setResizable(false);
        homeFrame.setVisible(true);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

//----------------------------------------------------------------------------------------------------
//add the student to the database
class AddStudent extends JFrame{

    JFrame addStudentFrame;

    JLabel  lName,
            lEmail,
            lMobile,
            lAddress,
            lCity,
            lState,
            lDistrict,
            lTaluka,
            lDob,
            lFee,
            lCategory,
            lCast;

    JTextField
            studentName,
            studentEmail,
            studentMobile,
            studentAddress,
            studentCity,
            studentState,
            studentDistrict,
            studentId,
            studentDob,
            studentFee,
            studentCategory,
            studentCast;

    JButton
            submit,
            reset,
            returnToMainMenu;

    AddStudent(){

        addStudentFrame = new JFrame("Add Student");

        lName = new JLabel("Fullname: ");
        lName.setBounds(50,30,100,30);

        lEmail = new JLabel("Email: ");
        lEmail.setBounds(390,30,100,30);

        lMobile = new JLabel("Mobile: ");
        lMobile.setBounds(50,100,100,30);

        lAddress = new JLabel("Address: ");
        lAddress.setBounds(390,100,100,30);

        lCity = new JLabel("City");
        lCity.setBounds(50,170,100,30);

        lState = new JLabel("State: ");
        lState.setBounds(390,170,100,30);

        lDistrict = new JLabel("District");
        lDistrict.setBounds(50,240,100,30);

        lTaluka = new JLabel("Student ID");
        lTaluka.setBounds(390,240,100,30);

        lDob = new JLabel("Dob");
        lDob.setBounds(50,310,100,30);

        lFee = new JLabel("Fee");
        lFee.setBounds(390,310,100,30);

        lCategory = new JLabel("Category");
        lCategory.setBounds(50,380,100,30);

        lCast = new JLabel("Cast");
        lCast.setBounds(390,380,100,30);


        studentName = new JTextField();
        studentName.setBounds(160,30,200,30);

        studentEmail = new JTextField();
        studentEmail.setBounds(480,30,200,30);

        studentMobile = new JTextField();
        studentMobile.setBounds(160,100,200,30);

        studentAddress = new JTextField();
        studentAddress.setBounds(480,100,200,30);

        studentCity = new JTextField();
        studentCity.setBounds(160,170,200,30);

        studentState = new JTextField();
        studentState.setBounds(480,170,200,30);

        studentDistrict = new JTextField();
        studentDistrict.setBounds(160,240,200,30);

        studentId = new JTextField();
        studentId.setBounds(480,240,200,30);

        //setting the random value for student id
        Random random = new Random();

        int randomNumber = random.nextInt(100000);
        String studentUniqueId = Integer.toString(randomNumber);

        studentId.setText(studentUniqueId);
    
        studentDob = new JTextField();
        studentDob.setBounds(160,310,200,30);

        studentFee = new JTextField();
        studentFee.setBounds(480,310,200,30);

        studentCategory = new JTextField();
        studentCategory.setBounds(160,380,200,30);

        studentCast = new JTextField();
        studentCast.setBounds(480,380,200,30);


        //buttons
        submit = new JButton("Submit");
        submit.setBounds(50,420,200,30);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //submit the student data to the database

                String[] studentData = {
                        studentName.getText(),        //storing all the data into the string array
                        studentEmail.getText(),
                        studentMobile.getText(),
                        studentAddress.getText(),
                        studentCity.getText(),
                        studentState.getText(),
                        studentDistrict.getText(),
                        studentId.getText(),
                        studentDob.getText(),
                        studentFee.getText(),
                        studentCategory.getText(),
                        studentCast.getText(),
                };

                //get today's date
                LocalDate date = LocalDate.now();
                String temp_date = date.toString();

                try {

                    Connection con = DBConnection.getDBConnection();
                    String query = "INSERT INTO `student_data` (`student_name`, `email_address`, `mobile_no`, " +
                            "`address`, `city`, `student_id`, `disctict`, `state`, `cast`, `category`, `dob`, `fee`," +
                            "`time`) " +
                            "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    PreparedStatement preStmt = con.prepareStatement(query);

                    //add all the values to the query
                    preStmt.setString(1,studentData[0]);
                    preStmt.setString(2,studentData[1]);
                    preStmt.setString(3,studentData[2]);
                    preStmt.setString(4,studentData[3]);
                    preStmt.setString(5,studentData[4]);
                    preStmt.setString(6,studentData[7]);
                    preStmt.setString(7,studentData[6]);
                    preStmt.setString(8,studentData[5]);
                    preStmt.setString(9,studentData[11]);
                    preStmt.setString(10,studentData[10]);
                    preStmt.setString(11,studentData[8]);
                    preStmt.setString(12,studentData[9]);
                    preStmt.setString(13,temp_date);

                    int sendData = preStmt.executeUpdate();

                    if (sendData>0){
                        JOptionPane.showMessageDialog(addStudentFrame,"Student Added Successfuly!");
                    }else{
                        JOptionPane.showMessageDialog(addStudentFrame,"Failed to add student. Try Again!");
                    }

                }catch (Exception ae){
                    ae.printStackTrace();
                }
            }
        });

        reset = new JButton("Reset");
        reset.setBounds(300,420,200,30);

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //reset all the fields
                studentName.setText("");
                studentAddress.setText("");
                studentCast.setText("");
                studentCategory.setText("");
                studentDistrict.setText("");
                studentCity.setText("");
                studentDob.setText("");
                studentFee.setText("");
                studentState.setText("");
                studentEmail.setText("");
                studentId.setText("");
                studentName.setText("");
                studentMobile.setText("");
            }
        });

        returnToMainMenu = new JButton("Cancel");
        returnToMainMenu.setBounds(550,420,200,30);

        returnToMainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //return to the admin panel frame
                new Home();
                addStudentFrame.dispose();
            }
        });

        //add labels
        addStudentFrame.add(lName);
        addStudentFrame.add(lEmail);
        addStudentFrame.add(lMobile);
        addStudentFrame.add(lAddress);
        addStudentFrame.add(lCity);
        addStudentFrame.add(lState);
        addStudentFrame.add(lDistrict);
        addStudentFrame.add(lTaluka);
        addStudentFrame.add(lDob);
        addStudentFrame.add(lFee);
        addStudentFrame.add(studentCategory);
        addStudentFrame.add(lCast);
        addStudentFrame.add(lCategory);

        //add textfields
        addStudentFrame.add(studentName);
        addStudentFrame.add(studentEmail);
        addStudentFrame.add(studentMobile);
        addStudentFrame.add(studentAddress);
        addStudentFrame.add(studentCity);
        addStudentFrame.add(studentState);
        addStudentFrame.add(studentDistrict);
        addStudentFrame.add(studentDistrict);
        addStudentFrame.add(studentId);
        addStudentFrame.add(studentDob);
        addStudentFrame.add(studentFee);
        addStudentFrame.add(studentFee);
        addStudentFrame.add(studentCast);
        addStudentFrame.add(studentCategory);

        //add buttons
        addStudentFrame.add(submit);
        addStudentFrame.add(reset);
        addStudentFrame.add(returnToMainMenu);

        //default operations
        addStudentFrame.setSize(800,500);
        addStudentFrame.setLocation(300,200);
        addStudentFrame.setLayout(null);
        addStudentFrame.setVisible(true);
        addStudentFrame.setResizable(false);
        addStudentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

//=======================Delete student=========================================================

class DeleteStudent extends JFrame{

    JFrame deleteStudentFrame;
    JLabel lenterId;
    JTextField enterStudentId;
    JButton deleteStudent, returnBack, resetId;

    DeleteStudent(){
        deleteStudentFrame = new JFrame("Remove Student");

        lenterId = new JLabel("Enter Student ID");
        lenterId.setBounds(10,12,300,30);

        enterStudentId = new JTextField();
        enterStudentId.setBounds(50,50,200,30);

        deleteStudent = new JButton("Remove Student");
        deleteStudent.setBounds(50,100,200,40);

        deleteStudent.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    //delete the student from the database
                    Connection conn = DBConnection.getDBConnection();
                    String query = "DELETE FROM `student_data` WHERE student_id=?";
                    PreparedStatement prepStmt = conn.prepareStatement(query);
                    prepStmt.setString(1,enterStudentId.getText());
                    int result = prepStmt.executeUpdate();
                    conn.close();

                    if (result > 0){
                        JOptionPane.showMessageDialog(deleteStudent,"Student Successfuly Removed!");
                    }else {
                        JOptionPane.showMessageDialog(deleteStudent,"Something Went Wrong! Try Again");
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        resetId = new JButton("Reset");
        resetId.setBounds(50,150,200,40);

        resetId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //reset the id field
                enterStudentId.setText("");
            }
        });

        returnBack = new JButton("Admin Panel");
        returnBack.setBounds(50,200,200,40);

        returnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //return to the admin panel frame
                new Home();
                deleteStudentFrame.dispose();
            }
        });

        deleteStudentFrame.add(enterStudentId);
        deleteStudentFrame.add(deleteStudent);
        deleteStudentFrame.add(resetId);
        deleteStudentFrame.add(returnBack);
        deleteStudentFrame.add(lenterId);

        //default operations
        deleteStudentFrame.setSize(300,300);
        deleteStudentFrame.setLocation(450,200);
        deleteStudentFrame.setLayout(null);
        deleteStudentFrame.setVisible(true);
        deleteStudentFrame.setResizable(false);
        deleteStudentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}

class UpdateStudent {

    JFrame updateStudentFrame;

    JLabel lName,
            lEmail,
            lMobile,
            lAddress,
            lCity,
            lState,
            lDistrict,
            lTaluka,
            lDob,
            lFee,
            lCategory,
            lCast,

    lenterId;

    JTextField studentName,
            studentEmail,
            studentMobile,
            studentAddress,
            studentCity,
            studentState,
            studentDistrict,
            studentId,
            studentDob,
            studentFee,
            studentCategory,
            studentCast,
            enterId;

    JButton update, returnToMainMenu, findStudent;

    UpdateStudent(){

        updateStudentFrame = new JFrame("Update Student");

        lenterId = new JLabel("Enter Student ID: ");
        lenterId.setBounds(10,10,200,30);

        lName = new JLabel("Fullname: ");
        lName.setBounds(50,50,100,30);

        lEmail = new JLabel("Email: ");
        lEmail.setBounds(390,50,100,30);

        lMobile = new JLabel("Mobile: ");
        lMobile.setBounds(50,120,100,30);

        lAddress = new JLabel("Address: ");
        lAddress.setBounds(390,120,100,30);

        lCity = new JLabel("City");
        lCity.setBounds(50,190,100,30);

        lState = new JLabel("State: ");
        lState.setBounds(390,190,100,30);

        lDistrict = new JLabel("District");
        lDistrict.setBounds(50,260,100,30);

        lTaluka = new JLabel("Student ID");
        lTaluka.setBounds(390,260,100,30);

        lDob = new JLabel("Dob");
        lDob.setBounds(50,330,100,30);

        lFee = new JLabel("Fee");
        lFee.setBounds(390,330,100,30);

        lCategory = new JLabel("Category");
        lCategory.setBounds(50,400,100,30);

        lCast = new JLabel("Cast");
        lCast.setBounds(390,400,100,30);


        //get the student id
        enterId = new JTextField();
        enterId.setBounds(160,10,200,30);

        studentName = new JTextField();
        studentName.setBounds(160,50,200,30);

        studentEmail = new JTextField();
        studentEmail.setBounds(480,50,200,30);

        studentMobile = new JTextField();
        studentMobile.setBounds(160,120,200,30);

        studentAddress = new JTextField();
        studentAddress.setBounds(480,120,200,30);

        studentCity = new JTextField();
        studentCity.setBounds(160,190,200,30);

        studentState = new JTextField();
        studentState.setBounds(480,190,200,30);

        studentDistrict = new JTextField();
        studentDistrict.setBounds(160,260,200,30);

        studentId = new JTextField();
        studentId.setBounds(480,260,200,30);
        studentId.setEditable(false);

        studentDob = new JTextField();
        studentDob.setBounds(160,330,200,30);

        studentFee = new JTextField();
        studentFee.setBounds(480,330,200,30);

        studentCategory = new JTextField();
        studentCategory.setBounds(160,400,200,30);

        studentCast = new JTextField();
        studentCast.setBounds(480,400,200,30);

    //find the student
        findStudent = new JButton("Find Student");
        findStudent.setBounds(480,10,200,30);

        findStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //get the student id and set to the respective fields
                String stuId = enterId.getText();
                try {
                    Connection con = DBConnection.getDBConnection();
                    Statement stmt = con.createStatement();
                    String query = "SELECT * FROM `student_data` WHERE student_id='"+stuId+"'";
                    ResultSet resultSet = stmt.executeQuery(query);

                    while (resultSet.next()){
                        //fetch the values and set to the respective texifield
                        studentName.setText(resultSet.getString(2));
                        studentEmail.setText(resultSet.getString(3));
                        studentMobile.setText(resultSet.getString(4));
                        studentAddress.setText(resultSet.getString(5));
                        studentCity.setText(resultSet.getString(6));
                        studentId.setText(resultSet.getString(7));
                        studentDistrict.setText(resultSet.getString(8));
                        studentState.setText(resultSet.getString(9));
                        studentCast.setText(resultSet.getString(10));
                        studentCategory.setText(resultSet.getString(11));
                        studentDob.setText(resultSet.getString(12));
                        studentFee.setText(resultSet.getString(13));
                    }
                    con.close();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        //action buttons
        update = new JButton("Update");
        update.setBounds(160,480,200,30);

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //send the updated data to the database
                try {
                    Connection con = DBConnection.getDBConnection();
                    String query = "UPDATE `student_data` SET student_name=?, email_address=?, mobile_no=?, " +
                            "address=?, city=?, disctict=?, state=?, cast=?, category=?, dob=?, fee=? WHERE " +
                            "student_id=?";

                    PreparedStatement prepStmt = con.prepareStatement(query);

                    prepStmt.setString(1,studentName.getText());
                    prepStmt.setString(2,studentEmail.getText());
                    prepStmt.setString(3,studentMobile.getText());
                    prepStmt.setString(4,studentAddress.getText());
                    prepStmt.setString(5,studentCity.getText());
                    prepStmt.setString(6,studentDistrict.getText());
                    prepStmt.setString(7,studentState.getText());
                    prepStmt.setString(8,studentCast.getText());
                    prepStmt.setString(9,studentCategory.getText());
                    prepStmt.setString(10,studentDob.getText());
                    prepStmt.setString(11,studentFee.getText());
                    prepStmt.setString(12,enterId.getText());

                    int result = prepStmt.executeUpdate();

                    if (result > 0) {
                        JOptionPane.showMessageDialog(updateStudentFrame,"Student Updated Successfully");
                    }else {
                        JOptionPane.showMessageDialog(updateStudentFrame,"Something Went Wrong. Please Try Again!");
                    }
                    con.close();
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });

        //return button
        returnToMainMenu = new JButton("Back");
        returnToMainMenu.setBounds(480,480,200,30);

        returnToMainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new Home();
                updateStudentFrame.dispose();
            }
        });

        //add testfields
        updateStudentFrame.add(studentName);
        updateStudentFrame.add(studentCast);
        updateStudentFrame.add(studentAddress);
        updateStudentFrame.add(studentCategory);
        updateStudentFrame.add(studentState);
        updateStudentFrame.add(studentFee);
        updateStudentFrame.add(studentId);
        updateStudentFrame.add(studentDistrict);
        updateStudentFrame.add(studentEmail);
        updateStudentFrame.add(studentMobile);
        updateStudentFrame.add(studentCity);
        updateStudentFrame.add(studentDob);
        updateStudentFrame.add(enterId);

        //add labels
        updateStudentFrame.add(lenterId);
        updateStudentFrame.add(lName);
        updateStudentFrame.add(lAddress);
        updateStudentFrame.add(lCast);
        updateStudentFrame.add(lCategory);
        updateStudentFrame.add(lCity);
        updateStudentFrame.add(lDistrict);
        updateStudentFrame.add(lDob);
        updateStudentFrame.add(lEmail);
        updateStudentFrame.add(lFee);
        updateStudentFrame.add(lTaluka);
        updateStudentFrame.add(lMobile);
        updateStudentFrame.add(lState);

        //add buttons
        updateStudentFrame.add(update);
        updateStudentFrame.add(returnToMainMenu);
        updateStudentFrame.add(findStudent);

        updateStudentFrame.setSize(800,600);
        updateStudentFrame.setLayout(null);
        updateStudentFrame.setVisible(true);
        updateStudentFrame.setResizable(false);
        updateStudentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
//=================================================================================================================
//find the student and show into the table

class FindStudent{

    JFrame findStudentFrame;
    JTable studentDetails;
    JTextField enterStudentID;
    JLabel lenterID;
    JButton findStudentBtn, returnToMainMenu;
    JTable table;

    FindStudent(){

        findStudentFrame = new JFrame("Find Student Details");

        lenterID = new JLabel("Enter Student ID: ");
        lenterID.setBounds(80,10,200,30);

        enterStudentID = new JTextField();
        enterStudentID.setBounds(230,10,200,30);

        findStudentBtn = new JButton("Find Details");
        findStudentBtn.setBounds(450,10,150,30);

        table.setBounds(10,50,500,500);

        //get the student id and search for the database
        try {
            String stuId = enterStudentID.getText();
            Connection con = DBConnection.getDBConnection();
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM `student_data` WHERE student_id='"+stuId+"'";
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()){
                //set the data to the array
                String data[][] = {
                    {"Student Name", resultSet.getString(2)},
                    {"Email address", resultSet.getString(3)},
                    {"Mobile number", resultSet.getString(4)},
                    {"Address", resultSet.getString(5)},
                    {"City", resultSet.getString(6)},
                    {"Student ID", resultSet.getString(7)},
                    {"District", resultSet.getString(8)},
                    {"State", resultSet.getString(9)},
                    {"Cast", resultSet.getString(10)},
                    {"Category", resultSet.getString(11)},
                    {"Date of Birth", resultSet.getString(12)},
                    {"Fees", resultSet.getString(13)},
                    {"Admission Date",resultSet.getString(14)}
                };
            }
            con.close();
        }catch (Exception ee){
            ee.printStackTrace();
        }

        findStudentFrame.add(lenterID);
        findStudentFrame.add(enterStudentID);
        findStudentFrame.add(findStudentBtn);
        findStudentFrame.add(table);

        findStudentFrame.setSize(600,300);
        findStudentFrame.setLayout(null);
        findStudentFrame.setVisible(true);
        findStudentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }
}
