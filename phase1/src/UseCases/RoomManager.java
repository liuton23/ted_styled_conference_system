package UseCases;

import Entities.Room;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Manages and stores the rooms in the tech conference system
 */
public class RoomManager implements Serializable {
    private ArrayList<Room> rooms;

    /**
     * Constructs a new instance of RoomManager with an empty list of rooms
     */
    public RoomManager() {
        rooms = new ArrayList<Room>();
    }

    /**
     * Creates and adds a room to the list of rooms
     * @param id the id of the new room (id is unique)
     * @param capacity the capacity of the new room
     * @return true if room was added (id was available) or false if it was not
     */
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

    /**
     * Takes in an id value and returns the corresponding room
     * @param id the id of the room to return
     * @return the Room object
     */
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

    /**
     * Checks if a room can be booked at a given time
     * @param room the room to check
     * @param start the start time of the booking
     * @param end the end time of the booking
     * @return true if the room can be booked (start and end times don't conflict with an existing
     * booking or false if it cannot be booked
     */
    public boolean checkIfRoomAvailable(Room room, LocalDateTime start, LocalDateTime end) {
        //check no conflicts
        for(LocalDateTime[] booking : room) {
            if (checkConflict(start, end, booking)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Books a room for a given event and time
     * @param room the room to book
     * @param eventName the name of the event
     * @param start the start time of the booking
     * @param end the end time of the booking
     */
    public void book(Room room, String eventName, LocalDateTime start, LocalDateTime end)
    {
        //add booking to room
        LocalDateTime[] booking = {start, end};
        room.addBooking(eventName, booking);
    }

    /**
     * Checks if there is any overlap between start and end and a given booking
     * @param start the start time
     * @param end the end time
     * @param booking the booking to check
     * @return true if there is a conflict or false if there is not
     */
    private boolean checkConflict(LocalDateTime start, LocalDateTime end, LocalDateTime[] booking) {
        LocalDateTime bookingStart = booking[0]; //start time of booking
        LocalDateTime bookingEnd = booking[1];  //end time of booking

        //check no overlap
        if (start.isAfter(bookingStart) && start.isBefore(bookingEnd)) {
            return true;
        } else if (end.isAfter(bookingStart) && end.isBefore(bookingEnd)) {
            return true;
        } else if (start.equals(bookingStart)) {
            return true;
        } else if (end.equals(bookingEnd)) {
            return true;
        } else if (start.isBefore(bookingStart) && end.isAfter(bookingEnd)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a booking for an event from a room
     * @param room the room to remove a booking from
     * @param eventName the name of the event to remove the booking for
     */
    public void unbook(Room room, String eventName)
    {
        room.removeBooking(eventName);
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
