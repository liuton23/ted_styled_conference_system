package Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Room {
    private int id;
    private int capacity;
    private ArrayList<LocalDateTime[]> bookings;
    private ArrayList<String> eventNames;

    public Room(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        bookings = new ArrayList<LocalDateTime[]>();
        eventNames = new ArrayList<String>();
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getCapacity(){
        return capacity;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public ArrayList<LocalDateTime[]> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<LocalDateTime[]> bookings) {
        this.bookings = bookings;
    }

    public ArrayList<String> getEventNames() {
        return eventNames;
    }

    public void setEventNames(ArrayList<String> eventNames) {
        this.eventNames = eventNames;
    }
}
