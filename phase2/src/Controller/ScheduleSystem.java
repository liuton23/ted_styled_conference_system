package Controller;


import Entities.*;
import Entities.Event;
import Entities.UserFactory.AttendAble;
import Entities.UserFactory.TalkAble;
import UseCases.EventManager;
import UseCases.UserManager;
import UseCases.RoomManager;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

/**
 * Controls scheduling tasks based on user input.
 */
public class ScheduleSystem extends Controller{
    private EventManager eventManager;// = new EventManager();
    private UserManager userManager;// = new UserManager();
    private RoomManager roomManager;// = new RoomManager();

    /**
     * Constructs an instance of ScheduleSystem with the provided EventManger, UserManager, and RoomManager.
     * @param eventManager the EventManager being manipulated.
     * @param userManager the UserManager being manipulated.
     * @param roomManager the RoomManager being manipulated.
     */
    public ScheduleSystem(EventManager eventManager, UserManager userManager, RoomManager roomManager){
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.roomManager = roomManager;
    }

    /**
     * This method schedules an event if all necessary conditions are satisfied and returns an integer representing
     * a successful event creation or a specific error message.
     * @param title the desired name for the event to be created.
     * @param speakerList the arrayList of usernames of the desired speakers for the event to be created.
     * @param year the desired starting year of the event to be created.
     * @param month the desired starting month of the event to be created.
     * @param day the desired starting day of the event to be created.
     * @param hour the desired starting hour of the event to be created.
     * @param minute the desired starting minute of the event to be created.
     * @param room the id of the desired room of the event to be created.
     * @param duration the length of time (in hours) of the event.
     * @return an integer signaling the successful creation of an event or a relevant error message.
     */
    public int scheduleSpeakerEvent(String title, ArrayList<String> speakerList, int year, String month, int day, int hour, int minute,
                                int room, int duration){
        LocalTime startTime = LocalTime.of(hour, minute);
        LocalTime endTime = startTime.plusHours(duration);
        LocalDateTime startDateTime = LocalDateTime.of(year, Month.valueOf(month), day, hour, minute);
        LocalDateTime endDateTime = LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute);
        ArrayList<LocalDateTime> eventTime = new ArrayList<LocalDateTime>();
        eventTime.add(startDateTime);
        eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute));
        Room tempRoom = roomManager.idToRoom(room);
        if(!roomManager.checkRoomInSystem(room)){
            //This room is not in the system.
            return 5;
        }
        else if(!roomManager.checkIfRoomAvailable(tempRoom, startDateTime, endDateTime)){
                //"Room is already booked for this timeslot."
                return 0 ;
        }
        else if(!eventManager.freeTitleCheck(title)){
            //"This event name has already been taken."
            return 2;
        }
        else {
            for (String speakerName: speakerList) {
                if(!userManager.registeredSpeaker(speakerName)){
                    //the username provided does not belong to a speaker in the system.
                    return 4;
                }
                else if(!eventManager.freeSpeakerCheck(eventTime, speakerName))
                    return 1;
            }
            for (String speakerName: speakerList) {
                Speaker sp = (Speaker) userManager.usernameToUserObject(speakerName).get();
                userManager.addEventToSpeakerList((TalkAble) sp, title);
            }
            roomManager.book(tempRoom, title, startDateTime, endDateTime);
            eventManager.createSpeakerEvent(title, speakerList, year, month, day, hour, minute, room, duration);
            //"Event successfully created."
            return 3;
        }
    }
    public int scheduleSpeakerlessEvent(String title, int year, String month, int day, int hour, int minute,
                                    int room, int duration){
        LocalTime startTime = LocalTime.of(hour, minute);
        LocalTime endTime = startTime.plusHours(duration);
        LocalDateTime startDateTime = LocalDateTime.of(year, Month.valueOf(month), day, hour, minute);
        LocalDateTime endDateTime = LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute);
        ArrayList<LocalDateTime> eventTime = new ArrayList<LocalDateTime>();
        eventTime.add(startDateTime);
        eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute));
        Room tempRoom = roomManager.idToRoom(room);
        if(!roomManager.checkRoomInSystem(room)){
            //This room is not in the system.
            return 5;
        }
        else if(!roomManager.checkIfRoomAvailable(tempRoom, startDateTime, endDateTime)){
            //"Room is already booked for this timeslot."
            return 0 ;
        }
        else if(!eventManager.freeTitleCheck(title)){
            //"This event name has already been taken."
            return 2;
        }
        else {
            roomManager.book(tempRoom, title, startDateTime, endDateTime);
            eventManager.createSpeakerlessEvent(title, year, month, day, hour, minute, room, duration);
            //"Event successfully created."
            return 3;
        }
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
     * @param eventName the name of the event whose speaker is to be changed.
     * @param newSpeaker the new desired speaker.
     * @return an integer signalling successful speaker change or a relevant error message.
     */
    public int changeSpeaker(String eventName, String newSpeaker, String oldSpeaker) {
        if (!eventManager.nameToEvent(eventName).isPresent()) {
            // event name does not correspond to any event.
            return 4;
        }
        else if (!userManager.registeredSpeaker(newSpeaker)){
            // not a registered speaker
            return 5;
        }
        else if(!eventManager.getSpeakerEvents().containsKey(eventName)){
            //Event name doesn't correspond to an event with speakers.
            return 7;
        }
        else {
            SpeakerEvent eventObject = (SpeakerEvent) eventManager.nameToEvent(eventName).get();
            // if the values are both not null
            if (userManager.usernameToUserObject(newSpeaker).isPresent() && userManager.usernameToUserObject(oldSpeaker).isPresent()) {
                // if object is speaker
                if (userManager.usernameToUserObject(newSpeaker).get() instanceof TalkAble && userManager.usernameToUserObject(oldSpeaker).get() instanceof TalkAble) {
                    // changing speaker
                    if (eventManager.freeSpeakerCheck(eventObject.getEventTime(), newSpeaker)) {
                        eventManager.changeSpeaker(eventObject, newSpeaker, oldSpeaker);
                        userManager.changeSpeaker(eventObject.getTitle(), newSpeaker, oldSpeaker);
                        //"Speaker changed successfully."
                        return 0;
                    }
                    // "Speaker is already booked at this time."
                    return 1;
                }
                //"At least one of these people are not a speaker."
                return 2;
            }
            //"One of the users does not exist."
            return 3;
        }
    }

    /**
     * Organizer only menu to schedule events, add rooms and change speakers.
     */
    public void scheduleActivity(){
        boolean scheduling = true;
        while (scheduling) {
            Scanner input = new Scanner(System.in);

            String chosen = askMenuInput(4);

            switch (chosen) {
                case "S":
                    scheduleSpeakerEvent();
                    save();
                    break;
                case "A":
                    presenter.displayMessages("requestAddRoom");
                    presenter.displayMessages("requestRoom");
                    int roomId;
                    int roomCapacity;
                    roomId = getIntInput();
                    presenter.displayMessages("requestCapacity");
                    roomCapacity = getIntInputGreaterThanEqualTo(1);
                    presenter.printAddRoomMessage(addRoom(roomId,roomCapacity));
                    save();
                    break;
                case "C":
                    int index;
                    presenter.displayMessages("changeSpeaker");
                    ArrayList<Event> events = new ArrayList<Event>(eventManager.getSpeakerEvents().values());
                    //events.sort(new bySpeakerEventComparator());
                    presenter.displayAllEvents(events, "speaker");
                    presenter.displayMessages("requestRoom");
                    index = getIntInput();
                    presenter.displayMessages("requestSpeaker");
                    // NEED TO ADD A REQUEST FOR THE OLD SPEAKER (vs the new speaker)
                    String newSpeaker = input.nextLine();
                    String oldSpeaker = input.nextLine();
                    String eventName = events.get(index - 1).getTitle();
                    int message = changeSpeaker(eventName,newSpeaker, oldSpeaker);
                    presenter.printChangeSpeakerMessage(message);
                    save();
                    break;
                case "B":
                    scheduling = false;
                    break;
            }
        }
    }

    /**
     * Method for organizers to input new event information.
     */
    private void scheduleSpeakerEvent(){
        Scanner input = new Scanner(System.in);
        presenter.displayMessages("S");
        String title = input.nextLine().trim();
        presenter.displayMessages("requestSpeaker");
        String speaker = input.nextLine();
        ArrayList<String> speakers = new ArrayList<String>();
        speakers.add(speaker);
        presenter.displayMessages("requestYear");
        int year = getIntInput();
        presenter.displayMessages("requestMonth"); //***********
        presenter.viewMonthsMenu();
        String month = askMenuInput(13); //input.nextLine().toUpperCase();
        presenter.displayMessages("requestDay");
        int day = getIntInputInRange(1, 31);
        presenter.displayMessages("requestHour");
        int hour = getIntInputInRange(0, 23);
        presenter.displayMessages("requestMinute");
        int min = getIntInputInRange(0, 59);
        presenter.displayMessages("requestDuration");
        int duration = getIntInput();
        presenter.displayMessages("requestRoom");
        int roomID = getIntInput();
        presenter.printScheduleEventMessage(scheduleSpeakerEvent(title, speakers, year, month, day,
                hour, min, roomID, duration));
    }

    /**
     * Makes sure user enters an int between start and end (inclusive)
     * @param start start of range
     * @param end end of range
     * @return the inputted int
     */
    private int getIntInputInRange(int start, int end) {
        boolean done = false;
        int in = 0;
        do {
            in = getIntInput();
            if (!(start <= in && in <= end)) {
                presenter.printInvalidIntRangeMessage(start, end);
            } else {
                done = true;
            }
        } while (!done);
        return in;
    }

    /**
     * Makes sure user enters an int greater than or equal to start
     * @param start start of range
     * @return the inputted int
     */
    private int getIntInputGreaterThanEqualTo(int start) {
        boolean done = false;
        int in = 0;
        do {
            in = getIntInput();
            if (!(start <= in)) {
                presenter.printInvalidIntRangeMessage(start);
            } else {
                done = true;
            }
        } while (!done);
        return in;
    }

//for PHASE 2
    public int cancelEvent(String eventName) {
        if (!eventManager.nameToEvent(eventName).isPresent()) {
            // event name does not correspond to an event.
            return 0;
        }
        else if(eventManager.getSpeakerEvents().containsKey(eventName)) {
            SpeakerEvent speakerEvent = eventManager.getSpeakerEvents().get(eventName);
            ArrayList<String> speakerUsernameList = eventManager.getSpeakers(speakerEvent);
            for (String speakerName : speakerUsernameList) {
                // these method types don't match... needs to be resolved in UserManager
                TalkAble speaker = (TalkAble) userManager.usernameToUserObject(speakerName).get();
                userManager.removeEventFromSpeakerList(speaker, eventName);
            }
        }
        Event event = eventManager.nameToEvent(eventName).get();
        eventManager.cancelEvent(event);
        Room room = roomManager.idToRoom(eventManager.getEventRoom(event));
        roomManager.unbook(room, eventName);
        //removing the event name from all of the attendee's lists
        for (String attendeeName : eventManager.eventToAttendees(event)) {
            User attendee = (User) userManager.usernameToUserObject(attendeeName).get();
            userManager.dropOut((AttendAble) attendee, eventName);
        }
        //event removed successfully.
        return 1;
        }
}
