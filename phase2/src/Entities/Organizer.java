package Entities;

import java.util.ArrayList;

public class Organizer extends User{
    private ArrayList<String> eventOrganized;

    public Organizer(String username, String password) {
        super(username, password);
        this.eventOrganized = new ArrayList<String>();
    }

}
