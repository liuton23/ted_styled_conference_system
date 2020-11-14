package Entities.EventComparators;

import Entities.Event;

import java.util.Comparator;

public class bySpeakerEventComparator implements Comparator<Event> {

    @Override
    public int compare(Event e1, Event e2) {
        String s1 = e1.getSpeaker();
        String s2 = e2.getSpeaker();
        return s1.compareTo(s2);
    }
}
