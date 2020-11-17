package Entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An instance of this class represent an registered attendee at the tech conference.
 */
public class Attendee implements Serializable {
    private String username;
    private String password;
    private boolean isOrganizer = false;
    private ArrayList<String> itinerary;

    /**
     * Constructor for an instance of Attendee.
     * @param username the attendee account login username.
     * @param password the attendee account login password.
     */
    public Attendee(String username, String password){
        this.username = username;
        this.password = password;
        this.itinerary = new ArrayList<>();
    }

    /**
     * Method that returns the username of the attendee.
     * @return the username of the attendee account.
     */
    public String getUsername(){
        return username;
    }

    /**
     * Method that returns the password of the attendee.
     * @return the password of the attendee account.
     */
    public String getPassword(){
        return password;
    }

    /**
     * Method that adds an event to an attendee's itinerary.
     * @param event upcoming event that the attendee will attend.
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
     * Method that makes an instance of Attendee an organizer
     */
    public void makeOrganizer(){
        isOrganizer = true;
    }

    /**
     * Method that checks if an instance of Attendee is an organizer.
     * @return true iff an attendee is an organizer
     */
    public Boolean isOrganizer(){
        return isOrganizer;
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
