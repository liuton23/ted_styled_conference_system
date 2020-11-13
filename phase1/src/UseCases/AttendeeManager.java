package UseCases;

import Entities.Attendee;
import Entities.Event;
import Entities.Speaker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

public class AttendeeManager implements Serializable {

    ArrayList<Attendee> attendeeList;
    ArrayList<Speaker> speakerList;

    public AttendeeManager(){
        attendeeList = new ArrayList<Attendee>();
        speakerList = new ArrayList<Speaker>();
    }

    public Attendee createAttendee(String name, String username, String password, Boolean isOrg){
        Attendee a = new Attendee(name, username, password);
        attendeeList.add(a);
        if ( isOrg ) {
            a.makeOrganizer();
        }
        return a;
    }

    public boolean inSystem(String username, String password){
        for (Attendee a: attendeeList){
            if (username.equals(a.getUsername()) && password.equals(a.getPassword())){
                return true;
            }
        }
        return false;
    }

    public Speaker createSpeaker(String name, String username, String password){
        Speaker sp = new Speaker(name, username, password);
        speakerList.add(sp);
        return sp;
    }

    public ArrayList<Attendee> getAllAttendees(){
        return attendeeList;
    }

    public ArrayList<Speaker> getAllSpeakers() {
        return speakerList;
    }

    public ArrayList<String> getItinerary(Attendee attendee){
        return attendee.getItinerary();
    }

    public void changeSpeaker(String title, Speaker oldSpeaker, Speaker newSpeaker){
        oldSpeaker.removeEvent(title);
        newSpeaker.addEvent(title);
    }

    public void signUp(Attendee attendee, String event){
        attendee.addEvent(event);
    }

    public void dropOut(Attendee attendee, String event){
        attendee.removeEvent(event);
    }


    // check this later Optionals are funky
    public Optional<Attendee> usernameToAttendeeObject(String username){
        for (Attendee user: attendeeList) {
            if(username.equals(user.getUsername())){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }


    public ArrayList<Attendee> eventToAttendees(Event event){
        ArrayList<Attendee> list = new ArrayList<Attendee>();
        String title = event.getTitle();
        for (Attendee a: attendeeList){
            if (a.getItinerary().contains(title)){
                list.add(a);
            }
        }
        return list;
    }

    /*

    public static void main(String[] args) {
        AttendeeManager a = new AttendeeManager();
        Attendee attendee = a.createAttendee("Bill Nye", "bill", "science");
        a.add(attendee);
        a.addAttendeeEvent(attendee, "CSC207");
        System.out.println(a.inSystem("Bill", "Nye"));
        System.out.println(a.inSystem("Bill", "James"));
        System.out.println(a.inSystem("Steve", "Hawking"));
        System.out.println(a.inSystem("bill", "science"));
        System.out.println(a.inSystem("Bill", "science"));
    }

     */


}
