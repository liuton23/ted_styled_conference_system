package Controller.Menus;

import java.util.ArrayList;

public class SendMessageOrganizerMenu extends Menu{

    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("Send to a (U)ser");
        options.add("Send to all (S)peakers");
        options.add("Send to all (A)ttendees");
        options.add("(B)ack");
        return options;
    }

    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("U");
        choices.add("S");
        choices.add("A");
        choices.add("B");
        return choices;
    }
}
