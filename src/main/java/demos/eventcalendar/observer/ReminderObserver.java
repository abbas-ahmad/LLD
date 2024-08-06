package demos.eventcalendar.observer;

import demos.eventcalendar.model.Event;

public interface ReminderObserver {
    void update(Event event);
}
