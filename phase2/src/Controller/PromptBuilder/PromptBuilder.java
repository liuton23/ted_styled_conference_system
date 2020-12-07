package Controller.PromptBuilder;

import Controller.Menus.*;
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
            case eventsMenu:
                StringPrompt eventsPrompt = new StringPrompt(presenter);
                eventsPrompt.setMenu(new EventsMenu());
                return eventsPrompt;
            case loginPrompt:
                StringPrompt loginPrompt = new StringPrompt(presenter);
                loginPrompt.setMenu(new LoginMenu());
                return loginPrompt;
            case organizerMenu:
                StringPrompt organizePrompt = new StringPrompt(presenter);
                organizePrompt.setMenu(new OrganizerMenu());
                return organizePrompt;
            case userTypeMenu:
                StringPrompt userTypes = new StringPrompt(presenter);
                userTypes.setMenu(new UserTypeMenu());
                return userTypes;
            case viewEventsMenu:
                StringPrompt eventPrompt = new StringPrompt(presenter);
                eventPrompt.setMenu(new ViewEventsMenu());
                return eventPrompt;
            case viewEventTypeMenu:
                StringPrompt eventTypes = new StringPrompt(presenter);
                eventTypes.setMenu(new ViewEventTypeMenu());
                return eventTypes;
            case viewMonthMenu:
                StringPrompt monthPrompt = new StringPrompt(presenter);
                monthPrompt.setMenu(new ViewMonthsMenu());
                return monthPrompt;
        }
        return null;
    }
}
