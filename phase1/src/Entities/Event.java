package Entities;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Event implements Serializable {
    private String speaker;
    private String title;
    // two item ArrayList
    private ArrayList<LocalDateTime> eventTime;
    private int room;
    private int capacity;
    private ArrayList<String> attendeeList;

    // Event with one hour length
    // month should be in all caps
    public Event(String title, String speaker, int year, String month, int day, int hour, int minute, int room, int capacity){
        LocalTime startTime = LocalTime.of(hour, minute);
        LocalTime endTime = startTime.plusHours(1);
        this.title = title;
        this.speaker = speaker;
        this.eventTime = new ArrayList<>();
        this.eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, hour, minute, 0));
        this.eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute, 0));
        this.room = room;
        this.capacity = capacity;
        this.attendeeList = new ArrayList<String>();
    }

    // getters
    public String getTitle() {
        return title;
    }

    public String getSpeaker() {
        return speaker;
    }

    public ArrayList<LocalDateTime> getEventTime(){
        return eventTime;
    }

    public int getRoom(){
        return room;
    }

    public int getCapacity(){
        return capacity;
        }

    public ArrayList<String> getAttendeeList(){ return attendeeList;}

    // setters
    public void setSpeaker(String newSpeaker){
        this.speaker = newSpeaker;
    }

    // this method is for events of a 1 hour duration
    public void setEventTime(int year, String month, int day, int hour, int minute){
        LocalTime startTime = LocalTime.of(hour, minute);
        LocalTime endTime = startTime.plusHours(1);
        this.eventTime.remove(1);
        this.eventTime.remove(0);
        //add check to make sure 0
        this.eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, hour, minute));
        this.eventTime.add(LocalDateTime.of(year, Month.valueOf(month), day, endTime.getHour(), minute));
    }

    public void setRoom(int newRoom){
        this.room = newRoom;
    }

    public void setCapacity(int newCapacity){
        this.capacity = newCapacity;
    }

    public void addAttendee(String attendee){
        this.attendeeList.add(attendee);
    }

    public void removeAttendee(String attendee){
        this.attendeeList.remove(attendee);
    }
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");

        return String.format(this.title + " runs from " + this.eventTime.get(0).format(formatter) + " to " +
                this.eventTime.get(1).format(formatter) + " in room: " + this.getRoom() + " with speaker: " +
                this.getSpeaker() + ".");
    }

    //testing
    public static void main(String[] args) {
        Event petConference = new Event("Pet Conference", "Caesar Milan", 2020, "NOVEMBER",
                16, 12, 0, 100, 500);
        System.out.println(petConference.getTitle());
        System.out.println(petConference.getSpeaker());
        System.out.println(petConference.getEventTime());
        System.out.println(petConference.getRoom());
        System.out.println(petConference.getCapacity());
        petConference.setEventTime(2020, "DECEMBER", 22, 12, 0);
        System.out.println(petConference.getEventTime());
        System.out.println(petConference.getAttendeeList());
        petConference.addAttendee("Heidi");
        petConference.addAttendee("Maya");
        System.out.println(petConference.getAttendeeList());
        petConference.removeAttendee("Iva");
        petConference.removeAttendee("Heidi");
        System.out.println(petConference.getAttendeeList());
        System.out.println(petConference);
    }
}