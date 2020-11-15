package Controller;


import Entities.Attendee;
import Entities.Event;
import Entities.Speaker;
import UseCases.EventManager;
import UseCases.AttendeeManager;
import UseCases.RoomManager;

import java.util.Optional;

public class ScheduleSystem {
    EventManager eventManager = new EventManager();
    AttendeeManager attendeeManager = new AttendeeManager();
    RoomManager roomManager = new RoomManager();

    public ScheduleSystem(EventManager eventmanager, AttendeeManager attendeeManager, RoomManager roomManager){
        this.eventManager = eventmanager;
        this.attendeeManager = attendeeManager;
        this.roomManager = roomManager;
    }
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
        // if the value is not null
        if(attendeeManager.usernameToAttendeeObject(newSpeaker).isPresent()) {
            // if object is speaker
            if (attendeeManager.usernameToAttendeeObject(newSpeaker).get() instanceof Speaker) {
                // changing speaker
                if (eventManager.freeSpeakerCheck(event.getEventTime(), newSpeaker)) {
                    eventManager.changeSpeaker(event, newSpeaker);
                    attendeeManager.changeSpeaker(event.getTitle(), newSpeaker);
                    return "Speaker changed successfully.";
                }
                return "Speaker is already booked at this time.";
            }
            return "This person is not a speaker.";
        }
        return "This user does not exist.";
    }
}