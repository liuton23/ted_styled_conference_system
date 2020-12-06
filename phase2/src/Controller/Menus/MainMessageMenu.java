package Controller.Menus;

import java.util.ArrayList;

public class MainMessageMenu extends Menu{

    public ArrayList<String> getMenuOptions(){
        ArrayList<String> options = new ArrayList<>();
        options.add("(M)essage users");
        options.add("(V)iew messages");
        options.add("(B)ack");
        return options;
    }

    public ArrayList<String> getAllChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("M");
        choices.add("V");
        choices.add("B");
        return choices;
    }
}
