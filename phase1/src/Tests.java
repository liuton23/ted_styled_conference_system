import static org.junit.jupiter.api.Assertions.assertEquals;

import UseCases.EventManager;
import org.junit.jupiter.api.Test;
import Entities.Event;
import Entities.Attendee;
import Entities.Message;
import Entities.Room;
import Entities.Speaker;
import UseCases.*;
import UseCases.EventManager;

public class Tests{
    // some initial set up
    EventManager eventManagerA;
    public Tests(){
        this.eventManagerA = new EventManager();
        Room hall = new Room(1, 1000);
        Room auditorium = new Room(2, 500);
        Speaker principle = new Speaker("Principle Skinner", "Mother");
        Speaker caesar = new Speaker("Caesar Milan", "iLikeDogs");
        eventManagerA.createEvent("Holiday Dance", "Principle Skinner", 2015, "DECEMBER",
                20, 14, 0, 2);
        eventManagerA.createEvent("PetConference", "Caesar Milan", 2020, "JUNE", 2,
                12,0, 1);
    }

    //eventManager tests
    @Test
    public void eventManagerTests() {
        Tests testCase1 = new Tests();
        // assert statements
        assertEquals("Holiday Dance @ 2015-12-20 14:00:00 PM to 2015-12-20 15:00:00 PM in room: 2 with : Principle Skinner.", eventManagerA.getEvents().get(0).toString());
    }
}