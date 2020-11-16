package Entities;

import java.util.ArrayList;

public class Speaker extends Attendee{

    private ArrayList<String> talkList;

    /**
     * Constructor for an instance of Speaker
     * @param username the attendee account login username.
     * @param password the attendee account login password.
     */
    public Speaker(String username, String password){
        super(username, password);
        this.talkList = new ArrayList<String>();
    }

    /**
     * Method that adds an event that the speaker will speak at.
     * @param eventName Name of the upcoming event that the speaker will speak at.
     */
    public void addTalk(String eventName){
        this.talkList.add(eventName);
    }

    /**
     * Method that removes an event from the list of events that the speaker will speak at.
     * @param eventName Name of the upcoming event that the speaker will not speak at.
     */
    public void removeTalk(String eventName){
        this.talkList.remove(eventName);
    }

    /**
     * Method that returns a list of all upcoming events that the speaker will speak at.
     * @return List of events that speaker will speak at.
     */
    public ArrayList<String> getTalkList(){
        return this.talkList;
    }
}

