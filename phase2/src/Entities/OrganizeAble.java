package Entities;

import java.util.ArrayList;

public interface OrganizeAble {
    void addEvent(String event);
    void removeEvent(String event);
    ArrayList<String> getEventOrganized();
}
