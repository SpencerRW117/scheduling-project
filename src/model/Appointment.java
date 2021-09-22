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
    /** Returns the appointment ID. */
    public int getAppointmentID() {
        return appointmentID;
    }
    /** Sets the appointment ID. */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }
    /** Returns the appointment title. */
    public String getTitle() {
        return title;
    }
    /** Sets the appointment title. */
    public void setTitle(String title) {
        this.title = title;
    }
    /** Returns the appointment description. */
    public String getDescription() {
        return description;
    }
    /** Sets the appointment description. */
    public void setDescription(String description) {
        this.description = description;
    }
    /** Returns the appointment location. */
    public String getLocation() {
        return location;
    }
    /** Sets the appointment location. */
    public void setLocation(String location) {
        this.location = location;
    }
    /** Returns the appointment type. */
    public String getType() {
        return type;
    }
    /** Sets the appointment type. */
    public void setType(String type) {
        this.type = type;
    }
    /** Returns the appointment start datetime. */
    public String getStartDT() {
        return startDT;
    }
    /** Sets the appointment start datetime. */
    public void setStartDT(String startDT) {
        this.startDT = startDT;
    }
    /** Returns the appointment end datetime. */
    public String getEndDT() {
        return endDT;
    }
    /** Sets the appointment end datetime. */
    public void setEndDT(String endDT) {
        this.endDT = endDT;
    }
    /** Returns the appointment create datetime. */
    public String getCreateDT() {
        return createDT;
    }
    /** Sets the appointment create datetime. */
    public void setCreateDT(String createDT) {
        this.createDT = createDT;
    }
    /** Returns the appointment create method. */
    public String getCreateMethod() {
        return createMethod;
    }
    /** Sets the appointment create method. */
    public void setCreateMethod(String createMethod) {
        this.createMethod = createMethod;
    }
    /** Returns the appointment last update datetime. */
    public String getLastUpdateDT() {
        return lastUpdateDT;
    }
    /** Sets the appointment last update datetime. */
    public void setLastUpdateDT(String lastUpdateDT) {
        this.lastUpdateDT = lastUpdateDT;
    }
    /** Returns the appointment last update method. */
    public String getLastUpdateMethod() {
        return lastUpdateMethod;
    }
    /** Sets the appointment last update method. */
    public void setLastUpdateMethod(String lastUpdateMethod) {
        this.lastUpdateMethod = lastUpdateMethod;
    }
    /** Returns the appointment customer ID. */
    public int getCustomerID() {
        return customerID;
    }
    /** Sets the appointment customer ID. */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    /** Returns the user ID. */
    public int getUserID() {
        return userID;
    }
    /** Sets the user ID. */
    public void setUserID(int userID) {
        this.userID = userID;
    }
    /** Returns the contact ID. */
    public int getContactID() {
        return contactID;
    }
    /** Sets the contact ID. */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }


}
