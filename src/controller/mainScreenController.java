package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.JDBC;
import model.User;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/** This is the controller for mainScreen.fxml. @author Spencer Watkins */
public class mainScreenController implements Initializable {
    public Button deleteAppointmentButton;
    public Button modifyAppointmentButton;
    public Button addAppointmentButton;
    public Button deleteCustomerButton;
    public Button modifyCustomerButton;
    public Button customerAddButton;
    private static User passedUser = null;
    public TableColumn appointmentsTitleCol;
    public TableColumn appointmentsDescCol;
    public TableColumn appointmentsLocCol;
    public TableColumn appointmentsStartCol;
    public TableColumn appointmentsEndCol;
    public TableView appointmentsTable;
    public TableView customersTable;
    public TableColumn customerIDCol;
    public TableColumn customerNameCol;
    public TableColumn customerAddressCol;
    public TableColumn customerPostalCol;
    public TableColumn customerPhoneCol;

    @Override
    /** Initializer method for the logged in account. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentsTable.setItems(getUserAppointments());
            customersTable.setItems(getCustomers());
        } catch (Exception e) {
            e.printStackTrace();
        }
        appointmentsTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentsDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentsLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentsStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentsEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }

    /** Imports the user from the login page. */
    public static void passTheUser(User u){
        passedUser = u;
    }
    /** Handles deleting an appointment. */
    public void deleteAppointmentHandler(ActionEvent actionEvent) {
    }
    /** Handles modifying an appointment. */
    public void modifyAppointmentHandler(ActionEvent actionEvent) {
    }
    /** Handles adding an appointment. */
    public void addAppointmentHandler(ActionEvent actionEvent) {
    }
    /** Handles deleting a customer. */
    public void deleteCustomerHandler(ActionEvent actionEvent) {
    }
    /** Handles modifying a customer. */
    public void modifyCustomerHandler(ActionEvent actionEvent) {
    }
    /** Handles adding a customer. */
    public void addCustomerHandler(ActionEvent actionEvent) throws IOException {
        goToAddCustomerScreen(actionEvent);
    }
    /** Displays the add customer form. */
    private void goToAddCustomerScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addCustomerScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 474, 699);
        stage.setTitle("Scheduling Software");
        stage.setScene(scene);
        stage.show();
    }
    /** Populates the appointments table with all appointments associated with the logged in user. */
    private ObservableList<Appointment> getUserAppointments() throws Exception{
        ObservableList<Appointment> returnAppointments = FXCollections.observableArrayList();
        Connection c = JDBC.getConnection();
        Statement st = c.createStatement();
        String userQuery = "SELECT * FROM appointments WHERE User_ID = " + passedUser.getUserID();
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
    private ObservableList<Customer> getCustomers() throws Exception{
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
