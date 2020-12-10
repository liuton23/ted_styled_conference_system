package Controller;

//import Controller.Registration.RegistrationPortal;
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
import java.util.List;

/**
 * Manages user input.
 */
public class Controller {

    private Gateway gateway = new Gateway("save.ser");
    private ScheduleDownloader scheduleDownloader = new ScheduleDownloader(new EventManager());
    private UserManager userManager = new UserManager();
    private EventManager eventManager = new EventManager();
    private MessageManager messageManager = new MessageManager();
    private RoomManager roomManager = new RoomManager();

    public Presenter presenter = new Presenter();

    /**
     * Starts the program. Login menu.
     */
    public void run() {
        init();
        presenter.welcomeMessage();
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
                        loginSystem.registerUsers(userManager);
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
        //boolean canAttend = userManager.checkIsAttendee(user);
        boolean isOrg = userManager.checkIsOrganizer(user);
        //boolean isSpeaker = userManager.checkIsSpeaker(user);
        //boolean hasVipAccess = userManager.hasVIPAccess(user);
        while (loggedin) {
            try {
                String chosen;
                PromptBuilder promptBuilder = new PromptBuilder();
                if (isOrg){
                    Prompt prompt = promptBuilder.buildPrompt(presenter, PromptType.basicMenu2);
                    chosen = prompt.ask();

                }else{
                    Prompt prompt = promptBuilder.buildPrompt(presenter, PromptType.basicMenu1);
                    chosen = prompt.ask();
                }

                switch (chosen) {
                    case "U"://Change account email
                        presenter.display("Please enter new account email.");
                        Scanner scanner = new Scanner(System.in);
                        String newEmail = scanner.nextLine();
                        if (newEmail.toUpperCase().equals(presenter.getExit())){
                            throw new IOException();
                        }
                        userManager.setUserEmail(username, newEmail);
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
                    case "C"://Create accounts
                        loginSystem.createAccounts();
                        break;
                    case "D"://Download PDF of conference schedule
                        scheduleDownloader = new ScheduleDownloader(eventManager);
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

    private void resetPassword(){
        Scanner input = new Scanner(System.in);
        String username = "";
        boolean isUser = true;
        do {
            isUser = true;
            presenter.printUsernameMessage();
            username = input.nextLine();
            if(!userManager.usernameToUserObject(username).isPresent()){
                isUser = false;
                presenter.invalidInput();
            }
        }while(!isUser);

        User user = userManager.usernameToUserObject(username).get();

        if(user.getEmail().isEmpty()){
            System.out.println("No email with this account.");
            return;
        }
        String code = gateway.passwordReset(username);
        String userCode;
        boolean correct;
        do{
            correct = true;
            presenter.displayMessages("requestCode");
            System.out.println("Enter the code that has been sent to your email.");
            userCode = input.nextLine();
            if(userCode.toUpperCase().equals("EXIT")){
                exit();
            }else if(userCode.toUpperCase().equals("B")){
                return;
            }else if(!code.equals(userCode)){
                correct = false;
                presenter.invalidInput();
            }
        }while(!correct);
        String password;
        presenter.printPasswordMessage();
        password = input.nextLine();
        userManager.setAttendeePassword(username, password);
    }

    /**
     * Makes sure user enters an int as input
     * @return the int the user entered
     */
    /*
    public int getIntInput() {
        Scanner input = new Scanner(System.in);
        boolean done = false;
        int in = 0;
        do {
            try {
                in = Integer.parseInt(input.nextLine());
                done = true;
            } catch (NumberFormatException e) {
                presenter.printInvalidIntMessage();
            }
        } while (!done);
        return in;
    }

     */


    /**
     * Exits the program after saving.
     */
    public void exit(){
        save();
        System.exit(0);
    }

    /**
     * Asks the user yes or no and receives input.
     * @return  true if the user inputs Y/YES/T/True and false if the user inputs N/NO/F/FALSE.
     */
    /*
    public boolean askBooleanInput(){
        Scanner input = new Scanner(System.in);
        ArrayList<String> choices = new ArrayList<>();
        choices.addAll(presenter.chooseMenuOptions(14));
        choices.addAll(presenter.chooseMenuOptions(15));
        String chosen;
        do{
            presenter.display("Yes or No?");
            chosen = input.next().toUpperCase();
        }while(invalidInput(choices, chosen));
        return presenter.chooseMenuOptions(14).contains(chosen);
    }

     */

    /**
     * Checks if <code>chosen</code> is a valid input in <code>choices</code>. If input is "EXIT", <code>exit</code>
     * will be called.
     * @param choices list of choices that are valid. Must be uppercase.
     * @param chosen input the user entered.
     * @return true iff <code>chosen</code> is an invalid input. False if it is valid.
     */
    /*
    public boolean invalidInput(List<String> choices, String chosen) {
        for(String choice: choices){
            if(choice.equals(chosen)){
                return false;
            }else if(chosen.equals(presenter.getExit())){
                exit();
            }
        }
        presenter.invalidInput();
        return true;
    }

     */

    /**
     * Asks the user for input and checks its validity.
     * @param i the menu id.
     * @return Uppercase String that is valid input from user.
     */
    /*
    public String askMenuInput(int i){
        Scanner input = new Scanner(System.in);
        ArrayList<String> choices = presenter.chooseMenuOptions(i);
        String chosen;
        do{
           chooseMenuPrompt(i);
           chosen = input.nextLine().toUpperCase();
        }while(invalidInput(choices, chosen));
        return chosen;
    }

     */



    /**
     * Displays a menu given <code>menu_id</code>.
     * @param menu_id determines which menu is needed.
     */
    /*
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
                presenter.sendMessageMenuAtt();
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
            case 10:
                presenter.sendOrgMessageOrg();
                break;
            case 11:
                presenter.sendMessageMenuSpeaker();
                break;
            case 12:
                presenter.wishToSendMoreEvent();
                break;
            case 17:
                presenter.viewEventTypeMenu();
                break;
            case 16:
                presenter.userTypeMenu();
                break;
        }
    }

     */

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
            // TODO: add new presenters?
            presenter = new Presenter();
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
