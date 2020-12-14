package Entities.EventComparators;

import Entities.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * An instance of this class implements the Comparator interface for the sorting of events by time.
 */
public class byTimeEventComparator implements Comparator<Event>{

    /**
     * Compares two events by time
     * @param e1 first event
     * @param e2 second event
     * @return and int comparing the two events
     */
    @Override
    public int compare(Event e1, Event e2) {
        ArrayList<LocalDateTime> slot1 = e1.getEventTime();
        ArrayList<LocalDateTime> slot2 = e2.getEventTime();
        LocalDateTime time1 = slot1.get(0);
        LocalDateTime time2 = slot2.get(0);
        return time1.compareTo(time2);
    }
}
