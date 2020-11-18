package Controller;

import Entities.Attendee;
import Entities.Event;
import Entities.EventComparators.bySpeakerEventComparator;
import Entities.EventComparators.byTimeEventComparator;
import Entities.EventComparators.byTitleEventComparator;
import Entities.Speaker;
import UseCases.*;
import Entities.Speaker;
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
        presenter.welcomeMessage();
        boolean running = true;
        while (running) {
            String chosen = askMenuInput(1);

            String username;

            switch (chosen) {
                case "R":
                    registerUser();
                    break;
                case "L":
                    username = login();
                    if (!username.isEmpty()) {
                        accountActivity(username);
                    }
                    break;
                case "EXIT":
                    running = false;
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

            String chosen;
            if (isOrg){
                chosen = askMenuInput(3);
            }else{
                chosen = askMenuInput(2);
            }

            switch (chosen) {
                case "M":
                    messageActivity(username);
                    break;
                case "E":
                    eventActivity(username);
                    break;
                case "I":
                    getItinerary(attendeeManager, username);
                    break;
                case "S":
                    scheduleActivity();
                    break;
                case "C":
                    createSpeaker();
                    break;
                case "B":
                    loggedin = false;
                    break;
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
    private void scheduleActivity(){
        ScheduleSystem scheduleSystem  = new ScheduleSystem(eventManager,attendeeManager, roomManager);
        boolean scheduling = true;
        while (scheduling) {
            Scanner input = new Scanner(System.in);

            String chosen = askMenuInput(4);

            switch (chosen) {
                case "S":
                    scheduleEvent(scheduleSystem);
                    save();
                    break;
                case "A":
                    presenter.displayMessages("requestAddRoom");
                    presenter.displayMessages("requestRoom");
                    int roomId = input.nextInt();
                    presenter.displayMessages("requestCapacity:");
                    int roomCapacity = input.nextInt();
                    presenter.printAddRoomMessage(scheduleSystem.addRoom(roomId,roomCapacity));
                    save();
                    break;
                case "C":
                    presenter.displayMessages("changeSpeaker");
                    ArrayList<Event> events = new ArrayList<>(eventManager.getEvents());
                    events.sort(new bySpeakerEventComparator());
                    presenter.displayAllEvents(events);
                    presenter.displayMessages("requestRoom");
                    int index = input.nextInt();
                    presenter.displayMessages("requestSpeaker");
                    String newSpeaker = input.nextLine();
                    String eventName = events.get(index).getTitle();
                    int message = scheduleSystem.changeSpeaker(eventName,newSpeaker);
                    presenter.printChangeSpeakerMessage(message);
                    save();
                    break;
                case "B":
                    scheduling = false;
                    break;
            }
        }
    }

    /**
     * Method for organizers to schedule new events.
     * @param scheduleSystem ScheduleSystem created in parent method.
     */
    private void scheduleEvent(ScheduleSystem scheduleSystem){
        Scanner input = new Scanner(System.in);
        presenter.displayMessages("S");
        String title = input.nextLine();
        presenter.displayMessages("requestSpeaker");
        String speaker = input.nextLine();
        presenter.displayMessages("requestYear");
        int year = Integer.parseInt(input.nextLine());
        presenter.displayMessages("requestMonth"); //Must be all CAPITAL FULLY SPELLED OUT
        String month = input.nextLine();
        presenter.displayMessages("requestDay");
        int day = Integer.parseInt(input.nextLine());
        presenter.displayMessages("requestHour");
        int hour = Integer.parseInt(input.nextLine());
        presenter.displayMessages("requestMinute");
        int min = Integer.parseInt(input.nextLine());
        presenter.displayMessages("requestRoom");
        int roomID = Integer.parseInt(input.nextLine());
        presenter.printScheduleEventMessage(scheduleSystem.scheduleEvent(title, speaker, year, month, day,
                hour, min, roomID));
    }

    /**
     * Messager menu to view and send messages.
     * @param username username of <code>Attendee</code>.
     */
    private void messageActivity(String username) {
        MessageSystem ms = new MessageSystem(messageManager,attendeeManager,eventManager);
        boolean messaging = true;
        while (messaging) {
            String chosen = askMenuInput(5);
            switch (chosen) {
                case "M":
                    messageUser(username, ms);
                    break;
                case "V":
                    viewMessages(username, ms);
                    break;
                case "B":
                    messaging = false;
                    break;
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
            String chosen = askMenuInput(6);

            switch (chosen) {
                case "U":
                    messageOneUser(username,ms);
                    save();
                    break;
                case "S":
                    messageAllSpeaker(username,ms);
                    save();
                    break;
                case "A":
                    messageAllAtt(username,ms);
                    save();
                    break;
                case "E":
                    messageEventAllAtt(username,ms);
                    save();
                case "B":
                    messagingOther = false;
                    break;
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
        presenter.printPleaseInputUsername();
        String user = obj.nextLine();
        presenter.printInputMessagePlz();
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
        presenter.printPleaseInputUsername();
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
        presenter.printInputMessagePlz();
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
        presenter.printInputEventNum();
        events.add(obj.nextInt());
        presenter.printInputEventNumOrZero();
        int i = obj.nextInt();
        while (i != 0){
            events.add(i);
            presenter.printInputEventNumOrZero();
            i = obj.nextInt();

        }
        presenter.printInputMessagePlz();
        String message = obj.nextLine();
        if (ms.messageEventAttendees(events,username,message) == 4){
            ArrayList<Integer> error = ms.viewEventsNotSpeak(events,username);
            presenter.display( presenter.udoNotSpeakAt() + ms.eventDisplayBuilder(error));
        } else if (events.size() == 1){
            presenter.printMessageMultipleEventsAttendees(ms.messageEventAttendees(events,username,message));
        } else presenter.printMessageEventAttendees(ms.messageEventAttendees(events,username,message));
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
            String chosen = askMenuInput(7);

            switch (chosen) {
                case "S":
                    ArrayList<String> messagesS = ms.viewSentMessage(username);
                    if (messagesS.size() == 0){
                        presenter.printNoSentForU();
                    } else presenter.displayListOfMessage(messagesS);
                    break;
                case "R":
                    ArrayList<String> messagesR = ms.viewReceivedMessage(username);
                    if (messagesR.size() == 0){
                        presenter.printNoRecForU();
                    } else presenter.displayListOfMessage(messagesR);
                    break;
                case "F":
                    viewFrom(username,ms);
                    break;
                case "B":
                    viewingMessage = false;
                    break;
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
        presenter.printPleaseInputUsername();
        String user = obj.nextLine();
        Optional<Attendee> obj1 = attendeeManager.usernameToAttendeeObject(user);
        if (!obj1.isPresent()){
            presenter.printIncorrectUsername();
            viewFrom(username, ms);
        } else {
            ArrayList<String> messageF = ms.viewAllMessagesFrom(user,username);
            if (messageF.size() == 0){
                presenter.display(presenter.thereAreNoMessForUFrom() + user);
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

            String chosen = askMenuInput(8);
            int index;

            switch (chosen) {
                case "V":
                    presenter.displayMessages("viewEvents");
                    viewAllEvents(signUpSystem);
                    break;
                case "S":
                    presenter.displayMessages("signUp");
                    presenter.displayMessages("requestEventId");
                    index = input.nextInt();
                    presenter.printSignUpMessage(signUpSystem.signUpEvent(username, index));
                    save();
                    break;
                case "D":
                    presenter.displayMessages("dropOut");
                    presenter.displayMessages("requestEventIdDropOut");
                    index = input.nextInt();
                    presenter.display(signUpSystem.dropOutEvent(username, index));
                    save();
                    break;
                case "B":
                    activity = false;
                    break;
            }
        }
    }

    /**
     * View all events in a sorted list.
     * @param sus system that manages event sign up.
     */
    private void viewAllEvents(SignUpSystem sus){
        String chosen = askMenuInput(9);

        switch (chosen) {
            case "T":
                sus.setComparator(new byTimeEventComparator());
                presenter.displaySchedule(sus.viewAllEvents(), "time");
                break;
            case "N":
                sus.setComparator(new byTitleEventComparator());
                presenter.displaySchedule(sus.viewAllEvents(), "name");
                break;
            case "S":
                sus.setComparator(new bySpeakerEventComparator());
                presenter.displaySchedule(sus.viewAllEvents(), "speaker");
                break;
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
     * Checks if <code>chosen</code> is a valid input in <code>choices</code>. If input is "EXIT", <code>exit</code>
     * will be called.
     * @param choices list of choices that are valid. Must be uppercase.
     * @param chosen input the user entered.
     * @return true iff <code>chosen</code> is an invalid input. False if it is valid.
     */
    public boolean invalidInput(List<String> choices, String chosen) {
        for(String choice: choices){
            if(choice.equals(chosen)){
                return false;
            }else if(chosen.equals("EXIT")){
                exit();
            }
        }
        presenter.invalidInput();
        return true;
    }

    /**
     * Asks the user for input and checks its validity.
     * @param i the menu id.
     * @return Uppercase String that is valid input from user.
     */
    public String askMenuInput(int i){
        Scanner input = new Scanner(System.in);
        ArrayList<String> choices = chooseMenuOptions(i);
        String chosen;
        do{
           chooseMenuPrompt(i);
           chosen = input.nextLine().toUpperCase();
        }while(invalidInput(choices, chosen));
        return chosen;
    }

    /**
     * Asks the user yes or no and receives input.
     * @return  true if the user inputs Y/YES/T/True and false if the user inputs N/NO/F/FALSE.
     */
    public boolean askBooleanInput(){
        Scanner input = new Scanner(System.in);
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Y"); choices.add("N");
        choices.add("T"); choices.add("TRUE");
        choices.add("YES"); choices.add("NO");
        choices.add("F"); choices.add("FALSE");
        String chosen;
        do{
            presenter.display("Yes or No?");
            chosen = input.next().toUpperCase();
        }while(invalidInput(choices, chosen));
        return chosen.equals("Y") || chosen.equals("YES") || chosen.equals("T") || chosen.equals("TRUE");
    }

    /**
     * Chooses which options are valid input options for a menu given <code>menu_id</code>.
     * @param menu_id determines which menu is needed.
     * @return list of valid options for a menu.
     */
    public ArrayList<String> chooseMenuOptions(int menu_id){
        ArrayList<String> choices = new ArrayList<>();
        switch (menu_id){
            case 1:
                choices.add("L");
                choices.add("R");
                break;
            case 3:
                choices.add("C");
                choices.add("S");
            case 2:
                choices.add("M");
                choices.add("E");
                choices.add("I");
                choices.add(("B"));
                break;
            case 4:
                choices.add("S");
                choices.add("A");
                choices.add("C");
                choices.add(("B"));
                break;
            case 5:
                choices.add("M");
                choices.add("V");
                choices.add(("B"));
                break;
            case 6:
                choices.add("U");
                choices.add("S");
                choices.add("A");
                choices.add("E");
                choices.add("B");
                break;
            case 7:
                choices.add("S");
                choices.add("R");
                choices.add("F");
                choices.add("B");
                break;
            case 8:
                choices.add("V");
                choices.add("S");
                choices.add("D");
                choices.add(("B"));
                break;
            case 9:
                choices.add("T");
                choices.add("N");
                choices.add("S");
                break;
        }
        return choices;
    }

    /**
     * Displays a menu given <code>menu_id</code>.
     * @param menu_id determines which menu is needed.
     */
    public void chooseMenuPrompt(int menu_id){
        switch (menu_id){
            case 1:
                presenter.loginMenu();
                break;
            case 2:
                presenter.basicMenu1();
                break;
            case 3:
                presenter.basicMenu2();
                break;
            case 4:
                presenter.organizerMenu();
                break;
            case 5:
                presenter.mainMessageMenu();
                break;
            case 6:
                presenter.sendMessageMenu();
                break;
            case 7:
                presenter.viewMessageMenu();
                break;
            case 8:
                presenter.eventMenu();
                break;
            case 9:
                presenter.viewEventsMenu();
                break;
        }
    }
    /**
     * Initializes the use case classes. If the program has not been run before, or the save file has been corrupted,
     * moved, or missing, then new use case classes will be instantiated. Otherwise, the use case classes will be loaded
     * from the save file.
     */
    public void init(){
        gateway = new Gateway("save.txt");
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
        presenter.display("Are you an organizer?"); //***replace with askInput system***
        boolean chosen = askBooleanInput();
        if(loginSystem.registerUser(username, password, chosen)){
            presenter.printRegisterSucceedMessage();
        }else{
            presenter.printRegisterFailMessage();
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
