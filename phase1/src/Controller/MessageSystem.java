package Controller;

import Entities.Attendee;
import Entities.Speaker;
import UseCases.AttendeeManager;
import UseCases.MessageManager;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;

public class MessageSystem {

    public MessageSystem(){
    }
    /*

    public String messageAttendee(MessageManager mm, AttendeeManager am, String sender,
                                  String attendee, String text){
        Optional<Attendee> recipient = am.usernameToAttendeeObject(sender);
        if (recipient.isOrganizer()){
            return "The message cannot be sent to an Organizer.";
        } else {
            mm.createMessage(sender, attendee, text);
            return "The message has been successfully sent.";
        }
    }

    public String messageAllSpeakers(MessageManager mm, AttendeeManager am, String sender,
                                     String text){
        Optional<Attendee> org = am.usernameToAttendeeObject(sender);
        ArrayList<Speaker> listOfSpeakers = am.getSpeakers();

        if (!org.isOrganizer()){
            return "Only Organizer can message all speakers.";
        } else {
            mm.CreateMessage()
        }
    }
    */


}
