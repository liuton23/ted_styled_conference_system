package Entities;

import java.util.ArrayList;

public class Attendee {
    private String username;
    private String password;
    private String name;
    private boolean isOrganizer = false;
    private ArrayList<String> itinerary;

    public Attendee(String name, String username, String password){
        this.username = username;
        this.password = password;
        this.itinerary = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void addEvent(String event){
        itinerary.add(event);
    }

    public void removeEvent(String event){
        itinerary.remove(event);
    }

    public ArrayList<String> getItinerary(){
        itinerary.sort(String::compareToIgnoreCase);
        return itinerary;
    }

    /*
    public static void main(String[] args) {
        Attendee a = new Attendee("Bill Nye", "billy", "science");
        Speaker s = new Speaker("Steve", "Steve1", "Hawking");
        a.addEvent("9:00");
        a.addEvent("8:00");
        System.out.println(a.getItinerary());
        a.removeEvent("9:00");
        System.out.println(a.getItinerary());
        s.addEvent("9:00");
        s.addEvent("8:00");
        System.out.println(s.getItinerary());
        s.removeEvent("9:00");
        System.out.println(s.getItinerary());
    }

     */
}
