package Entities.UserFactory;

import Entities.*;

public class UserFactory {

    public UserFactory(){}

    public User createAccount(String username, String password, UserType type){
        switch (type) {
            case ATTENDEE:
                return new Attendee(username, password);
            case ORGANIZER:
                return new Organizer(username, password);
            case SPEAKER:
                return new Speaker(username, password);
        }
        return null;
    }
}
