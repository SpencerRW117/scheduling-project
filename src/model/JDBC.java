package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class JDBC {
        private static final String protocol = "jdbc";
        private static final String vendor = ":mysql:";
        private static final String location = "//localhost/";
        private static final String databaseName = "client_schedule";
        private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
        private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
        private static final String userName = "sqlUser"; // Username
        private static String password = "Passw0rd!"; // Password
        private static Connection connection = null;  // Connection Interface
        private static PreparedStatement preparedStatement;

         public static void makeConnection() {

          try {
              Class.forName(driver); // Locate Driver
              //password = Details.getPassword(); // Assign password
              connection = DriverManager.getConnection(jdbcUrl, userName, password); // reference Connection object
              System.out.println("Connection successful!");
          }
                  catch(ClassNotFoundException e) {
                      System.out.println("Error:" + e.getMessage());
                  }
                  catch(SQLException e) {
                      System.out.println("Error:" + e.getMessage());
                  }
          }

            public static Connection getConnection() {
                return connection;
            }
             public static void closeConnection() {
                 try {
                     connection.close();
                     System.out.println("Connection closed!");
                 } catch (SQLException e) {
                     System.out.println(e.getMessage());
                 }
             }

       public static void makePreparedStatement(String sqlStatement, Connection conn) throws SQLException {
           if (conn != null)
               preparedStatement = conn.prepareStatement(sqlStatement);
           else
               System.out.println("Prepared Statement Creation Failed!");
       }
       public static PreparedStatement getPreparedStatement() throws SQLException {
           if (preparedStatement != null)
               return preparedStatement;
           else System.out.println("Null reference to Prepared Statement");
           return null;
       }

    public static ObservableList<Customer> getCustomers() throws Exception{
        ObservableList<Customer> returnCustomers = FXCollections.observableArrayList();
        Connection c = JDBC.getConnection();
        Statement st = c.createStatement();
        String query = "SELECT * FROM customers";
        ResultSet rs = st.executeQuery(query);
        while(rs.next()){
            Customer tempCus = new Customer(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"),
                    rs.getTimestamp("Create_Date"),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update"),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Division_ID"));
            returnCustomers.add(tempCus);
        }
        return returnCustomers;
    }



}