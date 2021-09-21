package model;

/** An instance of this class represents a single record from the contacts table. */
public class Contact {
    private int contactID;
    private String contactName;
    private String contactEmail;
    /** Contact constructor. */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }
    /** Returns the contact ID. */
    public int getContactID() { return contactID; }
    /** Sets the contact ID. */
    public void setContactID(int contactID) { this.contactID = contactID; }
    /** Returns the contact name. */
    public String getContactName() { return contactName; }
    /** Sets the contact name. */
    public void setContactName(String contactName) { this.contactName = contactName; }
    /** Returns the contact email. */
    public String getContactEmail() { return contactEmail; }
    /** Sets the contact email. */
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
}
