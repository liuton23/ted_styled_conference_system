package Controller;

import Entities.Attendee;
import Entities.Event;
import Entities.EventComparators.bySpeakerEventComparator;
import Entities.EventComparators.byTimeEventComparator;
import Entities.EventComparators.byTitleEventComparator;
import UseCases.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Controller {

    private boolean running = true;
    private Gateway gateway;
    private AttendeeManager attendeeManager;
    private EventManager eventManager;
    private MessageManager messageManager;
    private RoomManager roomManager;

    private Presenter presenter;

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

    private void accountActivity(String username) {
        boolean loggedin = true;
        boolean isOrg = attendeeManager.usernameToAttendeeObject(username).get().isOrganizer();
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
                case "B":
                    loggedin = false;
                    break;
                case "EXIT": exit();
            }
        }
    }

    private void getItinerary(AttendeeManager attendeeManager, String user){
        Optional<Attendee> obj = attendeeManager.usernameToAttendeeObject(user);
        Attendee attendee = obj.get();
        presenter.displaySchedule(attendeeManager.getItinerary(attendee), "Your itinerary:");
    }

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


            //String chosen = input.nextLine();
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
                            hour, min, roomID, cap));
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
                    int message = scheduleSystem.changeSpeaker(events.get(index),newSpeaker);
                    presenter.printChangeSpeakerMessage(message);
                    break;
                case "B":
                    scheduling = false;
                    break;
                case "EXIT": exit();
            }
        }
    }

    private void messageActivity(String username) {
        boolean messaging = true;
        while (messaging) {
            Scanner input = new Scanner(System.in);

            ArrayList<String> options = new ArrayList<>();
            ArrayList<String> choices = new ArrayList<>();
            options.add("(M)essages");
            options.add("(E)vents");
            options.add("(B)ack");
            choices.add("M");
            choices.add("E");
            choices.add(("B"));
            choices.add("EXIT");
            String chosen = askInput(options, choices, input);


            //String chosen = input.nextLine();
            switch (chosen) {
                case "M":
                    System.out.println("MessageSystem");
                    break;
                case "E":
                    System.out.println("SignUpSystem");
                    break;
                case "B":
                    messaging = false;
                    break;
                case "EXIT": exit();
            }
        }
    }

    private void eventActivity(String username) {
        SignUpSystem signUpSystem = new SignUpSystem(attendeeManager, eventManager);
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

    public void exit(){
        save();
        System.exit(0);
    }

    public boolean invalidInput(List<String> choices, String chosen) {
        for(String choice: choices){
            if(choice.equals(chosen)){
                return false;
            }
        }
        presenter.invalidInput();
        return true;
    }

    public String askInput(ArrayList<String> options, ArrayList<String> choices, Scanner input){
        String chosen;
        do{
           presenter.prompt(options);
           chosen = input.next().toUpperCase();
        }while(invalidInput(choices, chosen));
        return chosen;
    }

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
}
