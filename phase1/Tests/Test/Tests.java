package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Controller.ScheduleSystem;
import Entities.EventComparators.byTimeEventComparator;
import UseCases.EventManager;
import org.junit.jupiter.api.Test;
import UseCases.*;
import Controller.*;

public class Tests{
    // some initial set up
    EventManager eventManagerA;
    AttendeeManager attendeeManagerA;
    RoomManager roomManagerA;
    ScheduleSystem scheduleSystemA;
    Controller controllerA;
    MessageManager messageManagerA;
    public Tests(){
        this.eventManagerA = new EventManager();
        this.attendeeManagerA = new AttendeeManager();
        this.roomManagerA = new RoomManager();
        this.scheduleSystemA = new ScheduleSystem(eventManagerA, attendeeManagerA, roomManagerA);
        this.controllerA = new Controller();
        roomManagerA.addRoom(1, 1000);
        roomManagerA.addRoom(2, 500);
        roomManagerA.addRoom(3, 200);
        attendeeManagerA.createSpeaker("Principle Skinner", "Mother");
        attendeeManagerA.createSpeaker("Caesar Milan", "iLikeDogs");
        attendeeManagerA.createSpeaker("Caesar Milan", "iLikeDogs");
        attendeeManagerA.createSpeaker("Martha Stewart", "bread");
        eventManagerA.createEvent("Holiday Dance", "Principle Skinner", 2015, "DECEMBER",
                20, 14, 0, 2);
        eventManagerA.createEvent("PetConference", "Caesar Milan", 2020, "JUNE", 2,
                12,0, 1);
        // event name in use
        eventManagerA.createEvent("PetConference", "Martha Stewart", 2020, "JUNE", 2,
                12,0, 1);
        // speaker busy
        eventManagerA.createEvent("Bake Off", "Principle Skinner", 2015, "DECEMBER", 20,
                14,0, 1);
        // room in use
        eventManagerA.createEvent("Bake Off", "Martha Stewart", 2015, "DECEMBER", 20,
                14,0, 2);
    }

    //eventManager tests
    @Test
    public void eventManagerTests() {
        Tests testCase1 = new Tests();
        // assert statements
        //testing createEvent method BASIC
        assertEquals("Holiday Dance @ 2015-12-20 14:00:00 PM to 2015-12-20 15:00:00 PM in room: 2 with : Principle Skinner.", eventManagerA.getEvents().get(0).toString());
        assertEquals("PetConference @ 2020-06-02 12:00:00 PM to 2020-06-02 13:00:00 PM in room: 1 with : Caesar Milan.", eventManagerA.getEvents().get(1).toString());
        //testing that doesn't create even when the speaker is not free, room is not free, or title is not free
        assertEquals(eventManagerA.getEvents().size(), 5);
    }
    @Test
    public void scheduleSystemTests(){
        Tests testcase2 = new Tests();

        // testing scheduleEvent

        //should give speaker not in system error
        assertEquals(4 , scheduleSystemA.scheduleEvent("Cook off", "Guy", 2020, "JUNE", 20, 12, 0, 1));
        attendeeManagerA.createSpeaker("Guy", "food");
        assertEquals(3 , scheduleSystemA.scheduleEvent("Cook off", "Guy", 2020, "JUNE", 20, 12, 0, 1));

        System.out.println(scheduleSystemA.scheduleEvent("Fake event", "Martha Stewart", 2015, "AUGUST", 2, 14, 0, 3));
        // should give room unavailable error (0)
        assertEquals(0 , scheduleSystemA.scheduleEvent("Pot roast", "Guy", 2015, "AUGUST",
                2, 14, 0, 3));

        // should give speaker unavailable error
        assertEquals(1, scheduleSystemA.scheduleEvent("BadEvent", "Martha Stewart", 2015, "DECEMBER", 20, 14, 2, 1));
    }
    @Test
    public void signUpSystemTests(){
        Tests testcase3 = new Tests();
        testcase3.attendeeManagerA.createAttendee("Jim","123", false);
        SignUpSystem sus = new SignUpSystem(testcase3.attendeeManagerA, testcase3.eventManagerA, testcase3.roomManagerA);
        sus.setComparator(new byTimeEventComparator());
        System.out.println(sus.viewAllEvents());
        sus.signUpEvent("Jim", 1);
        System.out.println(testcase3.attendeeManagerA.getItinerary(testcase3.attendeeManagerA.usernameToAttendeeObject("Jim").get()));
        assertEquals("Holiday Dance", testcase3.attendeeManagerA.getItinerary(testcase3.attendeeManagerA.usernameToAttendeeObject("Jim").get()).get(0));
    }
}