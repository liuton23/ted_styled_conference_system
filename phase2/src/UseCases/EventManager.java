package UseCases;

import Entities.Attendee;
import Entities.Event;
import Entities.Speaker;
import Entities.SpeakerEvent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * Manages and stores events in this tech conference system.
 */
public class EventManager implements Serializable {
    private ArrayList<Event> events;
    private HashMap<String, SpeakerEvent> speakerEvents;

    /**
     * Constructs an instance of EventManager with an empty Arraylist of Events.
     */
    public EventManager(){
        this.events = new ArrayList<Event>();
        this.speakerEvents = new HashMap<>();
    }

    /**
     * This method returns an ArrayList of events.
     * @return an ArrayList<Event> representing the events occurring at the tech conference.
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * This method returns an ArrayList of speaker events.
     * @return an ArrayList<Event> representing the events occurring at the tech conference.
     */
    public HashMap<String, SpeakerEvent> getSpeakerEvents() {
        return speakerEvents;
    }

    /**
     * This method creates an event and adds it to EventManager's Arraylist of events.
     * @param title the desired event name.
     * @param speaker the desired speaker username.
     * @param year the year the event starts.
     * @param month the month the event starts.
     * @param day the day the event starts.
     * @param hour the hour the event starts.
     * @param minute the minute the event starts.
     * @param room the room id of the desired room where the event takes place.
     */
    public void createSpeakerEvent(String title, ArrayList<String> speaker, int year, String month, int day, int hour,
                              int minute, int room, int duration){
        SpeakerEvent newEvent = new SpeakerEvent(title, speaker, year, month, day, hour, minute, room, duration);
        events.add(newEvent);
        speakerEvents.put(title, newEvent);
    }

    /**
     * This method creates an event and adds it to EventManager's Arraylist of events.
     * @param title the desired event name.
     * @param year the year the event starts.
     * @param month the month the event starts.
     * @param day the day the event starts.
     * @param hour the hour the event starts.
     * @param minute the minute the event starts.
     * @param room the room id of the desired room where the event takes place.
     */
    public void createSpeakerlessEvent(String title, int year, String month, int day, int hour,
                                   int minute, int room, int duration){
        events.add(new Event(title, year, month, day, hour, minute, room, duration));
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
                    LocalDateTime existingEventStart = eventInstance.getEventTime().get(0);
                    LocalDateTime existingEventEnd = eventInstance.getEventTime().get(1);
                    if (newEventStart.isBefore(existingEventEnd) || newEventStart.isEqual(existingEventEnd) &&
                            existingEventStart.isBefore(newEventEnd) || existingEventStart.isEqual(newEventEnd)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Method checking that a name is available and can be assigned to a new event.
     * @param title the event name whose availability is being checked.
     * @return a boolean value where true signifies the event name is available and false signifies that it is already
     * in use.
     */
    public boolean freeTitleCheck(String title) {
        for (Event eventInstance : events) {
            if (eventInstance.getTitle().equals(title)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method that returns an event object from its name.
     * @param eventName the name of the event to be returned
     * @return the respective event object.
     */
    public Optional<Event> nameToEvent(String eventName){
        for (Event event: events) {
            if(event.getTitle().equals(eventName)){
                return Optional.of(event);
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
        for (Event eventInstance: events) {
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
        // checking that the corresponding speaker is free at this event time
        if(freeSpeakerCheck(event.getEventTime(), speaker)){
            if (event.getSpeaker().size() >= 2) {
                event.removeSpeaker(speaker);
                return true;
            }
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
    //for phase 2
    public void cancelEvent(Event event){
        events.remove(event);
        if (event instanceof SpeakerEvent){
            speakerEvents.remove(event.getTitle());
        }
    }
    //testing
    public static void main(String[] args) {
        EventManager eventManager = new EventManager();
        ArrayList<String> speakerList1 = new ArrayList<>();
        ArrayList<String> speakerList2 = new ArrayList<>();
        speakerList1.add("Caesar Milan");
        speakerList1.add("Fido");
        speakerList2.add("Karen Gillan");

        eventManager.createSpeakerEvent("Pet Conference", speakerList1, 2020, "NOVEMBER",
                16, 12, 0, 100, 5);
        eventManager.createSpeakerEvent("Fan Expo", speakerList2, 2015, "AUGUST",
                20, 2, 0, 500, 2);
    }
}
