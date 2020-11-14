package Controller;

import Entities.Attendee;
import Entities.Event;
import Entities.Speaker;
import UseCases.AttendeeManager;
import UseCases.MessageManager;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;

public class MessageSystem {

    MessageManager mm;
    AttendeeManager am;

    public MessageSystem(){
        mm = new MessageManager();
        am = new AttendeeManager();
    }

    public String messageAttendee(String sender, String attendee, String text){
        Optional<Attendee> obj = am.usernameToAttendeeObject(sender);
        if (!obj.isPresent()){
            return "Incorrect username. Please try again.";
        } 
        Attendee recipient = obj.get();
        if (recipient.isOrganizer()){
            return "The message can not be sent to an Organizer.";
        } else {
            mm.createMessage(sender, attendee, text);
            return "The message has been successfully sent.";
        }
    }

    public String messageAllSpeakers(String sender, String text){
        Optional<Attendee> obj = am.usernameToAttendeeObject(sender);
        ArrayList<Speaker> listOfSpeakers = am.getAllSpeakers();
        ArrayList<String> list = new ArrayList<String>();
        for (Speaker s : listOfSpeakers){
            list.add(s.getUsername());
        }
        if (!obj.isPresent()){
            return "Incorrect username. Please try again.";
        }
        Attendee org = obj.get();
        if (!org.isOrganizer()){
            return "Only Organizer can message all speakers.";
        } else {
            mm.createMessage(list, sender, text);
            return "The message has been successfully sent.";
        }
    }

    public String messageAllAttendees(Event event, String sender, String text){
        Optional<Attendee> obj = am.usernameToAttendeeObject(sender);
        ArrayList<String> list = event.getAttendeeList();
        if (!obj.isPresent()){
            return "Incorrect username. Please try again.";
        }
        Attendee se = obj.get();
        if (se.isOrganizer() || se instanceof Speaker){
            mm.createMessage(list, sender, text);
            return "The message has been successfully sent";
        } else {
            return "Attendee can not send message to all other attendees.";
        }
    }

    public String messageAllAttendees(ArrayList<Event> events, String sender, String text){
        Optional<Attendee> obj = am.usernameToAttendeeObject(sender);
        ArrayList<String> allAttendees = new ArrayList<String>();
        for (Event e: events){
            allAttendees.addAll(e.getAttendeeList());
        }
        if (!obj.isPresent()){
            return "Incorrect username. Please try again.";
        }
        Attendee se = obj.get();
        if (se.isOrganizer() || se instanceof Speaker){
            mm.createMessage(allAttendees, sender, text);
            return "The message has been successfully sent";
        } else {
            return "Attendee can not send message to all other attendees.";
        }
    }

}
