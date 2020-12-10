package Controller.Menus;

import java.util.ArrayList;

public class SendMessageSpeakerMenu extends Menu{

    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("Send to a (U)ser");
        options.add("Send to all attendees in one or multiple (E)vents");
        options.add("(R)ecall message");
        options.add("(B)ack");
        return options;
    }

    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("U");
        choices.add("E");
        choices.add("B");
        choices.add("R");
        return choices;
    }
}
