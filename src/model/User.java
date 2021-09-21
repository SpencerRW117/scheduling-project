package model;

/** An instance of this class represents a single record from the users table.
 * @author Spencer Watkins*/
public class User {
    private int userID;
    private String userName;
    private String userPassword;
    /** User constructor. */
    public User(int newID, String newName, String newPassword){
        this.userID = newID;
        this.userName = newName;
        this.userPassword = newPassword;
    }
    /** Returns the user ID. */
    public int getUserID() {
        return userID;
    }
    /** Sets the user ID. */
    public void setUserID(int userID) {
        this.userID = userID;
    }
    /** Returns the user name. */
    public String getUserName() {
        return userName;
    }
    /** Sets the user name. */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /** Returns the user password. */
    public String getUserPassword() {
        return userPassword;
    }
    /** Sets the user password. */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
