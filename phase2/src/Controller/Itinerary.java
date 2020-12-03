package Controller;

import Entities.UserFactory.AttendAble;
import Entities.UserFactory.TalkAble;
import Presenter.Presenter;
import UseCases.UserManager;

import java.util.Optional;

public class Itinerary {
    private UserManager userManager;
    private Presenter presenter;

    public Itinerary(UserManager userManager, Presenter presenter){
        this.userManager = userManager;
        this.presenter = presenter;
    }

    /**
     * Displays a schedule of all the events <code>Attendee</code> with username <code>user</code> is attending.
     * @param user username of <code>Attendee</code> to which the schedule belongs.
     */
    public void getItinerary(String user){
        Optional<AttendAble> obj = userManager.usernameToAttendeeObject(user);
        AttendAble userObj = obj.get();

        //Speaker sp = (Speaker) attendee;
        //presenter.displaySchedule(attendeeManager.getSpeakingList(sp), "speakItinerary");
        //presenter.displaySchedule(attendeeManager.getItinerary(sp), "itinerary");
        presenter.displaySchedule(userManager.getItinerary(userObj), "itinerary");
        //else presenter.displaySchedule(attendeeManager.getItinerary(attendee), "itinerary");
        if (userManager.checkIsSpeaker(userObj)){
            presenter.displaySchedule(userManager.getSpeakingList((TalkAble) userObj), "speakItinerary");
        }
    }
}
