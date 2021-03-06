package Controller;

import Controller.PromptBuilder.Prompt;
import Controller.PromptBuilder.PromptBuilder;
import Controller.PromptBuilder.PromptType;
import Entities.*;
import Gateway.Gateway;
import Gateway.ScheduleDownloader;
import Presenter.*;
import UseCases.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Manages user input.
 */
public class Controller {

    private Gateway gateway = new Gateway("save.ser");
    private UserManager userManager = new UserManager();
    private EventManager eventManager = new EventManager();
    private EmailSystem emailSystem;
    private MessageManager messageManager = new MessageManager();
    private RoomManager roomManager = new RoomManager();

    public Presenter presenter = new Presenter();

    /**
     * Starts the program. Login menu.
     */
    public void run() {
        init();
        presenter.welcomeMessage();
        init();
        boolean running = true;
        PromptBuilder promptBuilder = new PromptBuilder();
        try {
            Prompt prompt = promptBuilder.buildPrompt(presenter, PromptType.loginPrompt);
            while (running) {
                String chosen = prompt.ask();

                String username;
                LoginSystem loginSystem = new LoginSystem(userManager);

                switch (chosen) {
                    case "R":
                        loginSystem.registerUsers();
                        break;
                    case "L":
                        username = loginSystem.login();
                        if (!username.isEmpty()) {
                            accountActivity(username, loginSystem);
                        }
                        break;
                    case "P":
                        resetPassword();
                        break;
                    case "EXIT": //only used to prevent infinite loop
                        running = false;
                        exit();
                }
            }
        } catch (IOException e){
            exit();
        }
    }

    /**
     * Basic menu that differs for organizers and other attendees.
     * @param username <code>Attendee</code>'s username as <code>String</code>. Username must correspond to an
     *                 <code>Attendee</code> or an error will occur.
     */
    private void accountActivity(String username, LoginSystem loginSystem) {
        boolean loggedin = true;
        User user = userManager.usernameToUserObject(username).get();
        boolean isOrg = userManager.checkIsOrganizer(user);
        while (loggedin) {
            try {
                String chosen;
                PromptBuilder promptBuilder = new PromptBuilder();
                Prompt prompt;
                if (isOrg){
                    prompt = promptBuilder.buildPrompt(presenter, PromptType.basicMenu2);
                }else{
                    prompt = promptBuilder.buildPrompt(presenter, PromptType.basicMenu1);
                }
                chosen = prompt.ask();

                switch (chosen) {
                    case "U"://Change account email
                        presenter.display("You current email is: " + userManager.getUserEmail(username));
                        emailSystem.addEmail(username);
                        break;
                    case "M"://Messaging
                        MessageSystem messageSystem = new MessageSystem(messageManager,userManager,eventManager);
                        messageSystem.messageActivity(username);
                        break;
                    case "E"://View events
                        SignUpSystem signUpSystem = new SignUpSystem(userManager, eventManager, roomManager);
                        signUpSystem.eventActivity(username);
                        break;
                    case "I"://View Itineraries
                        Itinerary itinerary = new Itinerary(userManager,presenter);
                        itinerary.getItinerary(username);
                        break;
                    case "S"://Schedule activities
                        ScheduleSystem scheduleSystem  = new ScheduleSystem(eventManager,userManager, roomManager);
                        scheduleSystem.scheduleActivity();
                        break;
                    case "C"://Cancelling events
                        ScheduleSystem scheduleSystem1  = new ScheduleSystem(eventManager,userManager, roomManager);
                        presenter.display("Enter name of event that should be cancelled:");
                        Scanner input = new Scanner(System.in);
                        String name = input.nextLine();
                        presenter.cancelEventMessage(scheduleSystem1.cancelEvent(name));
                        break;
                    case "A"://Create accounts
                        loginSystem.createAccounts();
                        break;
                    case "D"://Download PDF of conference schedule
                        ScheduleDownloader scheduleDownloader = new ScheduleDownloader(eventManager);
                        scheduleDownloader.generatePDF();
                        break;
                    case "B":
                        loggedin = false;
                        break;
                }
            } catch (IOException e){
                exit();
            }
        }
    }
    /**
     * Can reset the users password iff they enter a valid username and there is an email associated with that user.
     */
    private void resetPassword() throws IOException{
        Scanner input = new Scanner(System.in);
        String username;
        boolean isUser;
        do {
            isUser = true;
            presenter.printUsernameMessage(1);
            username = input.nextLine();
            if (username.toUpperCase().equals(presenter.getExit())){
                throw new IOException();
            }
            if (username.equalsIgnoreCase("BACK") || username.equalsIgnoreCase("B")){
                return;
            }
            if(!userManager.usernameToUserObject(username).isPresent()){
                isUser = false;
                presenter.displayMessages("invalidInput");
            }
        }while(!isUser);

        User user = userManager.usernameToUserObject(username).get();

        if(user.getEmail().isEmpty()){
            System.out.println("No email with this account.");
            return;
        }
        String code = emailSystem.passwordReset(username);
        String userCode;
        boolean correct;
        do{
            correct = true;
            presenter.displayMessages("requestCode");
            System.out.println("Enter the code that has been sent to your email.");
            userCode = input.nextLine();
            if(userCode.equalsIgnoreCase("EXIT")){
                exit();
            }else if(userCode.equalsIgnoreCase("B")){
                return;
            }else if(!code.equals(userCode)){
                correct = false;
                presenter.displayMessages("invalidInput");
            }
        }while(!correct);
        String password;
        presenter.printPasswordMessage(2);
        password = input.nextLine();
        userManager.setAttendeePassword(username, password);
    }

    /**
     * Exits the program after saving.
     */
    public void exit(){
        save();
        System.exit(0);
    }


    /**
     * Initializes the use case classes. If the program has not been run before, or the save file has been corrupted,
     * moved, or missing, then new use case classes will be instantiated. Otherwise, the use case classes will be loaded
     * from the save file.
     */
    public void init(){
        gateway = new Gateway("save.ser");
        ArrayList<Serializable> listOfObj;
        try {
            listOfObj = gateway.readFromFile(4);
            userManager = (UserManager) listOfObj.get(0);
            eventManager = (EventManager) listOfObj.get(1);
            messageManager = (MessageManager) listOfObj.get(2);
            roomManager = (RoomManager) listOfObj.get(3);
        } catch (IOException e) {
            presenter.printNoSaveFile();
            userManager = new UserManager();
            eventManager = new EventManager();
            messageManager = new MessageManager();
            roomManager = new RoomManager();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            presenter = new Presenter();
            emailSystem = new EmailSystem(gateway, userManager);
        }
    }

    /**
     * Saves the use case classes and all their fields in a file.
     */
    public void save() {
        ArrayList<Serializable> listOfObj = new ArrayList<>();
        listOfObj.add(userManager);
        listOfObj.add(eventManager);
        listOfObj.add(messageManager);
        listOfObj.add(roomManager);
        try {
            gateway.writeToFile(listOfObj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
