package Controller.Activities;

import Controller.Controller;
import Controller.SignUpSystem;
import Controller.PromptController;
import Presenter.Presenter;
import Entities.EventComparators.bySpeakerEventComparator;
import Entities.EventComparators.byTimeEventComparator;
import Entities.EventComparators.byTitleEventComparator;
import UseCases.EventManager;
import UseCases.RoomManager;
import UseCases.UserManager;

public class EventActivity extends PromptController {
    private UserManager userManager;
    private EventManager eventManager;
    private RoomManager roomManager;
    private Controller controller;

    public EventActivity(Presenter presenter){
        super(presenter);
    }

    public void setUserManager(UserManager userManager){
        this.userManager = userManager;
    }

    public void setEventManager(EventManager eventManager){
        this.eventManager = eventManager;
    }

    public void setRoomManager(RoomManager roomManager){
        this.roomManager = roomManager;
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    /**
     * Menu to view, sign up, and drop events.
     * @param username username of <code>Attendee</code>.
     */
    public void runEventActivity(String username) {
        SignUpSystem signUpSystem = new SignUpSystem(userManager, eventManager, roomManager);
        boolean activity = true;
        while (activity) {
            String chosen = askMenuInput(8);
            int index;

            switch (chosen) {
                case "V":
                    presenter.displayMessages("viewEvents");
                    viewAllEvents(signUpSystem);
                    break;
                case "S":
                    presenter.displayMessages("signUp");
                    presenter.displayMessages("requestEventId");
                    index = getIntInput();
                    presenter.printSignUpMessage(signUpSystem.signUpEvent(username, index));
                    break;
                case "D":
                    presenter.displayMessages("dropOut");
                    presenter.displayMessages("requestEventIdDropOut");
                    index = getIntInput();
                    presenter.display(signUpSystem.dropOutEvent(username, index));
                    controller.save();
                    break;
                case "B":
                    activity = false;
                    break;
            }
        }
    }

    /**
     * View all events in a sorted list.
     * @param sus system that manages event sign up.
     */
    private void viewAllEvents(SignUpSystem sus){
        String chosen = askMenuInput(9);

        switch (chosen) {
            case "T":
                sus.setComparator(new byTimeEventComparator());
                presenter.displaySchedule(sus.viewAllEvents(), "time");
                break;
            case "N":
                sus.setComparator(new byTitleEventComparator());
                presenter.displaySchedule(sus.viewAllEvents(), "name");
                break;
            case "S":
                sus.setComparator(new bySpeakerEventComparator());
                presenter.displaySchedule(sus.viewAllEvents(), "speaker");
                break;
        }
    }
}
