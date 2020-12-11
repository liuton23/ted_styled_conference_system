package Entities.EventComparators;

import Entities.Event;
import Entities.SpeakerEvent;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * An instance of this class implements the Comparator interface for the sorting of events by speaker name.
 */
public class bySpeakerEventComparator implements Comparator<Event> {

    @Override
    /**
     * Comparator to sort an iterable collection events by speaker name.
     */
    public int compare(Event e1, Event e2) {
        if (e1 instanceof SpeakerEvent && !(e2 instanceof SpeakerEvent)){
            return -1;
        }
        if (!(e1 instanceof SpeakerEvent) && e2 instanceof SpeakerEvent){
            return 1;
        }
        if (e1 instanceof SpeakerEvent && e2 instanceof SpeakerEvent){
            ArrayList<String> s1 = ((SpeakerEvent) e1).getSpeaker();
            ArrayList<String> s2 = ((SpeakerEvent) e2).getSpeaker();
            if (s1.equals(s2)){
                return e1.getTitle().compareTo(e2.getTitle());
            }
            return s1.get(0).compareTo(s2.get(0));
        } else {
            return e1.getTitle().compareTo(e2.getTitle());
        }
    }
}
