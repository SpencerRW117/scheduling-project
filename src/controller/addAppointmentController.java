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
import model.Contact;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactComboBox.setItems(getContactNames());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /** Gets the name from a list of contact records to be displayed in the combo box. */
    private ObservableList<String> getContactNames() throws Exception{
        ObservableList<String> names = FXCollections.observableArrayList();
        for(Contact c : DBQueries.getContacts()){
            names.add(c.getContactName());
        }
        return names;
    }
    /** Generates a new ID that is always 1 larger than the largest customer ID. */
    private int newIDGen() throws Exception {
        ObservableList<Appointment> appts = DBQueries.getAllAppointments();
        int temp = 0;
        for (int i = 0; i < appts.size(); i++){
            if(appts.get(i).getAppointmentID() > temp){
                temp = appts.get(i).getAppointmentID();
            }
        }
        return temp + 1;
    }
    /** Returns the matching contact ID from the selected name. */
    private int contactNameToID() throws Exception {
        String name = (String) contactComboBox.getValue();
        for(Contact c : DBQueries.getContacts()){
            if(c.getContactName().equals(name)){
                return c.getContactID();
            }
        }
        return 0;
    }
    private boolean validDate(String inputDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        sdf.setLenient(false);
        try{
            Date d = sdf.parse(inputDate);
            return false;
        } catch (ParseException e) {
            return false;
        }
    }
    public void submitNewAppointment(ActionEvent actionEvent) throws Exception {
        if(!validDate(appointmentStartField.getText()) || !validDate(appointmentEndField.getText())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Date Format");
            alert.setHeaderText(null);
            alert.setContentText("Either the start or end date (or both) are given in an invalid format.\n" +
                    "Please provide start and end datetime as follows: \n" +
                    "Format: \"yyyy-mm-dd hh:mm:ss\" ");
            alert.show();
            return;
        }
        int newApptID = newIDGen();
        String newTitle = appointmentTitleField.getText();
        String newDescription = appointmentDescriptionField.getText();
        String newLocation = appointmentLocationField.getText();
        String newType = appointmentTypeField.getText();
        Timestamp newStart = Timestamp.valueOf(appointmentStartField.getText());
        Timestamp newEnd = Timestamp.valueOf(appointmentEndField.getText());
        Timestamp createDate = new Timestamp(System.currentTimeMillis());
        String createdBy = "user";
        Timestamp lastUpdate = createDate;
        String lastUpdatedBy = createdBy;
        int customerID = Integer.parseInt(customerIDField.getText());
        int userID = Integer.parseInt(userIDField.getText());
        int contactID = contactNameToID();



        Appointment a = new Appointment(newApptID, newTitle, newDescription, newLocation, newType,
                newStart, newEnd, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);

        DBQueries.insertNewAppointment(a);
        goToMainScreen(actionEvent);

    }

    public void cancelNewAppointment(ActionEvent actionEvent) throws IOException {
        goToMainScreen(actionEvent);
    }

    public void getContactIDFromName(ActionEvent actionEvent) {
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
