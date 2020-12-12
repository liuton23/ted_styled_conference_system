package Entities;

import Entities.UserFactory.OrganizeAble;

import java.util.ArrayList;

/**
 * An instance of this class represent an organizer at the tech conference.
 */
public class Organizer extends User implements OrganizeAble {

    /**
     * Constructs an instance of Organizer.
     * @param username username of the account.
     * @param password password of the account.
     */
    public Organizer(String username, String password) {
        super(username, password);
    }
}
