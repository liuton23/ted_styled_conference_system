package Entities.UserFactory;

import java.util.ArrayList;

public interface OrganizeAble extends Account{
    void addEvent(String event);
    void removeEvent(String event);
    ArrayList<String> getEventOrganized();
}
