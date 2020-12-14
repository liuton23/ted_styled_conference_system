package UseCases;

import Entities.*;
import Entities.EventComparators.byTimeEventComparator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Manages and stores events in this tech conference system.
 */
public class EventManager implements Serializable {
    private HashMap<String, Event> masterEventDict;
    private HashMap<String, Event> speakerlessEvents;
    private HashMap<String, SpeakerEvent> speakerEvents;

    /**
     * Constructs an instance of EventManager with empty hashmaps corresponding to the different types of Events.
     */
    public EventManager(){
        this.masterEventDict = new HashMap<>();
        this.speakerlessEvents = new HashMap<>();
        this.speakerEvents = new HashMap<>();
    }

    /**
     * This method returns a dictionary of all events.
     * @return a HashMap<String, Event>  representing all of the events occurring at the tech conference.
     */
    public HashMap<String, Event> getAllEvents() {
        return masterEventDict;
    }

    /**
     * This method returns a list of all events sorted by time.
     * @return a sorted list representing all of the events occurring at the tech conference.
     */
    public ArrayList<Event> getAllEventsSortedByTime() {
        ArrayList<Event> events = new ArrayList<>(this.getAllEvents().values());
        Comparator<Event> comparator = new byTimeEventComparator();
        events.sort(comparator);
        return events;
    }

    /**
     * This method returns an ArrayList of speaker events.
     * @return a HashMap<String, SpeakerEvent>  representing the events occurring at the tech conference.
     */
    public HashMap<String, SpeakerEvent> getSpeakerEvents() {
        return speakerEvents;
    }

    /**
     * This method returns an ArrayList of speaker-less events.
     * @return a HashMap<String, Event>  representing the events occurring at the tech conference.
     */
    public HashMap<String, Event> getSpeakerlessEvents() {
        return speakerlessEvents;
    }


    /**
     * This method creates an event and adds it to EventManager's hashmap of events (masterEventList) with the event
     * name as the key and the SpeakerEvent object as the value. It adds the event name as a key to EventManager's hashmap
     * of speaker events (speakerEvents) and the event object as the value as well.
     * @param title the desired event name.
     * @param speaker the desired speaker username.
     * @param year the year the event starts.
     * @param month the month the event starts.
     * @param day the day the event starts.
     * @param hour the hour the event starts.
     * @param minute the minute the event starts.
     * @param room the room id of the desired room where the event takes place.
     * @param capacity the capacity of the event.
     */
    public void createSpeakerEvent(String title, ArrayList<String> speaker, int year, String month, int day, int hour,
                              int minute, int room, int duration, int capacity){
        SpeakerEvent newEvent = new SpeakerEvent(title, speaker, year, month, day, hour, minute, room, duration, capacity);
        masterEventDict.put(title, newEvent);
        speakerEvents.put(title, newEvent);
    }

    /**
     * This method creates a speaker-less event and adds it to EventManager's hashmap of events. (masterEventList) with the event
     * name as the key and the event object as the value. It adds the event name as a key to EventManager's hashmap
     * of speaker-less events (speakerlessEvents) and the event object as the value as well.
     * @param title the desired event name.
     * @param year the year the event starts.
     * @param month the month the event starts.
     * @param day the day the event starts.
     * @param hour the hour the event starts.
     * @param minute the minute the event starts.
     * @param room the room id of the desired room where the event takes place.
     * @param capacity the capacity of the event.
     */
    public void createSpeakerlessEvent(String title, int year, String month, int day, int hour,
                                   int minute, int room, int duration, int capacity){
        Event newEvent = new Event(title, year, month, day, hour, minute, room, duration, capacity);
        masterEventDict.put(title, newEvent);
        speakerlessEvents.put(title, newEvent);
    }

    /**
     * This method creates a VIP-only event and adds it to EventManager's hashmap of events (masterEventList) with the
     * event name as the key and the SpeakerEvent object as the value. It adds the event name as a key to EventManager's
     * hashmap of speaker events (speakerEvents) and the event object as the value as well.
     * @param title the desired event name.
     * @param speaker the desired speaker username.
     * @param year the year the event starts.
     * @param month the month the event starts.
     * @param day the day the event starts.
     * @param hour the hour the event starts.
     * @param minute the minute the event starts.
     * @param room the room id of the desired room where the event takes place.
     * @param capacity the capacity of the event.
     */
    public void createVIPSpeakerEvent(String title, ArrayList<String> speaker, int year, String month, int day, int hour,
                                      int minute, int room, int duration, int capacity){
        VipSpeakerEvent newEvent = new VipSpeakerEvent(title, speaker, year, month, day, hour, minute, room, duration, capacity);
        masterEventDict.put(title, newEvent);
        speakerEvents.put(title, newEvent);
    }

    /**
     * This method creates a speaker-less VIP-only event and adds it to EventManager's hashmap of events.
     * (masterEventList) with the event name as the key and the event object as the value. It adds the event name as a
     * key to EventManager's hashmap of speaker-less events (speakerlessEvents) and the event object as the value as
     * well.
     * @param title the desired event name.
     * @param year the year the event starts.
     * @param month the month the event starts.
     * @param day the day the event starts.
     * @param hour the hour the event starts.
     * @param minute the minute the event starts.
     * @param room the room id of the desired room where the event takes place.
     * @param capacity the capacity of the event.
     */
    public void createVIPEvent(String title, int year, String month, int day, int hour,
                               int minute, int room, int duration, int capacity){
        VipEvent newEvent = new VipEvent(title, year, month, day, hour, minute, room, duration, capacity);
        masterEventDict.put(title, newEvent);
        speakerlessEvents.put(title, newEvent);
    }

    /**
     * This method checks the availability of the speaker whose username is provided.
     * @param eventTime the time the speaker's availability is being checked.
     * @param speaker the username of the speaker whose availability is being checked.
     * @return a boolean value where true signals the speaker is free at the specified time and false if they are not.
     */
    public boolean freeSpeakerCheck(ArrayList<LocalDateTime> eventTime, String speaker) {
        LocalDateTime newEventStart = eventTime.get(0);
        LocalDateTime newEventEnd = eventTime.get(1);
        for (SpeakerEvent eventInstance : speakerEvents.values()) {
            //checking if already registered event has this speaker speaking
            ArrayList<String> speakerList = eventInstance.getSpeaker();
            for (String speakerName : speakerList) {
                if(speaker.equals(speakerName)) {
                    if (checkSpeakerConflict(newEventStart, newEventEnd, eventInstance.getEventTime())) {
                        return false;
                        }
                    }
                }
            }
        return true;
    }

    /**
     * Method checking for speaker conflicts.
     * @param start the new potential start time.
     * @param end the new potential end time.
     * @param eventTime the already existing event start and end times.
     * @return true if there is a conflict and false otherwise.
     */
    public boolean checkSpeakerConflict(LocalDateTime start, LocalDateTime end, ArrayList<LocalDateTime> eventTime) {
        LocalDateTime bookingStart = eventTime.get(0); //start time of booking
        LocalDateTime bookingEnd = eventTime.get(1);  //end time of booking
        if (start.isAfter(bookingStart) && start.isBefore(bookingEnd)) {
            return true;
        } else if (end.isAfter(bookingStart) && end.isBefore(bookingEnd)) {
            return true;
        } else if (start.equals(bookingEnd)) {
            return true;
        } else if (end.equals(bookingEnd)) {
            return true;
        } else if (start.isBefore(bookingStart) && end.isAfter(bookingEnd)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method checking that a name is available and can be assigned to a new event.
     * @param title the event name whose availability is being checked.
     * @return a boolean value where true signifies the event name is available and false signifies that it is already
     * in use.
     */
    public boolean freeTitleCheck(String title) {
        for (String eventName : masterEventDict.keySet()) {
            if (eventName.equals(title)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the name of the provided event object
     * @param event the event whose name is being returned.
     * @return the name of the event.
     */
    public String getTitle(Event event){
        return event.getTitle();
    }

    /**
     * Method that returns an Optional<Event> object from its name.
     * @param eventName the name of the event to be returned
     * @return the respective Optional<Event> object.
     */
    public Optional<Event> nameToEvent(String eventName){
        for (String existingEventName: masterEventDict.keySet()) {
            if(existingEventName.equals(eventName)){
                return Optional.of(masterEventDict.get(existingEventName));
            }
        }
        return Optional.empty();
    }

    /**
     * This method returns a list of attendee usernames corresponding to the Attendees attending the specified event.
     * @param event the event whose list of Attendee usernames will be returned.
     * @return an Arraylist of Attendee usernames who are attending the provided Event.
     */
    public ArrayList<String> eventToAttendees(Event event) {
        for (Event eventInstance: masterEventDict.values()) {
            if (eventInstance == event){
                return eventInstance.getAttendeeList();
            }
        }
        return new ArrayList<>();
    }

    /**
     * Method adds an Attendee to an Event.
     * @param event the event an Attendee is attempting to be signed up for.
     * @param attendee the Attendee attempting to be signed up for the given event.
     */
    public void signUp(Event event, String attendee){
        event.addAttendee(attendee);
    }

    /**
     * This method removes the specified Attendee from the specified event.
     * @param event the event the Attendee will no longer be attending.
     * @param attendee the username of the Attendee being removed from the event.
     */
    public void dropOut(Event event, String attendee){
        event.removeAttendee(attendee);
    }

    /**
     * return a list of speakers of an event
     * @param event object we want the speaker names from
     * @return an arraylist of speaker usernames
     */
    public ArrayList<String> getSpeakers(SpeakerEvent event){
        return event.getSpeaker();
    }

    /**
     * This method changes the speaker to the specified new speaker at the specified event.
     * @param event the event whose speaker is being changed.
     * @param newSpeaker the username of the new speaker at the event.
     * @param oldSpeaker the username of the speaker to be changed.
     * @return a boolean where true signals a successful change and false signals the new speaker is unavailable.
     */
    public boolean changeSpeaker(SpeakerEvent event, String newSpeaker, String oldSpeaker){
        // checking that the corresponding speaker is free at this event time
        if(freeSpeakerCheck(event.getEventTime(), newSpeaker)){
            event.addSpeaker(newSpeaker);
            event.removeSpeaker(oldSpeaker);
            return true;
        }
        return false;
    }

    /**
     * This method adds an additional speaker to a specified event.
     * @param event the event whose speaker is being changed.
     * @param speaker the username of the new speaker at the event.
     * @return a boolean where true signals a successful change and false signals the new speaker is unavailable.
     */
    public boolean addSpeaker(SpeakerEvent event, String speaker){
        // checking that the corresponding speaker is free at this event time
        if(freeSpeakerCheck(event.getEventTime(), speaker)){
            event.addSpeaker(speaker);
            return true;
        }
        return false;
    }

    /**
     * This method removes a speaker from a specified event as long as at least one speaker would remain speaking at the event
     * after removal.
     * @param event the event whose speaker is being changed.
     * @param speaker the username of the new speaker at the event.
     * @return a boolean where true signals a successful change and false signals the new speaker is unavailable.
     */
    public boolean removeSpeaker(SpeakerEvent event, String speaker){
        if (event.getSpeaker().size() >= 2) {
            event.removeSpeaker(speaker);
            return true;
        }
        return false;
    }

    /**
     * Returns the room id of a specific event.
     * @param event the event object.
     * @return the room id of this event.
     */
    public int getEventRoom(Event event){
        return event.getRoom();
    }

    /**
     * This method removes the specified event from the list of events at the Tech Conference.
     * @param event the event to be removed.
     * */
    public void cancelEvent(Event event){
        masterEventDict.remove(event.getTitle());
        if (event instanceof SpeakerEvent){
            speakerEvents.remove(event.getTitle());
        }
        else {
            speakerlessEvents.remove(event.getTitle());
        }
    }

    /**
     * Returns the maximum number of people for an <code>Event</code>.
     * @param event event for which to get the capacity of.
     * @return the maximum capacity for the event.
     */
    public int getEventCapacity(Event event){
        return event.getCapacity();
    }

    /**
     * Sets the maximum number of attendees for an event.
     * @param event event for which to set the capacity of.
     * @param capacity the new maximum capacity for the event. Must be less than capacity of room the event is being
     *                 held in.
     */
    public void setEventCapacity(Event event, int capacity){
        event.setCapacity(capacity);
    }

    /**
     * Returns a list containing the linked hash maps of the event info for each event in a list of events
     * @return the list containing the linked hash maps of event info in order by time
     */
    public ArrayList<LinkedHashMap<String, String>> getEventInfoLists() {
        ArrayList<Event> events = this.getAllEventsSortedByTime();
        ArrayList<LinkedHashMap<String, String>> eventInfos = new ArrayList<>();
        for (Event event : events) {
            eventInfos.add(event.toStringLinkedHashMap());
        }
        return eventInfos;
    }

    /**
     * Method that returns the string representation of the provided event.
     * @param event the event to be returned in string form.
     * @return the string form of the event.
     */
    public String getToString(Event event){
        return event.toString();
    }
}
