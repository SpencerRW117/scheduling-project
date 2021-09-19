package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.JDBC;

import java.sql.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/loginScreen.fxml"));
        primaryStage.setTitle("Scheduling Software");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void printQuery() throws SQLException{
        Connection c = JDBC.getConnection();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("select * from customers");

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while(rs.next()) {
            for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                Object object = rs.getObject(columnIndex);
                System.out.printf("%s, ", object == null ? "NULL" : object.toString());
            }
            System.out.printf("%n");
        }
    }

    public static void main(String[] args) throws Exception {
        JDBC.makeConnection();
        //printQuery();
        launch(args);

    }
}
