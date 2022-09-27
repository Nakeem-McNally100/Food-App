package com.example.foodapplicationcustomer;

public class User {

    private String UserName;
    private String FirstName;
    private String LastName;
    private String Password;
    private String Email;
    private String CellNumber;

    public User(){
    }
    public User(String userName, String firstName, String lastName, String password, String email, String cellNumber) {
        UserName = userName;
        FirstName = firstName;
        LastName = lastName;
        Password = password;
        Email = email;
        CellNumber = cellNumber;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCellNumber() {
        return CellNumber;
    }

    public void setCellNumber(String cellNumber) {
        CellNumber = cellNumber;
    }
}
