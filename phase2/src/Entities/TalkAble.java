package Entities;

import java.util.ArrayList;

public interface TalkAble {
    void addTalk(String eventName);
    void removeTalk(String eventName);
    ArrayList<String> getTalkList();
}
