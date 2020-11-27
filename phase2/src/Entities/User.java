package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String username;
    private String password;
    private ArrayList<String> itinerary;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.itinerary = new ArrayList<String>();
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
     * Method that returns the itinerary of an attendee.
     * @return List of events that the attendee will attend.
     */
    public ArrayList<String> getItinerary(){
        itinerary.sort(String::compareToIgnoreCase);
        return itinerary;
    }
}
