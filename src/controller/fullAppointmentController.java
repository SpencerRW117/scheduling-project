package controller;

import DBPackage.DBQueries;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.ResourceBundle;

/** This class controls the behavior for fullAappointmentScreen.fxml/
 * @author Spencer Watkins */
public class fullAppointmentController implements Initializable {
    public TableView appointmentTable;
    public TableColumn appointmentIDCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn typeCol;
    public TableColumn contactCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn custIDCOl;
    public TableColumn userIDCol;
    public RadioButton filterMonthlyRadio;
    public ToggleGroup toggleGroup1;
    public RadioButton filterWeeklyRadio;
    public Button addButton;
    public Button modifyButton;
    public Button deleteButton;
    public Button backButton;
    public RadioButton showAllRadio;


    @Override
    /** Initializer, sets the values for the table. */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            appointmentTable.setItems(DBQueries.getAllAppointments());

        } catch (Exception e) {
            e.printStackTrace();
        }

        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDT"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDT"));
        custIDCOl.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));


    }

    /** Allows the table to be filtered based on the current local month. */
    public void filterMonthly(ActionEvent actionEvent) throws Exception {
        ObservableList<Appointment> filtered = FXCollections.observableArrayList();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Month now = LocalDateTime.now().getMonth();
        for(Appointment a : DBQueries.getAllAppointments()){
            Month mon = LocalDateTime.parse(a.getStartDT(), dtf).getMonth();
            if(mon == now){
                filtered.add(a);
            }
        }
        appointmentTable.setItems(filtered);
    }
    /** Allows the table to be filtered based on current local week. */
    public void filterWeekly(ActionEvent actionEvent) throws Exception {
        ObservableList<Appointment> filtered = FXCollections.observableArrayList();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate today = LocalDate.now();
        int weekOfYear = today.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        for(Appointment a : DBQueries.getAllAppointments()){
            int aWeek = LocalDate.parse(a.getStartDT(), dtf).get(ChronoField.ALIGNED_WEEK_OF_YEAR);
            if(weekOfYear == aWeek){
                filtered.add(a);
            }
        }
        appointmentTable.setItems(filtered);
    }
    /** Takes care of adding appointments, the same as on the main screen. */
    public void addNewAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addAppointmentScreen.fxml"));
        addAppointmentController.passOriginalScreen("Full Appointment");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 474, 699);
        stage.setTitle("Scheduling Software");
        stage.setScene(scene);
        stage.show();
    }
    /** Handles modifying appointments, same as main screen. */
    public void updateAppointment(ActionEvent actionEvent) throws IOException {
        Appointment a = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
        if(a == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a valid customer record to update.");
            alert.show();
            return;
        }
        modifyAppointmentController.passTheAppointment(a);
        modifyAppointmentController.passOriginalScreen("Full Appointment");
        Parent root = FXMLLoader.load(getClass().getResource("/view/modifyAppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 474, 699);
        stage.setTitle("Scheduling Software");
        stage.setScene(scene);
        stage.show();

    }
    /** Handles deleting appointments. */
    public void deleteAppointment(ActionEvent actionEvent) throws Exception {
        Appointment a = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
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
        appointmentTable.setItems(DBQueries.getAllAppointments());
    }
    /** Returns to the main screen. */
    public void backToMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 906, 537);
        stage.setTitle("Scheduling Software");
        stage.setScene(scene);
        stage.show();
    }
    /** Resets the filter to show all appointments in the database. */
    public void showAll(ActionEvent actionEvent) throws Exception {
        appointmentTable.setItems(DBQueries.getAllAppointments());
    }
}
