package Controller;

import Entities.Event;
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
        while (running) {
            presenter.welcomeMessage();
            ArrayList<String> options = new ArrayList<>();
            ArrayList<String> choices = new ArrayList<>();
            options.add("(L)ogin"); options.add("(R)egister");
            choices.add("L"); choices.add("R"); choices.add(("E"));
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
                    }
                    else if (username.equals("Incorrect Login!")){
                    break;
                    }else {
                        accountActivity(username);
                        break;
                    }
                case "E": exit();
            }

            if (!running){
                break;
            }
        }
        save();
    }

    private void accountActivity(String username) {
        boolean loggedin = true;
        while (loggedin) {
            Scanner input = new Scanner(System.in);

            ArrayList<String> options = new ArrayList<>();
            ArrayList<String> choices = new ArrayList<>();
            options.add("(M)essages");
            options.add("(E)vents");
            choices.add("M");
            choices.add("E");
            choices.add(("X"));
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
            }

            if (chosen.equals("X")){
                loggedin = false;
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
            choices.add("M");
            choices.add("E");
            choices.add(("X"));
            String chosen = askInput(options, choices, input);


            //String chosen = input.nextLine();
            switch (chosen) {
                case "M":
                    System.out.println("MessageSystem");

                    break;
                case "E":
                    System.out.println("SignUpSystem");
                    break;
            }

            if (chosen.equals("X")){
                messaging = false;
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
            options.add("(V)iew all events");
            options.add("(S)ign up for events");
            options.add("(D)rop out of events");
            choices.add("V");
            choices.add("S");
            choices.add("D");
            choices.add(("X"));
            String chosen = askInput(options, choices, input);

            switch (chosen) {
                case "V":
                    System.out.println("View all events");
                    signUpSystem.viewAllEvents(eventManager);
                    break;
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
            }

            if (chosen.equals("X")){
                activity = false;
            }
        }
    }

    public void exit(){
        running = false;
    }
    public boolean invalidInput(List<String> choices, String chosen) {
        for(String choice: choices){
            if(choice.equals(chosen.toUpperCase())){
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
           chosen = input.nextLine();
        }while(invalidInput(choices, chosen));
        return chosen;
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
    public String login(){
        System.out.println("Login");
        LoginSystem loginSystem = new LoginSystem(attendeeManager);
        Scanner obj1 = new Scanner(System.in);
        System.out.println("Username");
        String username = obj1.nextLine();
        System.out.println("Password");
        String password = obj1.nextLine();
        if (loginSystem.canLogin(username, password)){
            System.out.println("Logged in: " + username);
            return username;
        }
        return "Incorrect Login!";
    }
    public void registerUser(){
        System.out.println("Register User");
        LoginSystem loginSystem = new LoginSystem(attendeeManager);
        Scanner obj1 = new Scanner(System.in);
        System.out.println("Username");
        String username = obj1.nextLine();
        System.out.println("Password");
        String password = obj1.nextLine();
        System.out.println("Are you an organizer?");
        boolean isOrg = obj1.nextBoolean();
        loginSystem.registerUser(username, password, isOrg);
    }
}
