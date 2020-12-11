package Controller.PromptBuilder;

import Controller.Menus.*;
import Presenter.Presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Prompt {
    protected Presenter presenter;
    private Menu menu;

    public Prompt(Presenter presenter){
        this.presenter = presenter;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public ArrayList<String> getMenuOptions(){
        return menu.getMenuOptions();
    }

    public ArrayList<String> getMenuChoices(){
        return menu.getAllChoices();
    }


    public boolean invalidInput(List<String> choices, String chosen) {
        for(String choice: choices){
            if(choice.equals(chosen)){
                return false;
            }
            /*
            else if(chosen.equals(presenter.getExit())){
                throw new IOException();
            }

             */
        }
        presenter.displayMessages("invalidInput");
        return true;
    }

    public boolean booleanAsk() throws IOException {return false;}
    public int intAsk() throws IOException {return 0;}
    public String ask() throws IOException {return "";}
}
