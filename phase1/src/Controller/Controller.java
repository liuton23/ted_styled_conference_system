package Controller;

import Entities.Attendee;
import Entities.Event;
import Entities.EventComparators.bySpeakerEventComparator;
import Entities.EventComparators.byTimeEventComparator;
import Entities.EventComparators.byTitleEventComparator;
import Entities.Speaker;
import UseCases.*;
import Entities.Speaker;
//import jdk.nashorn.internal.runtime.arrays.ArrayIndex;

//import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * Manages user input.
 *
 * @version 1.2
 */
public class Controller {

    private boolean running = true;
    private Gateway gateway;
    private AttendeeManager attendeeManager;
    private EventManager eventManager;
    private MessageManager messageManager;
    private RoomManager roomManager;

    private Presenter presenter;

    /**
     * Starts the program. Login menu.
     */
    public void run() {
        init();
        Scanner input = new Scanner(System.in);
        presenter.welcomeMessage();
        while (running) {
            ArrayList<String> options = new ArrayList<>();
            ArrayList<String> choices = new ArrayList<>();
            options.add("(L)ogin"); options.add("(R)egister");
            choices.add("L"); choices.add("R"); choices.add(("EXIT"));
            String chosen = askInput(options, choices, input);

            String username;

            switch (chosen) {
                case "R":
                    registerUser();
                    break;
                case "L":
                    username = login();
                    if (username.isEmpty()){
                        break;
                    }else {
                        accountActivity(username);
                        break;
                    }
                case "EXIT": exit();
            }
        }
    }

    /**
     * Basic menu that differs for organizers and other attendees.
     * @param username <code>Attendee</code>'s username as <code>String</code>. Username must correspond to an
     *                 <code>Attendee</code> or an error will occur.
     */
    private void accountActivity(String username) {
        boolean loggedin = true;
        boolean isOrg = attendeeManager.usernameToAttendeeObject(username).get().isOrganizer();
        boolean isSpeaker = attendeeManager.usernameToAttendeeObject(username).get() instanceof Speaker;
        while (loggedin) {
            Scanner input = new Scanner(System.in);

            ArrayList<String> options = new ArrayList<>();
            ArrayList<String> choices = new ArrayList<>();
            options.add("(M)essages");
            options.add("(E)vents");
            options.add("(I)tinerary");
            if (isOrg){
                options.add("(S)chedule events");
                choices.add("S");
                options.add("(C)reate speaker account");
                choices.add("C");
            }
            options.add("(B)ack");
            choices.add("M");
            choices.add("E");
            choices.add("I");
            choices.add(("B"));
            choices.add("EXIT");
            String chosen = askInput(options, choices, input);

            //String chosen = input.nextLine();
            switch (chosen) {
                case "M":
                    presenter.displayMessages("MessageSystem");
                    messageActivity(username);
                    break;
                case "E":
                    presenter.displayMessages("SignUpSystem");
                    eventActivity(username);
                    break;
                case "I":
                    getItinerary(attendeeManager, username);
                    break;
                case "S":
                    scheduleActivity(username);
                    break;
                case "C":
                    createSpeaker();
                    break;
                case "B":
                    loggedin = false;
                    break;
                case "EXIT": exit();
            }
        }
    }

    /**
     * Displays a schedule of all the events <code>Attendee</code> with username <code>user</code> is attending.
     * @param attendeeManager gets the schedule.
     * @param user username of <code>Attendee</code> to which the schedule belongs.
     */
    private void getItinerary(AttendeeManager attendeeManager, String user){
        Optional<Attendee> obj = attendeeManager.usernameToAttendeeObject(user);
        Attendee attendee = obj.get();
        presenter.displaySchedule(attendeeManager.getItinerary(attendee), "Your itinerary:");
    }

    /**
     * Organizer only menu to schedule events, add rooms and change speakers.
     */
    private void scheduleActivity(String username){
        ScheduleSystem scheduleSystem  = new ScheduleSystem(eventManager,attendeeManager, roomManager);
        boolean scheduling = true;
        while (scheduling) {
            Scanner input = new Scanner(System.in);

            ArrayList<String> options = new ArrayList<>();
            ArrayList<String> choices = new ArrayList<>();
            options.add("(S)chedule Event");
            options.add("(A)dd Room");
            options.add("(C)hange Speaker");
            options.add("(B)ack");
            choices.add("S");
            choices.add("A");
            choices.add("C");
            choices.add(("B"));
            choices.add("EXIT");
            String chosen = askInput(options, choices, input);

            switch (chosen) {
                case "S":
                    presenter.displayMessages("Schedule Event");
                    presenter.displayMessages("Enter event title:");
                    String title = input.nextLine();
                    presenter.displayMessages("Enter event speaker:");
                    String speaker = input.nextLine();
                    presenter.displayMessages("Enter year:");
                    int year = input.nextInt();
                    presenter.displayMessages("Enter month:");
                    String month = input.nextLine();
                    presenter.displayMessages("Enter day:");
                    int day = input.nextInt();
                    presenter.displayMessages("Enter hour:");
                    int hour = input.nextInt();
                    presenter.displayMessages("Enter minute:");
                    int min = input.nextInt();
                    presenter.displayMessages("Enter room id:");
                    int roomID = input.nextInt();
                    presenter.displayMessages("Enter event capacity:");
                    int cap = input.nextInt();
                    presenter.printScheduleEventMessage(scheduleSystem.scheduleEvent(title, speaker, year, month, day,
                            hour, min, roomID));
                    break;
                case "A":
                    presenter.displayMessages("Add Room");
                    presenter.displayMessages("Enter room ID:");
                    int roomId = input.nextInt();
                    presenter.displayMessages("Enter room capacity:");
                    int roomCapacity = input.nextInt();
                    presenter.printAddRoomMessage(scheduleSystem.addRoom(roomId,roomCapacity));
                    break;
                case "C":
                    presenter.displayMessages("Change Speaker");
                    ArrayList<Event> events = new ArrayList<>();
                    events.addAll(eventManager.getEvents());
                    events.sort(new bySpeakerEventComparator());
                    presenter.displayAllEvents(events, "Events sorted by speakers:");
                    presenter.displayMessages("Enter ID of the event:");
                    int index = input.nextInt();
                    presenter.displayMessages("Enter name of new speaker:");
                    String newSpeaker = input.nextLine();
                    String eventName = events.get(index).getTitle();
                    int message = scheduleSystem.changeSpeaker(eventName,newSpeaker);
                    presenter.printChangeSpeakerMessage(message);
                    break;
                case "B":
                    scheduling = false;
                    break;
                case "EXIT": exit();
            }
        }
    }

    /**
     * Messager menu to view and send messages.
     * @param username username of <code>Attendee</code>.
     */
    private void messageActivity(String username) {
        MessageSystem ms = new MessageSystem(messageManager,attendeeManager,eventManager);
        boolean messaging = true;
        while (messaging) {
            Scanner input = new Scanner(System.in);

            ArrayList<String> options = new ArrayList<>();
            ArrayList<String> choices = new ArrayList<>();
            options.add("(M)essaging users");
            options.add("(V)iewing messages");
            options.add("(B)ack");
            choices.add("M");
            choices.add("V");
            choices.add(("B"));
            choices.add("EXIT");
            String chosen = askInput(options, choices, input);


            switch (chosen) {
                case "M":
                    presenter.displayMessages("MessageSystem");
                    messageUser(username, ms);
                    break;
                case "V":
                    presenter.displayMessages("MessageBoard");
                    viewMessages(username, ms);
                    break;
                case "B":
                    messaging = false;
                    break;
                case "EXIT": exit();
            }
        }
    }

    /**
     * Send message menu.
     * @param username username of the attendee that is messaging.
     * @param ms system that manages sending messages.
     */
    private void messageUser(String username, MessageSystem ms){
        boolean messagingOther = true;
        while (messagingOther) {
            Scanner input = new Scanner(System.in);

            ArrayList<String> options = new ArrayList<>();
            ArrayList<String> choices = new ArrayList<>();
            options.add("Sending to a (U)ser");
            options.add("Sending to all (S)peakers");
            options.add("Sending to all (A)ttendees");
            options.add("Sending to all attendees in one or multiple (E)vents");
            options.add("(B)ack");
            choices.add("U");
            choices.add("S");
            choices.add("A");
            choices.add("E");
            choices.add("B");
            choices.add("EXIT");
            String chosen = askInput(options, choices, input);

            switch (chosen) {
                case "U":
                    presenter.displayMessages("sending message to a user");
                    messageOneUser(username,ms);
                    break;
                case "S":
                    presenter.displayMessages("sending message to all speakers");
                    messageAllSpeaker(username,ms);
                    break;
                case "A":
                    presenter.displayMessages("sending message to all attendees");
                    messageAllAtt(username,ms);
                    break;
                case "E":
                    presenter.displayMessages("sending message to all attendees in one or multiple events");
                    messageEventAllAtt(username,ms);
                case "B":
                    messagingOther = false;
                    break;
                case "EXIT": exit();
            }
        }

    }

    /**
     * Messages a single user.
     * @param username username of the sender.
     * @param ms system that manages sending messages.
     */
    private void messageOneUser(String username, MessageSystem ms){
        Scanner obj = new Scanner(System.in);
        presenter.displayMessages("Please input an username");
        String user = obj.nextLine();
        presenter.displayMessages("Please input your message");
        String message = obj.nextLine();
        presenter.printMessageAttendee(ms.messageAttendee(username,user,message));
    }

    /**
     * Messages all speakers.
     * @param username username of the sender.
     * @param ms system that manages sending messages.
     */
    private void messageAllSpeaker(String username, MessageSystem ms){
        Scanner obj = new Scanner(System.in);
        presenter.displayMessages("Please input your message");
        String message = obj.nextLine();
        presenter.printMessageAllSpeakers(ms.messageAllSpeakers(username, message));
    }

    /**
     * Messages all users.
     * @param username username of the sender.
     * @param ms system that manages sending messages.
     */
    private void messageAllAtt(String username, MessageSystem ms){
        Scanner obj = new Scanner(System.in);
        presenter.displayMessages("Please input your message");
        String message = obj.nextLine();
        presenter.printMessageAllAttendees(ms.messageAllAttendees(username, message));
    }

    /**
     * Messages all users attending a specific event.
     * @param username username of the sender.
     * @param ms system that manages sending messages.
     */
    private void messageEventAllAtt(String username, MessageSystem ms){
        Scanner obj = new Scanner(System.in);
        ArrayList<Integer> events = new ArrayList<>();
        presenter.displayMessages("Please enter an event number");
        events.add(obj.nextInt());
        presenter.displayMessages("Please enter another event number if you wish, otherwise enter (0)");
        int i = obj.nextInt();
        while (i != 0){
            events.add(i);
            presenter.displayMessages("Please enter another event number if you wish, otherwise please enter (0)");
            i = obj.nextInt();

        }
        presenter.displayMessages("Please enter your message");
        String message = obj.nextLine();
        if (ms.messageEventAttendees(events,username,message) == 4){
            ArrayList<Integer> error = ms.viewEventsNotSpeak(events,username);
            presenter.displayMessages("You do not speak at event number " + ms.eventDisplayBuilder(error));
        } else presenter.printMessageMultipleEventsAttendees(ms.messageEventAttendees(events,username,message));
    }

    /**
     * View message menu to view sent and received messages, and messages sent to <code>Attendee</code> with username
     * <code>username</code> by another <code>Attendee</code>.
     * @param username username of <code>Attendee</code> viewing the messages.
     * @param ms system that manages sending messages.
     */
    private void viewMessages(String username, MessageSystem ms){
        boolean viewingMessage = true;
        while (viewingMessage) {
            Scanner input = new Scanner(System.in);

            ArrayList<String> options = new ArrayList<>();
            ArrayList<String> choices = new ArrayList<>();
            options.add("Viewing (S)ent messages");
            options.add("Viewing (R)eceived messages");
            options.add("Viewing messages (F)rom another user");
            options.add("(B)ack");
            choices.add("S");
            choices.add("R");
            choices.add("F");
            choices.add("B");
            choices.add("EXIT");
            String chosen = askInput(options, choices, input);

            switch (chosen) {
                case "S":
                    presenter.displayMessages("Viewing sent messages");
                    ArrayList<String> messagesS = ms.viewSentMessage(username);
                    if (messagesS.size() == 0){
                        presenter.displayMessages("There are no sent messages from you");
                    } else presenter.displayListOfMessage(messagesS);
                    break;
                case "R":
                    ArrayList<String> messagesR = ms.viewReceivedMessage(username);
                    if (messagesR.size() == 0){
                        presenter.displayMessages("There are no received messages for you");
                    } else presenter.displayListOfMessage(messagesR);
                    break;
                case "F":
                    viewFrom(username,ms);
                    break;
                case "B":
                    viewingMessage = false;
                    break;
                case "EXIT":
                    exit();
            }
        }
    }

    /**
     * Displays messages that were sent to an <code>Attendee</code> with username <code>username</code> by a specific
     * <code>Attendee</code>.
     * @param username username of <code>Attendee</code> viewing the messages.
     * @param ms system that manages sending messages.
     */
    private void viewFrom(String username, MessageSystem ms){
        Scanner obj = new Scanner(System.in);
        presenter.displayMessages("Please input an username");
        String user = obj.nextLine();
        Optional<Attendee> obj1 = attendeeManager.usernameToAttendeeObject(user);
        if (!obj1.isPresent()){
            presenter.displayMessages("Incorrect username please try again");
            viewFrom(username, ms);
        } else {
            ArrayList<String> messageF = ms.viewAllMessagesFrom(user,username);
            if (messageF.size() == 0){
                presenter.displayMessages("There are no messages sent to you from " + user);
            } else presenter.displayListOfMessage(messageF);
        }
    }

    /**
     * Menu to view, sign up, and drop events.
     * @param username username of <code>Attendee</code>.
     */
    private void eventActivity(String username) {
        SignUpSystem signUpSystem = new SignUpSystem(attendeeManager, eventManager, roomManager);
        boolean activity = true;
        while (activity) {
            Scanner input = new Scanner(System.in);

            ArrayList<String> options = new ArrayList<>();
            ArrayList<String> choices = new ArrayList<>();
            options.add("(V)iew all events");
            options.add("(S)ign up for events");
            options.add("(D)rop out of events");
            options.add("(B)ack");
            choices.add("V");
            choices.add("S");
            choices.add("D");
            choices.add(("B"));
            choices.add("EXIT");
            String chosen = askInput(options, choices, input);
            int index;

            switch (chosen) {
                case "V":
                    presenter.displayMessages("View all events");
                    viewAllEvents(signUpSystem);
                    break;
                case "S":
                    presenter.displayMessages("Sign Up");
                    presenter.displayMessages("Event ID of event you'd like to sign up for:");
                    index = input.nextInt();
                    presenter.printSignUpMessage(signUpSystem.signUpEvent(username, index));
                    break;
                case "D":
                    presenter.displayMessages("Drop out");
                    presenter.displayMessages("Event ID of event you'd like to drop up for:");
                    index = input.nextInt();
                    presenter.displayMessages(signUpSystem.dropOutEvent(username, index));
                    break;
                case "B":
                    activity = false;
                    break;
                case "EXIT": exit();
            }
        }
    }

    /**
     * View all events in a sorted list.
     * @param sus system that manages event sign up.
     */
    private void viewAllEvents(SignUpSystem sus){
        Scanner input = new Scanner(System.in);

        ArrayList<String> options = new ArrayList<>();
        ArrayList<String> choices = new ArrayList<>();
        options.add("Sort events by (T)ime");
        options.add("Sort events by (N)ame");
        options.add("Sort events by (S)peaker");
        choices.add("T");
        choices.add("N");
        choices.add("S");
        choices.add("EXIT");
        String chosen = askInput(options, choices, input);

        switch (chosen) {
            case "T":
                sus.setComparator(new byTimeEventComparator());
                presenter.displaySchedule(sus.viewAllEvents(), "Sort events by time");
                break;
            case "N":
                sus.setComparator(new byTitleEventComparator());
                presenter.displaySchedule(sus.viewAllEvents(), "Sort events by name");
                break;
            case "S":
                sus.setComparator(new bySpeakerEventComparator());
                presenter.displaySchedule(sus.viewAllEvents(), "Sort events by speaker");

                break;
            case "EXIT":
                exit();
        }
    }

    /**
     * Exits the program after saving.
     */
    public void exit(){
        save();
        System.exit(0);
    }

    /**
     * Checks if <code>chosen</code> is a valid input in <code>choices</code>.
     * @param choices list of choices that are valid. Must be uppercase.
     * @param chosen input the user entered.
     * @return true iff <code>chosen</code> is an invalid input. False if it is valid.
     */
    public boolean invalidInput(List<String> choices, String chosen) {
        for(String choice: choices){
            if(choice.equals(chosen)){
                return false;
            }
        }
        presenter.invalidInput();
        return true;
    }

    /**
     * Asks the user for input and checks its validity.
     * @param options human-readable options for the user to choose from.
     * @param choices list of valid inputs.
     * @param input scanner that is used to receive the input.
     * @return Uppercase String that is valid input from user.
     */
    public String askInput(ArrayList<String> options, ArrayList<String> choices, Scanner input){
        String chosen;
        do{
           presenter.prompt(options);
           chosen = input.next().toUpperCase();
        }while(invalidInput(choices, chosen));
        return chosen;
    }

    /**
     * Initializes the use case classes. If the program has not been run before, or the save file has been corrupted,
     * moved, or missing, then new use case classes will be instantiated. Otherwise, the use case classes will be loaded
     * from the save file.
     */
    public void init(){
        gateway = new Gateway("save.bin");
        ArrayList<Serializable> listOfObj;
        try {
            listOfObj = gateway.readFromFile(4);
            attendeeManager = (AttendeeManager) listOfObj.get(0);
            eventManager = (EventManager) listOfObj.get(1);
            messageManager = (MessageManager) listOfObj.get(2);
            roomManager = (RoomManager) listOfObj.get(3);
        } catch (IOException e) {
            attendeeManager = new AttendeeManager();
            eventManager = new EventManager();
            messageManager = new MessageManager();
            roomManager = new RoomManager();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            presenter = new Presenter();
        }
    }

    /**
     * Saves the use case classes and all their fields in a file.
     */
    public void save() {
        ArrayList<Serializable> listOfObj = new ArrayList<>();
        listOfObj.add(attendeeManager);
        listOfObj.add(eventManager);
        listOfObj.add(messageManager);
        listOfObj.add(roomManager);
        try {
            gateway.writeToFile(listOfObj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attendee login.
     * @return username of <code>Attendee</code> if it exists. Otherwise returns an empty string.
     */
    private String login(){
        LoginSystem loginSystem = new LoginSystem(attendeeManager);
        Scanner obj1 = new Scanner(System.in);
        presenter.printUsernameMessage();
        String username = obj1.nextLine();
        presenter.printPasswordMessage();
        String password = obj1.nextLine();
        if (loginSystem.canLogin(username, password)){
            presenter.printLoginSucceedMessage();
            return username;
        }
        presenter.printLoginFailMessage();
        return "";
    }

    /**
     * Attendee registration. Cannot choose a username that is already taken.
     */
    private void registerUser(){
        LoginSystem loginSystem = new LoginSystem(attendeeManager);
        Scanner obj1 = new Scanner(System.in);
        presenter.printUsernameMessage();
        String username = obj1.nextLine();
        presenter.printPasswordMessage();
        String password = obj1.nextLine();
        presenter.displayMessages("Are you an organizer?"); //***replace with askInput system***
        ArrayList<String> options = new ArrayList<>();
        ArrayList<String> choices = new ArrayList<>();
        options.add("(Y)es");
        options.add("(N)o");
        choices.add("Y"); choices.add("N"); choices.add(("EXIT"));
        String chosen = askInput(options, choices, obj1);
        switch (chosen) {
            case "Y":
                if(loginSystem.registerUser(username, password, true)) {
                    presenter.printRegisterSucceedMessage();
                } else {
                    presenter.printRegisterFailMessage();
                }
                break;
            case "N":
                if(loginSystem.registerUser(username, password, false)) {
                    presenter.printRegisterSucceedMessage();
                } else {
                    presenter.printRegisterFailMessage();
                }
                break;
            case "EXIT": exit();
        }

    }

    /**
     * Speaker registration. Cannot choose a username that is already taken.
     */
    private void createSpeaker(){
        LoginSystem loginSystem = new LoginSystem(attendeeManager);
        Scanner obj1 = new Scanner(System.in);
        presenter.printUsernameMessage();
        String username = obj1.nextLine();
        presenter.printPasswordMessage();
        String password = obj1.nextLine();
        if (loginSystem.registerSpeaker(username, password)){
            presenter.printRegisterSucceedMessage();
        } else {
            presenter.printRegisterFailMessage();
        }
    }
}
