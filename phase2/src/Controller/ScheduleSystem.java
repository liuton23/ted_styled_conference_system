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
     * @param capacity the capacity of the event.
     * @return an integer signaling that this SpeakerlessEvent may be created or a relevant message signifying why it shouldn't be.
     */
    private int scheduleSpeakerlessEventCheck(String title, int year, String month, int day, int hour, int minute,
                                             int room, int duration, int capacity){
        LocalTime startTime = LocalTime.of(hour, minute);
        LocalTime endTime = startTime.plusHours(duration);
        LocalDateTime startDateTime = LocalDateTime.of(year, Month.valueOf(month), day, hour, minute);
        LocalDateTime endDateTime = LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute);
        ArrayList<LocalDateTime> eventTime = new ArrayList<LocalDateTime>();
        eventTime.add(startDateTime);
        eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute));
        if(!roomManager.checkRoomInSystem(room)){
            //This room is not in the system.
            return 5;
        }
        Room tempRoom = roomManager.idToRoom(room);
        if(!roomManager.checkIfRoomAvailable(tempRoom, startDateTime, endDateTime)){
            //"Room is already booked for this timeslot."
            return 0 ;
        }
        else if(!eventManager.freeTitleCheck(title)){
            //"This event name has already been taken."
            return 2;
        }
        // TODO: add this check elsewhere
        else if(capacity > tempRoom.getCapacity()){
            //signifies that that's not allowed for this room
            return 100;
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
     * @param capacity the capacity of the event to be scheduled.
     * @return an integer signaling that a  SpeakerEvent may be created or a relevant message signifying why it shouldn't be.
     */
    private int scheduleSpeakerEventCheck(String title, ArrayList<String> speakerList, int year, String month, int day, int hour, int minute,
                                         int room, int duration, int capacity) {
        LocalTime startTime = LocalTime.of(hour, minute);
        LocalTime endTime = startTime.plusHours(duration);
        LocalDateTime startDateTime = LocalDateTime.of(year, Month.valueOf(month), day, hour, minute);
        LocalDateTime endDateTime = LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute);
        ArrayList<LocalDateTime> eventTime = new ArrayList<LocalDateTime>();
        eventTime.add(startDateTime);
        eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute));
        int speakerlessEventCheckValue = scheduleSpeakerlessEventCheck(title, year, month, day, hour, minute, room, duration, capacity);
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
     * @param capacity the capacity of the speaker event to be scheduled.
     * @return an integer signaling the successful creation of an event or a relevant error message.
     */
    public int scheduleSpeakerEvent(String title, ArrayList<String> speakerList, int year, String month, int day, int hour, int minute,
                                int room, int duration, int capacity){
        LocalTime startTime = LocalTime.of(hour, minute);
        LocalTime endTime = startTime.plusHours(duration);
        LocalDateTime startDateTime = LocalDateTime.of(year, Month.valueOf(month), day, hour, minute);
        LocalDateTime endDateTime = LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute);
        Room tempRoom = roomManager.idToRoom(room);
        int eventCheckValue = scheduleSpeakerEventCheck(title, speakerList, year, month, day, hour, minute, room, duration, capacity);
        if (eventCheckValue != 3) {
            return eventCheckValue;
        }
        for (String speakerName: speakerList) {
            Speaker speaker = (Speaker) userManager.usernameToUserObject(speakerName).get();
            userManager.addEventToSpeakerList(speaker, title);
        }
        roomManager.book(tempRoom, title, startDateTime, endDateTime);
        eventManager.createSpeakerEvent(title, speakerList, year, month, day, hour, minute, room, duration, capacity);
        //"Event successfully created."
        return 3;
    }

    /**
     * Helper method to create LocalDateTime objects from given values.
     * @param year the year of the first LocalDateTime.
     * @param month the month of the first LocalDateTime.
     * @param day the day of the first LocalDateTime.
     * @param hour the hour of the first LocalDateTime.
     * @param minute the minute of the first LocalDateTime.
     * @param duration the time in hours that the second LocalDateTime should be after the first.
     * @return an ArrayList of the two LocalDateTimes (start time and end time)
     */
    private ArrayList<LocalDateTime> dateTimeHelperMethod(int year, String month, int day, int hour, int minute, int duration){
        LocalTime startTime = LocalTime.of(hour, minute);
        LocalTime endTime = startTime.plusHours(duration);
        LocalDateTime startDateTime = LocalDateTime.of(year, Month.valueOf(month), day, hour, minute);
        LocalDateTime endDateTime = LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute);
        ArrayList<LocalDateTime> returnArray = new ArrayList<>();
        returnArray.add(startDateTime);
        returnArray.add(endDateTime);
        return returnArray;
    }

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
     * @param capacity the capacity of the speaker event to be scheduled.
     * @return an integer signaling the successful creation of an event or a relevant error message.
     */
    public int scheduleVIPSpeakerEvent(String title, ArrayList<String> speakerList, int year, String month, int day, int hour, int minute,
                                       int room, int duration, int capacity){
        ArrayList<LocalDateTime> timeArray = dateTimeHelperMethod(year, month, day, hour, minute, duration);
        LocalDateTime startDateTime = timeArray.get(0);
        LocalDateTime endDateTime = timeArray.get(1);
        int eventCheckValue = scheduleSpeakerEventCheck(title, speakerList, year, month, day, hour, minute, room, duration, capacity);
        if (eventCheckValue != 3) {
            return eventCheckValue;
        }
        Room tempRoom = roomManager.idToRoom(room);
        for (String speakerName: speakerList) {
            Speaker speaker = (Speaker) userManager.usernameToUserObject(speakerName).get();
            userManager.addEventToSpeakerList(speaker, title);
        }
        roomManager.book(tempRoom, title, startDateTime, endDateTime);
        eventManager.createVIPSpeakerEvent(title, speakerList, year, month, day, hour, minute, room, duration, capacity);
        //"Event successfully created."
        return 3;
    }
    public int scheduleVIPEvent(String title, int year, String month, int day, int hour, int minute,
                                int room, int duration, int capacity) {
        ArrayList<LocalDateTime> timeArray = dateTimeHelperMethod(year, month, day, hour, minute, duration);
        LocalDateTime startDateTime = timeArray.get(0);
        LocalDateTime endDateTime = timeArray.get(1);
        int eventCheckValue = scheduleSpeakerlessEventCheck(title, year, month, day, hour, minute, room, duration, capacity);
        if (eventCheckValue != 3) {
            return eventCheckValue;
        }
        Room tempRoom = roomManager.idToRoom(room);
        roomManager.book(tempRoom, title, startDateTime, endDateTime);
        eventManager.createVIPEvent(title, year, month, day, hour, minute, room, duration, capacity);
        //"Event successfully created."
        return 3;
    }

    public int scheduleSpeakerlessEvent(String title, int year, String month, int day, int hour, int minute,
                                int room, int duration, int capacity) {
        ArrayList<LocalDateTime> timeArray = dateTimeHelperMethod(year, month, day, hour, minute, duration);
        LocalDateTime startDateTime = timeArray.get(0);
        LocalDateTime endDateTime = timeArray.get(1);
        int eventCheckValue = scheduleSpeakerlessEventCheck(title, year, month, day, hour, minute, room, duration, capacity);
        if (eventCheckValue != 3) {
            return eventCheckValue;
        }
        Room tempRoom = roomManager.idToRoom(room);
        roomManager.book(tempRoom, title, startDateTime, endDateTime);
        eventManager.createSpeakerlessEvent(title, year, month, day, hour, minute, room, duration, capacity);
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
     * Method that adds a speaker to a speaker event or a VIP speaker event.
     * @param eventName the event the speaker is to be added to.
     * @param speakerName the username of the speaker to be added.
     * @return an integer message representing successful completion or an error.
     */
    public int addSpeaker(String eventName, String speakerName){
        if (!eventManager.nameToEvent(eventName).isPresent()) {
            // event name does not correspond to any event.
            return 4;
        }
        else if (!userManager.registeredSpeaker(speakerName)){
            // not a registered speaker
            return 5;
        }
        else if(!eventManager.getSpeakerEvents().containsKey(eventName)){
            //Event name doesn't correspond to an event with speakers.
            return 7;
        }
        else {
            SpeakerEvent eventObject = (SpeakerEvent) eventManager.nameToEvent(eventName).get();
            if (userManager.usernameToUserObject(speakerName).isPresent()) {
            // if object is speaker
                if (userManager.usernameToUserObject(speakerName).get() instanceof TalkAble) {
                // changing speaker
                    if (eventManager.freeSpeakerCheck(eventObject.getEventTime(), speakerName)) {
                        eventManager.addSpeaker(eventObject, speakerName);
                        userManager.addEventToSpeakerList(userManager.usernameToSpeakerObject(speakerName).get(), eventObject.getTitle());
                        //"Speaker added successfully."
                        //TODO: add to presenter
                        return 400;
                    }
                    // speaker is not free
                    return 1;
                }
            // user is not a speaker
            return 2;
            }
        //"One of the users does not exist."
        return 3;
        }
    }
    /**
     * Method that removes a speaker from a speaker event or a VIP speaker event.
     * @param eventName the event the speaker is to be added to.
     * @param speakerName the username of the speaker to be removed.
     * @return an integer message representing successful completion or an error.
     */
    public int removeSpeaker(String eventName, String speakerName){
        if (!eventManager.nameToEvent(eventName).isPresent()) {
            // event name does not correspond to any event.
            return 4;
        }
        else if (!userManager.registeredSpeaker(speakerName)){
            // not a registered speaker
            return 5;
        }
        else if(!eventManager.getSpeakerEvents().containsKey(eventName)){
            //Event name doesn't correspond to an event with speakers.
            return 7;
        }
        else {
            SpeakerEvent eventObject = (SpeakerEvent) eventManager.nameToEvent(eventName).get();
            if (userManager.usernameToUserObject(speakerName).isPresent()) {
                // if object is speaker
                if (userManager.usernameToUserObject(speakerName).get() instanceof TalkAble) {
                    // changing speaker
                    if (eventManager.getSpeakers(eventObject).size() != 1) {
                        eventManager.removeSpeaker(eventObject, speakerName);
                        userManager.removeEventFromSpeakerList(userManager.usernameToSpeakerObject(speakerName).get(), eventObject.getTitle());
                        //"Speaker removed successfully."
                        return 300;
                    }
                    // Cannot remove speaker a speaker event must have at least one speaker.
                    // TODO: add this message to presenter
                    return 500;
                }
                // user is not a speaker
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
                    presenter.displayMessages("requestRoom");
                    int roomId;
                    int roomCapacity;
                    Prompt intPrompt = promptBuilder.buildPrompt(presenter, PromptType.intPrompt);
                    roomId = intPrompt.intAsk();
                    presenter.displayMessages("requestCapacity");
                    Prompt capacityPrompt = promptBuilder.buildPrompt(presenter, PromptType.intAtLeastOnePrompt);
                    roomCapacity = capacityPrompt.intAsk();
                    schedulePresenter.printAddRoomMessage(addRoom(roomId,roomCapacity));
                    save();
                    break;
                case "C":
                    int index;
                    presenter.displayMessages("changeSpeaker");
                    ArrayList<Event> events = new ArrayList<Event>(eventManager.getSpeakerEvents().values());
                    schedulePresenter.displayAllEvents(events, "speaker");
                    presenter.displayMessages("requestCancelEvent");
                    Prompt indexPrompt = promptBuilder.buildPrompt(presenter, PromptType.intPrompt);
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
        PromptBuilder promptBuilder = new PromptBuilder();
        Prompt intPrompt = promptBuilder.buildPrompt(presenter, PromptType.intPrompt);
        presenter.displayMessages("requestYear");
        int year = intPrompt.intAsk();
        presenter.displayMessages("requestMonth");
        Prompt monthPrompt = promptBuilder.buildPrompt(presenter, PromptType.viewMonthMenu);
        String month = monthPrompt.ask();
        presenter.displayMessages("requestDay");
        Prompt dayPrompt = promptBuilder.buildPrompt(presenter, PromptType.intDayPrompt);
        int day = dayPrompt.intAsk();
        presenter.displayMessages("requestHour");
        Prompt hourPrompt = promptBuilder.buildPrompt(presenter, PromptType.intHourPrompt);
        int hour = hourPrompt.intAsk();
        presenter.displayMessages("requestMinute");
        Prompt minPrompt = promptBuilder.buildPrompt(presenter, PromptType.intMinutePrompt);
        int min = minPrompt.intAsk();
        presenter.displayMessages("requestDuration");
        int duration = intPrompt.intAsk();
        presenter.displayMessages("requestRoom");
        int roomID = intPrompt.intAsk();
        presenter.displayMessages("requestEventCapacity");
        int eventCapacity = intPrompt.intAsk();
        presenter.displayMessages("requestEventType");
        Prompt eventTypePrompt = promptBuilder.buildPrompt(presenter, PromptType.viewEventTypeMenu);
        String eventType = eventTypePrompt.ask();
        switch (eventType) {
            case "S": {
                ArrayList<String> speakers = scheduleSpeakerEventHelper();
                schedulePresenter.printScheduleEventMessage(scheduleSpeakerEvent(title, speakers, year, month, day,
                        hour, min, roomID, duration, eventCapacity));
                break;
            }
            case "V": {
                ArrayList<String> speakers = scheduleSpeakerEventHelper();
                schedulePresenter.printScheduleEventMessage(scheduleVIPSpeakerEvent(title, speakers, year, month, day,
                        hour, min, roomID, duration, eventCapacity));
                break;
            }
            case "I":
                schedulePresenter.printScheduleEventMessage(scheduleVIPEvent(title, year, month, day,
                        hour, min, roomID, duration, eventCapacity));

                break;
            case "E":
                schedulePresenter.printScheduleEventMessage(scheduleSpeakerlessEvent(title, year, month, day,
                        hour, min, roomID, duration, eventCapacity));
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
     * @param eventName Name of the event to be cancelled.
     * @return an integer value indicating success of this method, (0) if their is no such event in system and (1) for
     * successful completion.
     */
    public int cancelEvent(String eventName) {
        if (!eventManager.nameToEvent(eventName).isPresent()) {
            // event name does not correspond to an event.
            return 0;
        } else if(eventManager.getSpeakerEvents().containsKey(eventName)) {
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
