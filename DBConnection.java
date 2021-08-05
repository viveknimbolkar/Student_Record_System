//this class is used to get the connection object

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    static Connection con;

    public static Connection getDBConnection(){

        String url = "jdbc:mysql://localhost:3306/stu-manage";

        String user = "root";

        String pwd = "";

        try {

            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(url,user,pwd);

        }catch (Exception e){

            e.printStackTrace();
        }

        return con;
    }


}
