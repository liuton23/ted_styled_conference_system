package Entities;

import java.util.ArrayList;

public class Organizer extends User implements OrganizeAble {
    private ArrayList<String> eventOrganized;

    public Organizer(String username, String password) {
        super(username, password);
        this.eventOrganized = new ArrayList<String>();
    }

    /**
     * Methods that adds an event from an attendee's itinerary.
     * @param event upcoming event in the attendee's itinerary that the attendee will attend.
     */
    public void addEvent(String event){
        eventOrganized.add(event);
    }

    /**
     * Methods that removes an event from an attendee's itinerary.
     * @param event upcoming event in the attendee's itinerary that the attendee will not attend.
     */
    public void removeEvent(String event){
        eventOrganized.remove(event);
    }

    /**
     * Method that returns the itinerary of an attendee.
     * @return List of events that the attendee will attend.
     */
    public ArrayList<String> getEventOrganized(){
        eventOrganized.sort(String::compareToIgnoreCase);
        return eventOrganized;
    }
}
