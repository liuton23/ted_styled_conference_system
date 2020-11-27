package Entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An instance of this class represent an registered attendee at the tech conference.
 */
public class Attendee extends User implements Serializable{

    public Attendee(String username, String password) {
        super(username, password);
    }



    /*
    public static void main(String[] args) {
        Attendee a = new Attendee("Bill Nye", "billy", "science");
        Speaker s = new Speaker("Steve", "Steve1", "Hawking");
        a.addEvent("9:00");
        a.addEvent("8:00");
        System.out.println(a.getItinerary());
        a.removeEvent("9:00");
        System.out.println(a.getItinerary());
        s.addEvent("9:00");
        s.addEvent("8:00");
        System.out.println(s.getItinerary());
        s.removeEvent("9:00");
        System.out.println(s.getItinerary());
    }

     */
}
