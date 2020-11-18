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

/**
 * Control all the messages base on user's input
 */
public class MessageSystem {

    MessageManager mm;
    AttendeeManager am;
    EventManager em;

    /**
     * Create an instance of MessageSystem
     * @param mm MessageManager
     * @param am AttendeeManager
     * @param em EventManager
     */

    public MessageSystem(MessageManager mm, AttendeeManager am, EventManager em){
        this.mm = mm;
        this.am = am;
        this.em = em;
    }

    // General message methods (Suitable for all attendees, speakers, and organizers)

    /**
     * This method can let all attendee, speakers and organizers message a specific user which is not an organizer.
     * @param sender username of the sender
     * @param attendee username of the attendee's the sender want to send message to
     * @param text the content of the message
     * @return integer which will send to presenter and present corresponding message
     */
    public int messageAttendee(String sender, String attendee, String text){
        Optional<Attendee> obj1 = am.usernameToAttendeeObject(sender);
        Optional<Attendee> obj2 = am.usernameToAttendeeObject(attendee);
        if (!obj1.isPresent()){
            return 1; //"Incorrect username. Please try again."
        } else if (!obj2.isPresent()){
            return 1; //"Incorrect username. Please try again.";
        }
        Attendee recipient = obj2.get();
        if (recipient.isOrganizer()){
            return 2; //"The message can not be sent to an Organizer."
        } else {
            mm.createMessage(attendee, sender, text);
            return 3; //"The message has been successfully sent."
        }
    }


    //All the Organizer methods

    //helper

    /**
     * @param org attendee object
     * @return true of this attendee is organizer
     */
    private Boolean checkIsOrganizer(Attendee org){
        if (org instanceof Speaker){
            return false;
        } else return org.isOrganizer();
    }

    /**
     * This method will let only organizer sends message to all speakers at once.
     * @param sender the username of the sender
     * @param text the content of the message
     * @return integer which will send to presenter and presents the corresponding messages
     */

    public int messageAllSpeakers(String sender, String text){
        Optional<Attendee> obj = am.usernameToAttendeeObject(sender);
        ArrayList<Speaker> listOfSpeakers = am.getAllSpeakers();
        ArrayList<String> list = new ArrayList<String>();
        for (Speaker s : listOfSpeakers){
            list.add(s.getUsername());
        }
        if (!obj.isPresent()){
            return 1; /*"Incorrect username. Please try again."*/
        }
        Attendee org = obj.get();
        if (!checkIsOrganizer(org)){
            return 2;/*"Only Organizer can message all speakers."*/
        } else {
            mm.createMessage(list, sender, text);
            return 3; /*"The message has been successfully sent."*/
        }
    }

    /**
     * This method only let organizer sends message to all attendees in the system at once.
     * @param sender username of sender
     * @param text the content of the message
     * @return integer which will send to presenter and presents the crresponding messages
     */

    public int messageAllAttendees(String sender, String text){
        Optional<Attendee> obj = am.usernameToAttendeeObject(sender);
        if (!obj.isPresent()){
            return 1; /*"Incorrect username. Please try again."*/
        }
        ArrayList<String> allAtt = new ArrayList<String>();
        ArrayList<Attendee> allAttObj = am.getAllAttendees();
        for(Attendee att: allAttObj){
            allAtt.add(att.getUsername());
        }
        Attendee org = obj.get();
        if (!checkIsOrganizer(org)){
            return 2; /*"Only Organizer can message all attendees."*/
        } else {
            mm.createMessage(allAtt,sender,text);
            return 3; /*"The message has been successfully sent."*/
        }
    }

    //All the Speaker methods

    /**
     * This method only let speaker send message to a specific event's all attendees.
     * @param eventIndex the index of an event
     * @param sender username of the sender
     * @param text the content of the message
     * @return integer which will send to presenter and presents the corresponding messages
     */

    public int messageEventAttendees(int eventIndex, String sender, String text){
        Optional<Attendee> obj = am.usernameToAttendeeObject(sender);
        int max = em.getEvents().size();
        // check index
        if (eventIndex > max){
            return 1; //"There is no such event."
        }
        Event event = em.getEvents().get(eventIndex - 1);
        ArrayList<String> list = em.eventToAttendees(event);
        if (!obj.isPresent()){
            return 2; //"Incorrect username. Please try again."
        }
        Attendee se = obj.get();
        if (!(se instanceof Speaker)){
            return 3; //"Only speakers can sent messages to all attendees of their talks they give.";
        } else if (!event.getSpeaker().equals(sender)) {
            return 4; //"You do not speak at this event!";
        } else {
                mm.createMessage(list, sender, text);
                return 5; //"The message has been successfully sent.";
        }
    }

    /**
     * This method only let speaker message all attendees from multiple events they speak at.
     * @param eventIndexes a list of indexes of events
     * @param sender username of the sender
     * @param text the content of the message
     * @return integer which will send to presenter and presents the crresponding messages
     */

    public int messageEventAttendees(ArrayList<Integer> eventIndexes, String sender, String text){
        Optional<Attendee> obj = am.usernameToAttendeeObject(sender);
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<Integer> error = new ArrayList<Integer>();

        if (!obj.isPresent()){
            return 2; //"Incorrect username. Please try again.";
        }
        Attendee se = obj.get();
        if (!(se instanceof Speaker)){
            return 3; //"Only speakers can sent messages to all attendees of their talks they give.";
        }
        int max = em.getEvents().size();
        for (Integer i : eventIndexes){
            int j = i; //check index
            if (j > max){
                return 1; //"Event list contains non-existed event.";
            }
            Event event = em.getEvents().get(j - 1);
            if (!event.getSpeaker().equals(sender)) {
                error.add(j);
            }
            list.addAll(em.eventToAttendees(event));
        } //events that the speaker does not speak in
        if (error.size() != 0){
            return 4; //"Event list contains event which you do not speak at.";
        } else {
            mm.createMessage(list, sender, text);
            return 5; //"The message has been successfully sent.";
        }
    }


    //helper method for display

    public ArrayList<Integer> viewEventsNotSpeak (ArrayList<Integer> eventIndex, String user){
        ArrayList<Integer> error = new ArrayList<Integer>();
        for (Integer i: eventIndex){
            Event event = em.getEvents().get(i - 1);
            if (!event.getSpeaker().equals(user)) {
                error.add(i);
            }
        }
        return error;
    }

    public String eventDisplayBuilder(ArrayList<Integer> eventIndex){
        String x = "";
        int max = eventIndex.size();
        if (max == 1){
            return eventIndex.get(0).toString();
        } else {
            for (int i = 0; i < max - 2; i++) {
                x += eventIndex.get(i).toString() + ", ";
            }
            return x + eventIndex.get(max - 2).toString() + " and " + eventIndex.get(max - 1).toString();
        }
    }


    //ViewMessage methods

    public ArrayList<String> viewReceivedMessage(String r){
        return mm.getReceivedBy(r);
    }

    public ArrayList<String> viewSentMessage(String r){
        return mm.getSendBy(r);
    }

    public ArrayList<String> viewAllMessagesFrom(String s, String r){
        return mm.getAllMessagesFrom(r,s);
    }

    /*
    test case
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
        ScheduleSystem sls = new ScheduleSystem(em, am, rm);
        sls.scheduleEvent("cv workshop","lily",2020, "NOVEMBER",
                20,14,30,1, 2);
        SignUpSystem sus = new SignUpSystem(am, em);
        sus.signUpEvent("ritawon",1);
        sus.signUpEvent("liuton23",1);
        sls.scheduleEvent("Harry Potter Fan Conference", "james",2020,"DECEMBER",10,
                14,0,1,50);
        sls.addRoom(1,2);
        System.out.println(sus.viewAllEvents());
        sus.signUpEvent("ritawon",2);
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
        System.out.println(ms.messageEventAttendees(3,"lily","lol."));
    }
     */
}
