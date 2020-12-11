package Entities;

import Entities.UserFactory.AttendAble;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An instance of this class represent an registered attendee at the tech conference.
 */
public class Attendee extends User implements Serializable, AttendAble {
    private ArrayList<String> itinerary;

    /**
     * Constructs an instance of attendee account.
     * @param username account username.
     * @param password account password.
     */
    public Attendee(String username, String password) {
        super(username, password);
        this.itinerary = new ArrayList<String>();
    }

    /**
     * Methods that adds an event from an attendee's itinerary.
     * @param event upcoming event in the attendee's itinerary that the attendee will attend.
     */
    public void addEvent(String event){
        itinerary.add(event);
    }

    /**
     * Methods that removes an event from an attendee's itinerary.
     * @param event upcoming event in the attendee's itinerary that the attendee will not attend.
     */
    public void removeEvent(String event){
        itinerary.remove(event);
    }

    /**
     * Method that returns the itinerary of an attendee.
     * @return List of events that the attendee will attend.
     */
    public ArrayList<String> getItinerary(){
        itinerary.sort(String::compareToIgnoreCase);
        return itinerary;
    }
}
