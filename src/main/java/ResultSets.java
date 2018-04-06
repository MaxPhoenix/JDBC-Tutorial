import java.sql.*;

public class ResultSets {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/EMP";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql;
            sql = "INSERT INTO Employees (age,firstName,LastName) VALUES " +
                    "(?,?,?)," +
                    "(?,?,?)," +
                    "(?,?,?)";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, 50);
            stmt.setString(2, "Juancho");
            stmt.setString(3, "Suarez");


            stmt.setInt(4, 25);
            stmt.setString(5, "Pancho");
            stmt.setString(6, "Suarez");


            stmt.setInt(7, 35);
            stmt.setString(8, "Gercho");
            stmt.setString(9, "Suarez");

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected : " + rowsAffected);

            String select = "SELECT * FROM Employees WHERE id BETWEEN ? AND ?";
            stmt = conn.prepareStatement(select);
            stmt.setInt(1, 2);
            stmt.setInt(2, 5);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                //same methods (get index or get by column name)
                System.out.println("nombre por indice : "+rs.getString(3));
                System.out.println("nombre por columna : "+rs.getString("firstName"));
            }

            /*
            //move to first row, update first and then update the row in the database
            rs.first();
            rs.updateString("first","WOLOLO");
            rs.updateRow();

            //insert row
            rs.moveToInsertRow();
            rs.updateInt(1,200);
            rs.updateInt(2,80);
            rs.updateString(3,"Jorge");
            rs.updateString(4,"Lino");
            rs.insertRow();
            */
            rs = stmt.executeQuery();

            while(rs.next()) {

                System.out.println(rs.getInt(1));
                System.out.println(rs.getInt(2));
                System.out.println(rs.getString("firstName"));
                System.out.println(rs.getString(4));
            }
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
