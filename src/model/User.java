package model;

public class User {
    private int userID;
    private String userName;
    private String userPassword;

    public User(int newID, String newName, String newPassword){
        this.userID = newID;
        this.userName = newName;
        this.userPassword = newPassword;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
