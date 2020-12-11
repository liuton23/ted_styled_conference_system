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
import Entities.UserFactory.UserType;
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
    public ArrayList<String> viewAllEvents(){
        HashMap<String, Event> eventDict = eventManager.getAllEvents();
        ArrayList<Event> eventDictClone = getEventListClone(eventDict);
        ArrayList<String> stringEventList = new ArrayList<>();

        eventDictClone.sort(comparator);
        int index = 1;
        for (Event event: eventDictClone){
            String x = "";
            if(eventManager.getSpeakerEvents().containsKey(event.getTitle())) {
                SpeakerEvent speakerEvent = (SpeakerEvent) event;
                x += index + ") " + event.getTitle() + " @ " + event.getEventTime() + " with " + eventManager.getSpeakers(speakerEvent).get(0) + " and more, ";
            }
            else{
                x += index + ") " + event.getTitle() + " @ " + event.getEventTime() +", ";
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
        ArrayList<Event> eventListClone = new ArrayList<>();
        eventListClone.addAll(eventDict.values());
        return eventListClone;
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
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.addAll(eventManager.getAllEvents().values());
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
                    userManager.signUp((AttendAble) attendee, event.getTitle());
                    return 2;
                } else {
                    return 6;
                }
            }
        }
        return 3;

    }

    private boolean checkCanAttend(Event event, User attendee){
        if (userManager.hasVIPAccess(attendee) && event instanceof VipOnly){
            return true;
        } else if (!(userManager.hasVIPAccess(attendee)) && event instanceof VipOnly) {
            return false;
        } else {
            return true;
        }
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
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.addAll(eventManager.getAllEvents().values());
        eventList.sort(comparator);
        Event event = eventList.get(eventIndex-1);
        Optional<User> obj = userManager.usernameToUserObject(username);
        //check if username is valid
        if (!obj.isPresent()){
            return MessageType.incorrectUsername;
        }
        User attendee = obj.get();
        eventManager.dropOut(event, attendee.getUsername());
        userManager.dropOut((AttendAble) attendee, event.getTitle());
        return MessageType.successfulDropOut;
    }

    /**
     * Menu to view, sign up, and drop events.
     * @param username username of <code>Attendee</code>.
     */
    public void eventActivity(String username) throws IOException {
        boolean activity = true;
        while (activity) {
            //String chosen = askMenuInput(8);
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
                    viewAllEvent();
                    break;
                case "S":
                    signUpPresenter.printSignUpMessage(MessageType.signUp);
                    signUpPresenter.printSignUpMessage(MessageType.enterEventId);
                    //index = getIntInput();
                    Prompt indexPrompt = promptBuilder.buildPrompt(presenter, PromptType.intPrompt);
                    index = indexPrompt.intAsk();
                    signUpPresenter.printSignUpMessage(signUpEvent(username, index));
                    break;
                case "D":
                    signUpPresenter.printSignUpMessage(MessageType.dropOut);
                    signUpPresenter.printSignUpMessage(MessageType.enterEventId);
                    //index = getIntInput();
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
    public void viewAllEvent() throws IOException {
        //String chosen = askMenuInput(9);
        PromptBuilder promptBuilder = new PromptBuilder();
        Prompt eventPrompt = promptBuilder.buildPrompt(presenter, PromptType.viewEventsMenu);
        String chosen = eventPrompt.ask();

        switch (chosen) {
            case "T":
                setComparator(new byTimeEventComparator());
                signUpPresenter.displaySortedEvents(viewAllEvents(), "time");
                break;
            case "N":
                setComparator(new byTitleEventComparator());
                signUpPresenter.displaySortedEvents(viewAllEvents(), "name");
                break;
            case "S":
                setComparator(new bySpeakerEventComparator());
                signUpPresenter.displaySortedEvents(viewAllEvents(), "speaker");
                break;
        }
    }



    public static void main(String[] args) {
        UserManager atm = new UserManager();
        EventManager evm = new EventManager();
        RoomManager rm = new RoomManager();

        atm.createAttendee("jill", "123", UserType.SPEAKER);
        rm.addRoom(1, 10);
        ArrayList<String> speakerlist = new ArrayList<>();
        speakerlist.add("jill");
        evm.createSpeakerEvent("Pet conference",speakerlist, 2020, "DECEMBER", 12, 12, 0, 1, 1);
        evm.createSpeakerEvent("ABC",speakerlist, 2020, "DECEMBER", 13, 14, 0, 1, 1);

        SignUpSystem sus = new SignUpSystem(atm,evm, rm);
        sus.setComparator(new byTimeEventComparator());
        ArrayList<String> list = sus.viewAllEvents();
        System.out.println(list);

        sus.setComparator(new byTitleEventComparator());
        list = sus.viewAllEvents();
        System.out.println(list);

        sus.setComparator(new bySpeakerEventComparator());
        list = sus.viewAllEvents();
        System.out.println(list);

        try {
            sus.setComparator(new byTimeEventComparator());
            sus.viewAllEvent();

        } catch (IOException e){
            System.out.println("OPPS");

        }

        try {
            sus.setComparator(new bySpeakerEventComparator());
            sus.viewAllEvent();

        } catch (IOException e){
            System.out.println("OPPS");

        }

        try {
            sus.setComparator(new byTitleEventComparator());
            sus.viewAllEvent();

        } catch (IOException e){
            System.out.println("OPPS");

        }
    }


}
