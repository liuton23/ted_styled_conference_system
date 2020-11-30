package Entities;

import Entities.UserFactory.Account;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String username;
    private String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
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
}
