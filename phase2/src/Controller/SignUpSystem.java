package Controller;

import Controller.PromptBuilder.Prompt;
import Controller.PromptBuilder.PromptBuilder;
import Controller.PromptBuilder.PromptType;
import Entities.EventComparators.bySpeakerEventComparator;
import Entities.EventComparators.byTitleEventComparator;
import Entities.SpeakerEvent;
import Entities.UserFactory.AttendAble;
import Entities.Event;
import Entities.EventComparators.byTimeEventComparator;
import Entities.Room;
import Entities.User;
import Entities.VipOnly;
import Presenter.*;
import UseCases.UserManager;
import UseCases.EventManager;
import UseCases.RoomManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

/**
 * Controls sign-ups and drop-outs for events based on user input.
 */
public class SignUpSystem extends Controller{
    private UserManager userManager;
    private EventManager eventManager;
    private RoomManager roomManager;
    private SignUpPresenter signUpPresenter;
    private Comparator<Event> comparator = new byTimeEventComparator();

    /**
     * Constructor for an instance of SignUpSystem.
     * @param userManager stored attendeeManager
     * @param eventManager stored eventManager
     * @param roomManager stored roomManager
     */
    public SignUpSystem(UserManager userManager, EventManager eventManager, RoomManager roomManager){
        this.userManager = userManager;
        this.eventManager = eventManager;
        this.roomManager = roomManager;
        this.signUpPresenter = new SignUpPresenter();
    }

    /**
     * Method that sets a stored Comparator for sorting events.
     * @param comparator the Comparator used to sort events.
     */
    public void setComparator(Comparator<Event> comparator){
        this.comparator = comparator;
    }

    /**
     * Method that returns a list of all stored events.
     * @return list of all stored events.
     */
    public ArrayList<String> getEventList(){
        HashMap<String, Event> eventDict = eventManager.getAllEvents();
        ArrayList<Event> eventDictClone = getEventListClone(eventDict);
        ArrayList<String> stringEventList = new ArrayList<>();

        eventDictClone.sort(comparator);
        int index = 1;
        for (Event event: eventDictClone){
            String x = "";
            if(eventManager.getSpeakerEvents().containsKey(eventManager.getTitle(event))) {
                SpeakerEvent speakerEvent = (SpeakerEvent) event;
                x += index + ") " + eventManager.getTitle(event) + " @ " + event.getEventTime() + " with " + eventManager.getSpeakers(speakerEvent).get(0) + " and more, ";
            }
            else{
                x += index + ") " + eventManager.getTitle(event) + " @ " + event.getEventTime() +", ";
            }
            stringEventList.add(x);
            index += 1;
        }
        return stringEventList;
    }

    /**
     * Creates a copy of the list of events.
     * @param eventDict original hashmap of event names to events.
     * @return copy list of events.
     */
    private ArrayList<Event> getEventListClone(HashMap<String, Event> eventDict){
        return new ArrayList<>(eventDict.values());
    }

    /**
     * Method that allows attendees to sign up for events. Changes information stored in the attendeeManager and
     * eventManager.
     * @param username username of the attendee.
     * @param eventIndex index of the selected event from a sorted list of events.
     * @return boolean value that will be sent to presenter to display corresponding message.
     */
    public int signUpEvent(String username, int eventIndex) {
        if (eventIndex < 1 || eventIndex > eventManager.getAllEvents().size()) {
            return 4;
        }
        ArrayList<Event> eventList = new ArrayList<>(eventManager.getAllEvents().values());
        eventList.sort(comparator);
        if (eventIndex > eventList.size()){
            return 1;
        }
        Event event = eventList.get(eventIndex-1);
        Room room = roomManager.idToRoom(event.getRoom());
        Optional<User> obj = userManager.usernameToUserObject(username);
        //check if username is valid
        if (obj.isPresent()){
            User attendee = obj.get();
            //checking that person is not already signed up
            if(event.getAttendeeList().contains(username)){
                return 5;
            }
            //checking that there is space at the event
            if(event.getAttendeeList().size() < room.getCapacity()) {
                if (checkCanAttend(event, attendee)) {
                    eventManager.signUp(event, attendee.getUsername());
                    userManager.signUp((AttendAble) attendee, eventManager.getTitle(event));
                    return 2;
                } else {
                    return 6;
                }
            }
        }
        return 3;

    }

    /**
     * Checks if <code>attendee</code> can attend <code>event</code>.
     * @param event the event to be checked.
     * @param attendee the attendee to be checked.
     * @return true if the user can attend. Otherwise returns false.
     */
    private boolean checkCanAttend(Event event, User attendee){
        if (userManager.hasVIPAccess(attendee) && event instanceof VipOnly){
            return true;
        } else if (!(userManager.hasVIPAccess(attendee)) && event instanceof VipOnly) {
            return false;
        } else return eventManager.eventToAttendees(event).size() < eventManager.getEventCapacity(event);
    }

    /**
     * Method that allows attendees to sign up for events. Changes information stored in the attendeeManager and
     * eventManager.
     * @param username username of the attendee.
     * @param eventIndex index of the selected event from a sorted list of events.
     * @return status message saying whether or not the attendee successfully signed up for an event.
     */
    public MessageType dropOutEvent(String username, int eventIndex){
        if (eventIndex < 1 || eventIndex > eventManager.getAllEvents().size()){
            return MessageType.incorrectID;
        }
        ArrayList<Event> eventList = new ArrayList<>(eventManager.getAllEvents().values());
        eventList.sort(comparator);
        Event event = eventList.get(eventIndex-1);
        Optional<User> obj = userManager.usernameToUserObject(username);
        //check if username is valid
        if (!obj.isPresent()){
            return MessageType.incorrectUsername;
        }
        User attendee = obj.get();
        eventManager.dropOut(event, attendee.getUsername());
        userManager.dropOut((AttendAble) attendee, eventManager.getTitle(event));
        return MessageType.successfulDropOut;
    }

    /**
     * Menu to view, sign up, and drop events.
     * @param username username of <code>Attendee</code>.
     */
    public void eventActivity(String username) throws IOException {
        boolean activity = true;
        while (activity) {
            PromptBuilder promptBuilder = new PromptBuilder();
            Prompt eventPrompt;
            if (userManager.usernameToOrganizer(username).isPresent()){
                eventPrompt = promptBuilder.buildPrompt(presenter, PromptType.eventsOrgMenu);
            } else {
                eventPrompt = promptBuilder.buildPrompt(presenter, PromptType.eventsMenu);
            }
            String chosen = eventPrompt.ask();
            int index;

            switch (chosen) {
                case "V":
                    signUpPresenter.printSignUpMessage(MessageType.viewAllEvents);
                    viewAllEvents();
                    break;
                case "S":
                    signUpPresenter.printSignUpMessage(MessageType.signUp);
                    signUpPresenter.printSignUpMessage(MessageType.enterEventId);
                    Prompt indexPrompt = promptBuilder.buildPrompt(presenter, PromptType.intPrompt);
                    index = indexPrompt.intAsk();
                    signUpPresenter.printSignUpMessage(signUpEvent(username, index));
                    break;
                case "D":
                    signUpPresenter.printSignUpMessage(MessageType.dropOut);
                    signUpPresenter.printSignUpMessage(MessageType.enterEventId);
                    Prompt indexPrompt1 = promptBuilder.buildPrompt(presenter, PromptType.intPrompt);
                    index = indexPrompt1.intAsk();
                    signUpPresenter.printSignUpMessage(dropOutEvent(username, index));
                    save();
                    break;
                case "B":
                    activity = false;
                    break;
            }
        }
    }

    /**
     * View all events in a sorted list.
     */
    public void viewAllEvents() throws IOException {
        PromptBuilder promptBuilder = new PromptBuilder();
        Prompt eventPrompt = promptBuilder.buildPrompt(presenter, PromptType.viewEventsMenu);
        String chosen = eventPrompt.ask();

        switch (chosen) {
            case "T":
                setComparator(new byTimeEventComparator());
                signUpPresenter.displaySortedEvents(getEventList(), "time");
                break;
            case "N":
                setComparator(new byTitleEventComparator());
                signUpPresenter.displaySortedEvents(getEventList(), "name");
                break;
            case "S":
                setComparator(new bySpeakerEventComparator());
                signUpPresenter.displaySortedEvents(getEventList(), "speaker");
                break;
        }
    }


}
