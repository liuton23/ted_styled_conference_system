package UseCases;

import Entities.Event;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;

public class EventManager implements Serializable {
    private ArrayList<Event> events;

    public EventManager(){
        this.events = new ArrayList<Event>();
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public int createEvent(String title, String speaker, int year, String month, int day, int hour,
                              int minute, int room, int capacity){
        LocalTime startTime = LocalTime.of(hour, minute);
        LocalTime endTime = startTime.plusHours(1);
        ArrayList<LocalDateTime> eventTime = new ArrayList<LocalDateTime>();
        eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, hour, minute));
        eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute));
        if(!freeRoomCheck(eventTime, room)){
            //"Room is already booked for this timeslot."
            return 0 ;
        }
        else if(!freeSpeakerCheck(eventTime, speaker)) {
            //"Speaker is already booked for this timeslot."
            return 1;
        }
        else if(!freeTitleCheck(title)){
            //"This event name has already been taken."
            return 2;
        }
        else{
            events.add(new Event(title, speaker, year, month, day, hour, minute, room, capacity));
            //"Event successfully created."
            return 3;
        }
    }

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

    public boolean freeTitleCheck(String title) {
        for (Event eventInstance : events) {
            if (eventInstance.getTitle().equals(title)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> eventToAttendees(Event event) {
        return event.getAttendeeList();
    }

    // should I add an error message if at capacity?
    public void signUP(Event event, String attendee){
        if(event.getAttendeeList().size() < event.getCapacity()){
            event.addAttendee(attendee);
        }
    }

    public void dropOut(Event event, String attendee){
        event.getAttendeeList().remove(attendee);
    }

    public boolean changeSpeaker(Event event, String speaker){
        // checking that the corresponding speaker is free at this event time
        if(freeSpeakerCheck(event.getEventTime(), speaker)){
            event.setSpeaker(speaker);
            return true;
        }
        return false;
    }

    public void cancelEvent(Event event){
        events.remove(event);
    }
    //testing
    public static void main(String[] args) {
        EventManager eventManager = new EventManager();
        eventManager.createEvent("Pet Conference", "Caesar Milan", 2020, "NOVEMBER",
                16, 12, 0, 100, 500);
        eventManager.createEvent("Fan Expo", "Karen Gillan", 2015, "AUGUST",
                20, 2, 0, 500, 1000);
        System.out.println(eventManager.getEvents().get(0).getTitle());
        System.out.println(eventManager.getEvents().get(1).getTitle());
        eventManager.changeSpeaker(eventManager.events.get(1), "Matt Smith");
        System.out.println(eventManager.events.get(1).getSpeaker());
        System.out.println(eventManager.createEvent("Dog Show", "Caesar Milan", 2020, "NOVEMBER",
                16, 11, 30, 200, 200));
        System.out.println(eventManager.events.size());
        System.out.println(eventManager.createEvent("Fan Expo", "Stan Lee", 2020, "NOVEMBER",
                16, 11, 30, 210, 200));
        System.out.println(eventManager.createEvent("Garden Lover's", "The Green Thumb", 2015, "AUGUST",
                20, 2, 10, 500, 1000));
        eventManager.cancelEvent(eventManager.events.get(0));
        System.out.println(eventManager.events.size());
    }
}
