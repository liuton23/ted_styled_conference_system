package Entities.EventComparators;

import Entities.Event;

import java.util.Comparator;

public class byTitleEventComparator implements Comparator<Event> {

    @Override
    public int compare(Event e1, Event e2) {
        String t1 = e1.getTitle();
        String t2 = e2.getTitle();
        return t1.compareTo(t2);
    }
}
