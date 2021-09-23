package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/** This class is used flexibly in generating different appointment and customer reports.
 * See the lambda functions in customerReportsController.java and appointmentReportController.java for more details.
 * @author Spencer Watkins*/
public class Report {
    String stringParam1 = "";

    int count = 0;
    static ObservableList<String> months = FXCollections.observableArrayList("JANUARY", "FEBRUARY", "MARCH", "APRIL",
            "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");
    /** Report constructor. */
    public Report(String stringParam1, int count){
        this.stringParam1 = stringParam1;
        this.count = count;
    }
    /** Returns the string parameter. */
    public String getStringParam1() {
        return stringParam1;
    }
    /** Sets the String parameter. */
    public void setStringParam1(String stringParam1) {
        this.stringParam1 = stringParam1;
    }

    /** Returns the count. */
    public int getCount() {
        return count;
    }
    /** Sets the count. */
    public void setCount(int count) {
        this.count = count;
    }
    /** Returns the months list. */
    public static ObservableList<String> getMonths(){
        return  months;
    }
}
