package Controller;

import Entities.SpeakerEvent;
import Entities.UserFactory.AttendAble;
import Entities.Event;
import Entities.EventComparators.byTimeEventComparator;
import Entities.Room;
import Entities.User;
import Entities.VipOnly;
import UseCases.UserManager;
import UseCases.EventManager;
import UseCases.RoomManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

/**
 * Controls sign-ups and drop-outs for events based on user input.
 */
public class SignUpSystem {
    private UserManager userManager;
    private EventManager eventManager;
    private RoomManager roomManager;
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
    public String dropOutEvent(String username, int eventIndex){
        if (eventIndex < 1 || eventIndex > eventManager.getAllEvents().size()){
            return "Incorrect ID. Please try again.";
        }
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.addAll(eventManager.getAllEvents().values());
        eventList.sort(comparator);
        Event event = eventList.get(eventIndex-1);
        Optional<User> obj = userManager.usernameToUserObject(username);
        //check if username is valid
        if (!obj.isPresent()){
            return "Incorrect username. Please try again.";
        }
        User attendee = obj.get();
        eventManager.dropOut(event, attendee.getUsername());
        userManager.dropOut((AttendAble) attendee, event.getTitle());
        return "You have successfully dropped " + event.getTitle() + " @ " + event.getEventTime();
    }
/*
    public static void main(String[] args) {
        AttendeeManager atm = new AttendeeManager();
        EventManager evm = new EventManager();
        atm.createAttendee("Bill89", "science", false);
        evm.createEvent("Pets", "Mr. Simons", 2020, "NOVEMBER", 17, 5, 0, 3, 2);
        evm.createEvent("Cats", "Mr. Paul", 2020, "NOVEMBER", 17, 6, 0, 3, 2);
        System.out.println(atm.getAllAttendees());
        System.out.println(evm.getEvents());
        SignUpSystem sus = new SignUpSystem();
        System.out.println(sus.viewAllEvents(evm));

        sus.setComparator(new byTitleEventComparator());
        System.out.println(sus.viewAllEvents(evm));

        sus.setComparator(new bySpeakerEventComparator());
        System.out.println(sus.viewAllEvents(evm));

        System.out.println(sus.signUpEvent(atm,evm, "Bill8", 1));
        System.out.println(sus.signUpEvent(atm,evm, "Bill89", 1));
        System.out.println(sus.dropOutEvent(atm,evm, "Bill89", 1));
    }

 */

}
