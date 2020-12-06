package Controller.PromptBuilder;

import Controller.Menus.BooleanMenu;
import Presenter.Presenter;

public class PromptBuilder {

    public Prompt buildPrompt(Presenter presenter, PromptType type){
        switch (type){
            case booleanPrompt:
                BooleanPrompt booleanPrompt = new BooleanPrompt(presenter);
                booleanPrompt.setMenu(new BooleanMenu());
                booleanPrompt.setText("Yes or No?");
                return booleanPrompt;
        }
        return null;
    }
}
