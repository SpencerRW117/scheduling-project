package DBPackage;

import controller.mainScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.*;

/** This class handles all the SELECT, INSERT, UPDATE, and DELETE functions from the database.
 * @Author Spencer Watkins */
public class DBQueries {
    /** Returns the full list of records from the customer table. */
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
    /** Returns the full list of records from the first_level_divisions table. */
    public static ObservableList<FirstLevelDivision> getRegions() throws SQLException{
        ObservableList<FirstLevelDivision> r = FXCollections.observableArrayList();
        ResultSet rs = JDBC.getConnection().createStatement().executeQuery("SELECT * FROM first_level_divisions");
        while(rs.next()){
            FirstLevelDivision f = new FirstLevelDivision(
                    rs.getInt("Division_ID"),
                    rs.getString("Division"),
                    rs.getTimestamp("Create_Date"),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update"),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Country_ID"));
            r.add(f);
        }
        return r;
    }
    /** Returns the full list of records from the countries table. */
    public static ObservableList<Country> getCountries() throws SQLException{
        ObservableList<Country> l = FXCollections.observableArrayList();
        ResultSet rs = JDBC.getConnection().createStatement().executeQuery("SELECT * FROM countries");
        while(rs.next()){
            Country c = new Country(
                    rs.getInt("Country_ID"),
                    rs.getString("Country"),
                    rs.getTimestamp("Create_Date"),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update"),
                    rs.getString("Last_Updated_By")
            );
            l.add(c);
        }
        return l;
    }
    /** Populates the appointments table with all appointments associated with the logged in user. */
    public static ObservableList<Appointment> getUserAppointments() throws Exception{
        ObservableList<Appointment> returnAppointments = FXCollections.observableArrayList();
        Connection c = JDBC.getConnection();
        Statement st = c.createStatement();
        String userQuery = "SELECT * FROM appointments WHERE User_ID = " + mainScreenController.passedUser.getUserID();
        ResultSet rs = st.executeQuery(userQuery);
        while(rs.next()){
            Appointment a = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start"),
                    rs.getTimestamp("End"),
                    rs.getTimestamp("Create_Date"),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update"),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID"));
            returnAppointments.add(a);
        }
        return returnAppointments;
    }
    /** Returns all records in the appointments table as an observable list. */
    public static ObservableList<Appointment> getAllAppointments() throws Exception{
        ObservableList<Appointment> returnAppointments = FXCollections.observableArrayList();
        Connection c = JDBC.getConnection();
        Statement st = c.createStatement();
        String userQuery = "SELECT * FROM appointments";
        ResultSet rs = st.executeQuery(userQuery);
        while(rs.next()){
            Appointment a = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start"),
                    rs.getTimestamp("End"),
                    rs.getTimestamp("Create_Date"),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update"),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID"));
            returnAppointments.add(a);
        }
        return returnAppointments;
    }
    /** Reutns all records in the contacts table as an observable list. */
    public static ObservableList<Contact> getContacts() throws Exception{
        ObservableList<Contact> returnContacts = FXCollections.observableArrayList();
        String contactQuery = "SELECT * FROM contacts";
        ResultSet rs = JDBC.getConnection().createStatement().executeQuery(contactQuery);
        while(rs.next()){
            Contact c = new Contact(
                    rs.getInt("Contact_ID"),
                    rs.getString("Contact_Name"),
                    rs.getString("Email")
            );
            returnContacts.add(c);
        }
        return returnContacts;
    }
    /** Inserts a new record into the customers table. */
    public static void insertNewCustomer(Customer c) throws Exception {
        int newID = c.getID();
        String newName = c.getName();
        String newAddress = c.getAddress();
        String newPostal = c.getPostalCode();
        String newPhone = c.getPhoneNumber();
        Timestamp createDate = c.getCreateDate();
        String createdBy = c.getCreatedBy();
        Timestamp lastUpdate = c.getLastUpdate();
        String lastUpdatedBy = c.getLastUpdatedBy();
        int newDivisionID = c.getDivisionID();

        String insertQuery = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, " +
                "Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)" +
                "VALUES (" + newID + ",'" + newName + "','" + newAddress + "','" + newPostal + "','" + newPhone + "', NOW(),'"+
                createdBy + "',NOW(),'" + lastUpdatedBy + "'," + newDivisionID + ")";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(insertQuery);
        ps.executeUpdate();
    }
    /** Updates the record of a given customer based on its ID. Used in the modify customer form. */
    public static void updateCustomer(Customer c) throws SQLException {
        int newID = c.getID();
        String newName = c.getName();
        String newAddress = c.getAddress();
        String newPostal = c.getPostalCode();
        String newPhone = c.getPhoneNumber();
        Timestamp createDate = c.getCreateDate();
        String createdBy = c.getCreatedBy();
        Timestamp lastUpdate = c.getLastUpdate();
        String lastUpdatedBy = c.getLastUpdatedBy();
        int newDivisionID = c.getDivisionID();
        String updateQuery = "UPDATE customers"+
                " SET Customer_Name='"+ newName +"',Address='"+ newAddress +"',Postal_Code='"+ newPostal +"',Phone='"+ newPhone + "'," +
                "Last_Update=NOW(),Last_Updated_By='user',Division_ID="+ newDivisionID +  " WHERE Customer_ID=" + newID;
        PreparedStatement ps = JDBC.getConnection().prepareStatement(updateQuery);
        ps.executeUpdate(updateQuery);
    }

    public static void deleteCustomer(Customer c) throws SQLException {
        String deleteQuery = "DELETE FROM customers WHERE Customer_ID=" + c.getID();
        PreparedStatement ps = JDBC.getConnection().prepareStatement(deleteQuery);
        ps.executeUpdate();
    }

    public static void insertNewAppointment(Appointment a) {
        int appointmentID = a.getAppointmentID();
        String title = a.getTitle();
        String desc = a.getDescription();
        String loc = a.getLocation();
        String type = a.getType();
        Timestamp start = a.getStart();
        Timestamp end = a.getEnd();
        Timestamp create = a.getCreateDate();
        String createdBy = a.getCreatedBy();
        Timestamp lastUpdate = a.getLastUpdate();
        String lastUpdatedBy = a.getLastUpdatedBy();
        int customerID = a.getCustomerID();
        int userID = a.getUserID();
        int contactID = a.getContactID();

    System.out.println("APPT TO ADD: " + appointmentID + " " + title + " " + desc + " " + loc + " " +
            type + " " + start + " " + end + " " + create + " " + createdBy + " " + lastUpdate + " " +
            lastUpdatedBy + " " + customerID + " " + userID + " " + contactID);

    }
}
