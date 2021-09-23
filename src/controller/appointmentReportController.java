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
import model.Appointment;
import model.Report;
import model.ReportInterface;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

/** This class controls the behavior for appointmentReportScree.fxml.
 * @author Spencer Watkins */
public class appointmentReportController implements Initializable {
    public TableView reportTable;
    public TableColumn typeCol;
    public TableColumn countCol;
    public ComboBox monthCombo;
    public Button backButton;



    @Override
    /** Initializaer method, sets the values of the month combo box to the months of the year. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monthCombo.setItems(Report.getMonths());
        typeCol.setCellValueFactory(new PropertyValueFactory<>("stringParam1"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));

    }
    /** This filters the reports table by the selected month.
     * This method includes a lambda expression, described in detail below. */
    public void filterByMonth(ActionEvent actionEvent) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String selectedMonthString = (String) monthCombo.getValue();
        Month selectedMonth = Month.valueOf(selectedMonthString);

        /** @lambda This lambda expression is built on the ReportInterface class. Generates a report of appointments that shows the number of appointments
         * of each type that occur in the selected month.*/
        ReportInterface appointmentReportLambda = () -> {
            ResultSet rs = JDBC.getConnection().createStatement().executeQuery("SELECT Type, Start, COUNT(*) FROM appointments GROUP BY Type");
            ObservableList<Report> ret = FXCollections.observableArrayList();
            while(rs.next()){
                Month m = LocalDateTime.parse(rs.getString("Start"), dtf).getMonth();
                if(m.equals(selectedMonth)){
                    Report r = new Report(rs.getString("Type"), rs.getInt("COUNT(*)"));
                    ret.add(r);
                }
            }
            return ret;
        };

        reportTable.setItems(appointmentReportLambda.appointmentResultSetGen());

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
