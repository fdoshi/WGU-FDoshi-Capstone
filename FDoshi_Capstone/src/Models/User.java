/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author FDoshi
 */
public class User {
    
    private String userID;
    private String userName;
    private String userPW;

    public User(String id, String user, String pw) {
        this.userID = id;
        this.userName = user;
        this.userPW = pw;
    }

    public User() {
        
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String id) {
        this.userID = id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String user) {
        this.userName = user;
    }

    public String getPassword() {
        return userPW;
    }

    public void setPassword(String pw) {
        this.userPW = pw;
    }   
    
}
