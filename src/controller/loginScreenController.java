package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class controls loginScreen.fxml @author Spencer Watkins*/
public class loginScreenController implements Initializable {
    public TextField loginScreenUsernameField;
    public PasswordField loginScreenPasswordField;
    public Button loginScreenSubmitButton;
    public Button loginScreenExitButton;


    /** Handles the submit credentials event from the login screen. */
    public void loginScreenSubmitHandler (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 906, 537);
        stage.setTitle("Scheduling Software");
        stage.setScene(scene);
        stage.show();
    }



    /** Exits the program from the login screen. */
    public void exitProgram(ActionEvent actionEvent) {
        System.exit(1);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
