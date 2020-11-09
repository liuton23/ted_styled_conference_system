package Entities;

public class Speaker extends Attendee{

    public Speaker(String name, String username, String password){
        super(name, username, password);
        super.makeSpeaker();
    }
}
