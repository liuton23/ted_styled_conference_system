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

    //testing
    public static void main(String[] args) {
        RoomManager r = new RoomManager();
        System.out.println(r.addRoom(0, 0));
        System.out.println(r.addRoom(1, 0));
        System.out.println(r.addRoom(0, 1));
        Room one = r.idToRoom(0);
        Room two = r.idToRoom(1);
        LocalDateTime start1 = LocalDateTime.of(2020, 11, 11, 11, 0);
        LocalDateTime end1 = LocalDateTime.of(2020, 11, 11, 12, 0);
        LocalDateTime start2 = LocalDateTime.of(2020, 11, 11, 13, 0);
        LocalDateTime end2 = LocalDateTime.of(2020, 11, 11, 14, 0);
        LocalDateTime start3 = LocalDateTime.of(2020, 11, 11, 12, 0);
        LocalDateTime end3 = LocalDateTime.of(2020, 11, 11, 13, 0);
        System.out.println(one.book("party1", start1, end1));
        System.out.println(one.book("party2", start1, end1));
        System.out.println(one.book("party3", start2, end2));
        System.out.println(one.book("party4", start3, end3));
        System.out.println(two.book("party5", start1, end1));
        one.unbook("party1");
        System.out.println(one.book("party6", start1, end1));
    }
}
