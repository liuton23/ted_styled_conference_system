package Controller;

import Entities.*;
import Entities.EventComparators.bySpeakerEventComparator;
import Entities.EventComparators.byTimeEventComparator;
import Entities.EventComparators.byTitleEventComparator;
import UseCases.AttendeeManager;
import UseCases.EventManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class SignUpSystem {
    Comparator<Event> comparator = new byTimeEventComparator();

    public SignUpSystem(){
        //this.attendeeManager = new AttendeeManager();
        //this.eventManager = new EventManager();
    }

    public void setComparator(Comparator<Event> comparator){
        this.comparator = comparator;
    }

    public String viewAllEvents(EventManager eventManager){
        ArrayList<Event> eventlist = eventManager.getEvents();
        ArrayList<Event> eventlistclone = getEventListClone(eventlist);

        eventlistclone.sort(comparator);
        int index = 1;
        String x = "";
        for (Event event: eventlistclone){
            x += index + ") " + event.getTitle() + " @ " + event.getEventTime() + " with " + event.getSpeaker() + ", ";
            index += 1;
        }
        return x;
    }

    private ArrayList<Event> getEventListClone(ArrayList<Event> eventList){
        ArrayList<Event> eventlistclone = new ArrayList<>();
        for (Event event: eventList){
            eventlistclone.add(event);
        }
        return eventlistclone;
    }

    public String signUpEvent(AttendeeManager attendeeManager, EventManager eventManager,
                              String username, int eventIndex){
        ArrayList<Event> eventList = eventManager.getEvents();
        eventList.sort(comparator);
        Event event = eventList.get(eventIndex-1);
        //check if space available
        if (event.getAttendeeList().size() >= event.getCapacity()){
            return "Sorry! This event is at capacity. Please select another event";
        }
        Optional<Attendee> obj = attendeeManager.usernameToAttendeeObject(username);
        //check if username is valid
        if (obj.isPresent()){
            Attendee attendee = obj.get();
            eventManager.signUP(event, attendee.getUsername());
            attendeeManager.signUp(attendee, event.getTitle());
            return "You have successfully signed up for " + event.getTitle() + " @ " + event.getEventTime();
        }
        return "Incorrect username. Please try again.";
    }

    public String dropOutEvent(AttendeeManager attendeeManager, EventManager eventManager,
                               String username, int eventIndex){
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

}
