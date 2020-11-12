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

    public boolean book(String eventName, LocalDateTime start, LocalDateTime end)
    {
        for(LocalDateTime[] l : bookings) {
            if (checkConflict(start, end, l[0], l[1])) {
                return false;
            }
        }
        LocalDateTime[] dateTime = {start, end};
        this.bookings.add(dateTime);
        this.eventNames.add(eventName);
        return true;
    }

    private boolean checkConflict(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        if (start1.isAfter(start2) && start1.isBefore(end2)) {
            return true;
        } else if (end1.isAfter(start2) && end1.isBefore(end2)) {
            return true;
        } else if (start1.equals(start2)) {
            return true;
        } else if (end1.equals(end2)) {
            return true;
        } else {
            return false;
        }
    }

    public void unbook(String eventName)
    {
        int i = eventNames.indexOf(eventName);
        bookings.remove(i);
        eventNames.remove(i);
    }
}
