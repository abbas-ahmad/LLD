package demos.eventcalendar;

import demos.eventcalendar.model.Event;
import demos.eventcalendar.model.User;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    List<Event> events;

    public EventManager() {
        events = new ArrayList<>();
    }

    public void createEvent(){

    }

    public void inviteAttendee(Event event, User user){
        event.getAttendees().add(user);
    }

    public List<Event> getEvents() {
        return events;
    }
}
