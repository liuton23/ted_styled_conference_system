package Controller;


import Entities.Attendee;
import Entities.Event;
import Entities.Speaker;
import UseCases.EventManager;
import UseCases.AttendeeManager;
import UseCases.RoomManager;

public class ScheduleSystem {
    EventManager eventManager = new EventManager();
    AttendeeManager attendeeManager = new AttendeeManager();
    RoomManager roomManager = new RoomManager();
    public String scheduleEvent(String title, String speaker, int year, String month, int day, int hour, int minute,
                                int room, int capacity){
        return eventManager.createEvent(title, speaker, year, month, day, hour, minute, room, capacity);
    }
    public String addRoom(int roomId, int capacity){
        if(roomManager.addRoom(roomId, capacity)){
            return "Room successfully added.";
        }
        return "Room already exists in System";
    }
    public String changeSpeaker(Event event, String newSpeaker){
        // need to make a precondition that the strings represent speaker objects
        if(eventManager.freeSpeakerCheck(event.getEventTime(), newSpeaker)){
            eventManager.changeSpeaker(event, newSpeaker);
            attendeeManager.changeSpeaker(event.getTitle(), newSpeaker);
            return "Speaker changed successfully.";
        }
        return "Speaker is already booked at this time.";
    }
}
