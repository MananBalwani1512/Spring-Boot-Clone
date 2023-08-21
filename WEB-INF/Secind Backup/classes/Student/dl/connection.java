package Student.dl;
import java.sql.*;
public class connection
{
    public static Connection getConnection()
    {
        Connection connection = null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.DriverManager");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project","root","ISRO");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return connection;
    }    
}
