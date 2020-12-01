package Entities;

import java.util.ArrayList;

/**
 * An instance of this class represents a VIP only event with speakers.
 */
public class VipSpeakerEvent extends SpeakerEvent implements VipOnly{
    public VipSpeakerEvent(String title, ArrayList<String> speaker, int year, String month, int day, int hour, int minute, int room, int duration){
        super(("VIP" + title), speaker, year, month, day, hour, minute, room, duration);
    }
}
