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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.AppointmentHelpers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/** Controls behavior for contactScheduleScreen.fxml.
 * @author Spencer Watkins*/
public class contactScheduleScreenController implements Initializable {
    public TableView appointmentsTable;
    public TableColumn appointmentIDCol;
    public TableColumn titleCol;
    public TableColumn typeCol;
    public TableColumn descriptionCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn customerIdCol;
    public ComboBox contactCombo;
    public Button backButton;

    @Override
    /** Initializer method, sets the combo box to the company contacts. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDT"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDT"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        try {
            contactCombo.setItems(AppointmentHelpers.getContactNames());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /** Sets appointments in the table filtered by the selected contact. */
    public void setContactAppointments(ActionEvent actionEvent) throws Exception {
        ObservableList<Appointment> filtered = FXCollections.observableArrayList();
        String contactName = (String) contactCombo.getValue();
        for(Appointment a: DBQueries.getAllAppointments()){
            if(a.getContactName().equals(contactName)){
                filtered.add(a);
            }
        }
        appointmentsTable.setItems(filtered);
    }
    /** Returns to the main dashboard. */
    public void gotoMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 906, 537);
        stage.setTitle("Scheduling Software");
        stage.setScene(scene);
        stage.show();
    }

}
