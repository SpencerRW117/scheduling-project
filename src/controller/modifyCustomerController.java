package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;


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
import jdk.jshell.spi.ExecutionControlProvider;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import DBPackage.JDBC;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;

/** This class handles all the functionality for addCustomerScreen.fxml.
 * @Author Spencer Watkins */
public class modifyCustomerController implements Initializable {
    public TextField customerIDField;
    public TextField customerNameField;
    public TextField customerAddressField;
    public TextField customerPostalField;
    public TextField customerPhoneField;
    public ComboBox customerCountryCombo;
    public ComboBox customerRegionCombo;
    public Button submitButton;
    public Button cancelButton;
    private static Customer passedCustomer = null;

    /** Initializer method, sets the countries combo box to names selected from the database. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerCountryCombo.setItems(getCountryNames());
            customerNameField.setText(passedCustomer.getName());
            customerAddressField.setText(passedCustomer.getAddress());
            customerPostalField.setText(passedCustomer.getPostalCode());
            customerPhoneField.setText(passedCustomer.getPhoneNumber());
            customerCountryCombo.setValue(countryIDToName());
            customerRegionCombo.setValue(regionIDToName());
            setRegionNames();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }
    /** Brings in the customer to modify from the main screen. */
    public static void passTheCustomer(Customer c) {passedCustomer = c;}
    /** Returns the string valued name associated with the passed in region ID. */
    public String regionIDToName() throws Exception{
        int id = passedCustomer.getDivisionID();
        for(FirstLevelDivision f : DBQueries.getRegions()){
            if(f.getID() == id){
                return f.getDivision();
            }
        }
        return "";
    }
    /** Determines the appropriate country based on the passed in region ID. */
    public String countryIDToName() throws Exception{
        int id = passedCustomer.getDivisionID();
        if (id > 0 && id < 55) { return "U.S";}
        else if (id > 55 && id < 80) {return "Canada";}
        else if (id > 80) {return "UK";}
        else return "";
    }
    /** Gets the names from the list of Country records to be displayed in the combo box. */
    private ObservableList<String> getCountryNames() throws Exception {
        ObservableList<String> names = FXCollections.observableArrayList();
        for(Country c : DBQueries.getCountries()){
            names.add(c.getName());
        }
        return names;
    }
    /** Filters and sets the appropriate regions for the selected country upon selection. */
    public void setRegionNames() throws SQLException {
        String selectedCountryName = (String) customerCountryCombo.getValue();
        int selectedCountryID = 0;
        //System.out.println(selectedCountryName);
        for(Country c : DBQueries.getCountries()){
            if(c.getName().equals(selectedCountryName)){
                selectedCountryID = c.getID();
                break;
            }
        }
        ObservableList<String> filteredRegionNames = FXCollections.observableArrayList();
        for(FirstLevelDivision f : DBQueries.getRegions()){
            if(f.getCountryID() == selectedCountryID){
                filteredRegionNames.add(f.getDivision());
            }
        }
        customerRegionCombo.setItems(filteredRegionNames);
    }
    /** Gets the division ID for the selected region. */
    private int getNewDivID() throws SQLException {
        String selectedRegionName = (String) customerRegionCombo.getValue();
        for(FirstLevelDivision f : DBQueries.getRegions()){
            if(f.getDivision().equals(selectedRegionName)){
                return f.getID();
            }
        }
        return 0;
    }
    /** Handles submission of the new customer form into the database and returns to the dashboard. */
    public void submitUpdatedCustomer(ActionEvent actionEvent) throws Exception {
        java.util.Date date = new Date();
        Timestamp curr = new Timestamp(date.getTime());

        int newID = passedCustomer.getID();
        String newName = customerNameField.getText();
        String newAddress = customerAddressField.getText();
        String newPostal = customerPostalField.getText();
        String newPhone = customerPhoneField.getText();
        Timestamp createDate = curr;
        String createdBy = "user";
        Timestamp lastUpdate = curr;
        String lastUpdatedBy = "user";
        int newDivID = getNewDivID();

        if(newName.equals("") || newAddress.equals("") || newPostal.equals("") || newPhone.equals("") || newDivID == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid information in all fields. ");
            alert.show();
            return;
        }

        Customer c = new Customer(newID, newName, newAddress, newPostal, newPhone, createDate,
                createdBy, lastUpdate, lastUpdatedBy, newDivID);
        DBQueries.updateCustomer(c);
        goToMainScreen(actionEvent);

    }
    /** Cancels the new customer form and returns to dashboard. */
    public void cancelNewCustomer(ActionEvent actionEvent) throws IOException {
        goToMainScreen(actionEvent);
    }
    /** Redirects to the main dashboard. */
    private void goToMainScreen(ActionEvent a) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        Stage stage = (Stage) ((Node) a.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 906, 537);
        stage.setTitle("Scheduling Software");
        stage.setScene(scene);
        stage.show();
    }

}
