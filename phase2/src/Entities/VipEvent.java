package Entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * An instance of this class represents a speakerless event only for VIP attendees.
 */
public class VipEvent extends Event implements VipOnly{
    /**
     * Constructs an event that only allows VIP attendees.
     * @param title name of the event.
     * @param year year of event.
     * @param month month of event.
     * @param day day of event.
     * @param hour hour of event.
     * @param minute minute of event.
     * @param room room ID.
     * @param duration duration of event (hours).
     * @param capacity the capacity of this event.
     */
    public VipEvent(String title, int year, String month, int day, int hour, int minute, int room, int duration, int capacity) {
    super(("VIP" + title), year, month, day, hour, minute, room, duration, capacity);
    }
}
