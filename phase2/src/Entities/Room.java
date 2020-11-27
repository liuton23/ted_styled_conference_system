package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents a room in the system that can be booked for an event
 */
public class Room implements Iterable<LocalDateTime[]>, Serializable {
    private int id;
    private int capacity;
    private ArrayList<LocalDateTime[]> bookings;
    private ArrayList<String> eventNames;

    /**
     * Constructs an instance of Room
     * @param id the id of the room. The id of each room is unique.
     * @param capacity the maximum amount of people that can be in the room at a given time
     */
    public Room(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        bookings = new ArrayList<LocalDateTime[]>();
        eventNames = new ArrayList<String>();
    }

    /**
     * Getter for the id of the room
     * @return the int id of the room
     */
    public int getId(){
        return id;
    }

    /**
     * Getter for the capacity of the room.
     * @return capacity of the room.
     */
    public int getCapacity(){
        return capacity;
    }

    /**
     * Setter for the capacity of the room.
     * @param capacity updated room capacity.
     */
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    /**
     * Adds a booking to the room
     * @param eventName the name of the event to book
     * @param booking the start and end time of the event
     */
    public void addBooking(String eventName, LocalDateTime[] booking) {
        bookings.add(booking);
        eventNames.add(eventName);
    }

    /**
     * Removes a booking from a room. Assumes the event is booked in room.
     * @param eventName the name of the event to remove the booking for
     */
    public void removeBooking(String eventName) {
        int index = eventNames.indexOf(eventName);
        bookings.remove(index);
        eventNames.remove(index);
    }

    /**
     * Returns an iterator for this room.
     * @return an iterator for this room.
     */
    @Override
    public Iterator<LocalDateTime[]> iterator() {
        return new RoomIterator();
    }

    /**
     * An Iterator for the Room bookings.
     */
    private class RoomIterator implements Iterator<LocalDateTime[]> {

        /** The index of the next booking to return. */
        private int current = 0;

        /**
         * Returns whether there is another booking to return.
         * @return whether there is another booking to return.
         */
        @Override
        public boolean hasNext() {
            return current < bookings.size();
        }

        /**
         * Returns the next booking.
         * @return the next booking.
         */
        @Override
        public LocalDateTime[] next() {
            LocalDateTime[] res;

            try {
                res = bookings.get(current);
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
            current += 1;
            return res;
        }

        /**
         * Removes the booking just returned.  Unsupported.
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported.");
        }
    }
}
