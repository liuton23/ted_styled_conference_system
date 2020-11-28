package Entities;

import Entities.UserFactory.*;

import java.io.Serializable;
import java.util.ArrayList;

public class VIP extends Speaker implements Serializable, AttendAble, TalkAble, VIPAccess{
    private ArrayList<String> itinerary;
    private ArrayList<String> VIPEvents;

    public VIP(String username, String password){
        super(username, password);
        this.itinerary = new ArrayList<>();
        this.VIPEvents = new ArrayList<>();
    }

    public void addEvent(String event){
        itinerary.add(event);
    }

    public void removeEvent(String event){
        itinerary.remove(event);
    }

    public ArrayList<String> getItinerary(){
        return itinerary;
    }

    public void addVIPEvent(String vipEvent){
        VIPEvents.add(vipEvent);
    }

    public void removeVIPEvent(String vipEvent){
        VIPEvents.remove(vipEvent);
    }

    public ArrayList<String> getVIPEvents(){
        return VIPEvents;
    }
}
