package model;

import DBPackage.DBQueries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/** This class consists of helper, conversion, and sanity methods to assist with appointment functionality.
 * @author Spencer Watkins */
public class AppointmentHelpers {
    /** Gets the name from a list of contact records to be displayed in the combo box. */
    public static ObservableList<String> getContactNames() throws Exception{
        ObservableList<String> names = FXCollections.observableArrayList();
        for(Contact c : DBQueries.getContacts()){
            names.add(c.getContactName());
        }
        return names;
    }
    /** Generates a new ID that is always 1 larger than the largest customer ID. */
    public static int newIDGen() throws Exception {
        ObservableList<Appointment> appts = DBQueries.getAllAppointments();
        int temp = 0;
        for (int i = 0; i < appts.size(); i++){
            if(appts.get(i).getAppointmentID() > temp){
                temp = appts.get(i).getAppointmentID();
            }
        }
        return temp + 1;
    }

    /** Takes a string in the local timezone and returns it converted to UTC. */
    public static String convertToUTC(String s){
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime ldt = LocalDateTime.parse(s, dtf);
            ZoneId utc = ZoneId.of("UTC");
            ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
            ZonedDateTime zdt2 = zdt.withZoneSameInstant(utc);
            String ret = dtf.format(zdt2);
            return ret;
        } catch (Exception e){
            showError();
            return "";
        }

    }
    /** Takes a string in the local timezone and returns it converted to EST. */
    private static String convertToEST(String s){
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime ldt = LocalDateTime.parse(s, dtf);
            ZoneId est = ZoneId.of("America/New_York");
            ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
            ZonedDateTime zdt2 = zdt.withZoneSameInstant(est);
            String ret = dtf.format(zdt2);
            return ret;
        } catch (Exception e){
            showError();
            return "";
        }
    }
    /** Displays a general error if something is missing or incorrect in the form. */
    public static void showError(){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Invalid Field(s)");
        a.setHeaderText(null);
        a.setContentText("One or more fields is null or in an invalid date format.\n" +
                "Please provide valid types for every field.\n" +
                "Please provide datetimes in your local time zone in format: \n" +
                "yyyy-mm-dd HH:mm:ss\n");
        a.show();
    }
    /** Performs several sanity checks on the inputted start and end times for the appointment.
     * Ensures appointments start and end on the same day.
     * Ensures the end time comes after the start time and vice versa.
     * Ensures that start and end times are within the EST business hours. */
    public static boolean validateBusinessHours(String rawStart, String rawEnd){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Invalid Hours");
        a.setHeaderText(null);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String convertedStart = convertToEST(rawStart);
        String convertedEnd = convertToEST(rawEnd);

        LocalDateTime start_ldt = LocalDateTime.parse(convertedStart, dtf);
        LocalDateTime end_ldt = LocalDateTime.parse(convertedEnd, dtf);

        LocalTime start_lt = start_ldt.toLocalTime();
        LocalTime end_lt = end_ldt.toLocalTime();

        LocalTime businessStart = LocalTime.parse("08:00:00");
        LocalTime businessEnd = LocalTime.parse("22:00:00");

        if(!start_ldt.toLocalDate().equals(end_ldt.toLocalDate())){
            a.setContentText("Appointments must start and end on the same day.");
            a.show();
            return false;
        }

        if(start_lt.isAfter(end_lt) || end_lt.isBefore(start_lt)){
            a.setContentText("The appointment start time must be before the end time.\n" +
                    "The appointment end time must be after the start time.");
            a.show();
            return false;
        }

        if(start_lt.isBefore(businessStart) || start_lt.isAfter(businessEnd) || end_lt.isBefore(businessStart) ||
                end_lt.isAfter(businessEnd)){
            a.setContentText("Appointment hours must be between 08:00:00 and 22:00:00 EST (12:00:00 - 02:00:00 UTC).\n" +
                    "Please enter valid hours for the appointment. ");
            a.show();
            return false;
        }

        return true;
    }
}
