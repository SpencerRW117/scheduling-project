package controller;

import DBPackage.DBQueries;
import DBPackage.JDBC;
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
import model.Country;
import model.FirstLevelDivision;
import model.Report;
import model.ReportInterface;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
/** This class controls the behavior for customerReports.fxml.
 * @author Spencer Watkins */
public class customerReportsController implements Initializable {

    public TableView reportTable;
    public TableColumn regionCol;
    public TableColumn countCol;
    public ComboBox countryCombo;
    public Button backButton;


    @Override
    /** Initializer method, sets the country options. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countryCombo.setItems(getCountryNames());
            regionCol.setCellValueFactory(new PropertyValueFactory<>("stringParam1"));
            countCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Gets the names from the list of Country records to be displayed in the combo box. */
    private ObservableList<String> getCountryNames() throws Exception {
        ObservableList<String> names = FXCollections.observableArrayList();
        for(Country c : DBQueries.getCountries()){
            names.add(c.getName());
        }
        return names;
    }

    /** Sets the items in the report table to be a count of customers per territory in the selected country. Contains lambda.
     * @lambda This executes a complex SQL query that returns a count customers in each region of the selected country.
     * JUSTIFICATION: Using a single report interface with lambda expressions allows for use-specific modification of SQL queries, without adding excess files to the program. */
    public void setReportTable(ActionEvent actionEvent) throws SQLException {
        String selectedCountry = (String) countryCombo.getValue();

        ReportInterface customerReportLambda = () -> {
            ResultSet rs = JDBC.getConnection().createStatement().executeQuery("SELECT Division, Country, COUNT(*) " +
                    " FROM first_level_divisions " +
                    " INNER JOIN customers ON customers.Division_ID = first_level_divisions.Division_ID " +
                    " INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID" +
                    " GROUP BY Division");
            ObservableList<Report> ret = FXCollections.observableArrayList();
            while(rs.next()){
                if(rs.getString("Country").equals(selectedCountry)) {
                    Report r = new Report(rs.getString("Division"), rs.getInt("COUNT(*)"));
                    ret.add(r);
                }
            }
            return ret;
        };
        reportTable.setItems(customerReportLambda.appointmentResultSetGen());
    }

    /** Returns back to the main screen. */
    public void backToMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 906, 537);
        stage.setTitle("Scheduling Software");
        stage.setScene(scene);
        stage.show();
    }
}
