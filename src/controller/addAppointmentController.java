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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateTimeStringConverter;
import model.Appointment;
import model.AppointmentHelpers;
import model.Contact;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

/** Controls all the behavior for addAppointmentScreen.fxml.
 * @author Spencer Watkins*/
public class addAppointmentController implements Initializable {
    public TextField appointmentIDField;
    public TextField appointmentTitleField;
    public TextField appointmentDescriptionField;
    public TextField appointmentLocationField;
    public TextField appointmentTypeField;
    public TextField appointmentStartField;
    public TextField appointmentEndField;
    public TextField customerIDField;
    public TextField userIDField;
    public ComboBox contactComboBox;
    public Button submitButton;
    public Button cancelButton;

    @Override
    /** Initializer method, sets the combo box to the appropriate contact names. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactComboBox.setItems(AppointmentHelpers.getContactNames());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /** Returns the matching contact ID from the selected name. */
    public int contactNameToID() throws Exception {
        String name = (String) contactComboBox.getValue();
        for(Contact c : DBQueries.getContacts()){
            if(c.getContactName().equals(name)){
                return c.getContactID();
            }
        }
        return 0;
    }

    /** Handles the "submit" button event. Runs sanity checks on filled data and uploads the new appointment to the database. */
    public void submitNewAppointment(ActionEvent actionEvent) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int appointmentID = AppointmentHelpers.newIDGen();
        String title = appointmentTitleField.getText();
        String description = appointmentDescriptionField.getText();
        String location = appointmentLocationField.getText();
        String type = appointmentTypeField.getText();
        //Sanity check on the string fields
        if(title.equals("") || description.equals("") || location.equals("") || type.equals("")){
            AppointmentHelpers.showError();
            return;
        }
        //Sanity check on the raw business hours
        String rawStart = appointmentStartField.getText();
        String rawEnd = appointmentEndField.getText();
        if(!AppointmentHelpers.validateBusinessHours(rawStart, rawEnd)){
            return;
        }
        //If the raw stuff in the fields passes validation checks, convert them to UTC and submit.
        String start = AppointmentHelpers.convertToUTC(rawStart);
        String end = AppointmentHelpers.convertToUTC(rawEnd);

        String createDate = AppointmentHelpers.convertToUTC(LocalDateTime.now().format(dtf));
        String createdBy = "user";
        String lastUpdate = createDate;
        String lastUpdatedBy = createdBy;
        int customerID = Integer.parseInt(customerIDField.getText());
        int userId = Integer.parseInt(userIDField.getText());
        int contactID = contactNameToID();
        //Sanity check on FK id's, should never be 0
        if(customerID == 0 || userId == 0 || contactID == 0){
            AppointmentHelpers.showError();
            return;
        }
        //If it passes all the previous sanity checks, finally check for overlapping times for the given customer.
        if(DBQueries.overlappingAppointments(start, end, customerID)){
            return;
        }

        Appointment a = new Appointment(appointmentID, title, description, location, type, start, end,
                createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userId, contactID);
        DBQueries.insertNewAppointment(a);
        goToMainScreen(actionEvent);
    }
    /** Cancels the New Appointment form and returns to the main screen. */
    public void cancelNewAppointment(ActionEvent actionEvent) throws IOException {
        goToMainScreen(actionEvent);
    }

    /** Redirects to the main dashboard. */
    private void goToMainScreen(ActionEvent a) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        Stage stage = (Stage) ((Node) a.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 906, 537);
        stage.setTitle("Scheduling Software");
        stage.setScene(scene);
        stage.show();
    }
}
