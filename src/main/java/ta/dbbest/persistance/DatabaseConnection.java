package ta.dbbest.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public Connection getConnection() {
        try {
            Class.forName("org.h2.Driver");
            String pass = "";
            String user = "sa";
            String url = "jdbc:h2:~/test";
            return DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
