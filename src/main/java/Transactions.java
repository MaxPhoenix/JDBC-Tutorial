import com.mysql.jdbc.ResultSetRow;

import java.sql.*;

public class Transactions {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/EMP";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String [] args){
        Connection conn = null;
        PreparedStatement statement = null;
        Savepoint sp = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //turn autocommit off
            conn.setAutoCommit(false);

            String sql = "INSERT INTO Employees (age, firstName, lastName) VALUES (?,?,?)";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, 26);
            statement.setString(2, "Maxi");
            statement.setString(3, "Lencina");
            statement.executeUpdate();

            sp = conn.setSavepoint("Savepoint1");




            // In this case it will throw an exception so the transaction fails and is rollbacked later on (if one operation fails)
            String sql2 = "INSERTED INTO Employees (age, firstName, lastName) VALUES (?,?,?)";
            statement = conn.prepareStatement(sql2);
            statement.setInt(1, 25);
            statement.setString(2, "Maxi");
            statement.setString(3, "Lencina");
            statement.executeUpdate();


            conn.commit();


        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch(SQLException e){
            e.printStackTrace();
            try {
                //rollback comun
                //conn.rollback();
                //rollback con un savepoint
                conn.rollback(sp);

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        finally{
            try {
            String sql = "SELECT * FROM Employees";
            statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                System.out.println(" id: "+rs.getInt(1)
                                    +" \n age " + rs.getInt(2)
                                    +" \n firstName  " + rs.getString(3)
                                    +" \n lastName " + rs.getString(4));
            }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



    }
}
