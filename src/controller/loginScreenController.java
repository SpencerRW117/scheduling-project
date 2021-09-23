package controller;

import DBPackage.DBQueries;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import DBPackage.JDBC;
import model.Appointment;
import model.User;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/** This class controls loginScreen.fxml @author Spencer Watkins*/
public class loginScreenController implements Initializable {
    public TextField loginScreenUsernameField;
    public PasswordField loginScreenPasswordField;
    public Button loginScreenSubmitButton;
    public Button loginScreenExitButton;
    public Label UNlabel;
    public Label PWLabel;
    public Label signinLabel;
    public Label headLabel;
    private String invalidTitle;
    private String invalidContent;


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
    private User validateCredentials() throws SQLException, IOException {
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
                recordLoginAttempt(true);
                return u;
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(invalidTitle);
        alert.setHeaderText(null);
        alert.setContentText(invalidContent);
        alert.show();
        recordLoginAttempt(false);
        return null;
    }

    /** Records successful and failed login attempts with current timestamp and writes the information to login_activity.txt. */
    private void recordLoginAttempt(boolean success) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss");
        String now = LocalDateTime.now().format(dtf);
        String loginAttempt = "Login Attempt at - ";
        FileWriter fw = new FileWriter("login_activity.txt", true);
        PrintWriter pw = new PrintWriter(fw);
        if(success){
            pw.println(loginAttempt + now + " SUCCESS");
            pw.close();
            return;
        }
        pw.println(loginAttempt + now + " FAILURE");
        pw.close();
        return;

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
    /** Initializer method for the login screen, takes care of handling login form translation to French. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale loc = Locale.getDefault();

        ResourceBundle rb = ResourceBundle.getBundle("properties/Nat", loc);
        headLabel.setText(rb.getString("Scheduling") + " " + rb.getString("Software"));
        signinLabel.setText(rb.getString("Sign") + " " + rb.getString("in"));
        UNlabel.setText(rb.getString("Username"));
        loginScreenUsernameField.setPromptText(rb.getString("Enter") + " " + rb.getString("Username"));
        PWLabel.setText(rb.getString("Password"));
        loginScreenPasswordField.setPromptText(rb.getString("Enter") + " " + rb.getString("Password"));
        loginScreenSubmitButton.setText(rb.getString("Submit"));
        loginScreenExitButton.setText(rb.getString("Exit"));
        invalidContent = rb.getString("invalidContent");
        invalidTitle = rb.getString("invalidTitle");

    }
}
