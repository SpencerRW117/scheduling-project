package model;

import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ReportInterface {
    ObservableList<Report> appointmentResultSetGen() throws SQLException;
}

