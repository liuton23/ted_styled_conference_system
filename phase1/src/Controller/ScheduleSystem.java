package Controller;


import Entities.Attendee;
import Entities.Event;
import Entities.Speaker;
import UseCases.EventManager;
import UseCases.AttendeeManager;
import UseCases.RoomManager;

import java.util.Optional;

/**
 * Controls scheduling tasks based on user input.
 */
public class ScheduleSystem {
    private EventManager eventManager = new EventManager();
    private AttendeeManager attendeeManager = new AttendeeManager();
    private RoomManager roomManager = new RoomManager();

    /**
     * Constructs an instance of ScheduleSystem with the provided EventManger, AttendeeManager, and RoomManager.
     * @param eventManager the EventManager being manipulated.
     * @param attendeeManager the AttendeeManager being manipulated.
     * @param roomManager the RoomManager being manipulated.
     */
    public ScheduleSystem(EventManager eventManager, AttendeeManager attendeeManager, RoomManager roomManager){
        this.eventManager = eventManager;
        this.attendeeManager = attendeeManager;
        this.roomManager = roomManager;
    }

    /**
     * This method schedules an event if all necessary conditions are satisfied and returns an integer representing
     * a successful event creation or a specific error message.
     * @param title the desired name for the event to be created.
     * @param speaker the username of the desired speaker for the event to be created.
     * @param year the desired starting year of the event to be created.
     * @param month the desired starting month of the event to be created.
     * @param day the desired starting day of the event to be created.
     * @param hour the dessired starting hour of the event to be created.
     * @param minute the desired starting minute of the event to be created.
     * @param room the id of the desired room of the event to be created.
     * @param capacity
     * @return an integer signaling the successful creation of an event or a relevant error message.
     */
    public int scheduleEvent(String title, String speaker, int year, String month, int day, int hour, int minute,
                                int room, int capacity){
        return eventManager.createEvent(title, speaker, year, month, day, hour, minute, room, capacity);
    }

    /**
     * This method adds a room to the system if the room is not already present in the system.
     * @param roomId the id of the room to be added.
     * @param capacity the capacity of the room to be added to the system.
     * @return an integer (0) if the room was added or (1) if the room already exists in the system.
     */
    public int addRoom(int roomId, int capacity){
        if(roomManager.addRoom(roomId, capacity)){
            //"Room successfully added."
            return 0;
        }
        //"Room already exists in System"
        return 1;
    }

    /**
     * This method changes the speaker of the given event if all of the following conditions are satisfied:
     * 1) the string provided is a Speaker
     * 2) the Speaker is available at the time of the event
     * and reports successful completion or an error message through an integer return value (0 being successful change).
     * @param event the event whose speaker is to be changed.
     * @param newSpeaker the new desired speaker.
     * @return an integer signalling successful speaker change or a relevant error message.
     */
    public int changeSpeaker(Event event, String newSpeaker){
        // if the value is not null
        if(attendeeManager.usernameToAttendeeObject(newSpeaker).isPresent()) {
            // if object is speaker
            if (attendeeManager.usernameToAttendeeObject(newSpeaker).get() instanceof Speaker) {
                // changing speaker
                if (eventManager.freeSpeakerCheck(event.getEventTime(), newSpeaker)) {
                    eventManager.changeSpeaker(event, newSpeaker);
                    attendeeManager.changeSpeaker(event.getTitle(), newSpeaker);
                    //"Speaker changed successfully."
                    return 0;
                }
                // "Speaker is already booked at this time."
                return 1;
            }
            //"This person is not a speaker."
            return 2;
        }
        //"This user does not exist."
        return 3;
    }
}
