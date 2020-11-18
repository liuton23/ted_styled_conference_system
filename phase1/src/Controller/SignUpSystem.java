package Controller;

import Entities.*;
import Entities.EventComparators.bySpeakerEventComparator;
import Entities.EventComparators.byTimeEventComparator;
import Entities.EventComparators.byTitleEventComparator;
import UseCases.AttendeeManager;
import UseCases.EventManager;
import UseCases.RoomManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

/**
 * Controls sign-ups and drop-outs for events based on user input.
 */
public class SignUpSystem {
    private AttendeeManager attendeeManager;
    private EventManager eventManager;
    private RoomManager roomManager;
    private Comparator<Event> comparator = new byTimeEventComparator();

    /**
     * Constructor for an instance of SignUpSystem.
     * @param attendeeManager stored attendeeManager
     * @param eventManager stored eventManager
     * @param roomManager stored roomManager
     */
    public SignUpSystem(AttendeeManager attendeeManager, EventManager eventManager, RoomManager roomManager){
        this.attendeeManager = attendeeManager;
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
        ArrayList<Event> eventlist = eventManager.getEvents();
        ArrayList<Event> eventlistclone = getEventListClone(eventlist);
        ArrayList<String> stringeventlist = new ArrayList<>();

        eventlistclone.sort(comparator);
        int index = 1;
        for (Event event: eventlistclone){
            String x = "";
            x += index + ") " + event.getTitle() + " @ " + event.getEventTime() + " with " + event.getSpeaker() + ", ";
            stringeventlist.add(x);
            index += 1;
        }
        return stringeventlist;
    }

    /**
     * Creates a copy of the list of events.
     * @param eventList original list of events.
     * @return copy list of events.
     */
    private ArrayList<Event> getEventListClone(ArrayList<Event> eventList){
        ArrayList<Event> eventlistclone = new ArrayList<>();
        eventlistclone.addAll(eventList);
        return eventlistclone;
    }

    /**
     * Method that allows attendees to sign up for events. Changes information stored in the attendeeManager and
     * eventManager.
     * @param username username of the attendee.
     * @param eventIndex index of the selected event from a sorted list of events.
     * @return boolean value that will be sent to presenter to display corresponding message.
     */
    public boolean signUpEvent(String username, int eventIndex){
        ArrayList<Event> eventList = eventManager.getEvents();
        eventList.sort(comparator);
        Event event = eventList.get(eventIndex-1);
        Room room = roomManager.idToRoom(event.getRoom());
        Optional<Attendee> obj = attendeeManager.usernameToAttendeeObject(username);
        //check if username is valid
        if (obj.isPresent()){
            Attendee attendee = obj.get();
            //checking that there is space at the event
            if(event.getAttendeeList().size() < room.getCapacity()) {
                eventManager.signUp(event, attendee.getUsername());
                attendeeManager.signUp(attendee, event.getTitle());
                return true;
            }
        }
        return false;
    }

    /**
     * Method that allows attendees to sign up for events. Changes information stored in the attendeeManager and
     * eventManager.
     * @param username username of the attendee.
     * @param eventIndex index of the selected event from a sorted list of events.
     * @return status message saying whether or not the attendee successfully signed up for an event.
     */
    public String dropOutEvent(String username, int eventIndex){
        ArrayList<Event> eventList = eventManager.getEvents();
        eventList.sort(comparator);
        Event event = eventList.get(eventIndex-1);
        Optional<Attendee> obj = attendeeManager.usernameToAttendeeObject(username);
        //check if username is valid
        if (!obj.isPresent()){
            return "Incorrect username. Please try again.";
        }
        Attendee attendee = obj.get();
        eventManager.dropOut(event, attendee.getUsername());
        attendeeManager.dropOut(attendee, event.getTitle());
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
