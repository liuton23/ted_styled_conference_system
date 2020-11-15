package Controller;

import Entities.Event;
import UseCases.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

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
     * Starts the program.
     */
    public void run() {
        init();
        Scanner scanner = new Scanner(System.in);
        presenter.welcomeMessage();
        boolean running = true;
        while (running) {
            ArrayList<String> options = new ArrayList<>();
            ArrayList<String> choices = new ArrayList<>();
            options.add("(L)ogin"); options.add("(R)egister");
            choices.add("L"); choices.add("R"); choices.add(("EXIT"));
            String chosen = askInput(options, choices, scanner);

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
                case "EXIT":
                    running = false;
                    exit();
            }
        }
    }

    private void accountActivity(String username) {
        boolean loggedin = true;
        while (loggedin) {
            Scanner input = new Scanner(System.in);

            ArrayList<String> options = new ArrayList<>();
            ArrayList<String> choices = new ArrayList<>();
            options.add("Go to (M)essages");
            options.add("Go to (E)vents");
            options.add("Go (B)ack");
            choices.add("M");
            choices.add("E");
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
                case "B":
                    loggedin = false;
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
        SignUpSystem signUpSystem = new SignUpSystem();
        boolean activity = true;
        while (activity) {
            Scanner input = new Scanner(System.in);

            ArrayList<String> options = new ArrayList<>();
            ArrayList<String> choices = new ArrayList<>();
            options.add("(S)ign up for events");
            options.add("(D)rop out of events");
            options.add("(V)iew all events");
            options.add("Go (B)ack");
            choices.add("V");
            choices.add("S");
            choices.add("D");
            choices.add(("B"));
            choices.add("EXIT");
            String chosen = askInput(options, choices, input);

            switch (chosen) {
                case "S":
                    System.out.println("Sign Up");
                    int index = input.nextInt();
                    signUpSystem.signUpEvent(attendeeManager, eventManager, username, index);
                    break;
                case "D":
                    System.out.println("Drop out");
                    int index1 = input.nextInt();
                    signUpSystem.dropOutEvent(attendeeManager, eventManager, username, index1);
                    break;
                case "V":
                    System.out.println("View all events");
                    presenter.displayAllEvents(signUpSystem.viewAllEvents(eventManager));
                    break;
                case "B":
                    activity = false;
                    break;
                case "EXIT": exit();
            }
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
     * @param scanner scanner that is used to receive the input.
     * @return Uppercase String that is valid input from user.
     */
    public String askInput(ArrayList<String> options, ArrayList<String> choices, Scanner scanner){
        String chosen;
        do{
           presenter.prompt(options);
           chosen = scanner.nextLine().toUpperCase();
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
