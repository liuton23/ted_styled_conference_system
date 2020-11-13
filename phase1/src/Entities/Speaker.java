package Entities;

import java.util.ArrayList;

public class Speaker extends Attendee{

    private ArrayList<String> talkList;

    public Speaker(String name, String username, String password){
        super(name, username, password);
        this.talkList = new ArrayList<String>();
    }

    public void addTalk(String eventName){
        this.talkList.add(eventName);
    }
    public void removeTalk(String eventName){
        this.talkList.remove(eventName);
    }
    public ArrayList<String> getTalkList(){
        return this.talkList;
    }
}

