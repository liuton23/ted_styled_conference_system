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
        while (loggedin) {
            Scanner input = new Scanner(System.in);

            ArrayList<String> options = new ArrayList<>();
            ArrayList<String> choices = new ArrayList<>();
            options.add("(M)essages");
            options.add("(E)vents");
            options.add("(I)tinerary");
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
                    System.out.println("MessageSystem");
                    messageActivity(username);
                    break;
                case "E":
                    System.out.println("SignUpSystem");
                    eventActivity(username);
                    break;
                case "I":
                    getItinerary(attendeeManager, username);
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
            int index = input.nextInt();

            switch (chosen) {
                case "V":
                    System.out.println("View all events");
                    viewAllEvents(signUpSystem);
                    break;
                case "S":
                    System.out.println("Sign Up");
                    presenter.displayMessages(signUpSystem.signUpEvent(username, index));
                    break;
                case "D":
                    System.out.println("Drop out");
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
           chosen = input.nextLine().toUpperCase();
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
    public String login(){
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

    public void registerUser(){
        LoginSystem loginSystem = new LoginSystem(attendeeManager);
        Scanner obj1 = new Scanner(System.in);
        presenter.printUsernameMessage();
        String username = obj1.nextLine();
        presenter.printPasswordMessage();
        String password = obj1.nextLine();
        System.out.println("Are you an organizer?"); //***replace with askInput system***
        boolean isOrg = obj1.nextBoolean();
        if(loginSystem.registerUser(username, password, isOrg)) {
            presenter.printRegisterSucceedMessage();
        } else {
            presenter.printRegisterFailMessage();
        }
    }
}
