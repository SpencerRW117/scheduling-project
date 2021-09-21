package controller;

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
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            goToMainScreen(actionEvent);
        }

        else{ return; }
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
