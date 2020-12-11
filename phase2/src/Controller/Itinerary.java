package Controller;

import Entities.User;
import Entities.UserFactory.AttendAble;
import Entities.UserFactory.TalkAble;
import Presenter.Presenter;
import UseCases.UserManager;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Outlines the planned events of user at the conference.
 */
public class Itinerary {
    private UserManager userManager;
    private Presenter presenter;

    /**
     * Constructs an instance of Itinerary.
     * @param userManager the UserManager used by the conference.
     * @param presenter the main Presenter used by the conference.
     */
    public Itinerary(UserManager userManager, Presenter presenter){
        this.userManager = userManager;
        this.presenter = presenter;
    }

    /**
     * Displays a schedule of all the events <code>Attendee</code> with username <code>user</code> is attending.
     * @param user username of <code>Attendee</code> to which the schedule belongs.
     */
    public void getItinerary(String user){

        if (userManager.usernameToOrganizer(user).isPresent()){
            presenter.display("Sorry! Organizers cannot attend events.");
        } else {
            Optional<AttendAble> obj = userManager.usernameToAttendeeObject(user);

            if (obj.isPresent()){
                AttendAble userObj = obj.get();
                presenter.displaySchedule(userManager.getItinerary(userObj), "itinerary");
            }

            Optional<TalkAble> obj1 = userManager.usernameToSpeakerObject(user);
            if (obj1.isPresent()) {
                TalkAble userObj = obj1.get();
                presenter.displaySchedule(userManager.getSpeakingList((TalkAble) userObj), "speakItinerary");
            }
        }
    }
}
