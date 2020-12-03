package Controller;

import Entities.Attendee;
import Entities.Event;
import Entities.Speaker;
import UseCases.AttendeeManager;
import UseCases.EventManager;
import UseCases.MessageManager;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import UseCases.RoomManager;

import javax.swing.text.html.Option;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

/**
 * Control all the messages base on user's input
 */
public class MessageSystem {

    MessageManager messageManager;
    AttendeeManager attendeeManager;
    EventManager eventManager;
    Session session;
    String email;

    /**
     * Create an instance of MessageSystem
     * @param mm MessageManager
     * @param am AttendeeManager
     * @param em EventManager
     */

    public MessageSystem(MessageManager mm, AttendeeManager am, EventManager em){
        this.messageManager = mm;
        this.attendeeManager = am;
        this.eventManager = em;
        initEmail();
    }

    private void initEmail(){
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        final String myEmail = "csc207Group0757@gmail.com";
        this.email = myEmail;
        final String myPassword = "T2uring07";
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, myPassword);
            }
        };
        this.session = Session.getDefaultInstance(properties, auth);
    }

    public void sendEmail(String receiverEmail, String subject, String content){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
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
        Optional<Attendee> obj1 = attendeeManager.usernameToAttendeeObject(sender);
        Optional<Attendee> obj2 = attendeeManager.usernameToAttendeeObject(attendee);
        if (!obj1.isPresent()){
            return 1; //"Incorrect username. Please try again."
        } else if (!obj2.isPresent()){
            return 1; //"Incorrect username. Please try again.";
        }
        Attendee recipient = obj2.get();
        if (recipient.isOrganizer()){
            return 2; //"The message can not be sent to an Organizer."
        } else {
            messageManager.createMessage(attendee, sender, text);
            return 3; //"The message has been successfully sent."
        }
    }


    //All the Organizer methods

    //helper


    /**
     * This method will let only organizer sends message to all speakers at once.
     * @param sender the username of the sender
     * @param text the content of the message
     * @return integer which will send to presenter and presents the corresponding messages
     */

    public int messageAllSpeakers(String sender, String text){
        Optional<Attendee> obj = attendeeManager.usernameToAttendeeObject(sender);
        ArrayList<Speaker> listOfSpeakers = attendeeManager.getAllSpeakers();
        ArrayList<String> list = new ArrayList<>();
        for (Speaker s : listOfSpeakers){
            list.add(s.getUsername());
        }
        if (!obj.isPresent()){
            return 1; /*"Incorrect username. Please try again."*/
        }
        Attendee org = obj.get();
        if (!attendeeManager.checkIsOrganizer(org)){
            return 2;/*"Only Organizer can message all speakers."*/
        } else {
            messageManager.createMessage(list, sender, text);
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
        Optional<Attendee> obj = attendeeManager.usernameToAttendeeObject(sender);
        if (!obj.isPresent()){
            return 1; /*"Incorrect username. Please try again."*/
        }
        Attendee org = obj.get();
        if (!attendeeManager.checkIsOrganizer(org)) {
            return 2; /*"Only Organizer can message all attendees."*/
        }
        ArrayList<String> allAtt = new ArrayList<>();
        ArrayList<Attendee> allAttObj = attendeeManager.getAllAttendees();
        for(Attendee att: allAttObj){
            if (!attendeeManager.checkIsOrganizer(att)){
            allAtt.add(att.getUsername());
            }
        }
        messageManager.createMessage(allAtt,sender,text);
        return 3; /*"The message has been successfully sent."*/

    }

    //All the Speaker methods

    /**
     * This method only let speaker message all attendees from multiple events they speak at.
     * @param eventNames a list of names of events
     * @param sender username of the sender
     * @param text the content of the message
     * @return integer which will send to presenter and presents the crresponding messages
     */

    public int messageEventAttendees(ArrayList<String> eventNames, String sender, String text){
        Optional<Attendee> obj = attendeeManager.usernameToAttendeeObject(sender);
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> noAtt = new ArrayList<>();
        ArrayList<String> notSpeakAt = new ArrayList<>();

        if (!obj.isPresent()){
            return 2; //"Incorrect username. Please try again.";
        }
        Attendee se = obj.get();
        if (!(se instanceof Speaker)){
            return 3; //"Only speakers can sent messages to all attendees of their talks they give.";
        }
        for (String i : eventNames){
            Optional<Event> eve = eventManager.nameToEvent(i);
            if (!eve.isPresent()){
                return 4; //"Event do not exist or spell the name wrong
            }
            Event eventF = eve.get();
            if (eventF.getSpeaker().equals(sender)) {
                if (eventManager.eventToAttendees(eventF).size() != 0) {
                    list.addAll(eventManager.eventToAttendees(eventF));
                } else noAtt.add(i);
            } else notSpeakAt.add(i);
        }
        if (notSpeakAt.size() != 0){
            return 1; //contain event you do not speak at
        } else if (noAtt.size() != 0){
            return 6; //"no attendee at this event"
        } else {
            messageManager.createMessage(list, sender, text);
            return 5; //"The message has been successfully sent.";
        }
    }



    //ViewMessage methods

    /**
     * This method get all the received message from user r
     * @param r username
     * @return a list of received message for user r
     */
    public ArrayList<String> viewReceivedMessage(String r){
        return messageManager.getReceivedBy(r);
    }

    /**
     * This method get all the received message from user r
     * @param r username
     * @return a list of sent message by this user r
     */
    public ArrayList<String> viewSentMessage(String r){
        return messageManager.getSendBy(r);
    }

    /**
     * This method gets all the message sent from s to r
     * @param s sender's username
     * @param r recipient's username
     * @return a list of all the message sent from s to r
     */
    public ArrayList<String> viewAllMessagesFrom(String s, String r){
        return messageManager.getAllMessagesFrom(r,s);
    }

/* Test will be moved to test file
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
        sls.addRoom(1,2);
        sls.scheduleEvent("cv workshop","lily",2020, "NOVEMBER",
                20,14,30,1);
        SignUpSystem sus = new SignUpSystem(am, em, rm);
        sus.signUpEvent("ritawon",1);
        sus.signUpEvent("liuton23",1);
        sls.scheduleEvent("Harry Potter Fan Conference", "james",2020,"DECEMBER",10,
                14,0,1);
        System.out.println(sus.viewAllEvents());
        sus.signUpEvent("ritawon",2);
        ms.messageAttendee("ritawon","liuton23","See u at LCBO!");
        ArrayList<String> evs = new ArrayList<String>();
        evs.add("cv workshop");
        System.out.println(ms.messageEventAttendees(evs,
                "lily","The meeting is gonna be hold online!"));
        System.out.println(ms.messageAttendee("liuton23","ritawon","I'll bring some coffee:)!"));
        System.out.println(ms.messageAllSpeakers("steve",
                "I am looking for an experienced speaker, please DM me!"));
        System.out.println(ms.messageAllAttendees("steve","System will be down tmrw at 5:00pm!"));

        evs.add("Harry Potter Fan Conference");
        System.out.println(ms.messageEventAttendees(evs,"james","Don't be late!"));
        System.out.println(ms.viewReceivedMessage("ritawon"));
        System.out.println(ms.messageAllSpeakers("liuton23", "lol"));
    }

 */

}
