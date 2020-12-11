package Entities;

import Entities.UserFactory.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An instance of this class represent a VIP attendee at the tech conference.
 */
public class VIP extends Speaker implements Serializable, AttendAble, TalkAble, VIPAccess{
    private ArrayList<String> itinerary;
    private ArrayList<String> VIPEvents;

    /**
     * Constructs a VIP attendee.
     * @param username account username.
     * @param password account password.
     */
    public VIP(String username, String password){
        super(username, password);
        this.itinerary = new ArrayList<>();
        this.VIPEvents = new ArrayList<>();
    }

    /**
     * Add event that the user can attend.
     * @param event event name.
     */
    public void addEvent(String event){
        itinerary.add(event);
    }

    /**
     * Remove event that the user will not attend.
     * @param event event name.
     */
    public void removeEvent(String event){
        itinerary.remove(event);
    }

    /**
     * Return the list of events that the user will attend.
     * @return list of events.
     */
    public ArrayList<String> getItinerary(){
        return itinerary;
    }

}
