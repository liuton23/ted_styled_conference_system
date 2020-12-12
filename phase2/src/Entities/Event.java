package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * An Instance of this class represents a speaker-less event.
 */
public class Event implements Serializable{
    private String title;
    // two item ArrayList
    private ArrayList<LocalDateTime> eventTime;
    private int room;
    private ArrayList<String> attendeeList;
    private int capacity;

    // duration has to be an int representing a number of hours

    /**
     * Creates an event.
     * @param title name of the event.
     * @param year start year of the event.
     * @param month start month of the event.
     * @param day start day of the event.
     * @param hour start hour of the event.
     * @param minute start minute of the event.
     * @param room room of the event.
     * @param duration how long the event will last for.
     * @param capacity how many people can attend the event. Must be less than capacity of room.
     */
    public Event(String title, int year, String month, int day, int hour, int minute, int room, int duration, int capacity){
        LocalTime startTime = LocalTime.of(hour, minute);
        LocalTime endTime = startTime.plusHours(duration);
        this.title = title;
        this.eventTime = new ArrayList<>();
        this.eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, hour, minute, 0));
        this.eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute, 0));
        this.room = room;
        this.attendeeList = new ArrayList<>();
        this.capacity = capacity;
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

    /**
     * A method that returns the capacity of this event. Capacity should be less than equal to the room capacity.
     * @return an integer representing the capacity of this event.
     */
    public int getCapacity(){ return capacity;}


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
     * A method that changes the capacity of this event.
     * @param newCapacity the new event capacity.
     */
    public void setCapacity(int newCapacity){
        this.capacity = newCapacity;
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

    /**
     * This method returns a string representation of the event instance.
     * @return a string representing this event.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
        return String.format(this.title + " @ " + this.eventTime.get(0).format(formatter) + " to " +
                this.eventTime.get(1).format(formatter) + " in room: " + this.getRoom() + ".");
    }

    /**
     * Gets a linked hash map of string representations of this event's properties (title, room, etc.)
     * mapped to the corresponding part of event info they represent
     * @return a linked hash map of string representations of this event's properties
     */
    public LinkedHashMap<String, String> toStringLinkedHashMap() {
        LinkedHashMap<String, String> info = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");

        info.put("Title", this.title); //title
        info.put("Start", this.eventTime.get(0).format(formatter)); //start
        info.put("End", this.eventTime.get(1).format(formatter)); //end
        info.put("Room", String.valueOf(this.room)); //room id

        return info;
    }

}