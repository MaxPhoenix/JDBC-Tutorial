import java.sql.*;

public class Statements {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/EMP";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String[] args) {
        Connection conn = null;
        /*Statement
        Use the for general-purpose access to your database.
        Useful when you are using static SQL statements at runtime.
        The Statement interface cannot accept parameters.
         */
        Statement stmt = null;

        /*PreparedStatement
        Use the when you plan to use the SQL statements many times.
        The PreparedStatement interface accepts input parameters at runtime.
         */
        PreparedStatement ps = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "UPDATE Employees set age=30 WHERE id=103";

            // Let us check if it returns a true Result Set or not.
            Boolean ret = stmt.execute(sql);
            System.out.println("Return value is : " + ret.toString() );

            String sql2 = "INSERT INTO Employees (age,firstName,LastName) VALUES " +
                          "(18, 'Max', 'Power'),"+
                          "(19, 'Agus', 'Alex'),"+
                          "(42, 'Cund', 'Arg')";
            // Let us update age of the record with ID = 103;
            int rows = stmt.executeUpdate(sql2);
            System.out.println("Rows impacted : " + rows );

            // Let us select all the records and display them.
            sql = "SELECT id, firstName, lastName, age FROM Employees";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("firstName");
                String last = rs.getString("lastName");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Age: " + age);
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);
            }
            String sql3 = "INSERT INTO Employees (age,firstName,LastName) VALUES  (?,?,?)";
            ps = conn.prepareStatement(sql3);

            ps.setInt(1,25);
            ps.setString(2,"Cosme");
            ps.setString(3,"Fulanito");
            ps.executeUpdate();

            String sql4 = "SELECT * FROM Employees WHERE Employees.id = ?";
            ps = conn.prepareStatement(sql4);
            ps.setInt(1,5);
            ResultSet rs2 = ps.executeQuery();
            if(rs2.next()){
                System.out.println(rs2.getInt("id"));

            }



            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try


        System.out.println("Goodbye!");
    }//end main
}
