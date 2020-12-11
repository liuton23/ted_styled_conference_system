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
            case eventsOrgMenu:
                StringPrompt eventsOrgPrompt = new StringPrompt(presenter);
                eventsOrgPrompt.setMenu(new EventsOrgMenu());
                return eventsOrgPrompt;
            case intPrompt://require manual setting of display message
                return new IntPrompt(presenter);
            case intDayPrompt:
                IntPrompt dayPrompt = new IntDayPrompt(presenter);
                dayPrompt.setText("Enter day of month (1-31):");
                return dayPrompt;
            case intHourPrompt:
                IntHourPrompt hrPrompt = new IntHourPrompt(presenter);
                hrPrompt.setText("Enter hour of day (0-23):");
                return hrPrompt;
            case intMinutePrompt:
                IntMinutePrompt minPrompt = new IntMinutePrompt(presenter);
                minPrompt.setText("Enter minute of hour (0-59):");
                return minPrompt;
            case intAtLeastOnePrompt://require manual setting of display message
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
