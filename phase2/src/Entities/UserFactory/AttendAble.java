package Entities.UserFactory;

import java.util.ArrayList;

/**
 * Identifies a user that can attend events.
 */
public interface AttendAble extends Account{
    void addEvent(String event);
    void removeEvent(String event);
    ArrayList<String> getItinerary();
}
