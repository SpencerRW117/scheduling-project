package model;

import java.sql.Time;
import java.sql.Timestamp;
/** An instance of this class represents a single record from the appointments table in the database.
 * @author Spencer Watkins */
public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;
    /** The appointment constructor. */
    public Appointment(int appointmentID, String title, String description, String location,
                       String type, Timestamp start, Timestamp end, Timestamp createDate,
                       String createdBy, Timestamp lastUpdate, String lastUpdatedBy,
                       int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
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
    public void setDescription(String description) { this.description = description; }
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
    /** Returns the start time of the appointment. */
    public Timestamp getStart() {
        return start;
    }
    /** Sets the start time of the appointment. */
    public void setStart(Timestamp start) {
        this.start = start;
    }
    /** Returns the end time of the appointment. */
    public Timestamp getEnd() {
        return end;
    }
    /** Sets the end time of the appointment. */
    public void setEnd(Timestamp end) {
        this.end = end;
    }
    /** Returns the creation datetime of the appointment. */
    public Timestamp getCreateDate() {
        return createDate;
    }
    /** Sets the creation datetime of the appointment. */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
    /** Returns the creation method of the appointment. */
    public String getCreatedBy() { return createdBy; }
    /** Sets the creation method of the appointment. */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    /** Returns the datetime of the last appointment update. */
    public Timestamp getLastUpdate() { return lastUpdate; }
    /** Sets the datetime of the last appointment update. */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    /** Returns the most recent method used to update the appointment. */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    /** Sets the most recent method used to update the appointment. */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    /** Returns the customer ID for the appointment. */
    public int getCustomerID() {
        return customerID;
    }
    /** Sets the customer ID for the appointment. */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    /** Returns the user ID for the appointment. */
    public int getUserID() {
        return userID;
    }
    /** Sets the user ID for the appointment. */
    public void setUserID(int userID) {
        this.userID = userID;
    }
    /** Returns the contact ID for the appointment. */
    public int getContactID() {
        return contactID;
    }
    /** Sets the contact ID for the appointment. */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }



}
