package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/** This is the controller for mainScreen.fxml. @author Spencer Watkins */
public class mainScreenController implements Initializable {
    public Button deleteAppointmentButton;
    public Button modifyAppointmentButton;
    public Button addAppointmentButton;
    public Button deleteCustomerButton;
    public Button modifyCustomerButton;
    public Button customerAddButton;

    @Override
    /** Initializer method for the logged in account. */
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
    public void addCustomerHandler(ActionEvent actionEvent) {
    }
}
