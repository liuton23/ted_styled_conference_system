package Entities;

import java.util.ArrayList;

/**
 * An instance of this class represents a VIP only event with speakers.
 */
public class VipSpeakerEvent extends SpeakerEvent implements VipOnly{

    /**
     * Constructs a speaker event that only allows VIP attendees.
     * @param title name of the event.
     * @param speaker name of speaker(s).
     * @param year year of event.
     * @param month month of event.
     * @param day day of event.
     * @param hour hour of event.
     * @param minute minute of event.
     * @param room room ID.
     * @param duration duration of event (hours).
     * @param capacity the capacity of this event.
     */
    public VipSpeakerEvent(String title, ArrayList<String> speaker, int year, String month, int day, int hour, int minute, int room, int duration, int capacity){
        super(("VIP" + title), speaker, year, month, day, hour, minute, room, duration, capacity);
    }
}
