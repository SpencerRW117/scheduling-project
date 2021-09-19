package model;


import java.sql.Time;
import java.sql.Timestamp;

/** An instance of this class represents a single record from the customers table in the database.
 * @author Spencer Watkins*/
public class Customer {
    private int ID;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;

    /** Constructor for the customer class. */
    public Customer(int ID, String name, String address, String postalCode,
                    String phoneNumber, Timestamp createDate, String createdBy,
                    Timestamp lastUpdate, String lastUpdatedBy, int divisionID) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }
    /** Returns the customer ID. */
    public int getID() {
        return ID;
    }
    /** Sets the customer ID. */
    public void setID(int ID) {
        this.ID = ID;
    }
    /** Returns the customer name. */
    public String getName() {
        return name;
    }
    /** Sets the customer name. */
    public void setName(String name) {
        this.name = name;
    }
    /** Returns the customer address. */
    public String getAddress() {
        return address;
    }
    /** Sets the customer address. */
    public void setAddress(String address) {
        this.address = address;
    }
    /** Returns the customer postal code. */
    public String getPostalCode() {
        return postalCode;
    }
    /** Sets the customer postal code. */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /** Returns the customer phone number. */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /** Sets the customer phone number. */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /** Returns the creation datetime of the customer record. */
    public Timestamp getCreateDate() {
        return createDate;
    }
    /** Sets the creation datetime of the customer record. */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
    /** Returns the creation method of the customer record. */
    public String getCreatedBy() {
        return createdBy;
    }
    /** Sets the creation method of the customer record. */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    /** Returns the last update datetime of the customer record. */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }
    /** Sets the last update datetime for the customer record. */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    /** Returns the last update method for the customer. */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    /** Sets the last update method for the customer. */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    /** Returns the division ID for the customer. */
    public int getDivisionID() {
        return divisionID;
    }
    /** Sets the division ID for the customer. */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}
