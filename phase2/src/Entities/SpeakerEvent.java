package Entities;

import Entities.Event;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * An instance of this class represent an event at the tech conference that has at least one speaker speaking.
 */
public class SpeakerEvent extends Event implements Serializable {
    private ArrayList<String> speaker;
    private String title;
    // two item ArrayList
    private ArrayList<LocalDateTime> eventTime;
    private int room;
    private ArrayList<String> attendeeList;

    /**
     * Constructs an instance of event.
     * @param title the name of the event. Event names are unique.
     * @param speaker the username of the speaker speaking at the event.
     * @param year the year the event is starting in.
     * @param month the month the event is starting in.
     * @param day the first (and possibly only) day of the event.
     * @param hour the starting hour of the event.
     * @param minute the starting minute of the event.
     * @param room the room id of the room where the event is taking place.
     */

    public SpeakerEvent(String title, ArrayList<String> speaker, int year, String month, int day, int hour, int minute, int room, int duration){
        super(title, year, month, day, hour, minute, room, duration);
        this.speaker = speaker;
    }

    //getters
    /**
     * Method that returns a list of usernames of the speakers speaking at this event.
     * @return a list of usernames of the speakers of this event.
     */
    public ArrayList<String> getSpeaker() {
        return speaker;
    }

    // setters
    /**
     * A method that adds a speaker to an event instance.
     * @param newSpeaker the speaker that is speaking at this event's username.
     */
    public void addSpeaker(String newSpeaker){
        this.speaker.add(newSpeaker);
    }

    /**
     * A method that removes a speaker from an event instance as long as at least one speaker will remain after removal.
     * @param oldSpeaker the username of the speaker to remove from the event.
     */
    public void removeSpeaker(String oldSpeaker) {
        this.speaker.remove(oldSpeaker);
    }

    /**
     * This method returns a string representation of the event.
     * @return a string representation of the event.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
        if(this.speaker.size() == 1) {
            return String.format(this.title + " @ " + this.eventTime.get(0).format(formatter) + " to " +
                    this.eventTime.get(1).format(formatter) + " in room: " + this.getRoom() + " with : " +
                    this.getSpeaker() + ".");
        }
        else{
            return String.format(this.title + " @ " + this.eventTime.get(0).format(formatter) + " to " +
                    this.eventTime.get(1).format(formatter) + " in room: " + this.getRoom() + " with : " +
                    this.getSpeaker().get(0) + "and more.");
        }
    }
}