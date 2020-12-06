package Controller.PromptBuilder;

import Controller.Menus.*;
import Presenter.Presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Prompt {
    protected Presenter presenter;
    private Menu menu;
    private String text;

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

    public void setText(String text) {
        this.text = text;
    }

    public String getText(){
        return text;
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
        presenter.invalidInput();
        return true;
    }

    public boolean booleanAsk(){return false;}
    public String ask(){return "";}
}
