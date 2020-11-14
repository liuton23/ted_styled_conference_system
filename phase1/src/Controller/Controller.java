package Controller;

import Entities.Event;
import UseCases.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Controller {

    private static boolean running = true;
    private static Gateway gateway;
    private static AttendeeManager attendeeManager;
    private static EventManager eventManager;
    private static MessageManager messageManager;
    private static RoomManager roomManager;

    private static Presenter presenter;

    public static void main(String[] args){
        run();
    }

    public static void run() {
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

            switch (chosen){
                case "R": registerUser(); break;
                case "L": username = login(); break;
                case "E":
                    running = false;
            }

            if (!running){
                break;
            }
        }
        save();
    }
    public static void exit(){
        running = false;
    }
    public static boolean invalidInput(List<String> choices, String chosen) {
        for(String choice: choices){
            if(choice.equals(chosen.toUpperCase())){
                return false;
            }
        }
        presenter.invalidInput();
        return true;
    }

    public static String askInput(ArrayList<String> options, ArrayList<String> choices, Scanner input){
        String chosen;
        do{
           presenter.prompt(options);
           chosen = input.next();
        }while(invalidInput(choices, chosen));
        return chosen;
    }

    public static void save() {
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

    public static void init(){
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
    public static String login(){
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
    public static void registerUser(){
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
