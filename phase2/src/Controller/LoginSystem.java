package Controller;
import Controller.PromptBuilder.*;
import Entities.UserFactory.UserType;
import UseCases.UserManager;

import java.io.IOException;
import java.util.Scanner;

/**
 * Manages the logging in and registering of users to the system
 */
public class LoginSystem extends Controller {
    UserManager userManager;

    /**
     * Creates an instance of LoginSystem
     *
     * @param userManager the attendeeManager with the users in the system
     */
    public LoginSystem(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Checks if the user is in the system
     *
     * @param username inputted username
     * @param password inputted password
     * @return true if a user with the same username and password were found in the system. False if not.
     */
    public boolean canLogin(String username, String password) {
        return userManager.inSystem(username, password);
    }

    /**
     * Attempts to register a new account
     *
     * @param username inputted new username
     * @param password inputted new password
     * @param type     the type of the account
     * @return true if the account was successfully registered. False if not.
     */
    public boolean registerUser(String username, String password, UserType type) {
        if (userManager.usernameToUserObject(username).isPresent()) {
            return false;
        } else if (username.trim().isEmpty() || password.trim().isEmpty()) {
            return false;
        } else {
            userManager.createAttendee(username, password, type);
            return true;
        }
    }

    /**
     * Register a new speaker if there is no existing account with the same username.
     *
     * @param username speaker account username.
     * @param password speaker account password
     * @return true iff the account was successfully registered.
     */

    public boolean registerSpeaker(String username, String password) {
        if (userManager.usernameToUserObject(username).isPresent()) {
            return false;
        } else if (username.trim().isEmpty() || password.trim().isEmpty()) {
            return false;
        } else {
            userManager.createAttendee(username, password, UserType.SPEAKER);
            return true;
        }
    }

    /**
     * Attendee login.
     *
     * @return username of <code>Attendee</code> if it exists. Otherwise returns an empty string.
     */
    public String login() {

        Scanner obj1 = new Scanner(System.in);
        presenter.printUsernameMessage(1);
        String username = obj1.nextLine();
        presenter.printPasswordMessage(1);
        String password = obj1.nextLine();
        if (canLogin(username, password)) {
            presenter.printLoginSucceedMessage();
            return username;
        }
        presenter.printLoginFailMessage();
        return "";
    }

    /**
     * Attendee registration. Cannot choose a username that is already taken.
     */
    public void registerUsers(UserManager userManager) {
        Scanner obj1 = new Scanner(System.in);
        presenter.printUsernameMessage(1);
        String username = obj1.nextLine();
        presenter.printPasswordMessage(1);
        String password = obj1.nextLine();
        presenter.displayMessages("areUOrg");
        presenter.displayMessages("YorN");
        PromptBuilder promptBuilder = new PromptBuilder();
        Prompt booleanPrompt = promptBuilder.buildPrompt(presenter, PromptType.booleanPrompt);
        try {
            boolean chosen = booleanPrompt.booleanAsk();
        UserType type;
        if (chosen) {
            type = UserType.ORGANIZER;
        } else {
            type = UserType.ATTENDEE;
        }
        if (registerUser(username, password, type)) {
            presenter.printRegisterSucceedMessage();
        } else {
            presenter.printRegisterFailMessage();
        }
        } catch (IOException e){
            exit();
        }
    }

    /**
     * Speaker registration. Cannot choose a username that is already taken.
     */
    public void createAccounts() throws IOException {
        Scanner obj1 = new Scanner(System.in);
        presenter.printUsernameMessage(2);//"Enter your username:"
        String username = obj1.nextLine();
        presenter.printPasswordMessage(2);//"Enter your password"
        String password = obj1.nextLine();
        //presenter.printSelectUserType();
        //String chosen = askMenuInput(16);
        PromptBuilder promptBuilder = new PromptBuilder();
        Prompt userTypePrompt = promptBuilder.buildPrompt(presenter, PromptType.userTypeMenu);
        String chosen = userTypePrompt.ask();
        UserType type = null;
        switch (chosen) {
            case "A":
                type = UserType.ATTENDEE;
                break;
            case "O":
                type = UserType.ORGANIZER;
                break;
            case "S":
                type = UserType.SPEAKER;
                break;
            case "V":
                type = UserType.VIP;
                break;
        }
        if (registerUser(username, password, type)) {
            presenter.printAccountCreatedMessage();
        } else {
            presenter.printRegisterFailMessage();
        }
    }
}

