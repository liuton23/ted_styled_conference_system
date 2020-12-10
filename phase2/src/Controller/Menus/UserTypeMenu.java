package Controller.Menus;

import java.util.ArrayList;

public class UserTypeMenu extends Menu{

    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(A)ttendee account");
        options.add("(O)rganizer account");
        options.add("(S)peaker account");
        options.add("(V)IP account");
        return options;
    }

    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("A");//Attendee
        choices.add("O");//Organizer
        choices.add("S");//Speaker
        choices.add("V");//VIP
        return choices;
    }
}
