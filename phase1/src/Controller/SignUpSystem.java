package Controller;

import Entities.Attendee;
import Entities.Event;
import UseCases.AttendeeManager;
import UseCases.EventManager;

import java.util.ArrayList;
import java.util.Optional;

public class SignUpSystem {
    //AttendeeManager attendeeManager;
    //EventManager eventManager;

    public SignUpSystem(){
        //this.attendeeManager = new AttendeeManager();
        //this.eventManager = new EventManager();
    }

    public String viewAllEvents(EventManager eventManager){
        ArrayList<Event> eventlist = eventManager.getEvents();
        int index = 1;
        String x = "";
        for (Event event: eventlist){
            x += index + ") " + event.getTitle() + " @ " + event.getEventTime() + ", ";
            index += 1;
        }
        return x;
    }

    public String signUpEvent(AttendeeManager attendeeManager, EventManager eventManager,
                              String username, int eventIndex){
        Event event = eventManager.getEvents().get(eventIndex-1);
        //check if space available
        if (event.getAttendeeList().size() >= event.getCapacity()){
            return "Sorry! This event is at capacity. Please select another event";
        }
        Optional<Attendee> obj = attendeeManager.usernameToAttendeeObject(username);
        //check if username is valid
        if (obj.isPresent()){
            Attendee attendee = obj.get();
            eventManager.signUP(event, attendee.getName());
            attendeeManager.signUp(attendee, event.getTitle());
            return "You have successfully signed up for " + event.getTitle() + " @ " + event.getEventTime();
        }
        return "Incorrect username. Please try again.";
    }

    public String dropOutEvent(AttendeeManager attendeeManager, EventManager eventManager,
                               String username, int eventIndex){
        Event event = eventManager.getEvents().get(eventIndex-1);
        Optional<Attendee> obj = attendeeManager.usernameToAttendeeObject(username);
        //check if username is valid
        if (!obj.isPresent()){
            return "Incorrect username. Please try again.";
        }
        Attendee attendee = obj.get();
        eventManager.dropOut(event, attendee.getName());
        attendeeManager.dropOut(attendee, event.getTitle());
        return "You have successfully dropped " + event.getTitle() + " @ " + event.getEventTime();
    }
/*
    public static void main(String[] args) {
        AttendeeManager atm = new AttendeeManager();
        EventManager evm = new EventManager();
        atm.createAttendee("Bill Nye", "Bill89", "science", false);
        evm.createEvent("Pets", "Mr. Simons", 2020, "NOVEMBER", 17, 5, 0, 3, 2);
        evm.createEvent("Cats", "Mr. Simons", 2020, "NOVEMBER", 17, 6, 0, 3, 2);
        System.out.println(atm.getAllAttendees());
        System.out.println(evm.getEvents());
        SignUpSystem sus = new SignUpSystem();
        System.out.println(sus.viewAllEvents(evm));
        System.out.println(sus.signUpEvent(atm,evm, "Bill8", 1));
        System.out.println(sus.signUpEvent(atm,evm, "Bill89", 1));
        System.out.println(sus.dropOutEvent(atm,evm, "Bill89", 1));
    }

 */
}
