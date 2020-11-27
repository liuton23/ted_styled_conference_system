package Entities.EventComparators;

import Entities.Event;

import java.util.Comparator;

/**
 * An instance of this class implements the Comparator interface for the sorting of events by name.
 */
public class byTitleEventComparator implements Comparator<Event> {

    @Override
    /**
     * Comparator to sort an iterable collection events by event name.
     */
    public int compare(Event e1, Event e2) {
        String t1 = e1.getTitle();
        String t2 = e2.getTitle();
        return t1.compareTo(t2);
    }
}
