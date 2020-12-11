package Entities;

import Entities.UserFactory.Account;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Abstract class representing user accounts at the tech conference
 */
public abstract class User implements Serializable,Account {
    private String username;
    private String password;
    private String email;

    /**
     * Constructs an instance of a user account
     * @param username account username.
     * @param password account password.
     */
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.email = "";
    }

    /**
     * Method that returns the username of the attendee.
     * @return the username of the attendee account.
     */
    public String getUsername(){
        return username;
    }

    /**
     * Method that returns the password of the attendee.
     * @return the password of the attendee account.
     */
    public String getPassword(){
        return password;
    }

    /**
     * Method that sets the email.
     * @param email the email of the user.
     */
    public void setEmail(String email){ this.email = email; }

    /**
     * Method that gets the email of <code>User</code>.
     * @return email of <code>User</code>.
     */
    public String getEmail(){ return email;  }

    /**
     * Method that sets the password.
     * @param password password of the user.
     */
    public void setPassword(String password){ this.password = password; }
}
