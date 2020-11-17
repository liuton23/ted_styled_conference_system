package Controller;
import UseCases.AttendeeManager;

/**
 * Manages the logging in and registering of users to the system
 */
public class LoginSystem {
    AttendeeManager attendeeManager;

    /**
     * Creates an instance of LoginSystem
     * @param attendeeManager the attendeeManager with the users in the system
     */
    public LoginSystem(AttendeeManager attendeeManager) {
        this.attendeeManager = attendeeManager;
    }

    /**
     * Checks if the user is in the system
     * @param username inputted username
     * @param password inputted password
     * @return true if a user with the same username and password were found in the system. False if not.
     */
    public boolean canLogin(String username, String password) {
        return attendeeManager.inSystem(username, password);
    }

    /**
     * Attempts to register a new account
     * @param username inputted new username
     * @param password inputted new password
     * @param isOrg whether or not new account is an organizer account
     * @return true if the account was successfully registered. False if not.
     */
    public boolean registerUser(String username, String password, boolean isOrg) {
        if (attendeeManager.usernameToAttendeeObject(username).isPresent()) {
            return false;
        } else if (username.trim().isEmpty() || password.trim().isEmpty()){
            return false;
        } else {
            attendeeManager.createAttendee(username, password, isOrg);
            return true;
        }
    }

    /**
     * Register a new speaker if there is no existing account with the same username.
     * @param username speaker account username.
     * @param password speaker account password
     * @return true iff the account was successfully registered.
     */
    public boolean registerSpeaker(String username, String password){
        if (attendeeManager.usernameToAttendeeObject(username).isPresent()){
            return false;
        } else if (username.trim().isEmpty() || password.trim().isEmpty()){
            return false;
        } else {
            attendeeManager.createSpeaker(username, password);
            return true;
        }
    }
}
