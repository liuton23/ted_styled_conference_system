package Controller.PromptBuilder;

import Controller.Menus.BasicMenu1;
import Controller.Menus.BasicMenu2;
import Controller.Menus.BooleanMenu;
import Controller.Menus.LoginMenu;
import Presenter.Presenter;

public class PromptBuilder {

    public Prompt buildPrompt(Presenter presenter, PromptType type){
        switch (type){
            case basicMenu1:
                StringPrompt basicPrompt1 = new StringPrompt(presenter);
                basicPrompt1.setMenu(new BasicMenu1());
                return basicPrompt1;
            case basicMenu2:
                StringPrompt basicPrompt2 = new StringPrompt(presenter);
                basicPrompt2.setMenu(new BasicMenu2());
                return basicPrompt2;
            case booleanPrompt:
                BooleanPrompt booleanPrompt = new BooleanPrompt(presenter);
                booleanPrompt.setMenu(new BooleanMenu());
                booleanPrompt.setText("Yes or No?");
                return booleanPrompt;
            case loginPrompt:
                StringPrompt loginPrompt = new StringPrompt(presenter);
                loginPrompt.setMenu(new LoginMenu());
                return loginPrompt;
        }
        return null;
    }
}
