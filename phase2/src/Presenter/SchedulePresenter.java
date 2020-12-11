package Presenter;

import Entities.Event;

import java.util.ArrayList;

/**
 * Displays messages relevant for scheduling events.
 */
public class SchedulePresenter{
    /**
     * Displays message reflecting whether an event was successfully created and if not what the issue is.
     * @param scheduleEventMessage is the output of the ScheduleSystem.createEvent() method.
     */
    public void printScheduleEventMessage(int scheduleEventMessage) {
        switch (scheduleEventMessage) {
            case 0:
                System.out.println("Room is already booked for this timeslot.");
                break;
            case 1:
                System.out.println("Speaker is already booked for this timeslot.");
                break;
            case 2:
                System.out.println("This event name has already been taken.");
                break;
            case 3:
                System.out.println("Event successfully created.");
                break;
            case 4:
                System.out.println("The username provided does not match a speaker in the system.");
                break;
            case 5:
                System.out.println("This room is not in the system.");
                break;
        }
    }
    /**
     * Displays message reflecting whether a speaker was successfully changed.
     * @param changeSpeakerOutput output of the ScheduleSystem.changeSpeaker() method.
     */
    public void printChangeSpeakerMessage(int changeSpeakerOutput){
        switch (changeSpeakerOutput) {
            case 0:
                System.out.println("Speaker changed successfully.");
                break;
            case 1:
                System.out.println("Speaker is already booked at this time.");
                break;
            case 2:
                System.out.println("This person is not a speaker.");
                break;
            case 3:
                System.out.println("This user does not exist.");
                break;
            case 4:
                System.out.println("This event name does not correspond to an event in the system.");
                break;
            case 5:
                System.out.println("The username provided does not match a speaker in the system.");
                break;
        }
    }
    /**
     * Displays message reflecting whether a room was successfully added or a message indicating error.
     * @param addRoomMessage output from the ScheduleSystem.addRoom() method
     */
    public void printAddRoomMessage(int addRoomMessage) {
        switch (addRoomMessage) {
            case 0:
                System.out.println("Room successfully added.");
                break;
            case 1:
                System.out.println("Room already exists in System.");
                break;
        }
    }

    /**
     * Displays an initial message then displays a list of Strings one at a time by speakers
     * @param events list of events
     */
    public void displayAllEvents(ArrayList<Event> events, String way){
        switch(way){
            case "time":
                System.out.println("Events sorted by time");
                break;
            case "name":
                System.out.println("Events sorted by name");
                break;
            case "speaker":
                System.out.println("Events sorted by speaker");
                break;
        }
        for (Event e: events){
            System.out.println(e);
        }
    }




}
