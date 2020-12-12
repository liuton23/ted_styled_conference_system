package Entities.UserFactory;

import Entities.*;

/**
 * a user factory class
 */

public class UserFactory {
    /**
     * empty user factory constructor
     */
    public UserFactory(){}

    /**
     * creates a account depends on types
     * @param username username
     * @param password password
     * @param type types
     * @return a user object
     */
    public User createAccount(String username, String password, UserType type){
        switch (type) {
            case ATTENDEE:
                return new Attendee(username, password);
            case ORGANIZER:
                return new Organizer(username, password);
            case SPEAKER:
                return new Speaker(username, password);
            case VIP:
                return new VIP(username, password);
        }
        return null;
    }
}
