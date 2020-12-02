package UseCases;

import Entities.*;
import Entities.Event;
import Entities.UserFactory.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Manages and stores attendees and speakers in this tech conference system.
 */
public class UserManager implements Serializable {

    ArrayList<User> masterList;
    ArrayList<AttendAble> attendeeList;
    ArrayList<TalkAble> speakerList;
    ArrayList<OrganizeAble> organizerList;

    /**
     * Constructor for an instance of AttendeeManager.
     */
    public UserManager(){
        masterList = new ArrayList<>();
        attendeeList = new ArrayList<>();
        speakerList = new ArrayList<>();
        organizerList = new ArrayList<>();
    }

    /**
     * Method that creates an instance of Attendee and stores it in an instance of AttendeeManager
     * @param username the new attendee account login username.
     * @param password the new attendee account login password.
     * @param type type of the user
     * @return the new attendee instance.
     */
    public User createAttendee(String username, String password, UserType type){
        UserFactory userFactory = new UserFactory();
        User newUser = userFactory.createAccount(username, password, type);
        masterList.add(newUser);
        switch (type){
            case ATTENDEE:
                attendeeList.add((AttendAble) newUser);
                break;
            case ORGANIZER:
                organizerList.add((OrganizeAble) newUser);
                break;
            case SPEAKER:
                speakerList.add((TalkAble) newUser);
                break;
            case VIP:
                speakerList.add((TalkAble) newUser);
                attendeeList.add((AttendAble) newUser);
                break;
        }
        return newUser;
    }

    /**
     * Method that creates an instance of Attendee and stores it in an instance of AttendeeManager
     * @param username the new speaker account login username.
     * @param password the new speaker account login username.
     * @return the new speaker instance.
     */
    /*
    public Speaker createSpeaker(String username, String password){
        Speaker sp = new Speaker(username, password);
        speakerList.add(sp);
        return sp;
    }

     */

    /**
     * This method returns a boolean whether the given username belongs to a speaker in the system.
     * @param username the username to be checked.
     * @return a boolean reflecting if the username belongs to a speaker in the system.
     */
    public boolean registeredSpeaker(String username){
        for (TalkAble speaker: this.getAllSpeakers()) {
            if(speaker.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * Method that checks if a given username and password matches an account stored in an instance of
     * AttendeeManager.
     * @param username Username of user account.
     * @param password Password of user account.
     * @return true iff an attendee/speaker account matches the username and password.
     */
    public boolean inSystem(String username, String password){
        /*
        ArrayList<User> combinedlist = new ArrayList<>();
        combinedlist.addAll(getAllAttendees());
        combinedlist.addAll(getAllSpeakers());
        combinedlist.addAll(getAllOrganizers());

         */
        for (User a: masterList){
            if (username.equals(a.getUsername()) && password.equals(a.getPassword())){
                return true;
            }
        }
        return false;
    }

    public ArrayList<OrganizeAble> getAllOrganizers(){
        return organizerList;
    }

    /**
     * Method that returns the list of stored attendees.
     * @return list of all stored attendees.
     */
    public ArrayList<AttendAble> getAllAttendees(){
        return attendeeList;
    }

    /**
     * Method that returns the list of stored speakers.
     * @return list of all stored attendees.
     */
    public ArrayList<TalkAble> getAllSpeakers() {
        return speakerList;
    }

    /**
     * Add talk to talk list
     * @param speaker speaker
     * @param event event name
     */
    public void addEventToSpeakerList(TalkAble speaker, String event){
        speaker.addTalk(event);
    }

    /**
     * remove talk from speaker list
     * @param speaker speaker
     * @param event event name
     */
    public void removeEventFromSpeakerList(TalkAble speaker, String event){
        speaker.removeTalk(event);
    }


    /**
     * Method that returns the itinerary of an attendee.
     * @param attendee a stored attendee.
     * @return list of events that the attendee will attend.
     */
    public ArrayList<String> getItinerary(AttendAble attendee){
        return attendee.getItinerary();
    }

    /**
     * Method that returns the speaking list of an speaker.
     * @param speaker a stored attendee.
     * @return list of events that the speaker will speak at.
     */
    public ArrayList<String> getSpeakingList(TalkAble speaker) {
        return speaker.getTalkList();
    }

    /**
     * Method that replaces the current speak at an event with another speaker.
     * @param title Name of the upcoming event.
     * @param newSpeakerUsername username of the new speaker.
     */
    public void changeSpeaker(String title, String newSpeakerUsername, String oldSpeakerUsername){
        for (TalkAble speaker: speakerList) {
            if (speaker.getUsername().equals(oldSpeakerUsername)) {
                speaker.removeTalk(title);
            }
            if (speaker.getUsername().equals(newSpeakerUsername)) {
                speaker.addTalk(title);
            }
        }
    }

    /**
     * @param user attendee object
     * @return true of this attendee is organizer
     */
    public Boolean checkIsOrganizer(Account user){
        return user instanceof OrganizeAble;
    }

    public Boolean checkIsAttendee(Account user) {
        return user instanceof AttendAble;
    }

    public Boolean checkIsSpeaker(Account user) {
        return user instanceof AttendAble;
    }

    public Boolean hasVIPAccess(Account user){
        return user instanceof VIPAccess;
    }

    public String getUsername(Account user) {
        return user.getUsername();
    }

    /**
     * Method that allows an attendee to add an event to his/her itinerary.
     * @param attendee a stored attendee.
     * @param event name of an upcoming event.
     */
    public void signUp(AttendAble attendee, String event){
        attendee.addEvent(event);
    }

    /**
     * Method that allows an attendee to remove an event from his/here itinerary.
     * @param attendee a stored attendee.
     * @param event name of an upcoming event.
     */
    public void dropOut(AttendAble attendee, String event){
        attendee.removeEvent(event);
    }

    // check this later Optionals are funky
    /**
     * Method that takes an user username and returns a stored Attendee instance iff one exists.
     * @param username username of an attendee.
     * @return instance of Attendee iff one exists.
     */
    public Optional<User> usernameToUserObject(String username){
        for (User user: masterList){
            if (username.equals(user.getUsername())){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    // check this later Optionals are funky
    /**
     * Method that takes an user username and returns a stored Attendee instance iff one exists.
     * @param username username of an attendee.
     * @return instance of Attendee iff one exists.
     */
    public Optional<AttendAble> usernameToAttendeeObject(String username){
        for (AttendAble user: attendeeList){
            if (username.equals(user.getUsername())){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    /**
     * Method that returns a list of attendees that attend an event.
     * @param event instance of Event that some attendees will attend.
     * @return list of attendees who will attend the event.
     */
    public ArrayList<AttendAble> eventToAttendees(Event event){
        ArrayList<AttendAble> list = new ArrayList<>();
        String title = event.getTitle();
        for (AttendAble a: attendeeList){
            if (a.getItinerary().contains(title)){
                list.add(a);
            }
        }
        return list;
    }

    /*

    public static void main(String[] args) {
        AttendeeManager a = new AttendeeManager();
        Attendee attendee = a.createAttendee("Bill Nye", "bill", "science");
        a.add(attendee);
        a.addAttendeeEvent(attendee, "CSC207");
        System.out.println(a.inSystem("Bill", "Nye"));
        System.out.println(a.inSystem("Bill", "James"));
        System.out.println(a.inSystem("Steve", "Hawking"));
        System.out.println(a.inSystem("bill", "science"));
        System.out.println(a.inSystem("Bill", "science"));
    }

     */


}
