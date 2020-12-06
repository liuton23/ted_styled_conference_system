package Controller.PromptBuilder;

import Presenter.Presenter;

import java.util.ArrayList;
import java.util.Scanner;

public class BooleanPrompt extends Prompt {

    public BooleanPrompt(Presenter presenter){
        super(presenter);
    }

    public boolean booleanAsk(){
        Scanner input = new Scanner(System.in);
        ArrayList<String> choices = getMenuChoices();
        ArrayList<String> options = getMenuOptions();
        String chosen;
        do{
            super.presenter.display(getText());
            chosen = input.next().toUpperCase();
        }while(invalidInput(choices, chosen));
        return options.contains(chosen);
    }
}
