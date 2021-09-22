package controller;

import DBPackage.DBQueries;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import DBPackage.JDBC;
import model.Appointment;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/** This class controls loginScreen.fxml @author Spencer Watkins*/
public class loginScreenController implements Initializable {
    public TextField loginScreenUsernameField;
    public PasswordField loginScreenPasswordField;
    public Button loginScreenSubmitButton;
    public Button loginScreenExitButton;


    /** Handles the submit credentials event from the login screen and passes the valid user to the dashboard. */
    public void loginScreenSubmitHandler (ActionEvent actionEvent) throws Exception {
        User validUser = validateCredentials();
        if(validUser != null){
            mainScreenController.passTheUser(validUser);
            checkForUpcomingAppointments(validUser);
            goToMainScreen(actionEvent);
        }

        else{ return; }
    }

    /** Checks for appointments starting within 15 minutes of login time and displays appropriate alerts. */
    public void checkForUpcomingAppointments(User u) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        for(Appointment a : DBQueries.getAllAppointments()){
            LocalDateTime aStart = LocalDateTime.parse(a.getStartDT(), dtf);
            if(aStart.isBefore(now.plusMinutes(15)) && aStart.isAfter(now)){
                alert.setTitle("Upcoming Appointment");
                alert.setContentText("You have an appointment in less than 15 minutes.\n" +
                        "Please prepare for appointment ID: " + a.getAppointmentID() + ", at date and time: " + a.getStartDT() + "\n");
                alert.show();
                return;
            }
        }
        alert.setTitle("Welcome");
        alert.setContentText("You do not have any appointments starting in less than 15 minutes.\n");
        alert.show();
        return;
    }

    /** Validates the credentials against the database. */
    private User validateCredentials() throws SQLException {
        Connection c = JDBC.getConnection();
        Statement st = c.createStatement();
        String userNameAndPassQuery = "SELECT User_ID, User_Name, Password FROM users";
        ResultSet rs = st.executeQuery(userNameAndPassQuery);
        String enteredUserName = loginScreenUsernameField.getText();
        String enteredUserPassword = loginScreenPasswordField.getText();
        //System.out.println("STUFF: " + enteredUserPassword + enteredUserName); //Debugging print
        while(rs.next()) {
            int currUserID = rs.getInt(1);
            String currUserName = rs.getString(2);
            String currPassword = rs.getString(3);
            //System.out.println("STUFF 2: " + currPassword + currUserName); //Debugging print
            if (currUserName.equals(enteredUserName) && currPassword.equals(enteredUserPassword)) {
                User u = new User(currUserID, currUserName, currPassword);
                return u;
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid Credentials");
        alert.setHeaderText(null);
        alert.setContentText("Invalid login credentials, please enter a valid username and password.");
        alert.show();
        return null;
    }

    /** Changes the scene to the main dashboard. */
    public void goToMainScreen(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 906, 537);
        stage.setTitle("Scheduling Software");
        stage.setScene(scene);
        stage.show();
    }

    /** Exits the program from the login screen. */
    public void exitProgram() {
        System.exit(1);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
