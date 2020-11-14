package Controller;

import Entities.Attendee;
import Entities.Event;
import Entities.Speaker;
import UseCases.AttendeeManager;
import UseCases.EventManager;
import UseCases.MessageManager;
import UseCases.RoomManager;

import javax.swing.text.html.Option;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

public class MessageSystem {

    MessageManager mm;
    AttendeeManager am;

    public MessageSystem(MessageManager mm, AttendeeManager am){
        this.mm = mm;
        this.am = am;
    }

    public String messageAttendee(String sender, String attendee, String text){
        Optional<Attendee> obj = am.usernameToAttendeeObject(sender);
        Optional<Attendee> obj2 = am.usernameToAttendeeObject(attendee);
        if (!obj.isPresent()){
            return "Incorrect username. Please try again.";
        } else if (!obj2.isPresent()){
            return "Incorrect username. Please try again.";
        }
        Attendee recipient = obj2.get();
        if (recipient.isOrganizer()){
            return "The message can not be sent to an Organizer.";
        } else {
            mm.createMessage(attendee, sender, text);
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

    //Not sure if I should do the view Message here

    public Serializable viewReceivedMessage(String r){
        Optional<Attendee> obj = am.usernameToAttendeeObject(r);
        if (!obj.isPresent()){
            return "Incorrect username. Please try again.";
        } else {
            return mm.getReceivedBy(r);
        }
    }

    public Serializable viewSentMessage(String r){
        Optional<Attendee> obj = am.usernameToAttendeeObject(r);
        if (!obj.isPresent()){
            return "Incorrect username. Please try again.";
        } else {
            return mm.getSendBy(r);
        }
    }

    public static void main(String[] args){
        AttendeeManager am = new AttendeeManager();
        MessageManager mm = new MessageManager();
        LoginSystem lgs = new LoginSystem(am);
        MessageSystem ms = new MessageSystem(mm,am);
        lgs.registerUser("liuton23","12345kji",false);
        lgs.registerUser("ritawon","ilovewon",false);
        lgs.registerUser("steve","3456fw2",true);
        am.createSpeaker("lily","jhienc");
        EventManager em = new EventManager();
        RoomManager rm = new RoomManager();
        rm.addRoom(1,2);
        em.createEvent("cv workshop","lily",2020, "NOVEMBER",
                20,14,30,1, 2);
        //since the other systems have not been finished, i'll directly access use case class here
        SignUpSystem sus = new SignUpSystem();
        sus.signUpEvent(am,em,"ritawon",1);
        sus.signUpEvent(am,em,"liuton23",1);
        System.out.println(ms.messageAttendee("ritawon","liuton23","See u at LCBO!"));
        System.out.println(ms.messageAllAttendees(em.getEvents(),
                "lily","The meeting is gonna be hold online!"));
        System.out.println(ms.viewSentMessage("ritawon"));
        System.out.println(ms.viewReceivedMessage("liuton23"));
    }
}
