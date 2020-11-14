package Controller;
import UseCases.AttendeeManager;

public class LoginSystem {
    AttendeeManager attendeeManager;

    public LoginSystem(AttendeeManager attendeeManager) {
        this.attendeeManager = attendeeManager;
    }

    public boolean canLogin(String username, String password)  {
        return attendeeManager.inSystem(username, password);
    }

    public String registerUser(String username, String password, boolean isOrg)  {
        if(attendeeManager.usernameToAttendeeObject(username).isPresent()) {
            return "Username already taken.";
        } else {
            attendeeManager.createAttendee(username, password, isOrg);
            return "User registered.";
        }
    }
}
