package controller;

import DBPackage.DBQueries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import DBPackage.JDBC;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
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
    public static User passedUser = null;
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
    public TableColumn customerDivCol;
    public Button fullAppointmentDataButton;
    public Button appointmentReports;
    public Button contactSchedulesButton;
    public Button customerReportsButton;
    public Button logoutButton;

    @Override
    /** Initializer method for the logged in account. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentsTable.setItems(DBQueries.getAllAppointments());
            customersTable.setItems(DBQueries.getCustomers());
        } catch (Exception e) {
            e.printStackTrace();
        }
        appointmentsTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentsDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentsLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentsStartCol.setCellValueFactory(new PropertyValueFactory<>("startDT"));
        appointmentsEndCol.setCellValueFactory(new PropertyValueFactory<>("endDT"));

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerDivCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
    }

    /** Imports the user from the login page. */
    public static void passTheUser(User u){
        passedUser = u;
    }
    /** Handles deleting an appointment. */
    public void deleteAppointmentHandler(ActionEvent actionEvent) throws Exception {
        Appointment a = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if(a == null){
            alert.setTitle("Invalid Selection");
            alert.setContentText("Please select a valid appointment to cancel. ");
            alert.show();
            return;
        }
        DBQueries.deleteAppointment(a);
        alert.setTitle("Success!");
        alert.setContentText("Appointment with id: (" + a.getAppointmentID() + ") of type: (" + a.getType()
                + ") successfully removed from calendar. " );
        alert.show();
        appointmentsTable.setItems(DBQueries.getAllAppointments());
    }
    /** Handles modifying an appointment. */
    public void modifyAppointmentHandler(ActionEvent actionEvent) throws IOException {
        Appointment a = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
        if(a == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a valid customer record to update.");
            alert.show();
            return;
        }
        modifyAppointmentController.passTheAppointment(a);
        gotoModifyAppointmentScreen(actionEvent);
    }
    /** Handles adding an appointment. */
    public void addAppointmentHandler(ActionEvent actionEvent) throws IOException {
        gotoAddAppointmentScreen(actionEvent);
    }
    /** Handles deleting a customer. */
    public void deleteCustomerHandler(ActionEvent actionEvent) throws Exception {
        Customer c = (Customer) customersTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if(c == null){
            alert.setTitle("Invalid Selection");

            alert.setContentText("Please select a valid customer record to delete.");
            alert.show();
            return;
        }
        for(Appointment a : DBQueries.getAllAppointments()){
            if(a.getCustomerID() == c.getID()){
                alert.setTitle("Foreign Key Collision");

                alert.setContentText("The selected customer is associated with one or more existing appointments.\n"+
                        "Please delete all appointments associated with Customer ID: " + c.getID() + " before attempting to delete the customer record.");
                alert.show();
                return;
            }
        }

        DBQueries.deleteCustomer(c);
        alert.setTitle("Success");
        alert.setContentText("Customer ID: " + c.getID() + " successfully removed from the database.");
        alert.show();
        customersTable.setItems(DBQueries.getCustomers());

    }
    /** Handles modifying a customer. */
    public void modifyCustomerHandler(ActionEvent actionEvent) throws IOException {
        Customer c = (Customer) customersTable.getSelectionModel().getSelectedItem();
        if(c == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a valid customer record to update.");
            alert.show();
            return;
        }
        modifyCustomerController.passTheCustomer(c);
        gotoModifyCustomerScreen(actionEvent);
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
    /** Displays the modify customer form. */
    private void gotoModifyCustomerScreen(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/modifyCustomerScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 474, 699);
        stage.setTitle("Scheduling Software");
        stage.setScene(scene);
        stage.show();
    }
    /** Displays the add appointment form. */
    private void gotoAddAppointmentScreen(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/addAppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 474, 699);
        stage.setTitle("Scheduling Software");
        stage.setScene(scene);
        stage.show();
    }
    /** Displays the modify appointment form. */
    private void gotoModifyAppointmentScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/modifyAppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 474, 699);
        stage.setTitle("Scheduling Software");
        stage.setScene(scene);
        stage.show();
    }


    public void gotoFullAppointmentData(ActionEvent actionEvent) {
    }

    public void gotoAppointmentReports(ActionEvent actionEvent) {
    }

    public void gotoContactSchedules(ActionEvent actionEvent) {
    }

    public void gotoCustomerReports(ActionEvent actionEvent) {
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/loginScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Scheduling Software");
        stage.setScene(scene);
        stage.show();
    }
}
