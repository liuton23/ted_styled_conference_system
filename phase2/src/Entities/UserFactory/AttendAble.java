package Entities.UserFactory;

import java.util.ArrayList;

public interface AttendAble {
    void addEvent(String event);
    void removeEvent(String event);
    ArrayList<String> getItinerary();
}
