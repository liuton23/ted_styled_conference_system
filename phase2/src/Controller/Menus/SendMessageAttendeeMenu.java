package Controller.Menus;

import java.util.ArrayList;

public class SendMessageAttendeeMenu extends Menu{

    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("Send to a (U)ser");
        options.add("(B)ack");
        return options;
    }

    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("U");
        choices.add("B");
        return choices;
    }
}
