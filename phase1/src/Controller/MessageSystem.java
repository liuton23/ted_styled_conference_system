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
    EventManager em;

    public MessageSystem(MessageManager mm, AttendeeManager am, EventManager em){
        this.mm = mm;
        this.am = am;
        this.em = em;
    }

    // General message methods (Suitable for all attendees, speakers, and organizers)

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


    //All the Organizer methods

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
        if (org instanceof Speaker){
            return "A speaker can not message all other speakers";
        }
        if (!org.isOrganizer()){
            return "Only Organizer can message all speakers.";
        } else {
            mm.createMessage(list, sender, text);
            return "The message has been successfully sent.";
        }
    }

    public String messageAllAttendees(String sender, String text){
        Optional<Attendee> obj = am.usernameToAttendeeObject(sender);
        if (!obj.isPresent()){
            return "Incorrect username. Please try again.";
        }
        ArrayList<String> allAtt = new ArrayList<String>();
        ArrayList<Attendee> allAttObj = am.getAllAttendees();
        for(Attendee att: allAttObj){
            allAtt.add(att.getUsername());
        }
        Attendee org = obj.get();
        if (!org.isOrganizer()){
            return "Only organizers can message all attendees.";
        } else {
            mm.createMessage(allAtt,sender,text);
            return "The message has been successfully sent.";
        }

    }

    //All the Speaker methods

    public String messageEventAttendees(int eventIndex, String sender, String text){
        Optional<Attendee> obj = am.usernameToAttendeeObject(sender);
        Event event = em.getEvents().get(eventIndex - 1);
        ArrayList<String> list = em.eventToAttendees(event);
        if (!obj.isPresent()){
            return "Incorrect username. Please try again.";
        }
        Attendee se = obj.get();
        if (!(se instanceof Speaker)){
            return "Only speakers can sent messages to all attendees in a specific talk they give";
        } else if (!event.getSpeaker().equals(sender)) {
            return "You can not send messages to all attendees from this event.";
        } else {
                mm.createMessage(list, sender, text);
                return "The message has been successfully sent.";
            }

    }

    public String messageEventAttendees(ArrayList<Integer> eventIndexes, String sender, String text){
        Optional<Attendee> obj = am.usernameToAttendeeObject(sender);
        ArrayList<String> list = new ArrayList<String>();
        Boolean isSpeakerOf = true;
        ArrayList<Integer> error = new ArrayList<Integer>();

        if (!obj.isPresent()){
            return "Incorrect username. Please try again.";
        }
        Attendee se = obj.get();
        if (!(se instanceof Speaker)){
            return "Only speakers can sent messages to all attendees of there talks they give";
        }

        for (Integer i : eventIndexes){
            int j = i;
            Event event = em.getEvents().get(j - 1);
            if (!event.getSpeaker().equals(sender)) {
                error.add(j);
            }
            list.addAll(em.eventToAttendees(event));
        }
        if (error.size() != 0){
            return "You can not send message to events " + error.toString();
        } else {
            mm.createMessage(list, sender, text);
            return "The message has been successfully sent.";
        }
    }

    //ViewMessage methods

    public String viewReceivedMessage(String r){
        Optional<Attendee> obj = am.usernameToAttendeeObject(r);
        if (!obj.isPresent()){
            return "Incorrect username. Please try again.";
        } else {
            return mm.getReceivedBy(r).toString();
        }
    }

    public String viewSentMessage(String r){
        Optional<Attendee> obj = am.usernameToAttendeeObject(r);
        if (!obj.isPresent()){
            return "Incorrect username. Please try again.";
        } else {
            return mm.getSendBy(r).toString();
        }
    }

    public String viewAllMessagesFrom(String s, String r){
        Optional<Attendee> obj1 = am.usernameToAttendeeObject(s);
        Optional<Attendee> obj2 = am.usernameToAttendeeObject(r);
        if (!obj1.isPresent()){
            return "Incorrect username. Please try again.";
        } else if (!obj2.isPresent()){
            return "Incorrect username. Please try again.";
        } else {
            return mm.getAllMessagesFrom(r,s).toString();
        }
    }

    public static void main(String[] args){
        AttendeeManager am = new AttendeeManager();
        MessageManager mm = new MessageManager();
        EventManager em = new EventManager();
        LoginSystem lgs = new LoginSystem(am);
        MessageSystem ms = new MessageSystem(mm,am,em);
        lgs.registerUser("liuton23","12345kji",false);
        lgs.registerUser("ritawon","ilovewon",false);
        lgs.registerUser("steve","3456fw2",true);
        am.createSpeaker("lily","jhienc");
        am.createSpeaker("james","iplayallday");
        RoomManager rm = new RoomManager();
        rm.addRoom(1,2);
        em.createEvent("cv workshop","lily",2020, "NOVEMBER",
                20,14,30,1, 2);
        //since the other systems have not been finished, i'll directly access use case class here
        SignUpSystem sus = new SignUpSystem();
        ScheduleSystem sls = new ScheduleSystem(em, am, rm);
        sus.signUpEvent(am,em,"ritawon",1);
        sus.signUpEvent(am,em,"liuton23",1);
        em.createEvent("Harry Porter Fan Conference", "james",2020,"DECEMBER",10,
                14,0,1,50);
        System.out.println(sus.viewAllEvents(em));
        sus.signUpEvent(am,em,"ritawon",2);
        ms.messageAttendee("ritawon","liuton23","See u at LCBO!");
        System.out.println(ms.messageEventAttendees(1,
                "lily","The meeting is gonna be hold online!"));
        System.out.println(ms.messageAttendee("liuton23","ritawon","I'll bring some coffee:)!"));
        System.out.println(ms.viewAllMessagesFrom("liuton23","ritawon"));
        System.out.println(ms.messageAllSpeakers("steve",
                "I am looking for an experienced speaker, please DM me!"));
        System.out.println(ms.messageAllAttendees("steve","System will be down tmrw at 5:00pm!"));
        ArrayList<Integer> evs = new ArrayList<Integer>();
        evs.add(1);
        evs.add(2);
        System.out.println(ms.messageEventAttendees(evs,"james","Don't be late!"));
        System.out.println(ms.viewReceivedMessage("ritawon"));
    }
}
