package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 */
public class Event implements Serializable{
    private String title;
    // two item ArrayList
    private ArrayList<LocalDateTime> eventTime;
    private int room;
    private ArrayList<String> attendeeList;

    // duration has to be an int representing a number of hours
    public Event(String title, int year, String month, int day, int hour, int minute, int room, int duration){
        LocalTime startTime = LocalTime.of(hour, minute);
        LocalTime endTime = startTime.plusHours(duration);
        this.title = title;
        this.eventTime = new ArrayList<>();
        this.eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, hour, minute, 0));
        this.eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute, 0));
        this.room = room;
        this.attendeeList = new ArrayList<String>();
    }
    // getters
    /**
     * Method that returns the name of this event.
     * @return the name of the event.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method that returns the start and end times of this event.
     * @return an ArrayList of length 2, whose element at index 0 is a LocalDateTime representing the start time of the
     * event, and whose element at index 1 is a LocalDateTime representing the end time of the event.
     */
    public ArrayList<LocalDateTime> getEventTime(){
        return eventTime;
    }

    /**
     * Method that returns the room id of the room where the event instance takes place.
     * @return room id of the event room. The room id is always an integer.
     */
    public int getRoom(){
        return room;
    }

    /**
     * A method that returns an ArrayList of usernames corresponding to the Attendees attending this event.
     * @return Arraylist of Attendee usernames for all Attendees attending this event.
     */
    public ArrayList<String> getAttendeeList(){ return attendeeList;}

    //setters
    /**
     * This method sets the time of an event, for any events whose desired length is 1 hour.
     * @param year the year that this event starts.
     * @param month the month that this event starts in.
     * @param day the day this event starts in.
     * @param hour the hour this event starts in.
     * @param minute the minute this event starts in.
     * @param duration of the event in hours.
     */
    public void setEventTime(int year, String month, int day, int hour, int minute, int duration){
        LocalTime startTime = LocalTime.of(hour, minute);
        LocalTime endTime = startTime.plusHours(duration);
        this.eventTime.remove(1);
        this.eventTime.remove(0);
        this.eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, hour, minute));
        this.eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute));
    }

    /**
     * This method sets the room where the event takes place.
     * @param newRoom the id of the room where the event will take place.
     */
    public void setRoom(int newRoom){
        this.room = newRoom;
    }


    /**
     * This method adds an Attendee's username to the event's list of attendee usernames.
     * @param attendee the username of an attendee that is to be added to this event.
     */
    public void addAttendee(String attendee){
        this.attendeeList.add(attendee);
    }

    /**
     * This method removes an Attendee's username from the event's list of attendee usernames.
     * @param attendee the username of the attendee to be removed from this event.
     */
    public void removeAttendee(String attendee){
        this.attendeeList.remove(attendee);
    }
}

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
        super(title, year, month, day, hour, minute, room, duration)
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
     * @param newSpeaker the username of the speaker to remove from the event.
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

    //testing
    public static void main(String[] args) {
        Event petConference = new Event("Pet Conference", "Caesar Milan", 2020, "NOVEMBER",
                16, 12, 0, 100);
        System.out.println(petConference.getTitle());
        System.out.println(petConference.getSpeaker());
        System.out.println(petConference.getEventTime());
        System.out.println(petConference.getRoom());
        petConference.setEventTime(2020, "DECEMBER", 22, 12, 0);
        System.out.println(petConference.getEventTime());
        System.out.println(petConference.getAttendeeList());
        petConference.addAttendee("Heidi");
        petConference.addAttendee("Maya");
        System.out.println(petConference.getAttendeeList());
        petConference.removeAttendee("Iva");
        petConference.removeAttendee("Heidi");
        System.out.println(petConference.getAttendeeList());
        System.out.println(petConference);
    }
}