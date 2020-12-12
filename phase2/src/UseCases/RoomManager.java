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
     * Takes in an id value and returns the corresponding room. Assume room in system
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
     * Method returns the capacity of the provided room.
     * @param room the room whose capacity is being returned.
     * @return the capacity of the room.
     */
    public int getCapacity(Room room){
        return room.getCapacity();
    }

    /**
     * Takes in an id value and returns whether or not the room is in the system
     * @param id the id of the room to check
     * @return true if the room is in the system, false if not
     */
    public boolean checkRoomInSystem(int id) {
        for (Room r : rooms) {
            if (r.getId() == id) {
                return true;
            }
        }
        return false;
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
     * Removes a booking for an event from a room. Assumes event is booked in room.
     * @param room the room to remove a booking from
     * @param eventName the name of the event to remove the booking for
     */
    public void unbook(Room room, String eventName)
    {
        room.removeBooking(eventName);
    }

}
