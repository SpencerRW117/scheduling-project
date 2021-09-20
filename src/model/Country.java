package model;

import java.sql.Timestamp;

/** An instance of this class represents a single record from the countries table in the database.
 * @author Spencer Watkins*/
public class Country {
    private int ID;
    private String name;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    /** The constructor for the country class. */
    public Country(int ID, String name, Timestamp createDate, String createdBy,
                   Timestamp lastUpdate, String lastUpdatedBy) {
        this.ID = ID;
        this.name = name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
    /** Returns the country ID. */
    public int getID() {
        return ID;
    }
    /** Sets the country ID. */
    public void setID(int ID) {
        this.ID = ID;
    }
    /** Returns the country name. */
    public String getName() {
        return name;
    }
    /** Sets the country name. */
    public void setName(String name) {
        this.name = name;
    }
    /** Returns the country creation timestamp. */
    public Timestamp getCreateDate() {
        return createDate;
    }
    /** Sets the country creation timestamp. */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
    /** Returns the country creation method. */
    public String getCreatedBy() {
        return createdBy;
    }
    /** Sets the country creation method. */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    /** Returns the last update timestamp for the country. */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }
    /** Sets the last update timestamp for the country. */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    /** Returns the last update method for the country. */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    /** Sets the last update method for the country. */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
