package Controller;

import Entities.*;
import Entities.Event;
import Entities.UserFactory.*;
import Presenter.Presenter;
import UseCases.UserManager;
import UseCases.EventManager;
import UseCases.MessageManager;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

/**
 * Control all the messages base on user's input
 */
public class MessageSystem {

    MessageManager messageManager;
    UserManager userManager;
    EventManager eventManager;
    private Presenter presenter = new Presenter();

    /**
     * Create an instance of MessageSystem
     * @param messageManager MessageManager
     * @param userManager AttendeeManager
     * @param eventManager EventManager
     */

    public MessageSystem(MessageManager messageManager, UserManager userManager,
                         EventManager eventManager){
        this.messageManager = messageManager;
        this.userManager = userManager;
        this.eventManager = eventManager;
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
        Optional<Account> obj1 = userManager.usernameToUserObject(sender);
        Optional<Account> obj2 = userManager.usernameToUserObject(attendee);
        if (!obj1.isPresent()){
            return 1; //"Incorrect username. Please try again."
        } else if (!obj2.isPresent()){
            return 1; //"Incorrect username. Please try again.";
        }
        Account recipient = obj2.get();
        if (userManager.checkIsOrganizer(recipient)){
            return 2; //"The message can not be sent to an Organizer."
        } else {
            ArrayList<String> att = new ArrayList<String>();
            att.add(attendee);
            messageManager.createMessage(att, sender, text);
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
        Optional<Account> obj = userManager.usernameToUserObject(sender);
        ArrayList<TalkAble> listOfSpeakers = userManager.getAllSpeakers();
        ArrayList<String> list = new ArrayList<String>();
        for (TalkAble s : listOfSpeakers){
            list.add(userManager.getUsername((User) s));
        }
        if (!obj.isPresent()){
            return 1; /*"Incorrect username. Please try again."*/
        }
        User org = obj.get();
        if (!userManager.checkIsOrganizer(org)){
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
        Optional<Account> obj = userManager.usernameToUserObject(sender);
        if (!obj.isPresent()){
            return 1; /*"Incorrect username. Please try again."*/
        }
        Account org = obj.get();
        if (!userManager.checkIsOrganizer(org)) {
            return 2; /*"Only Organizer can message all attendees."*/
        }
        ArrayList<String> allAtt = new ArrayList<String>();
        ArrayList<AttendAble> allAttObj = userManager.getAllAttendees();
        for(AttendAble att: allAttObj){
            allAtt.add(userManager.getUsername((User) att));
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
     * @return integer which will send to presenter and presents the corresponding messages
     */

    public int messageEventAttendees(ArrayList<String> eventNames, String sender, String text){
        Optional<Account> obj = userManager.usernameToUserObject(sender);
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> noAtt = new ArrayList<String>();
        ArrayList<String> notSpeakAt = new ArrayList<String>();

        if (!obj.isPresent()){
            return 2; //"Incorrect username. Please try again.";
        }
        Account se = obj.get();
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


    // Activity method below


    /**
     * Messages a single user.
     * @param username username of the sender.
     */
    public void messageOneUser(String username){
        Scanner obj = new Scanner(System.in);
        presenter.printPleaseInputUsername();
        String user = obj.nextLine();
        presenter.printInputMessagePlz();
        String message = obj.nextLine();
        presenter.printMessageAttendee(messageAttendee(username,user,message));
    }

    /**
     * Messages all speakers.
     * @param username username of the sender.
     */
    public void messageAllSpeaker(String username){
        Scanner obj = new Scanner(System.in);
        presenter.printPleaseInputUsername();
        String message = obj.nextLine();
        presenter.printMessageAllSpeakers(messageAllSpeakers(username, message));
    }

    /**
     * Messages all users.
     * @param username username of the sender.
     */
    public void messageAllAtt(String username){
        Scanner obj = new Scanner(System.in);
        presenter.printInputMessagePlz();
        String message = obj.nextLine();
        presenter.printMessageAllAttendees(messageAllAttendees(username, message));
    }

    /**
     * Displays messages that were sent to an <code>Attendee</code> with username <code>username</code> by a specific
     * <code>Attendee</code>.
     * @param username username of <code>Attendee</code> viewing the messages.
     */
    public void viewFrom(String username){
        Scanner obj = new Scanner(System.in);
        presenter.printPleaseInputUsername();
        String user = obj.nextLine();
        Optional<Account> obj1 = userManager.usernameToUserObject(user);
        if (!obj1.isPresent()){
            presenter.printIncorrectUsername();
            viewFrom(username);
        } else {
            ArrayList<String> messageF = messageManager.getAllMessagesFrom(username, user);
            if (messageF.size() == 0){
                presenter.display(presenter.thereAreNoMessForUFrom() + user);
            } else presenter.displayListOfMessage(messageF);
        }
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
