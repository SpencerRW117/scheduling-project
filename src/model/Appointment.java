package model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/** An instance of this class represents a single record from the appointments table in the database.
 * @author Spencer Watkins */
public class Appointment {

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private String startDT;
    private String endDT;
    private String createDT;
    private String createMethod;
    private String lastUpdateDT;
    private String lastUpdateMethod;
    private int customerID;
    private int userID;
    private int contactID;

    /** Constructor for the appointment class. */
    public Appointment(int appointmentID, String title, String description, String location, String type, String startDT,
                       String endDT, String createDT, String createMethod, String lastUpdateDT, String lastUpdateMethod,
                       int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDT = startDT;
        this.endDT = endDT;
        this.createDT = createDT;
        this.createMethod = createMethod;
        this.lastUpdateDT = lastUpdateDT;
        this.lastUpdateMethod = lastUpdateMethod;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;


    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDT() {
        return startDT;
    }

    public void setStartDT(String startDT) {
        this.startDT = startDT;
    }

    public String getEndDT() {
        return endDT;
    }

    public void setEndDT(String endDT) {
        this.endDT = endDT;
    }

    public String getCreateDT() {
        return createDT;
    }

    public void setCreateDT(String createDT) {
        this.createDT = createDT;
    }

    public String getCreateMethod() {
        return createMethod;
    }

    public void setCreateMethod(String createMethod) {
        this.createMethod = createMethod;
    }

    public String getLastUpdateDT() {
        return lastUpdateDT;
    }

    public void setLastUpdateDT(String lastUpdateDT) {
        this.lastUpdateDT = lastUpdateDT;
    }

    public String getLastUpdateMethod() {
        return lastUpdateMethod;
    }

    public void setLastUpdateMethod(String lastUpdateMethod) {
        this.lastUpdateMethod = lastUpdateMethod;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }


}
