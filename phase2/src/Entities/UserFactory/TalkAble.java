package Entities.UserFactory;

import java.util.ArrayList;

/**
 * Identifies a user with speaker capabilities.
 */
public interface TalkAble extends Account{
    void addTalk(String eventName);
    void removeTalk(String eventName);
    ArrayList<String> getTalkList();
}
