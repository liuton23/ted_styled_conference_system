package Entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * An instance of this class represents a speakerless event only for VIP attendees.
 */
public class VipEvent extends Event implements VipOnly{
        public VipEvent(String title, int year, String month, int day, int hour, int minute, int room, int duration) {
        super(("VIP" + title), year, month, day, hour, minute, room, duration);
    }
}
