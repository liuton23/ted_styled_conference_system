package Controller;
import Entities.UserFactory.UserType;
import UseCases.UserManager;

/**
 * Manages the logging in and registering of users to the system
 */
public class LoginSystem {
    UserManager userManager;

    /**
     * Creates an instance of LoginSystem
     * @param userManager the attendeeManager with the users in the system
     */
    public LoginSystem(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Checks if the user is in the system
     * @param username inputted username
     * @param password inputted password
     * @return true if a user with the same username and password were found in the system. False if not.
     */
    public boolean canLogin(String username, String password) {
        return userManager.inSystem(username, password);
    }

    /**
     * Attempts to register a new account
     * @param username inputted new username
     * @param password inputted new password
     * @param type the type of the account
     * @return true if the account was successfully registered. False if not.
     */
    public boolean registerUser(String username, String password, UserType type) {
        if (userManager.usernameToUserObject(username).isPresent()) {
            return false;
        } else if (username.trim().isEmpty() || password.trim().isEmpty()){
            return false;
        } else {
            userManager.createAttendee(username, password, type);
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
        if (userManager.usernameToUserObject(username).isPresent()){
            return false;
        } else if (username.trim().isEmpty() || password.trim().isEmpty()){
            return false;
        } else {
            userManager.createAttendee(username, password, UserType.SPEAKER);
            return true;
        }
    }

}
