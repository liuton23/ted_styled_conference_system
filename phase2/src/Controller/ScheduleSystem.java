package Controller;


import Controller.PromptBuilder.Prompt;
import Controller.PromptBuilder.PromptBuilder;
import Controller.PromptBuilder.PromptType;
import Entities.*;
import Entities.Event;
import Entities.UserFactory.AttendAble;
import Entities.UserFactory.TalkAble;
import Presenter.SchedulePresenter;
import UseCases.EventManager;
import UseCases.UserManager;
import UseCases.RoomManager;

import java.io.IOException;
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

    //Private?
    public SchedulePresenter schedulePresenter = new SchedulePresenter();

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
     * This method checks if creating this SpeakerlessEvent would violate a rule of the TechConference. It returns an integer value
     * signifying if this event is allowed to be created or a value signifying why it shouldn't be.
     * @param title the desired name for the event to be created.
     * @param year the desired starting year of the event to be created.
     * @param month the desired starting month of the event to be created.
     * @param day the desired starting day of the event to be created.
     * @param hour the desired starting hour of the event to be created.
     * @param minute the desired starting minute of the event to be created.
     * @param room the id of the desired room of the event to be created.
     * @param duration the length of time (in hours) of the event.
     * @return an integer signaling that this SpeakerlessEvent may be created or a relevant message signifying why it shouldn't be.
     */
    private int scheduleSpeakerlessEventCheck(String title, int year, String month, int day, int hour, int minute,
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
        return 3;
    }

    /**
     * This method checks if creating this SpeakerEvent would violate a rule of the TechConference. It returns an integer value
     * signifying if this event is allowed to be created or a value signifying why it shouldn't be.
     * @param title the desired name for the event to be created.
     * @param speakerList the arrayList of usernames of the desired speakers for the event to be created.
     * @param year the desired starting year of the event to be created.
     * @param month the desired starting month of the event to be created.
     * @param day the desired starting day of the event to be created.
     * @param hour the desired starting hour of the event to be created.
     * @param minute the desired starting minute of the event to be created.
     * @param room the id of the desired room of the event to be created.
     * @param duration the length of time (in hours) of the event.
     * @return an integer signaling that a  SpeakerEvent may be created or a relevant message signifying why it shouldn't be.
     */
    private int scheduleSpeakerEventCheck(String title, ArrayList<String> speakerList, int year, String month, int day, int hour, int minute,
                                         int room, int duration) {
        LocalTime startTime = LocalTime.of(hour, minute);
        LocalTime endTime = startTime.plusHours(duration);
        LocalDateTime startDateTime = LocalDateTime.of(year, Month.valueOf(month), day, hour, minute);
        LocalDateTime endDateTime = LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute);
        ArrayList<LocalDateTime> eventTime = new ArrayList<LocalDateTime>();
        eventTime.add(startDateTime);
        eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute));
        Room tempRoom = roomManager.idToRoom(room);
        int speakerlessEventCheckValue = scheduleSpeakerlessEventCheck(title, year, month, day, hour, minute, room, duration);
        if (speakerlessEventCheckValue != 3){
            return speakerlessEventCheckValue;
        }
        else {
            for (String speakerName : speakerList) {
                if (!userManager.registeredSpeaker(speakerName)) {
                    //the username provided does not belong to a speaker in the system.
                    return 4;
                } else if (!eventManager.freeSpeakerCheck(eventTime, speakerName))
                    return 1;
            }
            //"Event is ready to be created."
            return 3;
        }
    }

    /**
     * This method schedules a SpeakerEvent if all necessary conditions are satisfied and returns an integer representing
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
        Room tempRoom = roomManager.idToRoom(room);
        int eventCheckValue = scheduleSpeakerEventCheck(title, speakerList, year, month, day, hour, minute, room, duration);
        if (eventCheckValue != 3) {
            return eventCheckValue;
        }
        for (String speakerName: speakerList) {
            Speaker speaker = (Speaker) userManager.usernameToUserObject(speakerName).get();
            userManager.addEventToSpeakerList(speaker, title);
        }
        roomManager.book(tempRoom, title, startDateTime, endDateTime);
        eventManager.createSpeakerEvent(title, speakerList, year, month, day, hour, minute, room, duration);
        //"Event successfully created."
        return 3;
    }

    // TODO: add helper to deal with the localtime repetitive code
    /**
     * This method schedules a VIPSpeakerEvent if all necessary conditions are satisfied and returns an integer representing
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
    public int scheduleVIPSpeakerEvent(String title, ArrayList<String> speakerList, int year, String month, int day, int hour, int minute,
                                       int room, int duration){
        LocalTime startTime = LocalTime.of(hour, minute);
        LocalTime endTime = startTime.plusHours(duration);
        LocalDateTime startDateTime = LocalDateTime.of(year, Month.valueOf(month), day, hour, minute);
        LocalDateTime endDateTime = LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute);
        Room tempRoom = roomManager.idToRoom(room);
        int eventCheckValue = scheduleSpeakerEventCheck(title, speakerList, year, month, day, hour, minute, room, duration);
        if (eventCheckValue != 3) {
            return eventCheckValue;
        }
        for (String speakerName: speakerList) {
            Speaker speaker = (Speaker) userManager.usernameToUserObject(speakerName).get();
            userManager.addEventToSpeakerList(speaker, title);
        }
        roomManager.book(tempRoom, title, startDateTime, endDateTime);
        eventManager.createVIPSpeakerEvent(title, speakerList, year, month, day, hour, minute, room, duration);
        //"Event successfully created."
        return 3;
    }
    public int scheduleVIPEvent(String title, int year, String month, int day, int hour, int minute,
                                int room, int duration) {
        LocalTime startTime = LocalTime.of(hour, minute);
        LocalTime endTime = startTime.plusHours(duration);
        LocalDateTime startDateTime = LocalDateTime.of(year, Month.valueOf(month), day, hour, minute);
        LocalDateTime endDateTime = LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute);
        Room tempRoom = roomManager.idToRoom(room);
        int eventCheckValue = scheduleSpeakerlessEventCheck(title, year, month, day, hour, minute, room, duration);
        if (eventCheckValue != 3) {
            return eventCheckValue;
        }
        roomManager.book(tempRoom, title, startDateTime, endDateTime);
        eventManager.createVIPEvent(title, year, month, day, hour, minute, room, duration);
        //"Event successfully created."
        return 3;
    }

    public int scheduleSpeakerlessEvent(String title, int year, String month, int day, int hour, int minute,
                                int room, int duration) {
        LocalTime startTime = LocalTime.of(hour, minute);
        LocalTime endTime = startTime.plusHours(duration);
        LocalDateTime startDateTime = LocalDateTime.of(year, Month.valueOf(month), day, hour, minute);
        LocalDateTime endDateTime = LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute);
        Room tempRoom = roomManager.idToRoom(room);
        int eventCheckValue = scheduleSpeakerlessEventCheck(title, year, month, day, hour, minute, room, duration);
        if (eventCheckValue != 3) {
            return eventCheckValue;
        }
        roomManager.book(tempRoom, title, startDateTime, endDateTime);
        eventManager.createSpeakerlessEvent(title, year, month, day, hour, minute, room, duration);
        //"Event successfully created."
        return 3;
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
    public void scheduleActivity() throws IOException {
        boolean scheduling = true;
        while (scheduling) {
            Scanner input = new Scanner(System.in);

            //String chosen = askMenuInput(4);
            PromptBuilder promptBuilder = new PromptBuilder();
            Prompt organizerPrompt = promptBuilder.buildPrompt(presenter, PromptType.organizerMenu);
            String chosen = organizerPrompt.ask();

            switch (chosen) {
                case "S":
                    scheduleEvent();
                    save();
                    break;
                case "A":
                    presenter.displayMessages("requestAddRoom");
                    presenter.displayMessages("requestRoom");
                    int roomId;
                    int roomCapacity;
                    //roomId = getIntInput();
                    Prompt intPrompt = promptBuilder.buildPrompt(presenter, PromptType.intPrompt);
                    intPrompt.setText("Enter room ID");
                    roomId = intPrompt.intAsk();
                    presenter.displayMessages("requestCapacity");
                    Prompt capacityPrompt = promptBuilder.buildPrompt(presenter, PromptType.intAtLeastOnePrompt);
                    //roomCapacity = getIntInputGreaterThanEqualTo(1);
                    roomCapacity = capacityPrompt.intAsk();
                    schedulePresenter.printAddRoomMessage(addRoom(roomId,roomCapacity));
                    save();
                    break;
                case "C":
                    int index;
                    presenter.displayMessages("changeSpeaker");
                    ArrayList<Event> events = new ArrayList<Event>(eventManager.getSpeakerEvents().values());
                    //events.sort(new bySpeakerEventComparator());
                    schedulePresenter.displayAllEvents(events, "speaker");
                    presenter.displayMessages("requestRoom");
                    //index = getIntInput();
                    Prompt indexPrompt = promptBuilder.buildPrompt(presenter, PromptType.intPrompt);
                    indexPrompt.setText("Enter room index:");
                    index = indexPrompt.intAsk();
                    presenter.displayMessages("requestNewSpeaker");
                    String newSpeaker = input.nextLine();
                    presenter.displayMessages("requestOldSpeaker");
                    String oldSpeaker = input.nextLine();
                    String eventName = events.get(index - 1).getTitle();
                    int message = changeSpeaker(eventName,newSpeaker, oldSpeaker);
                    schedulePresenter.printChangeSpeakerMessage(message);
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
    private void scheduleEvent() throws IOException {
        Scanner input = new Scanner(System.in);
        presenter.displayMessages("S");
        String title = input.nextLine().trim();
        presenter.displayMessages("requestYear");
        //int year = getIntInput();
        PromptBuilder promptBuilder = new PromptBuilder();
        Prompt intPrompt = promptBuilder.buildPrompt(presenter, PromptType.intPrompt);
        intPrompt.setText("Please enter a year (e.g. 20XX):");
        int year = intPrompt.intAsk();
        presenter.displayMessages("requestMonth"); //***********
        //presenter.viewMonthsMenu();
        //String month = askMenuInput(13); //input.nextLine().toUpperCase();

        Prompt monthPrompt = promptBuilder.buildPrompt(presenter, PromptType.viewMonthMenu);
        String month = monthPrompt.ask();
        presenter.displayMessages("requestDay");
        //int day = getIntInputInRange(1, 31);
        Prompt dayPrompt = promptBuilder.buildPrompt(presenter, PromptType.intDayPrompt);
        int day = dayPrompt.intAsk();
        presenter.displayMessages("requestHour");
        //int hour = getIntInputInRange(0, 23);
        Prompt hourPrompt = promptBuilder.buildPrompt(presenter, PromptType.intHourPrompt);
        int hour = hourPrompt.intAsk();
        presenter.displayMessages("requestMinute");
        // TODO: minute input keeps glitching on me...
        //int min = getIntInputInRange(0, 59);
        Prompt minPrompt = promptBuilder.buildPrompt(presenter, PromptType.intMinutePrompt);
        int min = minPrompt.intAsk();
        presenter.displayMessages("requestDuration");
        //int duration = getIntInput();
        intPrompt.setText("Please enter length of event (hours):");
        int duration = intPrompt.intAsk();
        presenter.displayMessages("requestRoom");
        //int roomID = getIntInput();
        intPrompt.setText("Please enter room ID:");
        int roomID = intPrompt.intAsk();
        // input the options etc in controller and presenter
        presenter.displayMessages("requestEventType");
        //String eventType = askMenuInput(17);
        Prompt eventTypePrompt = promptBuilder.buildPrompt(presenter, PromptType.viewEventTypeMenu);
        String eventType = eventTypePrompt.ask();
        switch (eventType) {
            case "S": {
                ArrayList<String> speakers = scheduleSpeakerEventHelper();
                schedulePresenter.printScheduleEventMessage(scheduleSpeakerEvent(title, speakers, year, month, day,
                        hour, min, roomID, duration));
                break;
            }
            case "V": {
                ArrayList<String> speakers = scheduleSpeakerEventHelper();
                schedulePresenter.printScheduleEventMessage(scheduleVIPSpeakerEvent(title, speakers, year, month, day,
                        hour, min, roomID, duration));
                break;
            }
            case "I":
                schedulePresenter.printScheduleEventMessage(scheduleVIPEvent(title, year, month, day,
                        hour, min, roomID, duration));

                break;
            case "E":
                schedulePresenter.printScheduleEventMessage(scheduleSpeakerlessEvent(title, year, month, day,
                        hour, min, roomID, duration));
                break;
        }
    }

    /**
     * Helper method to create events with speakers.
     * @return names of speakers
     */
    private ArrayList<String> scheduleSpeakerEventHelper(){
        Scanner input = new Scanner(System.in);
        presenter.displayMessages("requestSpeaker");
        String speaker = input.nextLine();
        ArrayList<String> speakers = new ArrayList<String>();
        speakers.add(speaker);
        String additionalInput = "Yes";
        while (!additionalInput.equals("No")) {
            presenter.displayMessages("requestAdditionalSpeaker");
            additionalInput = input.nextLine();
            if (!additionalInput.equals("No")) {
                speakers.add(additionalInput);
            }
        }
        return speakers;
    }

    /**
     * Makes sure user enters an int between start and end (inclusive)
     * @param start start of range
     * @param end end of range
     * @return the inputted int
     */
    private int getIntInputInRange(int start, int end) {
        boolean done = false;
        int in;
        do {
            in = getIntInput();
            if (start <= in && in <= end) {
                done = true;
            } else {
                schedulePresenter.printInvalidIntRangeMessage(start, end);
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
        int in;
        do {
            in = getIntInput();
            if (start <= in) {
                done = true;
            } else {
                schedulePresenter.printInvalidIntRangeMessage(start);
            }
        } while (!done);
        return in;
    }

    /**
     * @param eventName Name of the event to be cancelled.
     * @return an integer value indicating success of this method, (0) if their is no such event in system and (1) for
     * successful completion.
     */
    public int cancelEvent(String eventName) {
        if (!eventManager.nameToEvent(eventName).isPresent()) {
            // event name does not correspond to an event.
            return 0;
        }
        else if(eventManager.getSpeakerEvents().containsKey(eventName)) {
            SpeakerEvent speakerEvent = eventManager.getSpeakerEvents().get(eventName);
            ArrayList<String> speakerUsernameList = eventManager.getSpeakers(speakerEvent);
            for (String speakerName : speakerUsernameList) {
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
            User attendee = userManager.usernameToUserObject(attendeeName).get();
            userManager.dropOut((AttendAble) attendee, eventName);
        }
        //event removed successfully.
        return 1;
        }
}
