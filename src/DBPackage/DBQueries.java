package DBPackage;

import controller.mainScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
    /** Returns all records in the contacts table as an observable list. */
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
    /** Removes a customer from the database. */
    public static void deleteCustomer(Customer c) throws SQLException {
        String deleteQuery = "DELETE FROM customers WHERE Customer_ID=" + c.getID();
        PreparedStatement ps = JDBC.getConnection().prepareStatement(deleteQuery);
        ps.executeUpdate();
    }

    /** Inserts a new appointment into the database. */
    public static void insertNewAppointment(Appointment a) throws SQLException {
        int appointmentID = a.getAppointmentID();
        String title = a.getTitle();
        String description = a.getDescription();
        String location = a.getLocation();
        String type = a.getType();
        String start = a.getStartDT();
        String end = a.getEndDT();
        String create =  a.getCreateDT();
        String createdBy = a.getCreateMethod();
        String lastUpdate = a.getLastUpdateDT();
        String lastUpdatedBy = a.getLastUpdateMethod();
        int customerID = a.getCustomerID();
        int userID = a.getUserID();
        int contactID = a.getContactID();
        String insertQuery = "INSERT INTO appointments " +
                "VALUES (" + appointmentID + ",'" + title + "','" + description + "','" + location + "','" +
                type + "','" + start + "','" + end + "','" + create + "','" + createdBy + "','" + lastUpdate +
                "','" + lastUpdatedBy + "'," + customerID + "," + userID + "," + contactID + ")";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(insertQuery);
        ps.executeUpdate();
        return;
    }

    /** This is a helper function for getAllAppointments() that allows the times to be properly displayed in the local Timezone. */
    private static String convertToLocalTime(String s){
        //System.out.println(s); Debugging print
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(s, dtf);
        ZoneId localTz = ZoneId.systemDefault();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdt2 = zdt.withZoneSameInstant(localTz);
        String ret = dtf.format(zdt2);
        return ret;
    }

    /** Returns all records in the appointments table as an observable list. */
    public static ObservableList<Appointment> getAllAppointments() throws Exception{
        ObservableList<Appointment> returnAppointments = FXCollections.observableArrayList();
        ResultSet rs = JDBC.getConnection().createStatement().executeQuery("SELECT * FROM appointments");
        while(rs.next()){
            Appointment a = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    convertToLocalTime(rs.getString("Start")),
                    convertToLocalTime(rs.getString("End")),
                    rs.getString("Create_Date"),
                    rs.getString("Created_By"),
                    rs.getString("Last_Update"),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            );
            returnAppointments.add(a);
        }
        return returnAppointments;
    }


    /** Deletes an appointment from the database. */
    public static void deleteAppointment(Appointment a) throws SQLException {
        String deleteQuery = "DELETE FROM appointments WHERE Appointment_ID=" + a.getAppointmentID();
        PreparedStatement ps = JDBC.getConnection().prepareStatement(deleteQuery);
        ps.executeUpdate();
    }
    /** Checks for overlapping appointments.
     * @param start, end, customerID
     * @return true if there are any overlapping times, false if there are not*/
    public static boolean overlappingAppointments(String start, String end, int customerID) throws SQLException {
        String startQuery = "SELECT * FROM appointments WHERE Customer_ID="+customerID+"" +
                " AND Start BETWEEN '" + start + "' AND '" + end + "'";
        String endQuery = "SELECT * FROM appointments WHERE Customer_ID="+customerID+"" +
                " AND End BETWEEN '" + start + "' AND '" + end + "'";

        ResultSet startResults = JDBC.getConnection().createStatement().executeQuery(startQuery);
        ResultSet endResults = JDBC.getConnection().createStatement().executeQuery(endQuery);

        if(!startResults.next() && !endResults.next()){

            return false;
        }
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Overlapping Appointments");
        a.setHeaderText(null);
        a.setContentText("Your selected appointment times overlap with an existing record for Customer: "+
                customerID + "\nPlease enter valid appointment times. ");
        a.show();
        return true;
    }
}
