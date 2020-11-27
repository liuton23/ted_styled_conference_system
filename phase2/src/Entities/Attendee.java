package Entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An instance of this class represent an registered attendee at the tech conference.
 */
public class Attendee extends User implements Serializable, AttendAble{
    private ArrayList<String> itinerary;

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


    /*
    public static void main(String[] args) {
        Attendee a = new Attendee("Bill Nye", "billy", "science");
        Speaker s = new Speaker("Steve", "Steve1", "Hawking");
        a.addEvent("9:00");
        a.addEvent("8:00");
        System.out.println(a.getItinerary());
        a.removeEvent("9:00");
        System.out.println(a.getItinerary());
        s.addEvent("9:00");
        s.addEvent("8:00");
        System.out.println(s.getItinerary());
        s.removeEvent("9:00");
        System.out.println(s.getItinerary());
    }

     */
}