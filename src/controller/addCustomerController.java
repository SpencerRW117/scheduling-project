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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import model.JDBC;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class addCustomerController implements Initializable {
    public TextField customerIDField;
    public TextField customerNameField;
    public TextField customerAddressField;
    public TextField customerPostalField;
    public TextField customerPhoneField;
    public ComboBox customerCountryCombo;
    public ComboBox customerRegionCombo;
    public Button submitButton;
    public Button cancelButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerCountryCombo.setItems(getCountryNames());
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }


    }
    /** Gets the names from the list of Country records to be displayed in the combo box. */
    private ObservableList<String> getCountryNames() throws Exception {
        ObservableList<String> names = FXCollections.observableArrayList();
        for(Country c : getCountries()){
            names.add(c.getName());
        }
        return names;
    }
    /** Makes an Observable list of Countries. */
    private ObservableList<Country> getCountries() throws SQLException{
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
    /** Makes an Observable List of all the first level divisions. */
    private ObservableList<FirstLevelDivision> getRegions() throws SQLException{
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

    /** Filters and sets the appropriate regions for the selected country upon selection. */
    public void setRegionNames(ActionEvent actionEvent) throws SQLException {
        String selectedCountryName = (String) customerCountryCombo.getValue();
        int selectedCountryID = 0;
        //System.out.println(selectedCountryName);
        for(Country c : getCountries()){
            if(c.getName().equals(selectedCountryName)){
                selectedCountryID = c.getID();
                break;
            }
        }
        ObservableList<String> filteredRegionNames = FXCollections.observableArrayList();
        for(FirstLevelDivision f : getRegions()){
            if(f.getCountryID() == selectedCountryID){
                filteredRegionNames.add(f.getDivision());
            }
        }
        customerRegionCombo.setItems(filteredRegionNames);
    }

    private int getNewDivID() throws SQLException {
        String selectedRegionName = (String) customerRegionCombo.getValue();
        for(FirstLevelDivision f : getRegions()){
            if(f.getDivision().equals(selectedRegionName)){
                return f.getID();
            }
        }
        return 0;
    }

    public void submitNewCustomer(ActionEvent actionEvent) throws Exception {
        goToMainScreen(actionEvent);

    }

    public void cancelNewCustomer(ActionEvent actionEvent) throws IOException {
        goToMainScreen(actionEvent);
    }

    private void goToMainScreen(ActionEvent a) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        Stage stage = (Stage) ((Node) a.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 906, 537);
        stage.setTitle("Scheduling Software");
        stage.setScene(scene);
        stage.show();
    }

}
