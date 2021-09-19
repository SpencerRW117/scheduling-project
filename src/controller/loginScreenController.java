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
import model.JDBC;

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


    /** Handles the submit credentials event from the login screen. */
    public void loginScreenSubmitHandler (ActionEvent actionEvent) throws Exception {
        boolean validLogin = validateCredentials();
        if(validLogin){
            goToMainScreen(actionEvent);
        }

        else{ return; }
    }

    /** Validates the credentials against the database. */
    private boolean validateCredentials() throws SQLException {
        Connection c = JDBC.getConnection();
        Statement st = c.createStatement();
        String userNameAndPassQuery = "SELECT User_Name, Password FROM users";
        ResultSet rs = st.executeQuery(userNameAndPassQuery);
        String enteredUserName = loginScreenUsernameField.getText();
        String enteredUserPassword = loginScreenPasswordField.getText();
        //System.out.println("STUFF: " + enteredUserPassword + enteredUserName); //Debugging print
        while(rs.next()) {
            String currUserName = rs.getString(1);
            String currPassword = rs.getString(2);
            //System.out.println("STUFF 2: " + currPassword + currUserName); //Debugging print
            if (currUserName.equals(enteredUserName) && currPassword.equals(enteredUserPassword)) {
                return true;
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid Credentials");
        alert.setHeaderText(null);
        alert.setContentText("Invalid login credentials, please enter a valid username and password.");
        alert.show();
        return false;
    }

    /** Changes the scene to the main dashboard. */
    private void goToMainScreen(ActionEvent actionEvent) throws IOException{
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
