package UseCases;

import Entities.Room;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RoomManager implements Serializable {
    private ArrayList<Room> rooms;

    public RoomManager() {
        rooms = new ArrayList<Room>();
    }

    public boolean addRoom(int id, int capacity) {
        for (Room r : rooms){
            if (r.getId() == id) {
                return false;
            }
        }
        Room room = new Room(id, capacity);
        rooms.add(room);
        return true;
    }

    public Room idToRoom(int id) {
        Room room = new Room(0,0);
        for (Room r : rooms) {
            if (r.getId() == id) {
                room = r;
                break;
            }
        }
        return room;
    }

    public boolean checkIfRoomAvailable(Room room, LocalDateTime start, LocalDateTime end) {
        //get old bookings ArrayLists
        ArrayList<LocalDateTime[]> bookings = room.getBookings();
        //check no conflicts
        for(LocalDateTime[] l : bookings) {
            if (checkConflict(start, end, l[0], l[1])) {
                return false;
            }
        }
        return true;
    }

    public void book(Room room, String eventName, LocalDateTime start, LocalDateTime end)
    {
        //get old bookings and eventNames ArrayLists
        ArrayList<LocalDateTime[]> bookings = room.getBookings();
        ArrayList<String> eventNames = room.getEventNames();
        //add booking to room
        LocalDateTime[] dateTime = {start, end};
        bookings.add(dateTime);
        eventNames.add(eventName);
        room.setBookings(bookings);
        room.setEventNames(eventNames);
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
        } else if (start1.isBefore(start2) && end1.isAfter(end2)){
            return true;
        } else {
            return false;
        }
    }

    public void unbook(Room room, String eventName)
    {
        //get old bookings and eventNames ArrayLists
        ArrayList<LocalDateTime[]> bookings = room.getBookings();
        ArrayList<String> eventNames = room.getEventNames();
        //remove the event name it's booking
        int i = eventNames.indexOf(eventName);
        bookings.remove(i);
        eventNames.remove(i);
        room.setBookings(bookings);
        room.setEventNames(eventNames);
    }

    //testing
    public static void main(String[] args) {
        RoomManager r = new RoomManager();
        System.out.println(r.addRoom(0, 0)); //true
        System.out.println(r.addRoom(1, 0)); //true
        System.out.println(r.addRoom(0, 1)); //false
        Room one = r.idToRoom(0);
        Room two = r.idToRoom(1);
        LocalDateTime start1 = LocalDateTime.of(2020, 11, 11, 11, 0);
        LocalDateTime end1 = LocalDateTime.of(2020, 11, 11, 12, 0);
        LocalDateTime start2 = LocalDateTime.of(2020, 11, 11, 13, 0);
        LocalDateTime end2 = LocalDateTime.of(2020, 11, 11, 14, 0);
        LocalDateTime start3 = LocalDateTime.of(2020, 11, 11, 12, 0);
        LocalDateTime end3 = LocalDateTime.of(2020, 11, 11, 13, 0);
        LocalDateTime start4 = LocalDateTime.of(2020, 11, 11, 10, 0);
        LocalDateTime end4 = LocalDateTime.of(2020, 11, 11, 13, 0);
        System.out.println(r.checkIfRoomAvailable(one, start1, end1)); //true
        r.book(one, "party1", start1, end1);
        System.out.println(r.checkIfRoomAvailable(one, start1, end1)); //false
        //r.book(one,"partya", start1, end1);
        System.out.println(r.checkIfRoomAvailable(one, start4, end4)); //false
        //r.book(one,"partyb", start4, end4);
        System.out.println(r.checkIfRoomAvailable(one, start2, end2)); //true
        r.book(one, "party3", start2, end2);
        System.out.println(r.checkIfRoomAvailable(one, start3, end3)); //true
        r.book(one,"party4", start3, end3);
        System.out.println(r.checkIfRoomAvailable(two, start1, end1)); //true
        r.book(two, "party5", start1, end1);
        r.unbook(one, "party1");
        System.out.println(r.checkIfRoomAvailable(one, start1, end1)); //true
        r.book(one,"party6", start1, end1);
        System.out.println(r.checkIfRoomAvailable(one, start1, end1)); //false
    }
}
