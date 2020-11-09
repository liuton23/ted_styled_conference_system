package UseCases;

import Entities.Attendee;

import java.io.Serializable;
import java.util.ArrayList;

public class AttendeeManager implements Serializable {

    ArrayList<Attendee> AttendeeList;

    public AttendeeManager(){
        AttendeeList = new ArrayList<Attendee>();
    }

    public Attendee createAttendee(String name, String username, String password){
        return new Attendee(name, username, password);
    }

    public void add(Attendee attendee){
        AttendeeList.add(attendee);
    }

    public void addAttendeEvent(Attendee attendee, String event){
        attendee.addEvent(event);
    }

    public boolean inSystem(String username, String password){
        for (Attendee a: AttendeeList){
            if (username.equals(a.getUsername()) && password.equals(a.getPassword())){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Attendee> getAllAttendees(){
        return AttendeeList;
    }

    /*
    public ArrayList<Attendee> eventToAttendees(Event event){
        ArrayList<Attendee> attendeeList = new ArrayList<Attendee>();
        String title = event.getTitle();
        for (Attendee a: AttendeeList){
            if (a.getItinerary().contains(title)){
                attendeeList.add(a);
            }
        }
        return attendeeList;
    }

     */
    /*
    public static void main(String[] args) {
        AttendeeManager a = new AttendeeManager();
        Attendee attendee = a.createAttendee("Bill Nye", "bill", "science");
        a.add(attendee);
        a.addAttendeEvent(attendee, "CSC207");
        System.out.println(a.inSystem("Bill", "Nye"));
        System.out.println(a.inSystem("Bill", "James"));
        System.out.println(a.inSystem("Steve", "Hawking"));
        System.out.println(a.inSystem("bill", "science"));
        System.out.println(a.inSystem("Bill", "science"));
    }

     */
}
