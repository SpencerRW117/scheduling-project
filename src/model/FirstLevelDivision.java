package model;

import java.sql.Time;
import java.sql.Timestamp;

/** An instance of this class represents a single record from the first_level_divisions table in the database.
 * @author Spencer Watkins*/
public class FirstLevelDivision {
    private int ID;
    private String division;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryID;
    /** First Level Division constructor. */
    public FirstLevelDivision(int ID, String division, Timestamp createDate, String createdBy,
                              Timestamp lastUpdate, String lastUpdatedBy, int countryID) {
        this.ID = ID;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }
    /** Returns the division ID. */
    public int getID() {
        return ID;
    }
    /** Sets the division ID. */
    public void setID(int ID) {
        this.ID = ID;
    }
    /** Returns the division name. */
    public String getDivision() {
        return division;
    }
    /** Sets the division name. */
    public void setDivision(String division) {
        this.division = division;
    }
    /** Returns the creation date of the division. */
    public Timestamp getCreateDate() {
        return createDate;
    }
    /** Sets the creation date of the division. */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
    /** Returns the creation method of the division. */
    public String getCreatedBy() {
        return createdBy;
    }
    /** Sets the creation method of the division. */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    /** Returns the last update time of the division. */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }
    /**Sets the last update time. */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    /** Returns the last update method. */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    /** Sets the last update method. */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    /** Returns the country id FK of the division. */
    public int getCountryID() {
        return countryID;
    }
    /** Sets the country ID FK for the division. */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
