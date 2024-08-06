package demos.eventcalendar.state;

import demos.eventcalendar.model.Event;

public interface EventState {
    void handleEvent(Event event);
}
