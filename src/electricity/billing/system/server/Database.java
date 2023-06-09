package electricity.billing.system.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {

    Connection connection;
    public Statement statement; //to execute sql queries

    public Database(){
        //connection made through JDBC, with MySQL
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/[database_name]", "[username]", "[password]");
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            System.out.println("Connection Established");
        } catch (Exception e) {
            System.out.println("Connection not Established");
            System.out.println(e.toString());
        }
    }
}
