package Controller.PromptBuilder;

import Controller.Menus.*;
import Presenter.Presenter;

/**
 * Builds required prompts
 */
public class PromptBuilder {
    /**
     *Builds a prompt for various activities depending on input.
     * @param presenter Presenter that will display messages.
     * @param type specifies what type of prompt to produce.
     * @return prompts for user input.
     */
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
                return booleanPrompt;
            case eventsMenu:
                StringPrompt eventsPrompt = new StringPrompt(presenter);
                eventsPrompt.setMenu(new EventsMenu());
                return eventsPrompt;
            case eventsOrgMenu:
                StringPrompt eventsOrgPrompt = new StringPrompt(presenter);
                eventsOrgPrompt.setMenu(new EventsOrgMenu());
                return eventsOrgPrompt;
            case intPrompt:
                return new IntPrompt(presenter);
            case intDayPrompt:
                return new IntDayPrompt(presenter);
            case intHourPrompt:
                return new IntHourPrompt(presenter);
            case intMinutePrompt:
                return new IntMinutePrompt(presenter);
            case intAtLeastOnePrompt:
                return new IntAtLeastOnePrompt(presenter);
            case loginPrompt:
                StringPrompt loginPrompt = new StringPrompt(presenter);
                loginPrompt.setMenu(new LoginMenu());
                return loginPrompt;
            case mainMessageMenu:
                StringPrompt mainMessaging = new StringPrompt(presenter);
                mainMessaging.setMenu(new MainMessageMenu());
                return mainMessaging;
            case organizerMenu:
                StringPrompt organizePrompt = new StringPrompt(presenter);
                organizePrompt.setMenu(new OrganizerMenu());
                return organizePrompt;
            case sendMessageAttendeeMenu:
                StringPrompt attendeeMessage = new StringPrompt(presenter);
                attendeeMessage.setMenu(new SendMessageAttendeeMenu());
                return attendeeMessage;
            case sendMessageOrganizerMenu:
                StringPrompt organizerMessage = new StringPrompt(presenter);
                organizerMessage.setMenu(new SendMessageOrganizerMenu());
                return organizerMessage;
            case sendMessageSpeakerMenu:
                StringPrompt speakerMessage = new StringPrompt(presenter);
                speakerMessage.setMenu(new SendMessageSpeakerMenu());
                return speakerMessage;
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
            case viewMessageMenu:
                StringPrompt viewMessages = new StringPrompt(presenter);
                viewMessages.setMenu(new ViewMessagesMenu());
                return viewMessages;
            case viewMonthMenu:
                StringPrompt monthPrompt = new StringPrompt(presenter);
                monthPrompt.setMenu(new ViewMonthsMenu());
                return monthPrompt;
            case wishToSendMoreEventMenu:
                StringPrompt messageMoreEvents = new StringPrompt(presenter);
                messageMoreEvents.setMenu(new WishToSendMoreEventMenu());
                return messageMoreEvents;
        }
        return null;
    }
}
