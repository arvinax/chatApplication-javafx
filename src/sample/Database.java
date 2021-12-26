package sample;

import java.sql.*;

import static java.lang.System.*;

public class Database {

    public static Connection getConnection() throws Exception{
        try{
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sara", "root","");
            return myConn;
        }catch (Exception e) {
            out.println(e);
        }
        return null;
    }


}
