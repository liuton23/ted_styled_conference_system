package UseCases;

import Entities.Attendee;
import Entities.Event;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Manages and stores events in this tech conference system.
 */
public class EventManager implements Serializable {
    private ArrayList<Event> events;

    /**
     * Constructs an instance of EventManager with an empty Arraylist of Events.
     */
    public EventManager(){
        this.events = new ArrayList<Event>();
    }

    /**
     * This method returns an ArrayList of events.
     * @return an ArrayList<Event> representing the events occurring at the tech conference.
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * This method creates an event instance when the following conditions are satisfied:
     * 1) The desired room is free
     * 2) The selected speaker is free.
     * 3) The event name is available.
     * @param title the desired event name.
     * @param speaker the desired speaker username.
     * @param year the year the event starts.
     * @param month the month the event starts.
     * @param day the day the event starts.
     * @param hour the hour the event starts.
     * @param minute the minute the event starts.
     * @param room the room id of the desired room where the event takes place.
     * @return an integer signifying whether the event was successfully created or an error message.
     */
    public void createEvent(String title, String speaker, int year, String month, int day, int hour,
                              int minute, int room){
        LocalTime startTime = LocalTime.of(hour, minute);
        LocalTime endTime = startTime.plusHours(1);
        ArrayList<LocalDateTime> eventTime = new ArrayList<LocalDateTime>();
        eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, hour, minute));
        eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute));
        events.add(new Event(title, speaker, year, month, day, hour, minute, room));
    }

    /**
     * This method checks that the room with the given id is available at the specified time.
     * @param eventTime the time the room's availability is being checked at.
     * @param room the room id belonging to the room whose availability is being checked.
     * @return a boolean value; true signalling the room is available at the given time and false if it is not.
     */
    public boolean freeRoomCheck(ArrayList<LocalDateTime> eventTime, int room){
        LocalDateTime newEventStart = eventTime.get(0);
        LocalDateTime newEventEnd = eventTime.get(1);
        for(Event eventInstance : events){
            // if the room is being used in an already booked event
            if (eventInstance.getRoom() == room){
                LocalDateTime bookedEventStart = eventInstance.getEventTime().get(0);
                LocalDateTime bookedEventEnd = eventInstance.getEventTime().get(1);
                // case 1: pre-booked event starts before or at the same time as the event we are trying to schedule
                // AND ends after the new event start time, the room is not free
                if((bookedEventStart.isBefore(newEventStart) || bookedEventStart.isEqual(newEventStart)) &&
                        (bookedEventEnd.isAfter(newEventStart))){
                    return false;
                }
                // case 2: the pre-booked event starts after the new event start time and before the new event  end time,
                // the room is not free
                else if(bookedEventStart.isAfter(newEventStart) && bookedEventStart.isBefore(newEventEnd)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method checks the availability of the speaker whose username is provided.
     * @param eventTime the time the speaker's availability is being checked.
     * @param speaker the username of the speaker whose availability is being checked.
     * @return a boolean value where true signals the speaker is free at the specified time and false if they are not.
     */
    public boolean freeSpeakerCheck(ArrayList<LocalDateTime> eventTime, String speaker){
        LocalDateTime newEventStart = eventTime.get(0);
        LocalDateTime newEventEnd = eventTime.get(1);
        for(Event eventInstance : events){
            //checking if event pre-booked event has this speaker
            if (eventInstance.getSpeaker().equals(speaker)){
                LocalDateTime bookedEventStart = eventInstance.getEventTime().get(0);
                LocalDateTime bookedEventEnd = eventInstance.getEventTime().get(1);
                if((bookedEventStart.isBefore(newEventStart) || bookedEventStart.isEqual(newEventStart)) &&
                        (bookedEventEnd.isAfter(newEventStart))){
                    return false;
                }
                else if(bookedEventStart.isAfter(newEventStart) && bookedEventStart.isBefore(newEventEnd)) {
                    return false;
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
    public Optional<Event> nameToEvent(String eventName){
        for (Event event: this.getEvents()) {
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
        return event.getAttendeeList();
    }

    /**
     * Method adds an Attendee to an Event provided the event is not at capacity. Returns an integer signifying a
     * successful sign up (0) or that the event is full(1).
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
     * This method changes the speaker to the specified new speaker at the specified event.
     * @param event the event whose speaker is being changed.
     * @param speaker the username of the new speaker at the event.
     * @return a boolean where true signals a successful change and false signals the new speaker is unavailable.
     */
    public boolean changeSpeaker(Event event, String speaker){
        // checking that the corresponding speaker is free at this event time
        if(freeSpeakerCheck(event.getEventTime(), speaker)){
            event.setSpeaker(speaker);
            return true;
        }
        return false;
    }

    /**
     * This method removes the specified event from the list of events at the Tech Conference.
     * @param event the event to be removed.
     */
    public void cancelEvent(Event event){
        events.remove(event);
    }
    //testing
    public static void main(String[] args) {
        EventManager eventManager = new EventManager();
        eventManager.createEvent("Pet Conference", "Caesar Milan", 2020, "NOVEMBER",
                16, 12, 0, 100);
        eventManager.createEvent("Fan Expo", "Karen Gillan", 2015, "AUGUST",
                20, 2, 0, 500);
        System.out.println(eventManager.getEvents().get(0).getTitle());
        System.out.println(eventManager.getEvents().get(1).getTitle());
        eventManager.changeSpeaker(eventManager.events.get(1), "Matt Smith");
        System.out.println(eventManager.events.get(1).getSpeaker());
        //System.out.println(eventManager.createEvent("Dog Show", "Caesar Milan", 2020, "NOVEMBER",
        //       16, 11, 30, 200));
        System.out.println(eventManager.events.size());
        //System.out.println(eventManager.createEvent("Fan Expo", "Stan Lee", 2020, "NOVEMBER",
        //        16, 11, 30, 210));
        //System.out.println(eventManager.createEvent("Garden Lover's", "The Green Thumb", 2015, "AUGUST",
        //        20, 2, 10, 500));
        eventManager.cancelEvent(eventManager.events.get(0));
        System.out.println(eventManager.events.size());
        Attendee Ana = new Attendee("Ana", "Heidi");

    }
}
